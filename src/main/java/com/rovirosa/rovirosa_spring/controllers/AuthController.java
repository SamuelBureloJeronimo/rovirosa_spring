package com.rovirosa.rovirosa_spring.controllers;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.rovirosa.rovirosa_spring.services.StorageService;
import com.rovirosa.rovirosa_spring.utils.JwtUtil;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthService authServ;
    @Autowired
    private StorageService storageService;


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
        System.out.println(us);
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

        cliente.setIneFront("ine/"+storageService.store(ineFront,"ine/"));
        cliente.setIneBack("ine/"+storageService.store(ineBack,"ine/"));

        String token = authServ.register(cliente);
        reponse.put("token", token);
        return ResponseEntity.status(200).body(reponse);
    }

    @GetMapping("/validate-curp/{curp}")
    public ResponseEntity<HashMap<String, Boolean>> validate_curp(@PathVariable String curp) {
        HashMap<String, Boolean> response = new HashMap<>();
        Boolean exist = authServ.existCurp(curp);
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
        Boolean exist = authServ.existTel(tel);
        response.put("existe", exist);
        if (exist) {
            return ResponseEntity.status(200).body(response);
        } else {
            return ResponseEntity.status(400).body(response);
        }
    }

}
