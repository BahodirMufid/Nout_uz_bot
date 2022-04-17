package com.company.model;

import com.company.enums.CustomerStatus;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Customer {
    private String id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private CustomerStatus status;
}
