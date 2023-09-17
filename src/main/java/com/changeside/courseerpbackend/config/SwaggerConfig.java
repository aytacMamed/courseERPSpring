package com.changeside.courseerpbackend.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition
@SecurityScheme(
        name = "Authorization",
        scheme = "bearer",
        bearerFormat = "JWT",
        type = SecuritySchemeType.HTTP,
        in = SecuritySchemeIn.HEADER
)
public class SwaggerConfig {

    // todo: fill real data
    @Bean
    public OpenAPI openApi(){
        return new OpenAPI().addSecurityItem(new SecurityRequirement().addList("Authorization")).info(new Info().title("documentation by Aytac")
                .version("0.0.1").description("for course ERP").
                contact(new Contact().email("aytacmammmadli@gmail.com").name("Aytac Mammadli"))
        );
    }

}