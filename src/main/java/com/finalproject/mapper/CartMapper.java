package com.finalproject.mapper;

import com.finalproject.domain.Cart;
import com.finalproject.dto.CartDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CartMapper {


    private DishMapper dishMapper;

    public CartDto mapToCartDto(Cart cart) {
        CartDto cartDto = new CartDto(
                cart.getId()
        );
        return cartDto;
    }
}
