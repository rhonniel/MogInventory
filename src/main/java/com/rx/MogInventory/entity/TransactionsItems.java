package com.rx.MogInventory.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@Table(name = "transaction_items")
@Entity
public class TransactionsItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JoinColumn(name = "item_id", referencedColumnName = "id")
    @ManyToOne
    @NotNull
    private Item item;
    @ManyToOne
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;
    @Column
    @Positive
    private int quantity;

    public TransactionsItems() {
    }

    public TransactionsItems(Item item, int quantity) {
        this.item = item;
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

    public void setTransaction(Transaction transaction) {
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
