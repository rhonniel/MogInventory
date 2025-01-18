package com.rx.MogInventory.entity;

import jakarta.persistence.*;

@Table(name="item_sub_type")
@Entity
public class ItemSubType {
    @Id
    private int id;

    @Column
    private String name;

    @JoinColumn(name = "item_type_id", referencedColumnName = "id")
    @ManyToOne(cascade = CascadeType.ALL)
    private ItemType type;

    public ItemSubType() {
    }

    public ItemSubType(int id) {
        this.id = id;
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

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ItemSubType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type.getName() +
                '}';
    }
}
