package com.finalproject.domain;

import com.finalproject.dao.DishRepository;
import com.finalproject.dao.GroupRepository;
import com.finalproject.domain.Dish;
import com.finalproject.domain.Group;
import com.finalproject.service.DishDbService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class DishEntityTestSuite {

    @Autowired
    private DishRepository dishRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private DishDbService dishDbService;

    @Test
    public void createDishTest() {
        //Given
        Group group = new Group("Test group");
        Dish dish = new Dish("Test dish", 9.99,group);
        //When
        groupRepository.save(group);
        dishRepository.save(dish);
        //Then
        assertEquals(1,dishRepository.findAll().size());
        assertEquals(1,groupRepository.findAll().size());

        //CleanUp
        Dish dishToDelete = dishRepository.findAll().get(0);
        dishToDelete.setGroup(null);
        dishRepository.save(dishToDelete);
        dishRepository.deleteAll();
        groupRepository.deleteAll();
    }


    @Test
    public void updateDish() {
        //Given
        Group group = new Group("Test group");
        Group secondGroup = new Group("New group");
        Dish dish = new Dish("Test dish", 9.99,group);
        groupRepository.save(secondGroup);
        groupRepository.save(group);
        dishRepository.save(dish);
        //When
        Dish dishFromDb = dishRepository.findAll().get(0);
        dishFromDb.setGroup(secondGroup);
        dishFromDb.setName("New name");
        dishFromDb.setPrice(11.00);
        dishRepository.save(dishFromDb);
        //Then
        Dish updatedDish = dishRepository.findAll().get(0);
        assertEquals("New group",updatedDish.getGroup().getGroupName());
        assertEquals("New name",updatedDish.getName());
        assertEquals(11.00,updatedDish.getPrice());

        //CleanUp
        Dish dishToDelete = dishRepository.findAll().get(0);
        dishToDelete.setGroup(null);
        dishRepository.save(dishToDelete);
        dishRepository.deleteAll();
        groupRepository.deleteAll();
    }

    @Test
    public void deleteDish() {
        //Given
        Group group = new Group("Test group");
        Dish dish = new Dish("Test dish", 9.99,group);
        group.addDishToGroup(dish);
        groupRepository.save(group);
        dishRepository.save(dish);
        //When
        Group groupFromDb = groupRepository.findAll().get(0);
        groupFromDb.getDishList().remove(0);
        groupRepository.save(groupFromDb);
        Dish dishFromDb = dishRepository.findAll().get(0);
        dishFromDb.setGroup(null);
        dishRepository.save(dishFromDb);
        dishRepository.delete(dishRepository.findAll().get(0));
        //Then
        assertEquals(0,dishRepository.findAll().size());
        assertEquals(1,groupRepository.findAll().size());
        //CleanUp
        groupRepository.deleteAll();
    }

    @Test
    public void dishesFromGroupTest() {
        //Given
        Group group = new Group("Test group");
        Group secondGroup = new Group("New group");
        Dish dish = new Dish("Test dish", 9.99,group);
        Dish secondDish = new Dish("Second Dish", 1.22, secondGroup);
        groupRepository.save(group);
        groupRepository.save(secondGroup);
        dishRepository.save(dish);
        dishRepository.save(secondDish);
        //When
        List<Dish> dishesFromFirstGroup = dishDbService.getDishesFromGroup(group.getId());
        List<Dish> dishesFromSecondGroup = dishDbService.getDishesFromGroup(secondGroup.getId());
        //Then
        assertEquals(1,dishesFromFirstGroup.size());
        assertEquals(1,dishesFromSecondGroup.size());
        assertEquals("Test dish", dishesFromFirstGroup.get(0).getName());
        assertEquals(2,dishRepository.findAll().size());

        //CleanUp
        dish.setGroup(null);
        secondDish.setGroup(null);
        dishRepository.save(dish);
        dishRepository.save(secondDish);
        groupRepository.deleteAll();
        dishRepository.deleteAll();
    }

}
