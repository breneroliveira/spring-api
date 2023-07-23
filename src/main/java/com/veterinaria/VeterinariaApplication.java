package com.veterinaria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import com.google.common.net.HttpHeaders;

@SpringBootApplication
@Configuration
public class VeterinariaApplication {
	
	@Bean
	public WebClient webClient(WebClient.Builder builder) {
		return builder
			   .baseUrl("https://api.thedogapi.com/v1")
			   .defaultHeader(HttpHeaders.CONTENT_TYPE, 
					   		  MediaType.APPLICATION_JSON_VALUE)
			   .build();
	}

	public static void main(String[] args) {
		SpringApplication.run(VeterinariaApplication.class, args);
	}
}