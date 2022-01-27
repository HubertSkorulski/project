package com.finalproject.dao;

import com.finalproject.domain.Cart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface CartRepository extends CrudRepository<Cart,Long> {

    @Override
    List<Cart> findAll();
}
