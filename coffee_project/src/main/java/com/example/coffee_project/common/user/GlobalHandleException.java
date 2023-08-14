package com.example.coffee_project.common.user;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalHandleException {
    @ExceptionHandler(Exception.class)
    public String handleException(){
        return "error";
    }
}
