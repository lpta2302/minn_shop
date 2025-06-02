package com.thienan.file_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
@EnableJpaAuditing
public class FileServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileServiceApplication.class, args);
	}
}
