package com.finalproject.service;

import com.finalproject.dao.DishRepository;
import com.finalproject.dao.GroupRepository;
import com.finalproject.domain.Dish;
import com.finalproject.domain.Menu;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MenuTestSuite {

    @Autowired
    private Menu menu;

    @Autowired
    private DishDbService dishDbService;

    @Autowired
    private GroupDbService groupDbService;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private DishRepository dishRepository;


    @Test
    public void prepareMenuTest() {
        //Given
        //When
        menu.prepareDishes();
        //Then
        Assertions.assertEquals(12,dishDbService.getAllDishes().size());
        Assertions.assertEquals(2,groupDbService.getAllGroups().size());
        //CleanUp
        for (Dish dish:dishDbService.getAllDishes()) {
            dish.setGroup(null);
            dishRepository.save(dish);
        }

        groupRepository.deleteAll();
        dishRepository.deleteAll();
    }

}
