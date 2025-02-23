package com.rx.MogInventory.controller;

import com.rx.MogInventory.entity.Transaction;
import com.rx.MogInventory.entity.dto.TransactionCrudDTO;
import com.rx.MogInventory.service.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionsService transactionsService;

    @Autowired
    public TransactionController(TransactionsService transactionsService) {
        this.transactionsService = transactionsService;
    }

    @PostMapping
    public Transaction saveTransactions(@RequestBody TransactionCrudDTO transactionCrudDTO){

       return transactionsService.saveTransaction(transactionCrudDTO);

    }

    @GetMapping
    public Page<Transaction> getTransactionsFilter(@RequestParam(required = false, defaultValue = "0") int itemId,
                                                   @RequestParam(required = false , defaultValue = "") String type,
                                                   @RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "10") int limit){

        Pageable pageable = PageRequest.of(page,limit);
        return transactionsService.getTransactionsFilter(itemId,type,pageable);

    }



}
