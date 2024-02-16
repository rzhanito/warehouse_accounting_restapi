package ru.rzhanito.uchet.sklad.exception;

public class GoodsCannotFitIntoWarehouseException extends Exception{
    public GoodsCannotFitIntoWarehouseException(String message) {
        super(message);
    }
}
