package com.finalproject.domain;

import com.finalproject.dao.CustomerRepository;
import com.finalproject.domain.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CustomerEntityTestSuite {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void createCustomerTest() {
        //Given
        Customer customer = new Customer("Hubert", "Niehubert", "test mail");
        //When
        customerRepository.save(customer);
        //Them
        assertEquals(1,customerRepository.findAll().size());
        //CleanUp
        customerRepository.deleteAll();
    }

    @Test
    public void updateCustomer() {
                //Given
        Customer customer = new Customer("name", "surname", "test mail");
        customerRepository.save(customer);
        //When
        Customer customerFromDb = customerRepository.findAll().get(0);
        customerFromDb.setName("new name");
        customerFromDb.setSurname("new surname");
        customerFromDb.setEmailAddress("new email");
        customerRepository.save(customerFromDb);
        //Then
        Customer updatedCustomer = customerRepository.findAll().get(0);
        assertEquals("new name", updatedCustomer.getName());
        assertEquals("new surname",updatedCustomer.getSurname());
        assertEquals("new email",updatedCustomer.getEmailAddress());
        //CleanUp
        customerRepository.deleteAll();
    }

    @Test
    public void deleteCustomerTest() {
        //Given
        Customer customer = new Customer("name", "surname", "test mail");
        customerRepository.save(customer);
        //When
        Customer customerFromDb = customerRepository.findAll().get(0);
        customerRepository.deleteById(customerFromDb.getId());
        //Them
        assertEquals(0,customerRepository.findAll().size());

    }

}
