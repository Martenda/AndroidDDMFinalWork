package com.example.taskmasters.exceptions;

public final class AgeException extends Exception{
    public AgeException() {
        super("Age must be greater or equal than 18");
    }
}
