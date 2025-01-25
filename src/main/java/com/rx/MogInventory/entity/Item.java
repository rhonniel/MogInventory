package com.rx.MogInventory.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Table
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    @NotBlank
    private String name;

    @Column
    @NotBlank
    private String description;

    @JoinColumn(name = "sub_type_id", referencedColumnName = "id")
    @ManyToOne
    @NotNull
    private ItemSubType subType;

    @PositiveOrZero
    @Column
    private int quantity;

    @Column
    private boolean enable;

    public Item() {
    }

    public Item(String name, String description, ItemSubType subType, int quantity, boolean enable) {
        this.name = name;
        this.description = description;
        this.subType = subType;
        this.quantity = quantity;
        this.enable = enable;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ItemSubType getSubType() {
        return subType;
    }

    public void setSubType(ItemSubType subType) {
        this.subType = subType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", subType=" + subType.getName() +
                ", quantity=" + quantity +
                ", enable=" + enable +
                '}';
    }
}
