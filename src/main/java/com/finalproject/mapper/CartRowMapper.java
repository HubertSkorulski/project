package com.finalproject.mapper;

import com.finalproject.domain.Cart;
import com.finalproject.domain.CartRow;
import com.finalproject.dto.CartRowDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartRowMapper {

    private DishMapper dishMapper;

    public CartRowDto mapToCartRowDto(CartRow cartRow) {
        return new CartRowDto(
                cartRow.getDish().getName(),
                cartRow.getQuantity(),
                cartRow.getPrice(),
                cartRow.getUnitPrice()
        );
    }

    public List<CartRowDto> mapToCartRowDtoList(List<CartRow> cartRows) {
        List<CartRowDto> cartRowDtos = new ArrayList<CartRowDto>();

        for(CartRow cartRow : cartRows) {
            cartRowDtos.add(mapToCartRowDto(cartRow));
        }

        return cartRowDtos;
    }
}
