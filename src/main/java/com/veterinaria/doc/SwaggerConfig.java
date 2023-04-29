package com.veterinaria.doc;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	private Contact contato() {
		return new Contact("Brener Augusto de Oliveira (author)",
							null, 
						   "brener.oliveira@gft.com");
	}
	
	private ApiInfoBuilder informacoesApi() {
		
		ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder();
		
		String textBlock = 
		"""
		   Apontamentos:
		   - Para acessar os endpoints, é necessário gerar o token e inserí-lo pelo botão "Authorize". Os seguintes passos devem ser seguidos:
		   \t - 1º) Acessar o endpoint "autenticacao-controller";
		   
		   \t - 2º) Inserir as credenciais. Para isso, há dois perfis: o de usuário e o de administrador;
		   Administrador: {"email": "admin@gft.com", "senha": "Gft@1234"}
		   Usuário: {"email": "user@gft.com", "senha": "Gft@1234"}
		   O administrador tem acesso a todos os endpoints, já o usuário tem acesso apenas aos métodos GET.
		   \t - 3º) Copiar o token e colar no campo "Value" após clicar no botão "Authorize";
		   \t - 4º) Também é necessário esrever a palavra "Bearer" seguida de um espaço antes de colar o token;
		   \t - 5º) Após esses passos, os endpoints estarão disponíveis para uso.
		   - O histórico de atendimento pode ser acessado através dos endpoints de método GET em "medico-controller" e "cliente-controller";
		   - Algumas entidades possuem o atributo "dataNascimento", campo este que possui um formato específico;
		   \t - Exemplo: "dataNascimento": "2002-10-25";
		   \t - Dessa forma, o padrão é o seguinte: ano-mês-dia.
		   - A entidade Atendimento possui o atributo "dataHora", também é um campo de formato específico;
		   \t - Exemplo: "dataHora": "2002-10-25T15:00";
		   \t - Desse modo, o padrão é o seguinte: ano-mês-dia'T'horas:minutos.
		   - Para facilitar o uso dos endpoints, segue alguns exemplos para métodos de POST ou PUT;
		   \t - Entidade Cliente:
		   {"cpf": "33333333333", "nome": "Beltrano", "telefone": "33333333333", "dataNascimento": "2000-07-23", "endereco": { "logradouro": "Rua A", "numero": "1234", "complemento": "", "cep": "11111111"}}
		   
		   \t - Entidade Cachorro:
		   {"numeroRegistro": "66666", "nome": "Dog 6", "raca": {"id": 1}, "peso": 20.0, "altura": 0.7, "dataNascimento": "2021-08-02", "cliente": {"id": 2}}
		   \t - Entidade Médico:
		   {"crmv": "33334", "nome": "Medico 3", "telefone": "33333333333", "dataNascimento": "1982-07-23", "endereco": { "logradouro": "Rua A", "numero": "1234", "complemento": null, "cep": "11111111"}, "especialidade": { "titulo": "Cardiologista"}}
		   \t - Entidade Raça:
		   {"nome": "Golden Retriever"}
		   \t - Entidade Atendimento:
		   {"ticket": "9871", "medico": {"id": 1}, "cachorro": {"id": 1}, "pesoAtual": 29.0, "alturaAtual": 0.7, "dataHora": "2023-05-20T18:00", "diagnostico": "Saudável", "comentario": null}
		""";
		
		apiInfoBuilder.title("Dog API");
		apiInfoBuilder.description(textBlock);
		apiInfoBuilder.version("1.0");
		apiInfoBuilder.contact(this.contato());
		
		return apiInfoBuilder;
	}

    @Bean
    public Docket detalhesApi() {

        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        String groupName = "Swagger";

        docket
                .apiInfo(this.informacoesApi().build())
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(apiKey()))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.veterinaria.controllers"))
                .paths(PathSelectors.any())
                .build()
                .groupName(groupName)
                .consumes(new HashSet<>(Arrays.asList("application/json")))
                .produces(new HashSet<>(Arrays.asList("application/json")));

        return docket;
    }
	
	private ApiKey apiKey() { 
	    return new ApiKey("JWT", "Authorization", "header"); 
	}
	
	private SecurityContext securityContext() { 
	    return SecurityContext.builder().securityReferences(defaultAuth()).build(); 
	} 

	private List<SecurityReference> defaultAuth() { 
	    AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything"); 
	    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1]; 
	    authorizationScopes[0] = authorizationScope; 
	    return Arrays.asList(new SecurityReference("JWT", authorizationScopes)); 
	}
}