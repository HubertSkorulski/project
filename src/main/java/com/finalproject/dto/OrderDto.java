package com.finalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OrderDto {
    private Long id;
    private Long cartId;
    private Long customerId;
    private String status;
}
