package ru.rzhanito.uchet.sklad.exception;

public class GoodsAlreadyExistsException extends Exception{
    public GoodsAlreadyExistsException(String message) {
        super(message);
    }
}
