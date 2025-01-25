package com.rx.MogInventory.config;

import com.rx.MogInventory.entity.Item;
import com.rx.MogInventory.entity.ItemSubType;
import com.rx.MogInventory.entity.dto.ItemCrudDTO;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {



    @Bean
    public ModelMapper modelMapper(){

        // Customizacion de mapeo de un campo de esta forma podemos configurar los campos que del DTO que no sean totalmente iguales a la entidad pero si equivalentes
        ModelMapper modelMapper = new ModelMapper();
        Converter<Integer, ItemSubType> subTypeId = context ->
                context.getSource() == null ? null : new ItemSubType(context.getSource());
        modelMapper.typeMap(ItemCrudDTO.class, Item.class).addMappings(mapper ->
                mapper.using(subTypeId).map(ItemCrudDTO::getSubType, Item::setSubType)
        );

        return modelMapper;
    }
}
