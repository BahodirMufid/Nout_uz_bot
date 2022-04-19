package com.company.model;

import lombok.*;

@Getter
@Setter
@ToString
public class Product {
    private Integer id;
    private Integer categoryId;
    private String name;
    private Double price;
    private String image;
    private String description;
    private boolean deleted = false;
    private boolean activated = false;

    private static int generalId = 0;

    public Product(Integer id, Integer categoryId, String name, Double price, String image,String description, boolean deleted) {
        this.id = id;
        this.categoryId = categoryId;
        this.name = name;
        this.price = price;
        this.image = image;
        this.deleted = deleted;
        this.description = description;
    }
}
