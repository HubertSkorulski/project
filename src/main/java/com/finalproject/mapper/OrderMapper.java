package com.finalproject.mapper;

import com.finalproject.domain.Order;
import com.finalproject.dto.OrderDto;
import org.springframework.stereotype.Service;

@Service
public class OrderMapper {

    public OrderDto mapToOrderDto(Order order) {
        return new OrderDto(
                order.getId(),
                order.getCart().getId(),
                order.getRestaurantUser().getId(),
                order.getStatus().getValue()
        );
    }
}
