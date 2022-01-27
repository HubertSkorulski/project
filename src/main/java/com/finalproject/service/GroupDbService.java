package com.finalproject.service;

import com.finalproject.dao.GroupRepository;
import com.finalproject.domain.Group;
import com.finalproject.exception.GroupNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GroupDbService {

    private final GroupRepository groupRepository;


    public Optional<Group> getGroup(Long groupId) {
        return groupRepository.findById(groupId);
    }

    public void save(Group group) {
        groupRepository.save(group);
    }

    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    public void deleteGroup(Long groupId) throws GroupNotFoundException {
        if (groupRepository.existsById(groupId)) {
            groupRepository.deleteById(groupId);
        }
        else throw new GroupNotFoundException();
        }
}
