package com.rx.MogInventory.service;

import com.rx.MogInventory.entity.Item;
import com.rx.MogInventory.entity.JobLog;
import com.rx.MogInventory.entity.Transaction;
import com.rx.MogInventory.entity.dto.ItemCrudDTO;
import com.rx.MogInventory.entity.dto.ItemInventoryDTO;
import com.rx.MogInventory.exception.ItemNotFoundException;
import com.rx.MogInventory.exception.ItemSubTypeNotFoundException;
import com.rx.MogInventory.repository.ItemRepository;
import com.rx.MogInventory.repository.JobLogRepository;
import com.rx.MogInventory.repository.SubTypeRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ItemService {
    public final ItemRepository itemRepository;
    public final SubTypeRepository subTypeRepository;

    public final JobLogRepository jobLogRepository;
    private final ModelMapper modelMapper;
    @Autowired
    public ItemService(ItemRepository itemRepository, ModelMapper modelMapper, SubTypeRepository subTypeRepository, JobLogRepository jobLogRepository) {
        this.itemRepository = itemRepository;
        this.modelMapper = modelMapper;
        this.subTypeRepository = subTypeRepository;
        this.jobLogRepository = jobLogRepository;
    }

    public List<Item> getAllItems(){
       return itemRepository.findAll() ;
    }
    public Page<ItemInventoryDTO> getItemsWithFilter(int itemType, Pageable pageable) {
        Specification<Item> spec = Specification.where(null);

        if(itemType>0){
            spec = spec.and(((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("itemSubType").get("itemType").get("id"),itemType)));
        }

        Page<Item> items=itemRepository.findAll(spec,pageable);

        return items.map(item -> new ItemInventoryDTO(
                item.getId(),
                item.getName(),
                item.getSubType().getName(),
                item.getQuantity()
        ));
    }
    public Item save(ItemCrudDTO dto) {
        Item item=modelMapper.map(dto,Item.class);
        if (!subTypeRepository.existsById(item.getSubType().getId())) {
            throw new ItemSubTypeNotFoundException();
        }
        item.setEnable(true);
        return itemRepository.save(item);
    }

    public Item getItemById(Integer id) {
        return itemRepository.findById(id).orElseThrow(ItemNotFoundException::new);
    }

    public Item editItem(Integer itemId, ItemCrudDTO dto) {
        Item item =modelMapper.map(dto,Item.class);
        item.setId(getItemById(itemId).getId());
        if (!subTypeRepository.existsById(item.getSubType().getId())) {
            throw new ItemSubTypeNotFoundException();
        }
        return itemRepository.save(item);
    }

    public Item deleteItem(Integer itemId) {
        Item itemDisable=getItemById(itemId);
        itemDisable.setEnable(false);
        return itemRepository.save(itemDisable);
    }


    @Scheduled(cron = "0 0 2 * * ?")
    @Transactional
    @PostConstruct
    public void updateStocks(){
        if(jobLogRepository.existsLogByDate(LocalDate.now())==0){
            itemRepository.updatesStocks();
            JobLog jobLog = new JobLog("JobInventoryUpdate", LocalDateTime.now());
            jobLogRepository.save(jobLog);
        }
    }


}
