package com.finalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DishDto {
    private Long id;

    @NotNull
    private String name;
    @NotNull
    private Double price;
    @NotNull
    private Long groupId;
}
