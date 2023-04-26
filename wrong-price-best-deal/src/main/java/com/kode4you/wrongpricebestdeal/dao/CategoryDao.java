
package com.kode4you.wrongpricebestdeal.dao;

import com.kode4you.wrongpricebestdeal.domain.dto.CategoryDTO;
import com.kode4you.wrongpricebestdeal.domain.dto.ItemDTO;
import com.kode4you.wrongpricebestdeal.domain.entity.ItemEntity;
import com.kode4you.wrongpricebestdeal.mapper.CategoryMapper;
import com.kode4you.wrongpricebestdeal.mapper.ItemMapper;
import com.kode4you.wrongpricebestdeal.repository.CategoryRepository;
import com.kode4you.wrongpricebestdeal.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryDao {

    @Autowired
    public CategoryMapper categoryMapper;
    @Autowired
    public CategoryRepository categoryRepository;

    public List<CategoryDTO> findAll(){
        return categoryMapper.categoryEntitiesToCategoryDTOs(categoryRepository.findAll());
    }
}
