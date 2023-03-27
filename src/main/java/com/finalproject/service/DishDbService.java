package com.finalproject.service;

import com.finalproject.dao.DishRepository;
import com.finalproject.domain.Dish;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DishDbService {

    private final DishRepository dishRepository;

    public Optional<Dish> getDish(Long dishId) {
        return dishRepository.findById(dishId);
    }

    public Optional<Dish>  getDishByName(String name) {
        return dishRepository.findByName(name);
    }

    public void save(Dish dish) {
        dishRepository.save(dish);
    }


    public void delete(Dish dish) {
        dishRepository.delete(dish);
    }

    public List<Dish> getAllDishes() {
        return dishRepository.findAll();
    }

    public List<Dish> getDishesFromGroup(Long groupId) {
        return dishRepository.findByGroup_Id(groupId);
    }
}
