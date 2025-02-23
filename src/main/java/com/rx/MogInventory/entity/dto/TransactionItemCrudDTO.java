package com.rx.MogInventory.entity.dto;


public class TransactionItemCrudDTO {

    private int item;

    private int quantity;

    public TransactionItemCrudDTO(int item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public TransactionItemCrudDTO() {
    }

    public int getItem() {
        return item;
    }

    public void setItem(int item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "TransactionItemDTO{" +
                "itemId=" + item +
                ", quantity=" + quantity +
                '}';
    }
}
