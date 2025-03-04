package com.rx.MogInventory.repository;

import com.rx.MogInventory.entity.Item;
import com.rx.MogInventory.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ItemRepository  extends JpaRepository<Item,Integer> {

    @Modifying
    @Transactional
    @Query(value = """
             update item i set i.quantity=i.quantity+(select IFNULL(sum((case when t.transaction_type='OUT' then (ti.quantity*-1) else ti.quantity end)),0) total
                         from transaction t
                         inner join transaction_items ti on t.id=ti.transaction_id
                         where ti.item_id=i.id  and  TRUNC(t.date)= CURRENT_DATE())
            """, nativeQuery = true)

    public void updatesStocks();


    Page<Item> findAll(Specification<Item> spec, Pageable pageable);

}
