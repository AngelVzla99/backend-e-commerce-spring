package com.example.springboot.configuration;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("E-commerce documentation of the backend API")
                        .version("0.0.1")
                        .description("Basic e-commerce backend in spring boot")
                        .termsOfService("http://swagger.io.terms/")
                        .license(new License().name("Apaches 2.0").url("https://springdoc.org"))
                );
    }
}
