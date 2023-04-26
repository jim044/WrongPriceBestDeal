package com.kode4you.wrongpricebestdeal.mapper;

import com.kode4you.wrongpricebestdeal.domain.dto.CategoryDTO;
import com.kode4you.wrongpricebestdeal.domain.dto.ItemDTO;
import com.kode4you.wrongpricebestdeal.domain.entity.CategoryEntity;
import com.kode4you.wrongpricebestdeal.domain.entity.ItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

    CategoryDTO categoryEntityToCategoryDTO(CategoryEntity entity);

    CategoryEntity categoryDTOToCategoryEntity(CategoryDTO categoryDTO);

    List<CategoryDTO> categoryEntitiesToCategoryDTOs(List<CategoryEntity> itemList);

    List<CategoryEntity> categoryDTOsToCategoryEntities(List<CategoryDTO> itemDTOList);
}
