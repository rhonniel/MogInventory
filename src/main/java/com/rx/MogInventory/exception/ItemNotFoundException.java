package com.rx.MogInventory.exception;

import jakarta.persistence.EntityNotFoundException;

public class ItemNotFoundException extends EntityNotFoundException {
    public static final String myMessage="Item is not found";

    public ItemNotFoundException() {
        super(myMessage);
    }
}
