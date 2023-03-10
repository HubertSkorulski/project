package com.finalproject.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {

    ACTIVE("ACTIVE"), PAID("PAID");

    private final String value;
    

}
