package ru.alex.braim.exception;

public class AccountHaveAnimal extends RuntimeException{
    public AccountHaveAnimal(String message) {
        super(message);
    }
}
