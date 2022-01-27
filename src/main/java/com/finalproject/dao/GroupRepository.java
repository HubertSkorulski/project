package com.finalproject.dao;

import com.finalproject.domain.Group;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface GroupRepository extends CrudRepository<Group, Long> {

    @Override
    List<Group> findAll();
}
