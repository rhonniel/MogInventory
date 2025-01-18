package com.rx.MogInventory.repository;

import com.rx.MogInventory.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository  extends JpaRepository<Item,Integer> {

}
