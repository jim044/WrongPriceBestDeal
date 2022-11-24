package com.kode4you.wrongpricebestdeal.dao;

import com.kode4you.wrongpricebestdeal.domain.dto.ItemDTO;
import com.kode4you.wrongpricebestdeal.domain.entity.ItemEntity;
import com.kode4you.wrongpricebestdeal.mapper.ItemMapper;
import com.kode4you.wrongpricebestdeal.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ItemDao {

    @Autowired
    public ItemMapper itemMapper;
    @Autowired
    public ItemRepository itemRepository;

    public List<ItemDTO> saveItem(List<ItemDTO> itemDTOList){
        List<ItemEntity> itemEntityList = itemMapper.itemDtosToItemEntities(itemDTOList);
        itemEntityList.forEach(itemEntity -> {
            itemEntity.getItemPriceList().forEach(itemPriceEntity -> {
                itemPriceEntity.setItem(itemEntity);
            });
        });
        return itemMapper.itemEntitesToItemDtos(itemRepository.saveAll(itemEntityList));
    }

    public ItemDTO findItemByAsin(String asin){
        return itemMapper.itemEntityToItemDto(itemRepository.findByAsin(asin));
    }
}
