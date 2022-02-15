package com.finalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CartRowDto {

    private final String dishName;
    private final int quantity;
    private final double price;
    private final double unitPrice;


}
