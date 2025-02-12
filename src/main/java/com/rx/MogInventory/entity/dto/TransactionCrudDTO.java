package com.rx.MogInventory.entity.dto;

import java.util.List;

public class TransactionCrudDTO {
    private String client;

    private String transactionType;

    List<TransactionItemCrudDTO> transactionsItems;

    public TransactionCrudDTO(String client, String transactionType, List<TransactionItemCrudDTO> transactionsItems) {
        this.client = client;
        this.transactionType = transactionType;
        this.transactionsItems = transactionsItems;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public List<TransactionItemCrudDTO> getTransactionsItems() {
        return transactionsItems;
    }

    public void setTransactionsItems(List<TransactionItemCrudDTO> transactionsItems) {
        this.transactionsItems = transactionsItems;
    }

    @Override
    public String toString() {
        return "TransactionCrudDTO{" +
                ", client='" + client + '\'' +
                ", transactionType=" + transactionType +
                ", transactionsItems=" + transactionsItems +
                '}';
    }
}
