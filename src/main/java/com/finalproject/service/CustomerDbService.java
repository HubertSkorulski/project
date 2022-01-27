package com.finalproject.service;

import com.finalproject.dao.CustomerRepository;
import com.finalproject.domain.Customer;
import com.finalproject.dto.CustomerDto;
import com.finalproject.exception.CustomerNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CustomerDbService {
    private final CustomerRepository customerRepository;

    public void save(Customer customer) {
        customerRepository.save(customer);
    }

    public Optional<Customer> getCustomer(Long customerId) {
        return customerRepository.findById(customerId);
    }

    public void deleteCustomer(Customer customer) {
        customerRepository.delete(customer);
    }

    public List<Customer> getAllCustomers() {
       return customerRepository.findAll();
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }
}
