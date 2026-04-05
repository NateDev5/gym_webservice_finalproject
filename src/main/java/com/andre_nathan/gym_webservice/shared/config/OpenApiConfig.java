package com.andre_nathan.gym_webservice.shared.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI vrmsOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Gym WebService API")
                        .version("1.0.0")
                        .description("Gym Web Service API")
                        .contact(new Contact().name("Nathan Andre").email("n/a"))
                        .license(new License().name("Apache 2.0")));
    }
}