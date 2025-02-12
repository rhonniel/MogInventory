package com.rx.MogInventory.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;

@Table
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    @NotBlank
    private String client;

    @Column
    @NotNull
    private LocalDateTime date;


    @Column
    @NotNull
    @Size(min = 2, max = 3)
    private String transactionType;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "transaction_id")
    @NotNull
    List<TransactionsItems> transactionsItems;
    public Transaction() {
    }

    public Transaction(String client, LocalDateTime date, String transactionType, List<TransactionsItems> transactionsItems) {
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

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
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
                ", transactionType=" + transactionType+
                ", transactionsItems=" + transactionsItems +
                '}';
    }
}
