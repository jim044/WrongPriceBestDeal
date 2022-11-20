package com.kode4you.wrongpricebestdeal.domain.dto;

import lombok.Data;

@Data
public class Item {

    private String name;

    private String imageLink;

    private ItemPrice itemPrice;
}
