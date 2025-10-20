package com.rovirosa.rovirosa_spring.utils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            if (jwtUtil.validateToken(token)) {

                String username = jwtUtil.getUsernameFromToken(token);

                // 🔹 Extraer el rol desde el token
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(jwtUtil.getSigningKey()) // lo hacemos visible o por método getter
                        .build()
                        .parseClaimsJws(token)
                        .getBody();

                String role = claims.get("role", String.class);

                // 🔹 Crear la autoridad con prefijo ROLE_
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);

                // 🔹 Crear la autenticación con el rol incluido
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(username, null, List.of(authority));

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 🔹 Registrar en el contexto de seguridad
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        chain.doFilter(request, response);
    }
}
