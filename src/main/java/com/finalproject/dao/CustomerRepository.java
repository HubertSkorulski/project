package com.finalproject.dao;

import com.finalproject.domain.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface CustomerRepository extends CrudRepository<Customer,Long> {

    @Override
    List<Customer> findAll();
}
