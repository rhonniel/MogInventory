package com.rx.MogInventory.entity.dto;


public class ItemCrudDTO {
    private String name;
    private String description;
    private int subType;

    public ItemCrudDTO() {
    }

    public ItemCrudDTO(String name, String description, int subType) {
        this.name = name;
        this.description = description;
        this.subType = subType;
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

    public int getSubType() {
        return subType;
    }

    public void setSubType(int subType) {
        this.subType = subType;
    }
}
