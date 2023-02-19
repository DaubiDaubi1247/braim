package ru.alex.braim.exception;

public class BadRequestData extends RuntimeException{
    public BadRequestData(String message) {
        super(message);
    }
}
