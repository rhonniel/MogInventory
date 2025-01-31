package com.rx.MogInventory.service;

import com.rx.MogInventory.entity.Item;
import com.rx.MogInventory.entity.dto.ItemCrudDTO;
import com.rx.MogInventory.repository.ItemRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    public final ItemRepository itemRepository;

    private final ModelMapper modelMapper;
    @Autowired
    public ItemService(ItemRepository itemRepository, ModelMapper modelMapper) {
        this.itemRepository = itemRepository;
        this.modelMapper = modelMapper;
    }

    public List<Item> getAllItems(){
       return itemRepository.findAll() ;
    }

    public Item save(ItemCrudDTO item) {
        return itemRepository.save(modelMapper.map(item,Item.class));
    }

    public Item getItemById(Integer id) {
        return itemRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Item is not found"));
    }

    public Item editItem(Integer itemId, ItemCrudDTO dto) {
        Item item =modelMapper.map(dto,Item.class);
        item.setId(getItemById(itemId).getId());
        return itemRepository.save(item);
    }

    public Item deleteItem(Integer itemId) {
        Item itemDisable=getItemById(itemId);
        itemDisable.setEnable(false);
        return itemRepository.save(itemDisable);
    }
}
