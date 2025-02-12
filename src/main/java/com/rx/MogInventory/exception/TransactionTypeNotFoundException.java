package com.rx.MogInventory.exception;

import jakarta.persistence.EntityNotFoundException;

public class TransactionTypeNotFoundException extends EntityNotFoundException {
    public static final String myMessage=" Transaction type is incorrect";

    public TransactionTypeNotFoundException() {
        super(myMessage);
    }
}
