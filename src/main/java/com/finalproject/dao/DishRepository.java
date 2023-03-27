package com.finalproject.dao;

import com.finalproject.domain.Dish;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface DishRepository extends CrudRepository<Dish,Long> {

    @Override
    Optional<Dish> findById(Long dishId);

    @Override
    List<Dish> findAll();

    Optional<Dish> findByName(String name);

    List<Dish> findByGroup_Id(Long groupId);
}
