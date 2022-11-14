# Desafio API
[![NPM](https://img.shields.io/npm/l/react)](https://github.com/breneroliveira/spring-api/blob/master/LICENSE)

## Descrição
Implementação de uma REST API para um sistema de clínica veterinária utilizando o framework Spring. Segue as principais funcionalidades da aplicação:
- CRUD de todas as classes;
- Validações nos atributos das classes;
- Integração com banco de dados MySQL;
- Segurança no acesso da aplicação;
- Documentação de endpoints pelo Swagger 2.0;
- Consumo de <a href="https://www.thedogapi.com/">API externa</a>.

## Configuração
O projeto foi desenvolvido com as seguintes tecnologias:
- Java 17
- Spring Boot 2.7.0
- Maven 4.0.0
- JUnit 5
- Eclipse IDE for Enterprise Java and Web Developers 4.23.0

## Diagrama de classes
![UML - 26 07](https://user-images.githubusercontent.com/73718076/201680027-28abc606-3021-4d4a-9640-0d58a698cd83.png)

## Instalação e execução
Para deixar o projeto pronto para a execução, os seguintes passos devem ser seguidos:
- Baixar o projeto pelo GitLab e descompatá-lo em sua máquina ou cloná-lo;
- Importar o projeto para a sua IDE Java;
- Atualizar as dependências contidas no pom.xml;
- Possuir o banco de dados MySQL instalado em sua máquina;
- Inserir o seu usuário e senha do banco de dados pelo arquivo `application.properties` nos seguintes campos:
    - `spring.datasource.username=root`
    - `spring.datasource.password=root`
- Ter acesso a internet;
- Após esses passos, o projeto pode ser executado;
- Depois da execução, os endpoints podem ser acessados através do [Swagger](http://localhost:8080/swagger-ui.html).

## Endpoints
### Autenticação

| Método    | URL                             | Perfil(is) autorizado(s)  |
| --------- | ------------------------------- | ------------------------ |
| POST      | http://localhost:8080/v1/auth   | Público                  |

***

### Cliente

| Método    | URL                                | Perfil(is) autorizado(s)  |
| --------- | ---------------------------------- | ------------------------ |
| POST      | http://localhost:8080/v1/clientes  | ADMIN                    |
| GET       | http://localhost:8080/v1/clientes  | ADMIN, USER              |
| GET       | http://localhost:8080/v1/clientes/{id}  | ADMIN, USER         |
| PUT       | http://localhost:8080/v1/clientes/{id}  | ADMIN               |
| DELETE    | http://localhost:8080/v1/clientes/{id}  | ADMIN               |

***

### Cachorro

| Método    | URL                                | Perfil(is) autorizado(s)  |
| --------- | ---------------------------------- | ------------------------ |
| POST      | http://localhost:8080/v1/cachorros  | ADMIN                    |
| GET       | http://localhost:8080/v1/cachorros  | ADMIN, USER              |
| GET       | http://localhost:8080/v1/cachorros/{id}  | ADMIN, USER         |
| PUT       | http://localhost:8080/v1/cachorros/{id}  | ADMIN               |
| DELETE    | http://localhost:8080/v1/cachorros/{id}  | ADMIN               |

***

### Médico

| Método    | URL                                | Perfil(is) autorizado(s)  |
| --------- | ---------------------------------- | ------------------------ |
| POST      | http://localhost:8080/v1/medicos  | ADMIN                    |
| GET       | http://localhost:8080/v1/medicos  | ADMIN, USER              |
| GET       | http://localhost:8080/v1/medicos/{id}  | ADMIN, USER         |
| PUT       | http://localhost:8080/v1/medicos/{id}  | ADMIN               |
| DELETE    | http://localhost:8080/v1/medicos/{id}  | ADMIN               |

***

### Raça

| Método    | URL                                | Perfil(is) autorizado(s)  |
| --------- | ---------------------------------- | ------------------------ |
| POST      | http://localhost:8080/v1/racas  | ADMIN                    |
| GET       | http://localhost:8080/v1/racas  | ADMIN, USER              |
| GET       | http://localhost:8080/v1/racas  | ADMIN, USER              |
| GET       | http://localhost:8080/v1/racas/dog-api | ADMIN, USER         |
| GET       | http://localhost:8080/v1/racas/dog-api/{nomeRaca} | ADMIN, USER         |
| GET       | http://localhost:8080/v1/racas/dog-api/imagens | ADMIN, USER         |
| PUT       | http://localhost:8080/v1/racas/{id}  | ADMIN               |
| DELETE    | http://localhost:8080/v1/racas/{id}  | ADMIN               |

***

### Atendimento

| Método    | URL                                | Perfil(is) autorizado(s)  |
| --------- | ---------------------------------- | ------------------------ |
| POST      | http://localhost:8080/v1/atendimentos  | ADMIN                    |
| GET       | http://localhost:8080/v1/atendimentos  | ADMIN, USER              |
| GET       | http://localhost:8080/v1/atendimentos/{id}  | ADMIN, USER         |

A documentação completa dos endpoints está disponível através do [Swagger](http://localhost:8080/swagger-ui.html).

## Testes unitários
Testes unitários foram implementados com o JUnit 5:
- Para executar todos os testes, basta clicar com o botão direito no seu projeto, posicionar o mouse na opção <i>Run As</i> e clicar em <i>JUnit Test</i>;
- Após a execução, o resultado dos testes aparecerá na guia do JUnit em sua IDE.

## Suporte
Se você possui todos os requisitos, seguiu todas as instruções de como executar a aplicação e mesmo assim está com problemas, pode mandar um email por <a href="mailto:brener.oliveira@gft.com? subject=subject text">aqui</a> constatando o erro.

## Autor
<a href="https://www.linkedin.com/in/brener-augusto-de-oliveira/" target="_blank">Brener Augusto de Oliveira</a>
