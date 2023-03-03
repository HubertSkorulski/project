package com.finalproject.controller;

import com.finalproject.domain.Dish;
import com.finalproject.domain.Group;
import com.finalproject.dto.GroupDto;
import com.finalproject.exception.GroupNotFoundException;
import com.finalproject.mapper.GroupMapper;
import com.finalproject.service.DishDbService;
import com.finalproject.service.GroupDbService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("v1/group")
@CrossOrigin
public class GroupController {

    private final GroupDbService groupDbService;
    private final GroupMapper groupMapper;
    private final DishDbService dishDbService;

    @PostMapping()
    public GroupDto createGroup(@RequestBody Group group) {
        groupDbService.save(group);
        return groupMapper.mapToGroupDto(group);
    }

    @GetMapping()
    public List<GroupDto> getAllGroups() {
        List<Group> groups = groupDbService.getAllGroups();
        return groupMapper.mapToGroupDtoList(groups);
    }

    @GetMapping("/{groupId}")
    public GroupDto getGroup(@PathVariable Long groupId) throws GroupNotFoundException {
        Group group = groupDbService.getGroup(groupId).orElseThrow(GroupNotFoundException::new);
        return groupMapper.mapToGroupDto(group);
    }

    @PutMapping
    public GroupDto updateGroup(@RequestParam Long groupId, @RequestParam String groupName) throws GroupNotFoundException {
        Group group = groupDbService.getGroup(groupId).orElseThrow(GroupNotFoundException::new);
        group.setGroupName(groupName);
        groupDbService.save(group);
        return groupMapper.mapToGroupDto(group);
    }

    @DeleteMapping
    public void deleteGroup(@RequestParam Long groupId) throws GroupNotFoundException {
        Group group = groupDbService.getGroup(groupId).orElseThrow(GroupNotFoundException::new);
        for (Dish dish : group.getDishList()) {
            dish.setGroup(null);
            dishDbService.save(dish);
        }
        group.clearDishList();//to nie jest potrzebne!!!!!!
        groupDbService.deleteGroup(groupId);
    }
}
