package com.finalproject.mapper;


import com.finalproject.domain.Cart;
import com.finalproject.dto.CartDto;
import com.finalproject.service.CartDbService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class CartMapperTestSuite {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private CartDbService cartDbService;

    @Test
    void mapToCartDtoTest() {
        //Given
        Cart cart = new Cart();
        cartDbService.save(cart);
        //When
        CartDto cartDto = cartMapper.mapToCartDto(cart);
        //Then
        Assertions.assertEquals(cart.getId(),cartDto.getId());

        //CleanUp
        cartDbService.deleteAllCarts();
    }

    @Test
    void mapToCartDtoListTest() {
        //Given
        List<Cart> carts = new ArrayList<Cart>();
        Cart cart = new Cart();
        Cart cart2 = new Cart();
        cartDbService.save(cart);
        cartDbService.save(cart2);
        carts.add(cart);
        carts.add(cart2);
        //When
        List<CartDto> cartsDto = cartMapper.mapToCartDtoList(carts);
        //Then
        Assertions.assertEquals(cart.getId(),cartsDto.get(0).getId());
        Assertions.assertEquals(cart2.getId(),cartsDto.get(1).getId());

        //CleanUp
        cartDbService.deleteAllCarts();
    }
}
