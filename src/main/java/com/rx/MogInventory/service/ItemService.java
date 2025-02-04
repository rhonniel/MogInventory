package com.rx.MogInventory.service;

import com.rx.MogInventory.entity.Item;
import com.rx.MogInventory.entity.dto.ItemCrudDTO;
import com.rx.MogInventory.exception.ItemNotFoundException;
import com.rx.MogInventory.exception.ItemSubTypeNotFoundException;
import com.rx.MogInventory.repository.ItemRepository;
import com.rx.MogInventory.repository.SubTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    public final ItemRepository itemRepository;
    public final SubTypeRepository subTypeRepository;

    private final ModelMapper modelMapper;
    @Autowired
    public ItemService(ItemRepository itemRepository, ModelMapper modelMapper, SubTypeRepository subTypeRepository) {
        this.itemRepository = itemRepository;
        this.modelMapper = modelMapper;
        this.subTypeRepository = subTypeRepository;
    }

    public List<Item> getAllItems(){
       return itemRepository.findAll() ;
    }

    public Item save(ItemCrudDTO dto) {
        Item item=modelMapper.map(dto,Item.class);
        if (!subTypeRepository.existsById(item.getSubType().getId())) {
            throw new ItemSubTypeNotFoundException();
        }
        item.setEnable(true);
        return itemRepository.save(item);
    }

    public Item getItemById(Integer id) {
        return itemRepository.findById(id).orElseThrow(ItemNotFoundException::new);
    }

    public Item editItem(Integer itemId, ItemCrudDTO dto) {
        Item item =modelMapper.map(dto,Item.class);
        item.setId(getItemById(itemId).getId());
        if (!subTypeRepository.existsById(item.getSubType().getId())) {
            throw new ItemSubTypeNotFoundException();
        }
        return itemRepository.save(item);
    }

    public Item deleteItem(Integer itemId) {
        Item itemDisable=getItemById(itemId);
        itemDisable.setEnable(false);
        return itemRepository.save(itemDisable);
    }
}
