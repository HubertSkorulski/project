package com.finalproject.mapper;

import com.finalproject.domain.Cart;
import com.finalproject.dto.CartDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CartMapper {

    public CartDto mapToCartDto(Cart cart) {
        return new CartDto(
                cart.getId()
        );
    }

    public List<CartDto> mapToCartDtoList(List<Cart> carts) {
        List<CartDto> cartDtos = new ArrayList<CartDto>();

        for (Cart cart : carts) {
            cartDtos.add(mapToCartDto(cart));
        }
        return cartDtos;
    }
}
