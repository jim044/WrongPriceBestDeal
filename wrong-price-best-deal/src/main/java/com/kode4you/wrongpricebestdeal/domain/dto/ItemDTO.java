package com.kode4you.wrongpricebestdeal.domain.dto;

import lombok.Data;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ItemDTO {

    private String name;

    private String imageLink;

    private String asin;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    private List<ItemPriceDTO> itemPriceList;
}
