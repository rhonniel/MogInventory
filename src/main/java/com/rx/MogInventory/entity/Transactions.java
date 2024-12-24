package com.rx.MogInventory.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Table
public class Transactions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String client;

    @Column
    private LocalDateTime date;


    @JoinColumn(name = "transaction_type_id", referencedColumnName = "id")
    @ManyToOne(cascade = CascadeType.ALL)
    private TransactionType transactionType;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "transaction_id")
    List<TransactionsItems> transactionsItems;
    public Transactions() {
    }

    public Transactions(String client, LocalDateTime date, TransactionType transactionType, List<TransactionsItems> transactionsItems) {
        this.client = client;
        this.date = date;
        this.transactionType = transactionType;
        this.transactionsItems = transactionsItems;
    }

    public int getId() {
        return id;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public List<TransactionsItems> getTransactionsItems() {
        return transactionsItems;
    }

    public void setTransactionsItems(List<TransactionsItems> transactionsItems) {
        this.transactionsItems = transactionsItems;
    }

    @Override
    public String toString() {
        return "Transactions{" +
                "id=" + id +
                ", client='" + client + '\'' +
                ", date=" + date +
                ", transactionType=" + transactionType +
                ", transactionsItems=" + transactionsItems +
                '}';
    }
}
