package com.training.util;

public class EmployeeNotFoundException extends Throwable{
    public EmployeeNotFoundException(String message){
        super(message);
    }
}
