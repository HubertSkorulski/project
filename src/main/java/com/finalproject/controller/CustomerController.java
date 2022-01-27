package com.finalproject.controller;

import com.finalproject.domain.Customer;
import com.finalproject.dto.CustomerDto;
import com.finalproject.exception.CustomerNotFoundException;
import lombok.AllArgsConstructor;
import com.finalproject.mapper.CustomerMapper;
import org.springframework.web.bind.annotation.*;
import com.finalproject.service.CustomerDbService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("v1/customer")
public class CustomerController {

    private CustomerDbService customerDbService;
    private CustomerMapper customerMapper;


    @GetMapping
    public CustomerDto getCustomer(@RequestParam Long customerId) throws CustomerNotFoundException {
        Customer customer = customerDbService.getCustomer(customerId).orElseThrow(CustomerNotFoundException::new);
        return customerMapper.mapToCustomerDto(customer);
    }

    @PostMapping("createCustomer")
    public CustomerDto createCustomer(@RequestParam String name, @RequestParam String surname, @RequestParam String emailAddress) {
        Customer customer = new Customer(name, surname, emailAddress);
        customerDbService.save(customer);
        return customerMapper.mapToCustomerDto(customer);
    }

    @PutMapping("updateCustomer")
    public CustomerDto updateCustomer(@RequestParam Long customerId, @RequestParam String name, @RequestParam String surname, @RequestParam String emailAddress) throws CustomerNotFoundException {
        Customer customer = customerDbService.getCustomer(customerId).orElseThrow(CustomerNotFoundException::new);
        customer.setName(name);
        customer.setSurname(surname);
        customer.setEmailAddress(emailAddress);
        customerDbService.save(customer);
        return customerMapper.mapToCustomerDto(customer);
    }

    @DeleteMapping
    public void deleteCustomer(@RequestParam Long customerId) throws CustomerNotFoundException {
        Customer customer = customerDbService.getCustomer(customerId).orElseThrow(CustomerNotFoundException::new);
        customerDbService.deleteCustomer(customer);
        //tu tez sie przyjrzec czy sie usunie jak mu sie nie wyjebie wszystkich pozostalych obiektów
    }

    @GetMapping(value = "getCustomers")
    public List<CustomerDto> getAllCustomers() {
        List<Customer> customers = customerDbService.getAllCustomers();
        return customerMapper.mapToCustomerDtoList(customers);
    }
}
