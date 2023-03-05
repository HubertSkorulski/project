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
import com.finalproject.mapper.DishMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.finalproject.service.DishDbService;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("v1/dish")
@CrossOrigin
public class DishController {

    private final DishDbService dishDbService;
    private final DishMapper dishMapper;
    private final GroupDbService groupDbService;
    private final CartDbService cartDbService;
    private final Menu menu;

    @GetMapping("/{dishId}")
    public DishDto getDish(@PathVariable Long dishId) throws DishNotFoundException {
        Dish dish = dishDbService.getDish(dishId).orElseThrow(DishNotFoundException::new);
        return dishMapper.mapToDishDto(dish);
    }

    @PostMapping("/")
    public DishDto createDish(@Valid @RequestBody DishDto dishDto) throws GroupNotFoundException {
        Group group = groupDbService.getGroup(dishDto.getGroupId()).orElseThrow(GroupNotFoundException::new);
        Dish dish = dishMapper.mapToDish(dishDto, group);
        dishDbService.save(dish);
        return dishMapper.mapToDishDto(dish);
    }


    @PutMapping("/")
    public DishDto updateDish(@Valid @RequestBody DishDto dishDto) throws GroupNotFoundException, DishNotFoundException {
        Group group = groupDbService.getGroup(dishDto.getGroupId()).orElseThrow(GroupNotFoundException::new);
        Dish dish = dishDbService.getDish(dishDto.getId()).orElseThrow(DishNotFoundException::new);
        dish.update(dishDto.getName(), dishDto.getPrice(), group);
        dishDbService.save(dish);
        return dishMapper.mapToDishDto(dish);
    }

    @DeleteMapping("/{dishId}") //TO DO sprawdzić w sumie czy to na pewno tak ma byc tj powiązania w encjach
    public void deleteDish(@PathVariable Long dishId) throws DishNotFoundException {
        Dish dish = dishDbService.getDish(dishId).orElseThrow(DishNotFoundException::new);
        dish.prepareCartsForDishDeletion();
        for (Cart cart : dish.getCarts()) {
            cartDbService.save(cart);
        }
        dishDbService.delete(dish);
    }

    @GetMapping(value = "/getDishes")
    public List<DishDto> getDishes() {
        List<Dish> dishes = dishDbService.getAllDishes();
        return dishMapper.mapToDishDtoList(dishes);
    }

    @GetMapping(value = "/dishesInGroup/{groupId}")
    public List<DishDto> getDishesWithGroup(@PathVariable Long groupId) {
        List<Dish> dishesFromGroup = dishDbService.getDishesFromGroup(groupId);
        return dishMapper.mapToDishDtoList(dishesFromGroup);
    }

    @GetMapping(value = "/prepareDishes")
    public void prepareDishes () {
        menu.prepareDishes();
    }

}
