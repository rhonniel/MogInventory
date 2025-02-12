package com.rx.MogInventory.service;

import com.rx.MogInventory.entity.Item;
import com.rx.MogInventory.entity.Transaction;
import com.rx.MogInventory.entity.TransactionsItems;
import com.rx.MogInventory.entity.dto.TransactionCrudDTO;
import com.rx.MogInventory.exception.ItemNotFoundException;
import com.rx.MogInventory.exception.TransactionTypeNotFoundException;
import com.rx.MogInventory.repository.ItemRepository;
import com.rx.MogInventory.repository.TransactionRepository;
import com.rx.MogInventory.util.TransactionType;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionsService {

    public final TransactionRepository transactionsRepository;
    public final ItemService itemService;
    public final ModelMapper modelMapper;

    @Autowired
    public TransactionsService(TransactionRepository transactionsRepository, ItemService itemService, ModelMapper modelMapper) {
        this.transactionsRepository = transactionsRepository;
        this.itemService = itemService;
        this.modelMapper = modelMapper;
    }




    public Transaction saveTransaction(TransactionCrudDTO transactionCrudDTO){

        if(!TransactionType.isValid(transactionCrudDTO.getTransactionType())){
            throw new TransactionTypeNotFoundException();
        }

        List<TransactionsItems> items= transactionCrudDTO.getTransactionsItems().stream().map( itemDTO ->{
            Item item = itemService.getItemById(itemDTO.getItem());
            return new TransactionsItems(item,itemDTO.getQuantity());
        }).toList();

        Transaction transaction =  new Transaction(transactionCrudDTO.getClient(),LocalDateTime.now(),
                transactionCrudDTO.getTransactionType(),items);

       return  transactionsRepository.save(transaction);

    }

}
