package com.finalproject.controller;

import com.finalproject.domain.Cart;
import com.finalproject.domain.Dish;
import com.finalproject.dto.CartDto;
import com.finalproject.dto.DishDto;
import com.finalproject.exception.CartNotFoundException;
import com.finalproject.exception.DishNotFoundException;
import com.finalproject.mapper.CartMapper;
import com.finalproject.mapper.DishMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.finalproject.service.CartDbService;
import com.finalproject.service.DishDbService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("v1/cart")
public class CartController {

    private final CartDbService cartDbService;
    private final CartMapper cartMapper;
    private final DishMapper dishMapper;
    private final DishDbService dishDbService;

    @PostMapping("createCart")
    public CartDto createCart() {
        Cart cart = new Cart();
        cartDbService.save(cart);
        return cartMapper.mapToCartDto(cart);
    }

    @GetMapping("getProductsFromCart")
    public List<DishDto> getProductsFromCart(@RequestParam Long cartId) throws CartNotFoundException  {
        Cart cart = cartDbService.getCart(cartId).orElseThrow(CartNotFoundException::new);
        return dishMapper.mapToDishDtoList(cart.getChosenDishes());
    }

    @PutMapping("addProductToCart")
    public void addProductToCart(@RequestParam Long cartId,@RequestParam Long dishId) throws CartNotFoundException, DishNotFoundException {
        Cart cart = cartDbService.getCart(cartId).orElseThrow(CartNotFoundException::new);
        Dish dish = dishDbService.getDish(dishId).orElseThrow(DishNotFoundException::new);

        cart.getChosenDishes().add(dish);
        dish.getCarts().add(cart);

        cartDbService.save(cart);
        dishDbService.save(dish);
    }

    @PutMapping("removeDishFromCart")
    public void removeDishFromCart(@RequestParam Long cartId, @RequestParam Long dishId) throws CartNotFoundException, DishNotFoundException {
        Cart cart = cartDbService.getCart(cartId).orElseThrow(DishNotFoundException::new);;
        Dish dish = dishDbService.getDish(dishId).orElseThrow(DishNotFoundException::new);;

        if (cart.getChosenDishes().contains(dish) || dish.getCarts().contains(cart)) {
            cart.getChosenDishes().remove(dish);
            dish.getCarts().remove(cart);
        } else {
            System.out.println("There is no such a dish in the cart.");
        }

        cartDbService.save(cart);
        dishDbService.save(dish);
    }

    //GetCarts
    //GetCart
    //DeleteCart

}
