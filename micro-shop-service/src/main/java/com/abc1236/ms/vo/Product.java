package com.abc1236.ms.vo;

import lombok.Data;

@Data
public class Product {
    private Long id;
    private String name;
    private String img;
    public Product(){

    }

    public Product(Long id, String name, String img) {
        this.id = id;
        this.name = name;
        this.img = img;
    }
}
