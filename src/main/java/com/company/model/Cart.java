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
public class Cart {
    private Integer id;
    private Customer customer;
    private Product product;
    private Integer quantity;


}
