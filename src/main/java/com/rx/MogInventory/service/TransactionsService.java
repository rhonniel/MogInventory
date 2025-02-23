package com.rx.MogInventory.service;

import com.rx.MogInventory.entity.Item;
import com.rx.MogInventory.entity.Transaction;
import com.rx.MogInventory.entity.TransactionsItems;
import com.rx.MogInventory.entity.dto.TransactionCrudDTO;
import com.rx.MogInventory.exception.InvalidTransactionException;
import com.rx.MogInventory.repository.TransactionRepository;
import com.rx.MogInventory.util.ErrorMessage;
import com.rx.MogInventory.util.TransactionType;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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
            throw new InvalidTransactionException(ErrorMessage.INVALID_TRANSACTION_TYPE.getMsg());
        }
        List<TransactionsItems> items;
        try {
             items = transactionCrudDTO.getTransactionsItems().stream().map(itemDTO -> {
                Item item = itemService.getItemById(itemDTO.getItem());
                return new TransactionsItems(item, itemDTO.getQuantity());
            }).toList();
        }catch (EntityNotFoundException e){
            throw new InvalidTransactionException(e.getMessage());
        }

        Transaction transaction =  new Transaction(transactionCrudDTO.getClient(),LocalDateTime.now(),
                transactionCrudDTO.getTransactionType(),items);

       return  transactionsRepository.save(transaction);

    }

    public Page<Transaction> getTransactionsFilter(int itemId, String type, Pageable pageable) {
        Specification<Transaction> spec = Specification.where(null);

        if(itemId>0){
            spec = spec.and(((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("transactionsItems").get("item").get("id"),itemId)));
        }
        if(!type.isBlank()){
            spec = spec.and(((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("transactionType"),type)));
        }
        return transactionsRepository.findAll(spec,pageable);
    }

}
