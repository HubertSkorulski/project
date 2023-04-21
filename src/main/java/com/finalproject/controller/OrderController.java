package com.finalproject.controller;


import com.finalproject.domain.Cart;
import com.finalproject.domain.Order;
import com.finalproject.domain.RestaurantUser;
import com.finalproject.dto.OrderDto;
import com.finalproject.exception.CartNotFoundException;
import com.finalproject.exception.OrderNotFoundException;
import com.finalproject.exception.UserNotFoundException;
import com.finalproject.mapper.OrderMapper;
import com.finalproject.service.CartDbService;
import com.finalproject.service.OrderDbService;
import com.finalproject.service.UserDbService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("v1/order")
public class OrderController {

    private CartDbService cartDbService;
    private UserDbService userDbService;
    private OrderDbService orderDbService;
    private OrderMapper orderMapper;

    @PostMapping
    public OrderDto createOrder(@RequestParam Long cartId, @RequestParam Long customerId) throws CartNotFoundException, UserNotFoundException {
        Cart cart = cartDbService.getCart(cartId).orElseThrow(CartNotFoundException::new);
        RestaurantUser restaurantUser = userDbService.getUser(customerId).orElseThrow(UserNotFoundException::new);
        Order order = new Order(cart, restaurantUser);

        cart.setOrder(order);
        restaurantUser.getOrders().add(order);

        orderDbService.save(order);
        cartDbService.save(cart);
        userDbService.save(restaurantUser);

        return orderMapper.mapToOrderDto(order);
    }


    @GetMapping
    public OrderDto getOrder(@RequestParam Long orderId) throws OrderNotFoundException {
        Order order = orderDbService.getOrder(orderId).orElseThrow(OrderNotFoundException::new);
        return orderMapper.mapToOrderDto(order);
    }

    @GetMapping(value = "getOrders")
    public List<OrderDto> getAllOrders() {
        List<Order> orders = orderDbService.getAllOrders();
        List<OrderDto> ordersDto = new ArrayList<>();
        for (Order order : orders) {
            ordersDto.add(orderMapper.mapToOrderDto(order));
        }
        return ordersDto;
    }

    //UpdateOrder
    //to jest dobre pytanie co tu zrobić poniewaz update ordera to tak naprawde update carta lub customera

    //DeleteOrder
    @DeleteMapping
    public void deleteOrder(@RequestParam Long orderId) throws OrderNotFoundException, CartNotFoundException, UserNotFoundException {
        Order order = orderDbService.getOrder(orderId).orElseThrow(OrderNotFoundException::new);
        Long customerId = order.getRestaurantUser().getId();
        Long cartId = order.getCart().getId();

        RestaurantUser restaurantUser = userDbService.getUser(customerId).orElseThrow(UserNotFoundException::new);
        Cart cart = cartDbService.getCart(cartId).orElseThrow(CartNotFoundException::new);

        restaurantUser.getOrders().remove(order);
        cart.setOrder(null);

        userDbService.save(restaurantUser);
        cartDbService.save(cart);

        orderDbService.delete(order);
        System.out.println("Order deleted");
        //ładniejszy komunikat by się przydał
    }



}
