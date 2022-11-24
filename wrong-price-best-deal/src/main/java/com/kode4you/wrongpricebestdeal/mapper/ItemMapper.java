package com.kode4you.wrongpricebestdeal.mapper;

import com.kode4you.wrongpricebestdeal.domain.dto.ItemDTO;
import com.kode4you.wrongpricebestdeal.domain.entity.ItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ItemPriceMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ItemMapper {

    ItemDTO itemEntityToItemDto(ItemEntity item);

    ItemEntity itemDTOToItemEntity(ItemDTO itemDTO);

    List<ItemDTO> itemEntitesToItemDtos(List<ItemEntity> itemList);

    List<ItemEntity> itemDtosToItemEntities(List<ItemDTO> itemDTOList);
}
