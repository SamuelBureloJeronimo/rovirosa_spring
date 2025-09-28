package com.rovirosa.rovirosa_spring.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rovirosa.rovirosa_spring.services.StorageService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.core.io.Resource;

@RestController
@RequestMapping("api/v1/storage")
public class StorageController {

    @Autowired
    private StorageService storageService;
    @Autowired
    private HttpServletRequest request;

    @SuppressWarnings("deprecation")
    @PostMapping("/upload")
    public Map<String, String> uploadFile(@RequestParam("file") MultipartFile multipartFile) {

        String path = storageService.store(multipartFile,"");
        String host = request.getRequestURL().toString().replace(request.getRequestURI(), "");
        String url = ServletUriComponentsBuilder
                .fromHttpUrl(host)
                .path("/api/v1/auth/")
                .path(path)
                .toUriString();

        return Map.of("url", url, "filename", path);
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