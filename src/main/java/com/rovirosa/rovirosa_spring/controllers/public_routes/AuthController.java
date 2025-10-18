package com.rovirosa.rovirosa_spring.controllers.public_routes;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rovirosa.rovirosa_spring.DTOs.ApiResponse;
import com.rovirosa.rovirosa_spring.DTOs.Auth.Login.LoginPostDTO;
import com.rovirosa.rovirosa_spring.DTOs.Auth.Login.LoginResponseDTO;
import com.rovirosa.rovirosa_spring.clases.CodigoVerificacion;
import com.rovirosa.rovirosa_spring.models.Cliente;
import com.rovirosa.rovirosa_spring.services.AuthService;
import com.rovirosa.rovirosa_spring.services.EmailService;
import com.rovirosa.rovirosa_spring.services.StorageService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authServ;
    @Autowired
    private StorageService storageService;
    @Autowired
    private EmailService emailService;

    @GetMapping("/test")
    public ResponseEntity<Void> test() {
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDTO>> login(@Valid @RequestBody LoginPostDTO loginDTO) {
        LoginResponseDTO res = authServ.login(loginDTO);
        if (res == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new ApiResponse<>(false, "Credenciales inválidas", null));
        } else if (res.getEstado().equals("suspendido")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResponse<>(false, "Usuario suspendido por mal uso de la aplicación.", null));
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(true, "Login exitoso", res));
    }

    @GetMapping("/get-coords")
    public ResponseEntity<HashMap<String, List<String>>> getCoords() {
        HashMap<String, List<String>> response = new HashMap<>();
        List<String> direcciones = authServ.getCoords();
        if (direcciones == null) {
            return ResponseEntity.status(404).body(response);
        }
        response.put("coords", direcciones);
        return ResponseEntity.status(200).body(response);
    }

    @PostMapping("/register")
    public ResponseEntity<HashMap<String, String>> registerClient(
            @RequestPart Cliente cliente,
            @RequestPart("ine_front") MultipartFile ineFront,
            @RequestPart("ine_back") MultipartFile ineBack) {
        HashMap<String, String> reponse = new HashMap<>();

        System.out.println(cliente);

        cliente.setIneFront("ine/" + storageService.store(ineFront, "ine/"));
        cliente.setIneBack("ine/" + storageService.store(ineBack, "ine/"));

        String token = authServ.register(cliente);
        reponse.put("token", token);
        return ResponseEntity.status(200).body(reponse);
    }

    private final Map<String, CodigoVerificacion> codigoStorage = new ConcurrentHashMap<>();

    /**
     * Handles POST requests to send an HTML email.
     *
     * @param to        the recipient's email address
     * @param subjet    the subject of the email (note: parameter name may be a
     *                  typo, should be 'subject')
     * @param contenido the HTML content of the email
     * @return a message indicating whether the email was sent successfully or an
     *         error occurred
     */
    @PostMapping("/send-code")
    public ResponseEntity<ApiResponse<String>> enviarCodigo(@RequestParam String to) {
        try {
            // 1. Generar código aleatorio de 6 dígitos
            String codigo = String.format("%06d", new Random().nextInt(999999));
            System.out.println(codigo);

            CodigoVerificacion guardado = codigoStorage.get(to);
            if (guardado == null) {
                System.out.println("No existe, generar uno nuevo.");
                // 2. Guardarlo (ejemplo simple en memoria, puedes usar DB o Redis)
                codigoStorage.put(to, new CodigoVerificacion(codigo, LocalDateTime.now().plusMinutes(5)));
            } else {
                codigoStorage.remove(to);
                codigoStorage.put(to, new CodigoVerificacion(codigo, LocalDateTime.now().plusMinutes(5)));
            }

            // 3. Crear contenido HTML
            String htmlContenido = "Tu código de verificación es: " + codigo + " - Válido por 5 minutos.";

            emailService.sendEmail(to, "Código de verificación", htmlContenido);
            return ResponseEntity.ok().body(
                    new ApiResponse<>(true, "Código enviado exitosamente", to));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(
                    new ApiResponse<>(false, "Error al enviar el código: " + e.getMessage(), null));
        }
    }

    @PostMapping("/verify-code")
    public ResponseEntity<HashMap<String, String>> verificarCodigo(@RequestParam String to,
            @RequestParam String codigo) {
        CodigoVerificacion guardado = codigoStorage.get(to);
        HashMap<String, String> response = new HashMap<>();
        if (guardado == null) {
            response.put("error", "No se ha generado un código para este correo.");
            return ResponseEntity.badRequest().body(response);
        }

        if (guardado.getExpira().isBefore(LocalDateTime.now())) {
            response.put("error", "El código ha expirado.");
            return ResponseEntity.badRequest().body(response);
        }

        if (!guardado.getCodigo().equals(codigo)) {
            response.put("error", "El código ha expirado.");
            return ResponseEntity.badRequest().body(response);
        }

        response.put("success", "Código validado correctamente");
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/validate/{correo}")
    public ResponseEntity<ApiResponse<Boolean>> validate(@PathVariable String correo) {
        
        Boolean userExist = emailService.validate(correo);
        
        if (userExist) {
            return ResponseEntity.status(200).body(
                    new ApiResponse<>(true, "El correo ya está registrado", userExist)
            );
        } else {
            return ResponseEntity.status(200).body(
                    new ApiResponse<>(false, "El correo no está registrado", userExist)
            );
        }
    }

    @GetMapping("/validate-curp/{curp}")
    public ResponseEntity<ApiResponse<Boolean>> validate_curp(@PathVariable String curp) {

        Boolean exist = authServ.existCurp(curp);

        if (exist) {
            return ResponseEntity.status(200).body(
                    new ApiResponse<>(true, "El CURP ya está registrado", exist));
        } else {
            return ResponseEntity.status(200).body(
                    new ApiResponse<>(false, "El CURP no está registrado", exist));
        }
    }

}
