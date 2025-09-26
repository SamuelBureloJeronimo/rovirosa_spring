package com.rovirosa.rovirosa_spring.controllers;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rovirosa.rovirosa_spring.services.StorageService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;


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
        
        String path = storageService.store(multipartFile);
        String host = request.getRequestURL().toString().replace(request.getRequestURI(), "");
        String url = ServletUriComponentsBuilder
                .fromHttpUrl(host)
                .path("/api/v1/auth/")
                .path(path)
                .toUriString();
        
        return Map.of("url", url, "filename", path);   
    }
    
}