package com.kode4you.wrongpricebestdeal.repository;

import com.kode4you.wrongpricebestdeal.domain.entity.ItemPriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemPriceRepository extends JpaRepository<ItemPriceEntity, Long> {

}
