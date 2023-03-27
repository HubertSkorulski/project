
package com.finalproject.domain;

import com.finalproject.dao.CartRepository;
import com.finalproject.dao.DishRepository;
import com.finalproject.dao.GroupRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CartEntityTestSuite {

    @Test
    void prepareCartDisplayTest() {
        //Given
        Cart cart = new Cart();
        Group group = new Group("Test group");
        Dish firstDish = new Dish("Test",1.00,group);
        Dish secondDish = new Dish("Test 2", 2.00, group);
        cart.addDish(firstDish,5);
        cart.addDish(secondDish,3);
        //When
        List<CartRow> cartRows = cart.prepareCartDisplay();
        //Then
        Assertions.assertEquals("Test",cartRows.get(0).getDish().getName());
        Assertions.assertEquals(5,cartRows.get(0).getQuantity());

        Assertions.assertEquals("Test 2",cartRows.get(1).getDish().getName());
        Assertions.assertEquals(3,cartRows.get(1).getQuantity());
    }

    @Test
    void findDistinctDishesTest() {
        //Given
        Cart cart = new Cart();
        Group group = new Group("Test group");
        Dish firstDish = new Dish("Test",1.00,group);
        Dish secondDish = new Dish("Test 2", 2.00, group);
        cart.addDish(firstDish,5);
        cart.addDish(secondDish,3);
        //When
        int distinctDishes = cart.findDistinctDishes().size();
        //Then
        Assertions.assertEquals(2,distinctDishes);
    }

    @Test
    void addDishTest() {
        //Given
        Cart cart = new Cart();
        Group group = new Group("Test group");
        Dish dish = new Dish("Test",12.22,group);
        //When
        cart.addDish(dish,5);
        //Then
        List<Dish> dishesInCart = cart.getChosenDishes();
        Assertions.assertEquals(5,dishesInCart.size());
    }

    @Test
    void countServings() {
        //Given
        Cart cart = new Cart();
        Group group = new Group("Test group");
        Dish firstDish = new Dish("Test",12.22,group);
        Dish secondDish = new Dish("Test 2", 13.33, group);
        cart.addDish(firstDish,5);
        cart.addDish(secondDish,3);
        //When
        int quantityOfFirstDish = cart.countServings(firstDish);
        int quantityOfSecondDish = cart.countServings(secondDish);
        //Then
        Assertions.assertEquals(5,quantityOfFirstDish);
        Assertions.assertEquals(3,quantityOfSecondDish);
    }

    @Test
    void removeDishAllServingsTest() {
        //Given
        Cart cart = new Cart();
        Group group = new Group("Test group");
        Dish dish = new Dish("Test",12.22,group);
        Dish secondDish = new Dish("Test 2", 11.00,group);
        cart.addDish(dish,5);
        cart.addDish(secondDish,3);
        //When
        cart.removeAllServingsOfDish(dish);
        //Then
        Assertions.assertEquals(0,cart.countServings(dish));
        Assertions.assertEquals(3,cart.countServings(secondDish));
    }

    @Test
    void countTotalCost() {
        //Given
        Cart cart = new Cart();
        Group group = new Group("Test group");
        Dish firstDish = new Dish("Test",1.00,group);
        Dish secondDish = new Dish("Test 2", 2.00, group);
        cart.addDish(firstDish,5);
        cart.addDish(secondDish,3);
        //When
        double price = cart.totalCost();
        //Then
        Assertions.assertEquals(11,price);
    }

    @Test
    void updateQuantityInCartAdding() {
        //Given
        Group group = new Group("test");
        Cart cart = new Cart();
        Dish dish = new Dish("test", 1.00,group);
        cart.addDish(dish,10);
        //When
        cart.updateQuantityInCart(dish,12);
        //Then
        Assertions.assertEquals(12,cart.getChosenDishes().size());

    }

    @Test
    void updateQuantityInCartRemoving() {
        //Given
        Group group = new Group("test");
        Cart cart = new Cart();
        Dish dish = new Dish("test", 1.00,group);
        cart.addDish(dish,10);
        //When
        cart.updateQuantityInCart(dish,8);
        //Then
        Assertions.assertEquals(8,cart.getChosenDishes().size());
    }
}
