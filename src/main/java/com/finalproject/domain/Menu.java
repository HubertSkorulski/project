package com.finalproject.domain;

import com.finalproject.dao.GroupRepository;
import com.finalproject.dto.DishDto;
import com.finalproject.mapper.DishMapper;
import com.finalproject.service.DishDbService;
import com.finalproject.service.GroupDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class Menu {

    private final GroupDbService groupDbService;
    private final DishDbService dishDbService;
    private final GroupRepository groupRepository;
    private final DishMapper dishMapper;

    public void prepareDishes() {
        Group group = new Group("Pierwsza grupa");
        Group group2 = new Group("Druga grupa");
        groupDbService.save(group);
        groupDbService.save(group2);
        Group groupFromDb = groupRepository.findAll().get(0);
        Group groupFromDb2 = groupRepository.findAll().get(1);

        Dish dish = new Dish("First Dish", 9.99,groupFromDb);
        Dish dish2 = new Dish("Second Dish", 11.99, groupFromDb);
        Dish dish3 = new Dish("Third", 12.99, groupFromDb2);
        Dish dish4 = new Dish("Fourth", 12.99, groupFromDb);
        Dish dish5 = new Dish("Fifth", 12.99, groupFromDb2);
        Dish dish6 = new Dish("Sixth", 12.99, groupFromDb);
        Dish dish7 = new Dish("Seventh", 12.99, groupFromDb2);
        Dish dish8 = new Dish("Eighth", 12.99, groupFromDb);
        Dish dish9 = new Dish("Ninth", 12.99, groupFromDb2);
        Dish dish10 = new Dish("Tenth", 12.99, groupFromDb);
        Dish dish11 = new Dish("Eleventh", 12.99, groupFromDb2);
        Dish dish12 = new Dish("Twelfth", 12.99, groupFromDb);
        group.addDishToGroup(dish);
        group.addDishToGroup(dish2);
        group.addDishToGroup(dish4);
        group.addDishToGroup(dish6);
        group.addDishToGroup(dish8);
        group.addDishToGroup(dish10);
        group.addDishToGroup(dish4);
        group.addDishToGroup(dish12);

        group2.addDishToGroup(dish3);
        group2.addDishToGroup(dish5);
        group2.addDishToGroup(dish7);
        group2.addDishToGroup(dish9);
        group2.addDishToGroup(dish11);

        dishDbService.save(dish);
        dishDbService.save(dish2);
        dishDbService.save(dish3);
        dishDbService.save(dish4);
        dishDbService.save(dish5);
        dishDbService.save(dish6);
        dishDbService.save(dish7);
        dishDbService.save(dish8);
        dishDbService.save(dish9);
        dishDbService.save(dish10);
        dishDbService.save(dish11);
        dishDbService.save(dish12);

        groupDbService.save(groupFromDb);
        groupDbService.save(groupFromDb2);
    }
}
