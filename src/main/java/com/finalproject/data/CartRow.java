package com.finalproject.data;

import com.finalproject.domain.Dish;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
public class CartRow {

    private Dish dish;
    private int quantity;
    private double price;

    public CartRow(Dish dish, int quantity) {
        this.dish = dish;
        this.quantity = quantity;
        this.price = quantity * dish.getPrice();
    }
}
