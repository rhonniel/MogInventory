package com.rx.MogInventory.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "item_type")
@Entity
public class ItemType {

    @Id
    private int id;

    @Column
    private String name;

    public ItemType() {
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

    @Override
    public String toString() {
        return "ItemType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
