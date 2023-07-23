package com.veterinaria;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DicionarioApplicationTests {

	@Test
	void contextLoads() {
		
	}
}


/*package com.gft.veterinaria;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.gft.veterinaria.entities.Raca;

@RunWith(SpringRunner.class)
@SpringBootTest
class DesafioApiApplicationTests {

	@Test
	void consumoAPI() {
		RestTemplate template = new RestTemplate();
		
		//https://api.thedogapi.com/v1/breeds
		
		UriComponents uri = UriComponentsBuilder.newInstance()
							.scheme("https")
							.host("api.thedogapi.com")
							.path("v1/breeds")
							//.queryParam("q", "air") // air é o nome da raça
							.build();
		
		ResponseEntity<Raca> entity = template.getForEntity(uri.toUriString(), Raca.class);
		
		System.out.println(entity.getBody().getNome());
	}
}*/