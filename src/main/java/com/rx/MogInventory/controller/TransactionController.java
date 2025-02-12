package com.rx.MogInventory.controller;

import com.rx.MogInventory.entity.Transaction;
import com.rx.MogInventory.entity.dto.TransactionCrudDTO;
import com.rx.MogInventory.service.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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



}
