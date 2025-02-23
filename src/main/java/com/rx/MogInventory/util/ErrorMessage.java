package com.rx.MogInventory.util;

public enum ErrorMessage {
    INVALID_TRANSACTION_TYPE("Transaction type is invalid");
    private String msg;

    ErrorMessage(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
