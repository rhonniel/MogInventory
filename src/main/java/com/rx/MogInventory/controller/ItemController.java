package com.rx.MogInventory.controller;

import com.rx.MogInventory.entity.Item;
import com.rx.MogInventory.entity.Transaction;
import com.rx.MogInventory.entity.dto.ItemCrudDTO;
import com.rx.MogInventory.entity.dto.ItemInventoryDTO;
import com.rx.MogInventory.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/item")
public class ItemController {

    public final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public List<Item> getAllItems(){

        return itemService.getAllItems();
    }


    @PostMapping
    public Item saveItem(@RequestBody ItemCrudDTO item){
       return itemService.save(item);

    }
    @GetMapping("/inventory")
    public Page<ItemInventoryDTO> getInventory(@RequestParam(required = false, defaultValue = "0") int itemType,
                                               @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int limit){

        Pageable pageable = PageRequest.of(page,limit);
        return itemService.getItemsWithFilter(itemType,pageable);

    }


    @GetMapping("/{id}")
    public Item getItemById(@PathVariable("id") Integer id){

        return itemService.getItemById(id);
    }

    @PutMapping("/{id}")
    public Item editItem(@PathVariable("id") Integer itemId,@RequestBody ItemCrudDTO item){

      return itemService.editItem(itemId,item);

    }

    @DeleteMapping("/{id}")
    public Item deleteItem(@PathVariable("id") Integer itemId){

        return itemService.deleteItem(itemId);

    }


}
