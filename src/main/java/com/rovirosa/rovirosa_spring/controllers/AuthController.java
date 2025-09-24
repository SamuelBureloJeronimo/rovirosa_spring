package com.rovirosa.rovirosa_spring.controllers;

import java.time.LocalDate;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rovirosa.rovirosa_spring.models.Persona;
import com.rovirosa.rovirosa_spring.models.Usuario;
import com.rovirosa.rovirosa_spring.services.AuthService;
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

    /**
     * Handles GET requests to the "/test" endpoint.
     * 
     * @return a ResponseEntity with HTTP status 202 (Accepted) and no body.
     */
    @GetMapping("/test")
    public ResponseEntity<Void> test() {
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    /**
     * Handles HTTP GET requests for user login.
     * 
     * @param user The username provided by the client as a request parameter.
     * @param password The password provided by the client as a request parameter.
     * @return A ResponseEntity containing a JWT token if authentication is successful,
     *         or a bad request response with an error message if credentials are invalid.
     */
    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestParam String user, @RequestParam String password) {
        Usuario us = authServ.login(user, password);
        if(us != null){
            if(us.getEstado().equals("suspendido"))
                return ResponseEntity.status(401).body("Tu cuenta ha sido suspendida por mal uso de la aplicación.");
            return ResponseEntity.ok().body(jwtUtil.generateToken(us.getCorreo(), us.getRol()));
        }
        return ResponseEntity.badRequest().body("Credenciales inválidas");
    }

    /**
     * Handles user registration requests.
     * 
     * Expects user and personal information as request parameters, creates a new {@link Usuario}
     * and associated {@link Persona}, and persists them using the user service.
     *
     * @param curp      The CURP (unique population registry code) of the user.
     * @param tel       The user's telephone number.
     * @param nombre    The user's first name.
     * @param app       The user's paternal surname.
     * @param apm       The user's maternal surname.
     * @param fech_nac  The user's date of birth.
     * @param sexo      The user's gender (as an integer code).
     * @param correo    The user's email address.
     * @param password  The user's password.
     * @return          A {@link ResponseEntity} containing the ID of the newly created user and HTTP status 201 (Created).
     */
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(
        @RequestParam String curp, @RequestParam String tel, @RequestParam String nombre, @RequestParam String app,
        @RequestParam String apm, @RequestParam LocalDate fech_nac, @RequestParam Integer sexo, @RequestParam String correo,
        @RequestParam String password) {
            
        Persona persona = new Persona();
        persona.setCurp(curp);
        persona.setTel(tel);
        persona.setNombre(nombre);
        persona.setApp(app);
        persona.setApm(apm);
        persona.setFechNac(fech_nac);
        persona.setSexo(sexo);

        Usuario user = new Usuario();
        user.setPersona(persona);
        user.setCorreo(correo);
        user.setPassword(password);

        try {
            //Validar si no existe el correo/curp/telefono
            String res = this.userServ.existUser(user);
            if(res.isEmpty()){ // Si no existe entonces procede a crear el usuario
                this.userServ.create(user);
                return ResponseEntity.ok().body(jwtUtil.generateToken(correo, "CLIENTE"));
            } else // Si existe entonces responde con el campo duplicado
                return ResponseEntity.badRequest().body(res);
        } catch (Exception e) {            
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
