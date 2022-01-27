package com.finalproject;

import com.finalproject.data.Menu;
import com.finalproject.service.CartDbService;
import com.finalproject.service.DishDbService;
import com.finalproject.service.GroupDbService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MenuTest {

    @Autowired
    private Menu menu;

    @Autowired
    private DishDbService dishDbService;

    @Autowired
    private GroupDbService groupDbService;

    @Autowired
    private CartDbService cartDbService;


    @Test
    public void runMenu() {
        System.out.println(dishDbService.getAllDishes().size());
        System.out.println(groupDbService.getAllGroups().size());
        menu.prepareDishes();
        System.out.println(dishDbService.getAllDishes().size());
        System.out.println(groupDbService.getAllGroups().size());
    }

    @Test
    public void checkAllRepo() {
        System.out.println(dishDbService.getAllDishes().size());
        System.out.println(groupDbService.getAllGroups().size());
        System.out.println(cartDbService.getAllCarts().size());
    }
}
