package com.finalproject.domain;

import com.finalproject.domain.Dish;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class CartRow {

    private Dish dish;
    private int quantity;
    private double price;
    private double unitPrice;


    public CartRow(Dish dish, int quantity) {
        this.dish = dish;
        this.quantity = quantity;
        this.price = quantity * dish.getPrice();
        this.unitPrice = dish.getPrice();
    }
}
