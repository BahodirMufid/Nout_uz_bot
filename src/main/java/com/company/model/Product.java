package com.company.model;

import lombok.*;

@Getter
@Setter
@ToString
public class Product {
    private Integer id;
    private Integer categoryId;
    private Integer brandId;
    private String name;
    private Double price;
    private String image;
    private boolean deleted = false;
    private boolean activated = false;

    private static int generalId = 0;

    public Product(Integer id, Integer brandId, String name, Double price, String image, boolean deleted) {
        this.id = id;
        this.brandId = brandId;
        this.name = name;
        this.price = price;
        this.image = image;
        this.deleted = deleted;
    }
}
