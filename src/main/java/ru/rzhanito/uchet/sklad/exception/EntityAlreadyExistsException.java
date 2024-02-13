package ru.rzhanito.uchet.sklad.exception;

public class EntityAlreadyExistsException extends Exception{
    public EntityAlreadyExistsException(String message) {
        super(message);
    }
}
