package com.finalproject.mapper;

import com.finalproject.domain.RestaurantUser;
import com.finalproject.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class UserMapper {

    public UserDto mapToCustomerDto(RestaurantUser restaurantUser) {
        return new UserDto(
                restaurantUser.getId(),
                restaurantUser.getUsername()
        );
    }

    public List<UserDto> mapToCustomerDtoList(List<RestaurantUser> restaurantUsers) {
        List<UserDto> customersDto = new ArrayList<>();
        for (RestaurantUser restaurantUser : restaurantUsers) {
            customersDto.add(mapToCustomerDto(restaurantUser));
        }
        return customersDto;
    }
}
