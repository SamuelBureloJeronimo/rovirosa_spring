package com.rovirosa.rovirosa_spring.config;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import jakarta.annotation.PostConstruct;

@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void init() {
        // intenta cargar desde classpath
        try (InputStream serviceAccount = getClass().getClassLoader()
                .getResourceAsStream("firebase/firebase-key.json")) {

            if (serviceAccount == null) {
                // Mensaje claro para logs: recurso no encontrado en el classpath
                throw new IllegalStateException("No se encontró 'firebase/firebase-key.json' en el classpath. " +
                        "Verifica que esté en src/main/resources/firebase y que haya sido empacado en el JAR.");
            }

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                System.out.println("🔥 Firebase inicializado correctamente");
            }

        } catch (IOException e) {
            // IOException al leer el stream
            e.printStackTrace();
        } catch (RuntimeException e) {
            // Por ejemplo IllegalStateException si el recurso no existe
            e.printStackTrace();
        }
    }
}
