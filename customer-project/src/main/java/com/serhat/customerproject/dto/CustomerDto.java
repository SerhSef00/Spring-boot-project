package com.serhat.customerproject.dto;

import lombok.Data;

@Data
public class CustomerDto {
    private Long id;
    private String firstName;
    private String lastName;
    private int phoneNumber;
}
