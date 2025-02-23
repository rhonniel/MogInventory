package com.rx.MogInventory.exception;

public class InvalidTransactionException extends RuntimeException {
    public InvalidTransactionException(String msg) {
        super(msg);
    }
}
