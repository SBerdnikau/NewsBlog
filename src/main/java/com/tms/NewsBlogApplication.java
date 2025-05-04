package com.tms;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@OpenAPIDefinition(info = @Info(
        title = "Application News Blog",
        description = "This application describes the work of the news blog",
        contact = @Contact(
                name = "Sergey Berdnikov",
                email = "berdnikausiarhei@gmail.com"
        )
))
@SpringBootApplication
public class NewsBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewsBlogApplication.class, args);

    }

}
