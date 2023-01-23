package com.finalproject.domain;

import com.finalproject.service.DishDbService;
import com.finalproject.service.GroupDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;


@Service
@RequiredArgsConstructor
public class Menu {

    private final GroupDbService groupDbService;
    private final DishDbService dishDbService;

    public void prepareDishes() {

        Group group = new Group("Pierwsza grupa");
        Group group2 = new Group("Druga grupa");
        groupDbService.save(group);
        groupDbService.save(group2);

        Dish dish = new Dish("First Dish", 9.99,group);
        Dish dish2 = new Dish("Second Dish", 11.99, group);
        Dish dish3 = new Dish("Third", 12.99, group2);
        Dish dish4 = new Dish("Fourth", 12.99, group);
        Dish dish5 = new Dish("Fifth", 12.99, group2);
        Dish dish6 = new Dish("Sixth", 12.99, group);
        Dish dish7 = new Dish("Seventh", 12.99, group);
        Dish dish8 = new Dish("Eighth", 12.99, group);
        Dish dish9 = new Dish("Ninth", 12.99, group2);
        Dish dish10 = new Dish("Tenth", 12.99, group);
        Dish dish11 = new Dish("Eleventh", 12.99, group2);
        Dish dish12 = new Dish("Twelfth", 142.99, group);
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

        groupDbService.save(group);
        groupDbService.save(group2);
    }
}
