package com.rx.MogInventory.entity;

import jakarta.persistence.*;

import java.util.List;

@Table(name = "transaction_items")
@Entity
public class TransactionsItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JoinColumn(name = "item_id", referencedColumnName = "id")
    @ManyToOne(cascade = CascadeType.ALL)
    private Item item;
    @ManyToOne
    @JoinColumn(name = "transaction_id")
    private Transactions transaction;
    @Column
    private int quantity;

    public TransactionsItems() {
    }

    public TransactionsItems(Item item, Transactions transaction, int quantity) {
        this.item = item;
        this.transaction = transaction;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Transactions getTransaction() {
        return transaction;
    }

    public void setTransaction(Transactions transaction) {
        this.transaction = transaction;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "TransactionsItems{" +
                "id=" + id +
                ", item=" + item +
                ", quantity=" + quantity +
                '}';
    }
}
