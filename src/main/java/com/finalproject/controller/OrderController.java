package com.finalproject.controller;


import com.finalproject.domain.Cart;
import com.finalproject.domain.Customer;
import com.finalproject.domain.Order;
import com.finalproject.dto.OrderDto;
import com.finalproject.exception.CartNotFoundException;
import com.finalproject.exception.CustomerNotFoundException;
import com.finalproject.exception.OrderNotFoundException;
import com.finalproject.mapper.OrderMapper;
import com.finalproject.service.CartDbService;
import com.finalproject.service.CustomerDbService;
import com.finalproject.service.OrderDbService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("v1/order")
public class OrderController {

    private CartDbService cartDbService;
    private CustomerDbService customerDbService;
    private OrderDbService orderDbService;
    private OrderMapper orderMapper;

    @PostMapping
    public OrderDto createOrder(@RequestParam Long cartId, @RequestParam Long customerId) throws CartNotFoundException, CustomerNotFoundException {
        Cart cart = cartDbService.getCart(cartId).orElseThrow(CartNotFoundException::new);
        Customer customer = customerDbService.getCustomer(customerId).orElseThrow(CustomerNotFoundException::new);
        Order order = new Order(cart,customer);
        cart.setOrder(order);
        customer.getOrders().add(order);
        orderDbService.save(order);
        cartDbService.save(cart);
        customerDbService.save(customer);
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
    public void deleteOrder(@RequestParam Long orderId) throws OrderNotFoundException, CartNotFoundException, CustomerNotFoundException {
        Order order = orderDbService.getOrder(orderId).orElseThrow(OrderNotFoundException::new);
        Long customerId = order.getCustomer().getId();
        Long cartId = order.getCart().getId();

        Customer customer = customerDbService.getCustomer(customerId).orElseThrow(CustomerNotFoundException::new);
        Cart cart = cartDbService.getCart(cartId).orElseThrow(CartNotFoundException::new);

        customer.getOrders().remove(order);
        cart.setOrder(null);

        customerDbService.save(customer);
        cartDbService.save(cart);

        orderDbService.delete(order);
        System.out.println("Order deleted");
        //ładniejszy komunikat by się przydał
    }



}
