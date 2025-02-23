package com.rx.MogInventory.repository;

import com.rx.MogInventory.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Integer> {
    Page<Transaction> findAll(Specification<Transaction> spec, Pageable pageable);
}
