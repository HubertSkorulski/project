package com.finalproject.controller;


import com.finalproject.domain.Dish;
import com.finalproject.domain.Group;
import com.finalproject.dto.DishDto;
import com.finalproject.exception.DishNotFoundException;
import com.finalproject.exception.GroupNotFoundException;
import com.finalproject.service.GroupDbService;
import lombok.AllArgsConstructor;
import com.finalproject.mapper.DishMapper;
import org.springframework.web.bind.annotation.*;
import com.finalproject.service.DishDbService;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("v1/dish")
public class DishController {

    private DishDbService dishDbService;
    private DishMapper dishMapper;
    private GroupDbService groupDbService;

    @GetMapping
    public DishDto getDish(Long dishId) throws DishNotFoundException {
        Dish dish = dishDbService.getDish(dishId).orElseThrow(DishNotFoundException::new);
        return dishMapper.mapToDishDto(dish);
    }

    @PostMapping("/{name}/{price}/{groupId}")
    public DishDto createDish(@PathVariable java.lang.String name, @PathVariable Double price, @PathVariable Long groupId) throws GroupNotFoundException {
        Group group = groupDbService.getGroup(groupId).orElseThrow(GroupNotFoundException::new);
        Dish dish = new Dish(name,price, group);
        dishDbService.save(dish);
        return dishMapper.mapToDishDto(dish);
    }


    @PutMapping("/{dishId}/{name}/{price}/{groupId}")
    public DishDto updateDish(@PathVariable Long dishId, @PathVariable String name, @PathVariable double price, @PathVariable Long groupId) throws DishNotFoundException, GroupNotFoundException {
        Dish dish = dishDbService.getDish(dishId).orElseThrow(DishNotFoundException::new);
        Group group = groupDbService.getGroup(groupId).orElseThrow(GroupNotFoundException::new);
        dish.setName(name);
        dish.setPrice(price);
        dish.setGroup(group);
        dishDbService.save(dish);
        return dishMapper.mapToDishDto(dish);
    }

    @DeleteMapping("/{dishId}")
    public void deleteDish(@PathVariable Long dishId) throws DishNotFoundException{
        Dish dish = dishDbService.getDish(dishId).orElseThrow(DishNotFoundException::new);
        //zabrac z listy w Group
        //zabrać z listy w Carts

        dishDbService.delete(dish);
        //i tu tez sprawdzic czy nie powinienem usunac wszystkich cartów z disha i grup
    }

    @GetMapping(value = "getDishes")
    public List<DishDto> getDishes() {
        List<Dish> dishes = dishDbService.getAllDishes();
        return dishMapper.mapToDishDtoList(dishes);
    }

    @GetMapping(value = "dishesInGroup/{groupId}")
    public List<DishDto> getDishesWithGroup(@PathVariable Long groupId) {
        List<Dish> dishesFromGroup = dishDbService.getDishesFromGroup(groupId);
        return dishMapper.mapToDishDtoList(dishesFromGroup);
    }

}
