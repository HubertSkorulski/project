package com.finalproject.service;

import com.finalproject.dao.UserRepository;
import com.finalproject.domain.RestaurantUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserDbService {
    private final UserRepository userRepository;

    public void save(RestaurantUser restaurantUser) {
        userRepository.save(restaurantUser);
    }

    public Optional<RestaurantUser> getUser(Long userId) {
        return userRepository.findById(userId);
    }

    public void deleteUser(RestaurantUser restaurantUser) {
        userRepository.delete(restaurantUser);
    }

    public List<RestaurantUser> getAllUsers() {
       return userRepository.findAll();
    }

    public List<RestaurantUser> findAll() {
        return userRepository.findAll();
    }




}
