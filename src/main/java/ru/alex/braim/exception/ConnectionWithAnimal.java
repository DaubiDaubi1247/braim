package ru.alex.braim.exception;

public class ConnectionWithAnimal extends RuntimeException{
    public ConnectionWithAnimal(String message) {
        super(message);
    }
}
