package com.rovirosa.rovirosa_spring.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rovirosa.rovirosa_spring.models.Cliente;
import com.rovirosa.rovirosa_spring.models.Usuario;
import com.rovirosa.rovirosa_spring.services.AuthService;
import com.rovirosa.rovirosa_spring.services.ClienteService;
import com.rovirosa.rovirosa_spring.services.StorageService;
import com.rovirosa.rovirosa_spring.services.UsuarioService;
import com.rovirosa.rovirosa_spring.utils.JwtUtil;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthService authServ;
    @Autowired
    private UsuarioService userServ;
    @Autowired
    private StorageService storageService;
    @Autowired
    private ClienteService clientServ;


    /**
     * Handles GET requests to the "/test" endpoint.
     * 
     * @return a ResponseEntity with HTTP status 202 (Accepted) and no body.
     */
    @GetMapping("/test")
    public ResponseEntity<Void> test() {
        System.out.println("Dispositivo conectado.");
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    /**
     * Handles HTTP GET requests for user login.
     * 
     * @param user     The username provided by the client as a request parameter.
     * @param password The password provided by the client as a request parameter.
     * @return A ResponseEntity containing a JWT token if authentication is
     *         successful,
     *         or a bad request response with an error message if credentials are
     *         invalid.
     */
    @PostMapping("/login")
    public ResponseEntity<HashMap<String, String>> login(@RequestParam String user, @RequestParam String password) {
        Usuario us = authServ.login(user, password);
        HashMap<String, String> res = new HashMap<>();
        if (us != null) {
            if (us.getEstado().equals("suspendido")) {
                res.put("msg", "Tu cuenta ha sido suspendida por mal uso de la aplicación.");
                return ResponseEntity.status(403).body(res);
            }
            res.put("token", jwtUtil.generateToken(us.getCorreo(), us.getRol()));
            res.put("rol", us.getRol());
            return ResponseEntity.status(HttpStatus.OK).body(res);
        }
        res.put("error", "Credenciales inválidas");
        return ResponseEntity.status(401).body(res);
    }

    /**
     * Handles HTTP GET requests to retrieve a file by its filename.
     * Loads the requested file as a {@link Resource} using the storage service,
     * determines its content type, and returns it in the response with the
     * appropriate
     * Content-Type header.
     *
     * @param filename the name of the file to retrieve from storage
     * @return a {@link ResponseEntity} containing the file as a resource and the
     *         correct content type
     * @throws IOException if an I/O error occurs while loading the file or
     *                     determining its content type
     */
    @GetMapping("/{filename}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) throws IOException {
        Resource file = storageService.loadAsResource(filename);
        String contentType = Files.probeContentType(file.getFile().toPath());

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .body(file);
    }

    @PostMapping("/register")
    public ResponseEntity<HashMap<String, String>> registerClient(
            @RequestPart Cliente cliente,
            @RequestPart("ine_front") MultipartFile ineFront,
            @RequestPart("ine_back") MultipartFile ineBack) {
        HashMap<String, String> reponse = new HashMap<>();

        System.out.println(cliente);

        cliente.setIneFront(storageService.store(ineFront));
        cliente.setIneBack(storageService.store(ineBack));

        String token = clientServ.register(cliente);
        reponse.put("token", token);
        return ResponseEntity.status(200).body(reponse);
    }

    @GetMapping("/validate-curp/{curp}")
    public ResponseEntity<HashMap<String, Boolean>> validate_curp(@PathVariable String curp) {
        HashMap<String, Boolean> response = new HashMap<>();
        Boolean exist = userServ.existCurp(curp);
        response.put("existe", exist);
        if (exist) {
            return ResponseEntity.status(200).body(response);
        } else {
            return ResponseEntity.status(400).body(response);
        }
    }

    @GetMapping("/validate-tel/{tel}")
    public ResponseEntity<HashMap<String, Boolean>> validate_tel(@PathVariable String tel) {
        HashMap<String, Boolean> response = new HashMap<>();
        Boolean exist = userServ.existTel(tel);
        response.put("existe", exist);
        if (exist) {
            return ResponseEntity.status(200).body(response);
        } else {
            return ResponseEntity.status(400).body(response);
        }
    }

}
