package com.group10.paperlessexamwebservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class PaperlessexamwebserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaperlessexamwebserviceApplication.class, args);
    }

}
