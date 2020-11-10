package com.group10.paperlessexamwebservice.service.exceptions.user;

import java.io.IOException;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalException
{
    @ExceptionHandler(IOException.class)
    public ModelAndView  processIOException(IOException ioe)
    {
        ModelAndView mav = new ModelAndView("exceptionPage");
        mav.addObject("name", ioe.getClass().getSimpleName());
        mav.addObject("message", ioe.getMessage());

        return mav;
    }
    @ExceptionHandler(UsernameNotFoundException.class)
    public ModelAndView  usernameNotFoundException(CustomException ud)
    {
        ModelAndView mav = new ModelAndView("exceptionPage");
        mav.addObject("name", ud.getName());
        mav.addObject("message", ud.getMessage());

        return mav;
    }
}