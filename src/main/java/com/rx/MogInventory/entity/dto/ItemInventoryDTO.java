package com.rx.MogInventory.entity.dto;

import java.util.Objects;

public class ItemInventoryDTO {

    private  int id;
    private String name;
    private String subType;
    private int quantity;

    public ItemInventoryDTO(int id, String name, String subType, int quantity) {
        this.id = id;
        this.name = name;
        this.subType = subType;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemInventoryDTO that = (ItemInventoryDTO) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ItemInventoryDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", subType='" + subType + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
