package com.rx.MogInventory.repository;

import com.rx.MogInventory.entity.ItemSubType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface SubTypeRepository extends JpaRepository<ItemSubType,Integer> {
}
