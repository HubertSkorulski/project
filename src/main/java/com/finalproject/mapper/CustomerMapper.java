package com.finalproject.mapper;

import com.finalproject.domain.Customer;
import com.finalproject.dto.CustomerDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class CustomerMapper {

    public CustomerDto mapToCustomerDto(Customer customer) {
        CustomerDto customerDto = new CustomerDto(
                customer.getId(),
                customer.getName(),
                customer.getSurname(),
                customer.getEmailAddress()
        );
        return customerDto;
    }

    public List<CustomerDto> mapToCustomerDtoList(List<Customer> customers) {
        List<CustomerDto> customersDto = new ArrayList<>();
        for (Customer customer: customers) {
            customersDto.add(mapToCustomerDto(customer));
        }
        return customersDto;
    }
}
