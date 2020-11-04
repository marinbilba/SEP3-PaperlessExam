package com.group10.paperlessexamwebservice.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface SecurityService {
    PasswordEncoder encoder();
}
