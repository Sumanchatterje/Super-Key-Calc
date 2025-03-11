package com.example.super_keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SuperKeysApplication implements CommandLineRunner {

	@Value("${server.address:localhost}")
	private String serverAddress;

	@Value("${server.port:8080}")
	private int serverPort;

	public static void main(String[] args) {
		SpringApplication.run(SuperKeysApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Print the server's address and port
		System.out.println("Spring Boot application is running at:");
		System.out.println("http://" + serverAddress + ":" + serverPort);
	}
}
