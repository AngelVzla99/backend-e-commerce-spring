package com.example.springboot.Notification.Exceptions;

public class TemplateNotFound extends Exception{
    public TemplateNotFound(String errorMessage) {
        super(errorMessage);
    }
}
