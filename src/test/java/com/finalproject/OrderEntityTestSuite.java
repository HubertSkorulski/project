package com.finalproject;

import com.finalproject.dao.CartRepository;
import com.finalproject.dao.CustomerRepository;
import com.finalproject.dao.OrderRepository;
import com.finalproject.domain.Cart;
import com.finalproject.domain.Customer;
import com.finalproject.domain.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class OrderEntityTestSuite {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CustomerRepository customerRepository;


    @Test
    public void createOrderTest() {
        //Given
        Cart cart = new Cart();
        cartRepository.save(cart);
        Customer customer = new Customer("Test name","Test surname", "email");
        customerRepository.save(customer);
        //When
        Cart cartFromDb = cartRepository.findAll().get(0);
        Customer customerFromDb = customerRepository.findAll().get(0);
        Order order = new Order(cartFromDb,customerFromDb);
        orderRepository.save(order);
        //Then
        Order orderFromDb = orderRepository.findAll().get(0);
        assertEquals(1,orderRepository.findAll().size());
        assertEquals("Test name", orderFromDb.getCustomer().getName());
        assertEquals("Open", orderFromDb.getStatus());

        //CleanUp
        orderRepository.deleteAll();
        customerRepository.deleteAll();
        cartRepository.deleteAll();
    }

    @Test
    public void updateOrderTest() {
        //Given
        Cart cart = new Cart();
        cartRepository.save(cart);
        Customer customer = new Customer("Test name","Test surname", "email");
        customerRepository.save(customer);
        Cart cartFromDb = cartRepository.findAll().get(0);
        Customer customerFromDb = customerRepository.findAll().get(0);
        Order order = new Order(cartFromDb,customerFromDb);
        orderRepository.save(order);
        //When
        Order orderFromDb = orderRepository.findAll().get(0);
        orderFromDb.setStatus("Paid");
        orderRepository.save(orderFromDb);
        //Then
        Order updatedOrder = orderRepository.findAll().get(0);
        assertEquals("Paid",updatedOrder.getStatus());

        //CleanUp
        orderRepository.deleteAll();
        cartRepository.deleteAll();
        customerRepository.deleteAll();
    }

    @Test
    public void deleteOrderTest() {
        //Given
        Cart cart = new Cart();
        cartRepository.save(cart);
        Customer customer = new Customer("Test name","Test surname", "email");
        customerRepository.save(customer);

        Cart cartFromDb = cartRepository.findAll().get(0);
        Customer customerFromDb = customerRepository.findAll().get(0);
        Order order = new Order(cartFromDb,customerFromDb);
        cartFromDb.setOrder(order);
        customerFromDb.getOrders().add(order);

        orderRepository.save(order);
        cartRepository.save(cartFromDb);
        customerRepository.save(customerFromDb);

        //When
        Order orderFromDb = orderRepository.findAll().get(0);
        Customer customerWithOrder = customerRepository.findAll().get(0);
        Cart cartWithOrder = cartRepository.findAll().get(0);
        customerWithOrder.getOrders().remove(orderFromDb);
        cartWithOrder.setOrder(null);
        customerRepository.save(customerWithOrder);
        cartRepository.save(cartWithOrder);
        orderRepository.deleteById(orderFromDb.getId());
        //Then
        assertEquals(0,orderRepository.findAll().size());
        assertEquals(1,customerRepository.findAll().size());
        assertEquals(1,cartRepository.findAll().size());

        //CleanUp
        customerRepository.deleteAll();
        cartRepository.deleteAll();
    }
}
