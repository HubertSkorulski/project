package com.finalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomerDto {
    private Long id;
    private String name;
    private String surname;
    private String emailAddress;
}
