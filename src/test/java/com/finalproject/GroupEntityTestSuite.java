package com.finalproject;

import com.finalproject.dao.DishRepository;
import com.finalproject.dao.GroupRepository;
import com.finalproject.domain.Dish;
import com.finalproject.domain.Group;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class GroupEntityTestSuite {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private DishRepository dishRepository;

    @Test
    public void createGroupTest() {
        //Given
        Group group = new Group("Test group");
        //When
        groupRepository.save(group);
        //Then
        int groups = groupRepository.findAll().size();
        assertEquals(1,groups);
        //CleanUp
        groupRepository.deleteAll();
    }

    @Test
    public void updateGroupDishFromDbTest() {
        //Given
        Group group = new Group("Test group");
        groupRepository.save(group);
        Group groupFromDb = groupRepository.findAll().get(0);

        Dish dish = new Dish("test dish",23.33,groupFromDb);
        dishRepository.save(dish);
        Dish dishFromDb = dishRepository.findAll().get(0);

        //When
        groupFromDb.addDishToGroup(dishFromDb);
        groupFromDb.setGroupName("new name");
        groupRepository.save(groupFromDb);
        //Then
        Group updatedGroup = groupRepository.findAll().get(0);
        assertEquals(1,updatedGroup.getDishList().size());
        assertEquals("new name",updatedGroup.getGroupName());

        //CleanUp
        dishRepository.deleteAll();
        groupRepository.deleteAll();
    }

    @Test
    public void deleteGroup() {
        //Given
        Group group = new Group("Test group");
        groupRepository.save(group);

        Group groupFromDb = groupRepository.findAll().get(0);
        Dish dish = new Dish("test dish",23.33,groupFromDb);
        groupFromDb.addDishToGroup(dish);

        dishRepository.save(dish);
        groupRepository.save(groupFromDb);
        //When
        Group groupWithDish = groupRepository.findAll().get(0);
        Dish dishWithGroup = dishRepository.findAll().get(0);
        dishWithGroup.setGroup(null);
        dishRepository.save(dishWithGroup);
        groupRepository.delete(groupWithDish);
        //Then
        assertEquals(0,groupRepository.findAll().size());
        assertEquals(1,dishRepository.findAll().size());
        assertEquals(null,dishRepository.findAll().get(0).getGroup());
        //Cleanup
        dishRepository.deleteAll();







    }
}
