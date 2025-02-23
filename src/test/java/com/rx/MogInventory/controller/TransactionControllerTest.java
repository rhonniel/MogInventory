package com.rx.MogInventory.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rx.MogInventory.entity.Item;
import com.rx.MogInventory.entity.dto.TransactionCrudDTO;
import com.rx.MogInventory.entity.dto.TransactionItemCrudDTO;
import com.rx.MogInventory.exception.ItemNotFoundException;
import com.rx.MogInventory.exception.ItemSubTypeNotFoundException;
import com.rx.MogInventory.util.ErrorMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest
@AutoConfigureMockMvc
public class TransactionControllerTest {
    private final MockMvc mockMvc;


    @Autowired
    public TransactionControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    public void saveTransactionSuccessfully() throws Exception {
        TransactionCrudDTO dto =new TransactionCrudDTO();
        dto.setClient("Gigalmech");
        dto.setTransactionType("OUT");

        int ItemId= 1;

        List<TransactionItemCrudDTO> transactionItemCrudDTOS = new ArrayList<>();
        transactionItemCrudDTOS.add(new TransactionItemCrudDTO(ItemId,5));

        dto.setTransactionsItems(transactionItemCrudDTOS);
        

        mockMvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(dto)))
                .andExpect(status().isOk());

    }



    @Test
    public void saveTransactionWhenTransactionTypeIsNotFound() throws Exception {
        TransactionCrudDTO dto =new TransactionCrudDTO();
        dto.setClient("Sarissa");
        dto.setTransactionType("Kupo!");

        int ItemId= 1;

        List<TransactionItemCrudDTO> transactionItemCrudDTOS = new ArrayList<>();
        transactionItemCrudDTOS.add(new TransactionItemCrudDTO(ItemId,5));

        dto.setTransactionsItems(transactionItemCrudDTOS);


        mockMvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").value(ErrorMessage.INVALID_TRANSACTION_TYPE.getMsg()));

    }


    @Test
    public void saveTransactionWhenItemNotFound() throws Exception {
        TransactionCrudDTO dto =new TransactionCrudDTO();
        dto.setClient("Sarissa");
        dto.setTransactionType("IN");

        int ItemId= 777;

        List<TransactionItemCrudDTO> transactionItemCrudDTOS = new ArrayList<>();
        transactionItemCrudDTOS.add(new TransactionItemCrudDTO(ItemId,5));

        dto.setTransactionsItems(transactionItemCrudDTOS);


        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").value(ItemNotFoundException.myMessage));

    }



    @Test
    public void saveTransactionWithBadData() throws Exception {
        TransactionCrudDTO dto =new TransactionCrudDTO();
        dto.setClient(null);
        dto.setTransactionType("IN");

        int ItemId= 1;

        List<TransactionItemCrudDTO> transactionItemCrudDTOS = new ArrayList<>();
        transactionItemCrudDTOS.add(new TransactionItemCrudDTO(ItemId,-10));

        dto.setTransactionsItems(transactionItemCrudDTOS);


        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto)))
                .andExpect(status().isBadRequest());


    }

//TODO usar parametros
    @Test
    public void getTransactionWithFilterSuccessfully() throws Exception {
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("itemId","1");
        params.add("type","IN");
        params.add("page","1");
        params.add("limit","5");
        mockMvc.perform(get("/transactions")
                        .queryParams(params))
                .andExpect(status().isOk());
    }
    @Test
    public void getTransactionWithFilterBadRequest() throws Exception {
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("itemId","1");
        params.add("type","XEZAR");
        params.add("page","1");
        params.add("limit","pedro");
        mockMvc.perform(get("/transactions")
                        .queryParams(params))
                .andExpect(status().isBadRequest());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
