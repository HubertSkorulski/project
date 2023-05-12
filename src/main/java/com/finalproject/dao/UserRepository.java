package com.finalproject.dao;

import com.finalproject.domain.RestaurantUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface UserRepository extends CrudRepository<RestaurantUser,Long> {

    List<RestaurantUser> findAll();



}
