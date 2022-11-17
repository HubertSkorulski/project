package com.finalproject.controller;


import com.finalproject.domain.Cart;
import com.finalproject.domain.Dish;
import com.finalproject.domain.Group;
import com.finalproject.domain.Menu;
import com.finalproject.dto.DishDto;
import com.finalproject.exception.DishNotFoundException;
import com.finalproject.exception.GroupNotFoundException;
import com.finalproject.service.CartDbService;
import com.finalproject.service.GroupDbService;
import lombok.AllArgsConstructor;
import com.finalproject.mapper.DishMapper;
import org.springframework.web.bind.annotation.*;
import com.finalproject.service.DishDbService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("v1/dish")
public class DishController {

    private DishDbService dishDbService;
    private DishMapper dishMapper;
    private GroupDbService groupDbService;
    private CartDbService cartDbService;
    private Menu menu;

    @GetMapping("/{dishId}")
    public DishDto getDish(@PathVariable Long dishId) throws DishNotFoundException {
        Dish dish = dishDbService.getDish(dishId).orElseThrow(DishNotFoundException::new);
        return dishMapper.mapToDishDto(dish);
    }

    @PostMapping("/{name}/{price}/{groupId}")
    public DishDto createDish(@PathVariable String name, @PathVariable Double price, @PathVariable Long groupId) throws GroupNotFoundException {
        Group group = groupDbService.getGroup(groupId).orElseThrow(GroupNotFoundException::new);
        Dish dish = new Dish(name,price, group);
        dishDbService.save(dish);
        return dishMapper.mapToDishDto(dish);
    }


    @PutMapping("/{dishId}/{name}/{price}/{groupId}")
    public DishDto updateDish(@PathVariable Long dishId, @PathVariable String name, @PathVariable double price, @PathVariable Long groupId) throws DishNotFoundException, GroupNotFoundException {
        Dish dish = dishDbService.getDish(dishId).orElseThrow(DishNotFoundException::new);
        Group group = groupDbService.getGroup(groupId).orElseThrow(GroupNotFoundException::new);
        dish.update(name,price,group);
        dishDbService.save(dish);
        return dishMapper.mapToDishDto(dish);
    }

    @DeleteMapping("/{dishId}")
    public void deleteDish(@PathVariable Long dishId) throws DishNotFoundException {
        Dish dish = dishDbService.getDish(dishId).orElseThrow(DishNotFoundException::new);
        dish.prepareCartsForDishDeletion();
        for (Cart cart : dish.getCarts()) {
            cartDbService.save(cart);
        }
        dishDbService.delete(dish);
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

    @GetMapping(value = "prepareDishes")
    public void prepareDishes () {
        menu.prepareDishes();
    }

}
