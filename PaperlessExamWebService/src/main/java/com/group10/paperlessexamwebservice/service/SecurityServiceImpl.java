package com.group10.paperlessexamwebservice.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
@Configuration
public class SecurityServiceImpl implements SecurityService{

    @Bean
    @Override
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
