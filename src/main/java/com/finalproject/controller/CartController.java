package com.finalproject.controller;

import com.finalproject.domain.Cart;
import com.finalproject.domain.Dish;
import com.finalproject.dto.CartDto;
import com.finalproject.dto.CartRowDto;
import com.finalproject.dto.DishDto;
import com.finalproject.exception.CartNotFoundException;
import com.finalproject.exception.DishNotFoundException;
import com.finalproject.mapper.CartMapper;
import com.finalproject.mapper.CartRowMapper;
import com.finalproject.mapper.DishMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import com.finalproject.service.CartDbService;
import com.finalproject.service.DishDbService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/cart")
@CrossOrigin
public class CartController {

    private final CartMapper cartMapper;
    private final DishMapper dishMapper;
    private final DishDbService dishDbService;
    private final CartRowMapper cartRowMapper;
    private final CartDbService cartDbService;

    @GetMapping("create")
    public CartDto createCart() {
        Cart cart = new Cart();
        cartDbService.save(cart);
        log.info("new cart" + cart.getId());
        return cartMapper.mapToCartDto(cart);
    }

    @GetMapping("getProducts/{cartId}")
    public List<DishDto> getProducts(@PathVariable Long cartId) throws CartNotFoundException  {
        Cart cart = cartDbService.getCart(cartId).orElseThrow(CartNotFoundException::new);
        return dishMapper.mapToDishDtoList(cart.getChosenDishes());
    }

    @PutMapping("addProduct/{dishName}/{cartId}/{quantity}")
    public List<CartRowDto> addProductToCart(@PathVariable String dishName, @PathVariable Long cartId, @PathVariable int quantity) throws CartNotFoundException, DishNotFoundException {
        Cart cart = cartDbService.getCart(cartId).orElseThrow(CartNotFoundException::new);
        Dish dish = dishDbService.getDishByName(dishName).orElseThrow(DishNotFoundException::new);

        cart.addDish(dish,quantity);
        log.info("Adding " + dishName + " to " + cart.getId());
        cartDbService.save(cart);
        dishDbService.save(dish);

        return cartRowMapper.mapToCartRowDtoList(cart.prepareCartDisplay());
    }

    @PutMapping("removeDish/{cartId}/{dishName}")
    public void removeDishFromCart(@PathVariable Long cartId, @PathVariable String dishName) throws CartNotFoundException, DishNotFoundException {
        Cart cart = cartDbService.getCart(cartId).orElseThrow(CartNotFoundException::new);
        Dish dish = dishDbService.getDishByName(dishName).orElseThrow(DishNotFoundException::new);

        cart.removeDishQuantity(dish,cart.countServings(dish));

        cartDbService.save(cart);
        dishDbService.save(dish);
    }

    @PutMapping("updateCart/{cartId}/{dishName}/{newQuantity}")
    public void updateDishServingsInCart(@PathVariable Long cartId, @PathVariable String dishName, @PathVariable int newQuantity) throws CartNotFoundException, DishNotFoundException {
        Cart cart = cartDbService.getCart(cartId).orElseThrow(CartNotFoundException::new);
        Dish dish = dishDbService.getDishByName(dishName).orElseThrow(DishNotFoundException::new);

        cart.updateQuantityInCart(dish,newQuantity);

        cartDbService.save(cart);
        dishDbService.save(dish);
    }


    @GetMapping("summary/{cartId}")
    public List<CartRowDto> getCartSummary(@PathVariable Long cartId) throws CartNotFoundException {
        Cart cart = cartDbService.getCart(cartId).orElseThrow(CartNotFoundException::new);
        return cartRowMapper.mapToCartRowDtoList(cart.prepareCartDisplay());
    }

    @GetMapping("totalCost/{cartId}")
    public double getTotalCost(@PathVariable Long cartId) throws CartNotFoundException {
        Cart cart = cartDbService.getCart(cartId).orElseThrow(CartNotFoundException::new);
        return cart.totalCost();
    }

    @GetMapping("all")
    public List<CartDto> getAllCarts() {
        List<Cart> carts = cartDbService.getAllCarts();
        return cartMapper.mapToCartDtoList(carts);
    }

    @GetMapping("/{cartId}")
    public CartDto getCart(@PathVariable Long cartId) throws CartNotFoundException {
        Cart cart = cartDbService.getCart(cartId).orElseThrow(CartNotFoundException::new);
        return cartMapper.mapToCartDto(cart);
    }

    @DeleteMapping("/cartId")
    public void deleteCart(@PathVariable Long cartId) throws Exception {
        cartDbService.delete(cartId);
    }

}
