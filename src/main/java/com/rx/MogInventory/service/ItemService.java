package com.rx.MogInventory.service;

import com.rx.MogInventory.entity.Item;
import com.rx.MogInventory.repository.ItemRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    public final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> getAllItems(){
       return itemRepository.findAll();
    }

    public Item save(Item item) {
        return itemRepository.save(item);
    }

    public Item getItemById(Integer id) {
        return itemRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Item is not found"));
    }

    public Item editItem(Integer itemId, Item item) {
        item.setId(getItemById(itemId).getId());
        return itemRepository.save(item);
    }

    public Item deleteItem(Integer itemId) {
        Item itemDisable=getItemById(itemId);
        itemDisable.setEnable(false);
        return itemRepository.save(itemDisable);
    }
}
