package com.rx.MogInventory.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rx.MogInventory.entity.Item;
import com.rx.MogInventory.entity.ItemSubType;
import com.rx.MogInventory.entity.Transaction;
import com.rx.MogInventory.entity.TransactionsItems;
import com.rx.MogInventory.entity.dto.TransactionCrudDTO;
import com.rx.MogInventory.entity.dto.TransactionItemCrudDTO;
import com.rx.MogInventory.exception.ItemNotFoundException;
import com.rx.MogInventory.exception.InvalidTransactionException;
import com.rx.MogInventory.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;


public class TransactionServicesTest {

    @Mock
    public  TransactionRepository transactionsRepository;
    @Mock
    public  ItemService itemService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @InjectMocks
    public TransactionsService transactionsService;


    @Test
    public void saveTransactionSuccessfully() throws JsonProcessingException {
        TransactionCrudDTO transactionCrudDTO = new TransactionCrudDTO();
        transactionCrudDTO.setTransactionType("IN");
        transactionCrudDTO.setClient("Bartz");

        Item swordX= new Item();
        int ItemId= 777;
        swordX.setName("Espada X");
        swordX.setSubType(new ItemSubType(1));
        swordX.setId(ItemId);
        swordX.setQuantity(5);

        Item expectedSwordX = new Item();
        expectedSwordX.setName(swordX.getName());
        expectedSwordX.setSubType(swordX.getSubType());
        expectedSwordX.setId(swordX.getId());

        int transactionItemQuantity=7;

        //  validando sumatoria en el inventario
        expectedSwordX.setQuantity(swordX.getQuantity()+transactionItemQuantity);


        List<TransactionItemCrudDTO> transactionItemCrudDTOS = new ArrayList<>();
        transactionItemCrudDTOS.add(new TransactionItemCrudDTO(ItemId,transactionItemQuantity));
        transactionCrudDTO.setTransactionsItems(transactionItemCrudDTOS);


        Transaction transaction = new Transaction(
                transactionCrudDTO.getClient(),
                LocalDateTime.now(),
                transactionCrudDTO.getTransactionType(),
                List.of(new TransactionsItems(swordX, transactionItemCrudDTOS.get(0).getQuantity())));



        Transaction expectedTransaction = new Transaction(
                transactionCrudDTO.getClient(),
                LocalDateTime.now(),
                transactionCrudDTO.getTransactionType(),
                List.of(new TransactionsItems(expectedSwordX, transactionItemCrudDTOS.get(0).getQuantity()))
        );

        when(itemService.getItemById(ItemId)).thenReturn(swordX);
        when(transactionsRepository.save(any(Transaction.class))).thenReturn(transaction);

        Transaction result = transactionsService.saveTransaction(transactionCrudDTO);

        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("date") // Ignorar la fecha porque es generada dentro del metodo, nunca seria igual
                .isEqualTo(expectedTransaction);
    }

    @Test
    public void saveTransactionWithOutStockItem(){
        TransactionCrudDTO transactionCrudDTO = new TransactionCrudDTO();
        transactionCrudDTO.setTransactionType("OUT");
        transactionCrudDTO.setClient("Bartz");

        Item swordX= new Item();
        int ItemId= 777;
        swordX.setName("Espada X");
        swordX.setId(ItemId);

        List<TransactionItemCrudDTO> transactionItemCrudDTOS = new ArrayList<>();
        transactionItemCrudDTOS.add(new TransactionItemCrudDTO(ItemId,5000));
        transactionCrudDTO.setTransactionsItems(transactionItemCrudDTOS);

        Transaction expectedTransaction = new Transaction(
                transactionCrudDTO.getClient(),
                LocalDateTime.now(),
                transactionCrudDTO.getTransactionType(),
                List.of(new TransactionsItems(swordX, transactionItemCrudDTOS.get(0).getQuantity())));


        when(itemService.getItemById(ItemId)).thenReturn(swordX);
        when(transactionsRepository.save(any(Transaction.class))).thenReturn(expectedTransaction);

        assertThrows(RuntimeException.class, () -> transactionsService.saveTransaction(transactionCrudDTO));
        verify(transactionsRepository,times(0)).save(any());


    }

    @Test
    public void saveTransactionWhenItemNotFound (){
        TransactionCrudDTO transactionCrudDTO = new TransactionCrudDTO();
        transactionCrudDTO.setTransactionType("IN");
        transactionCrudDTO.setClient("Faris");

        Item swordX= new Item();
        int ItemId= 777;
        swordX.setName("Espada X");
        swordX.setId(ItemId);

        List<TransactionItemCrudDTO> transactionItemCrudDTOS = new ArrayList<>();
        transactionItemCrudDTOS.add(new TransactionItemCrudDTO(ItemId,5));
        transactionCrudDTO.setTransactionsItems(transactionItemCrudDTOS);
        when(itemService.getItemById(any())).thenThrow(ItemNotFoundException.class);

        assertThrows(InvalidTransactionException.class, () -> transactionsService.saveTransaction(transactionCrudDTO));
        verify(transactionsRepository,times(0)).save(any()); // validar que el save no se llame
    }


    @Test
    public void saveTransactionWithWrongType (){
        TransactionCrudDTO transactionCrudDTO = new TransactionCrudDTO();
        transactionCrudDTO.setTransactionType("TREE");
        transactionCrudDTO.setClient("Exdeath");

        Item swordX= new Item();
        int ItemId= 777;
        swordX.setName("The void");
        swordX.setId(ItemId);

        List<TransactionItemCrudDTO> transactionItemCrudDTOS = new ArrayList<>();
        transactionItemCrudDTOS.add(new TransactionItemCrudDTO(ItemId,5));
        transactionCrudDTO.setTransactionsItems(transactionItemCrudDTOS);
        when(itemService.getItemById(any())).thenThrow(ItemNotFoundException.class);
        assertThrows(InvalidTransactionException.class, () -> transactionsService.saveTransaction(transactionCrudDTO));
        verify(transactionsRepository,times(0)).save(any());
        verify(itemService,times(0)).getItemById(any());
    }


    @Test
    public void getTransactionsWithFilterSuccessfully(){

        Pageable pageable = PageRequest.of(1,10);
        int itemId=777;
        String type ="OUT";
        List<Transaction> transactions =List.of(
                new Transaction(),
                new Transaction(),
                new Transaction()
        );

        Page<Transaction> expectedPage = new PageImpl<>(transactions);

        when(transactionsRepository.findAll(any(Specification.class),eq(pageable))).thenReturn(expectedPage);

        Page<Transaction> result =transactionsService.getTransactionsFilter(itemId,type,pageable);

        assertNotNull(result);
        assertFalse(result.getContent().isEmpty());


    }

}
