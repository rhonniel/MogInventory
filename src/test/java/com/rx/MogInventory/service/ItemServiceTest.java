package com.rx.MogInventory.service;


import com.rx.MogInventory.entity.Item;
import com.rx.MogInventory.entity.ItemSubType;
import com.rx.MogInventory.entity.Transaction;
import com.rx.MogInventory.entity.dto.ItemCrudDTO;
import com.rx.MogInventory.entity.dto.ItemInventoryDTO;
import com.rx.MogInventory.repository.ItemRepository;
import com.rx.MogInventory.repository.SubTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;
    @Mock
    private SubTypeRepository subTypeRepository;
    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ItemService itemService;

    @BeforeEach
     void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getItemsSuccessfully(){
        List<Item> items = new ArrayList<>();
        items.add(new Item());
        items.add(new Item());
        items.add(new Item());
        items.add(new Item());

       when(itemRepository.findAll()).thenReturn(items);
        List<Item> itemReturn= itemService.getAllItems();
        assertEquals(4, itemReturn.size());


    }

    @ParameterizedTest
    @ValueSource(ints = {0,1}) // 0 sin filtro
    public void getItemInventorySuccessfully(int itemType){
        List<Item> items = new ArrayList<>();
        items.add(new Item("Vaina1","vaina cooment1",new ItemSubType(1),true));
        items.add(new Item("Vaina2","vaina cooment2",new ItemSubType(1),true));
        items.add(new Item("Vaina3","vaina cooment3",new ItemSubType(1),true));
        items.add(new Item("Vaina4","vaina cooment3",new ItemSubType(1),true));

        Pageable pageable = PageRequest.of(1,10);



        Page<Item> expectedPage = new PageImpl<>(items);

        when(itemRepository.findAll(any(Specification.class),eq(pageable))).thenReturn(expectedPage);
        Page<ItemInventoryDTO> itemReturn= itemService.getItemsWithFilter(itemType,pageable);
        assertEquals(4, itemReturn.stream().count());

    }


    @Test
    public void getItemByIdSuccessfully(){
        Item swordX= new Item();
        Integer id= 777;
        swordX.setName("Espada X");
        swordX.setId(id);

        when(itemRepository.findById(id)).thenReturn(Optional.of(swordX));

        Item result= itemService.getItemById(id);

        assertEquals(result.getId(), swordX.getId());

    }


    @Test
    public void getItemByIdFailed(){
        Integer id= 777;
        when(itemRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> itemService.getItemById(id));

    }

    @Test
    public void createItemSuccessfully() {
        ItemCrudDTO dto = new ItemCrudDTO();
        dto.setName("Espada X");
        dto.setDescription("");
        dto.setSubType(1);


        Item item = new Item();

        item.setName(dto.getName());
        item.setDescription(dto.getDescription());
        item.setSubType(new ItemSubType(dto.getSubType()));


        when(modelMapper.map(dto,Item.class)).thenReturn(item);
        when(subTypeRepository.existsById(dto.getSubType())).thenReturn(true);
        when(itemRepository.save(item)).thenReturn(item);

        Item result= itemService.save(dto);


        assertEquals(0, result.getName().compareTo(item.getName()));

    }

    @Test
    public void editItemSuccessfully(){
        ItemCrudDTO dto = new ItemCrudDTO();
        dto.setName("Espada X");
        dto.setDescription("");
        dto.setSubType(1);

        int id = 777;
        Item item = new Item();

        item.setId(id);
        item.setName(dto.getName());
        item.setDescription(dto.getDescription());
        item.setSubType(new ItemSubType(dto.getSubType()));

        when(itemRepository.findById(id)).thenReturn(Optional.of(item));
        when(subTypeRepository.existsById(dto.getSubType())).thenReturn(true);
        when(modelMapper.map(dto,Item.class)).thenReturn(item);
        when(itemRepository.save(item)).thenReturn(item);

        Item result= itemService.editItem(id,dto);

        assertEquals(id,result.getId());
    }

    @Test
    public void deleteItemSuccessfully(){
        int id = 777;
        Item item = new Item();

        item.setId(id);
        item.setName("Espada x");
        item.setEnable(true);

        when(itemRepository.findById(id)).thenReturn(Optional.of(item));
        when(itemRepository.save(item)).thenReturn(item);

        Item result =itemService.deleteItem(id);

        assertFalse(result.isEnable());


    }



}
