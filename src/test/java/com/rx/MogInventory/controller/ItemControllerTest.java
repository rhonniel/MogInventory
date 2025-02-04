package com.rx.MogInventory.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rx.MogInventory.entity.Item;
import com.rx.MogInventory.entity.dto.ItemCrudDTO;
import com.rx.MogInventory.exception.ItemNotFoundException;
import com.rx.MogInventory.exception.ItemSubTypeNotFoundException;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ItemControllerTest {

    private final  MockMvc mockMvc;


    @Autowired
    public ItemControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    public void getAllItemsSuccessfully() throws Exception {

        mockMvc.perform(get("/item"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(1)));
    }


    @Test
    public void getItemByIdSuccessfully() throws Exception {

        mockMvc.perform(get("/item/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }



    @Test
    public void getItemByIdFailed() throws Exception {

        mockMvc.perform(get("/item/7"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(ItemNotFoundException.myMessage));


    }



    @Test
    public void createItemSuccessfully() throws Exception {
        ItemCrudDTO dto=new ItemCrudDTO();
        dto.setName("Weapon X");
        dto.setDescription("Es buena");
        dto.setSubType(1);
        mockMvc.perform(post("/item")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.enable").value(true));



    }


    @Test
    public void createItemFailedValidation() throws Exception {
        ItemCrudDTO dto=new ItemCrudDTO();
        dto.setName(null);
        dto.setDescription(null);
        dto.setSubType(1);
        mockMvc.perform(post("/item")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.description").exists());
    }
    @Test
    public void createItemFailedSubtype() throws Exception {
        ItemCrudDTO dto=new ItemCrudDTO();
        dto.setName("Weapon X");
        dto.setDescription("Es buena");
        dto.setSubType(777);
        mockMvc.perform(post("/item")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto)))
                .andExpect(status().isNotFound())
                .andExpect(content().string(ItemSubTypeNotFoundException.myMessage));

    }


    @Test
    public void editItemSuccessfully() throws Exception {
        ItemCrudDTO dto=new ItemCrudDTO();
        dto.setName("Weapon X");
        dto.setDescription("Es buena");
        dto.setSubType(1);
        mockMvc.perform(put("/item/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(dto.getName()));



    }
    @Test
    public void editItemFailedSubtype() throws Exception {
        ItemCrudDTO dto=new ItemCrudDTO();
        dto.setName("Weapon X");
        dto.setDescription("Es buena");
        dto.setSubType(777);
        mockMvc.perform(put("/item/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto)))
                .andExpect(status().isNotFound())
                .andExpect(content().string(ItemSubTypeNotFoundException.myMessage));

    }

    @Test
    public void editItemFailedItemId() throws Exception {
        ItemCrudDTO dto=new ItemCrudDTO();
        dto.setName("Weapon X");
        dto.setDescription("Es buena");
        dto.setSubType(777);
        mockMvc.perform(put("/item/777")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto)))
                .andExpect(status().isNotFound())
                .andExpect(content().string(ItemNotFoundException.myMessage));

    }



    @Test
    public void deleteItemSuccessfully() throws Exception {

        mockMvc.perform(delete("/item/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.enable").value(false));



    }

    @Test
    public void deleteItemFailed() throws Exception {

        mockMvc.perform(delete("/item/777"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(ItemNotFoundException.myMessage));
    }


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
