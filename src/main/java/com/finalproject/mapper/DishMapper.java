package com.finalproject.mapper;

import com.finalproject.domain.Dish;
import com.finalproject.domain.Group;
import com.finalproject.dto.DishDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class DishMapper {

    public List<DishDto> mapToDishDtoList(List<Dish> dishList) {
        List<DishDto> dishDtoList = new ArrayList<>();
        for (Dish dish:dishList){
            dishDtoList.add(mapToDishDto(dish));
        }
        return dishDtoList;
    }

    public DishDto mapToDishDto(Dish dish) {
        DishDto dishDto = new DishDto(
                dish.getId(),
                dish.getName(),
                dish.getPrice(),
                dish.getGroup().getId()
        );
        return dishDto;
    }

    public Dish mapToDish(DishDto dishDto, Group group) {
        return new Dish(dishDto.getName(),dishDto.getPrice(),group);
    }
}
