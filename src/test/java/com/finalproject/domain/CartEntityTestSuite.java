
package com.finalproject.domain;

import com.finalproject.dao.CartRepository;
import com.finalproject.dao.DishRepository;
import com.finalproject.dao.GroupRepository;
import com.finalproject.domain.Cart;
import com.finalproject.domain.Dish;
import com.finalproject.domain.Group;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CartEntityTestSuite {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Test
    public void createNewCartTest() {
        //Given
        Cart cart = new Cart();
        //When
        cartRepository.save(cart);
        //Then
        int cartsNumber = cartRepository.findAll().size();
        assertEquals(1,cartsNumber);
        //CleanUp
        cartRepository.deleteAll();
    }

    @Test
    public void addDishToCartTest() {

        //Given
        Group testGroup = new Group("Test Group");
        groupRepository.save(testGroup);
        Dish dish = new Dish("Dish one", 12.33, testGroup);
        dishRepository.save(dish);
        Cart cart = new Cart();
        cartRepository.save(cart);

        //When
        Cart cartFromDb = cartRepository.findById(cart.getId()).get();
        cartFromDb.getChosenDishes().add(dish);
        dish.getCarts().add(cartFromDb);
        cartRepository.save(cartFromDb);

        //Then
        Cart cartWithDish = cartRepository.findById(cartFromDb.getId()).get();
        int dishesNumber = cartWithDish.getChosenDishes().size();
        List<Dish> dishesInDb = dishRepository.findAll();
        assertEquals(1,dishesNumber);
        assertEquals(1,dishesInDb.size());

        //CleanUp
        Dish dishWithCart = dishesInDb.get(0);
        cartWithDish.getChosenDishes().remove(0);
        dishWithCart.getCarts().remove(0);
        cartRepository.save(cartWithDish);
        dishRepository.save(dishWithCart);
        dishRepository.deleteAll();
        cartRepository.deleteAll();
        groupRepository.deleteAll();
    }

    @Test
    public void deleteCartTest() {
        //Given
        Group testGroup = new Group("Test Group");
        groupRepository.save(testGroup);
        Dish dish = new Dish("Dish one", 12.33, testGroup);
        dishRepository.save(dish);
        Cart cart = new Cart();
        cart.getChosenDishes().add(dish);
        cartRepository.save(cart);
        Cart cartFromDb = cartRepository.findById(cart.getId()).get();
        Dish dishFromDb = dishRepository.findById(dish.getId()).get();
        cartFromDb.getChosenDishes().remove(0);
        dishFromDb.getCarts().remove(0);
        cartRepository.save(cartFromDb);
        dishRepository.save(dishFromDb);
        //When
        cartRepository.deleteById(cartFromDb.getId());
        //Then
        int carts = cartRepository.findAll().size();
        int dishes = dishRepository.findAll().size();
        assertEquals(0,carts);
        assertEquals(1,dishes);

        //CleanUp
        Dish dishToDelete = dishRepository.findAll().get(0);
        dishToDelete.setGroup(null);
        dishRepository.save(dishToDelete);
        dishRepository.deleteAll();
        groupRepository.deleteAll();
    }


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
    void removeDishTest() {
        //Given
        Cart cart = new Cart();
        Group group = new Group("Test group");
        Dish dish = new Dish("Test",12.22,group);
        cart.addDish(dish,5);
        //When
        cart.removeDish(dish);
        //Then
        Assertions.assertEquals(0,cart.countServings(dish));
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

}
