package com.kode4you.wrongpricebestdeal.domain.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "CATEGORY")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;
}
