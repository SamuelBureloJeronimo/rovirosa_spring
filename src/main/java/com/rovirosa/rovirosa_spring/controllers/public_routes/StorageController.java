package com.rovirosa.rovirosa_spring.controllers.public_routes;

import java.io.IOException;
import java.nio.file.Files;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rovirosa.rovirosa_spring.services.StorageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.core.io.Resource;

@RestController
@RequestMapping("api/storage")
public class StorageController {

    @Autowired
    private StorageService storageService;

    @GetMapping("/marcas/{filename}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) throws IOException {
        Resource file = storageService.loadAsResource(filename, "marcas/");
        String contentType = Files.probeContentType(file.getFile().toPath());

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .body(file);
    }

    @GetMapping("/ine/{filename}")
    public ResponseEntity<Resource> getIne(@PathVariable String filename) throws IOException {
        Resource file = storageService.loadAsResource(filename, "ine/");
        String contentType = Files.probeContentType(file.getFile().toPath());

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .body(file);
    }

    @GetMapping("/productos/{filename}")
    public ResponseEntity<Resource> getProducto(@PathVariable String filename) throws IOException {
        Resource file = storageService.loadAsResource(filename, "productos/");
        String contentType = Files.probeContentType(file.getFile().toPath());

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .body(file);
    }

    @GetMapping("/descuentos/{filename}")
    public ResponseEntity<Resource> getDescuentos(@PathVariable String filename) throws IOException {
        Resource file = storageService.loadAsResource(filename, "descuentos/");
        String contentType = Files.probeContentType(file.getFile().toPath());

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .body(file);
    }

    @GetMapping("/chat_ventas/{carpeta}/{filename}")
    public ResponseEntity<Resource> getChatImages(@PathVariable String carpeta, @PathVariable String filename) throws IOException {
        Resource file = storageService.loadAsResource(filename, "chat_ventas/" + carpeta + "/");
        String contentType = Files.probeContentType(file.getFile().toPath());

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .body(file);
    }

    @GetMapping("/comprobantes/{filename}")
    public ResponseEntity<Resource> getComprobante(@PathVariable String filename) throws IOException {
        Resource file = storageService.loadAsResource(filename, "comprobantes/");
        String contentType = Files.probeContentType(file.getFile().toPath());

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .body(file);
    }

    @GetMapping("/{filename}")
    public ResponseEntity<Resource> getBrandLogo(@PathVariable String filename) throws IOException {
        Resource file = storageService.loadAsResource(filename, "");
        String contentType = Files.probeContentType(file.getFile().toPath());

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .body(file);
    }

}