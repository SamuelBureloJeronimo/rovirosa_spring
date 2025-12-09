package com.rovirosa.rovirosa_spring;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RovirosaSpringApplication {

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("America/Mexico_City"));
		SpringApplication.run(RovirosaSpringApplication.class, args);
	}

}
