package com.rovirosa.rovirosa_spring.services.interfaces;

import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IStorage {
    void init() throws IOException;

    String store(MultipartFile file);

    Resource loadAsResource(String filename);

    void delete(String filename);
}