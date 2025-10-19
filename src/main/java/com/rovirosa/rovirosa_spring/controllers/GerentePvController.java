package com.rovirosa.rovirosa_spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rovirosa.rovirosa_spring.services.GerentePvService;


@RestController
@RequestMapping("/api/gerente-pv")
public class GerentePvController {

    @Autowired
    private GerentePvService gerentePvService;
    
    
}
