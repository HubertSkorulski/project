package com.finalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DishDto {
    private Long id;
    private String name;
    private Double price;
}
