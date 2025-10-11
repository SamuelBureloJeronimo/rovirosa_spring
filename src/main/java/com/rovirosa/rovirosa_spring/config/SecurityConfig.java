package com.rovirosa.rovirosa_spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.rovirosa.rovirosa_spring.utils.JwtFilter;

@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**", "/api/public/**", "/api/ocr/**").permitAll()  // rutas públicas
                        
                        .requestMatchers("/api/usuario/**").hasAnyRole("CLIENTE","ADMIN","GERENTE","REPARTIDOR") // Todos los usuarios autenticados
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")  // solo ADMIN
                        .requestMatchers("/api/cliente/**").hasRole("CLIENTE") // solo CLIENTE
                        .requestMatchers("/api/repartidor/**").hasRole("REPARTIDOR") // solo REPARTIDOR
                        .requestMatchers("/api/gerente/**").hasRole("GERENTE") // solo GERENTE
                        .anyRequest().authenticated()             // lo demás requiere JWT
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
