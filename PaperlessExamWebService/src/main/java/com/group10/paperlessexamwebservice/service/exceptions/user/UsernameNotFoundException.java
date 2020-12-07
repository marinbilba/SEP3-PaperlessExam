package com.group10.paperlessexamwebservice.service.exceptions.user;

public class UsernameNotFoundException extends Exception{
    public UsernameNotFoundException(String message)
    {
        super(message);
    }
}
