package com.rx.MogInventory.exception;

import jakarta.persistence.EntityNotFoundException;

public class ItemSubTypeNotFoundException extends EntityNotFoundException {
    public static final String myMessage="Subtype is not found";

    public ItemSubTypeNotFoundException() {
        super(myMessage);
    }
}
