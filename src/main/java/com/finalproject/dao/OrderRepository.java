package com.finalproject.dao;

import com.finalproject.domain.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface OrderRepository extends CrudRepository<Order,Long> {

    @Override
    List<Order> findAll();

}
