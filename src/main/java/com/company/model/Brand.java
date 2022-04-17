package com.company.model;

import lombok.*;

// PROJECT NAME shop_bot
// TIME 14:15
// MONTH 04
// DAY 12
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Brand {
    private Integer id;
//    private Integer brandId;
    private String name;
    private Product product ;
    private String image;

    private static int generalId = 0;

    public Brand(String name) {
        this.id = ++generalId;
        this.name = name;
    }
}
