package com.kode4you.wrongpricebestdeal.mapper;

import com.kode4you.wrongpricebestdeal.domain.dto.ItemPriceDTO;
import com.kode4you.wrongpricebestdeal.domain.entity.ItemPriceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ItemMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ItemPriceMapper {

    ItemPriceDTO itemPriceEntityToItemPriceDto(ItemPriceEntity itemPrice);

    ItemPriceEntity itemPriceDTOToItemPriceEntity(ItemPriceDTO itemPriceDTO);

    List<ItemPriceDTO> itemPriceEntitesToItemPriceDtos(List<ItemPriceEntity> itemPriceList);

    List<ItemPriceEntity> itemPriceDtosToItemPriceEntities(List<ItemPriceDTO> itemPriceDTOList);

}
