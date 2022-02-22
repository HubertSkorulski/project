package com.finalproject.mapper;

import com.finalproject.domain.Group;
import com.finalproject.dto.GroupDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class GroupMapper {

    public GroupDto mapToGroupDto(Group group) {

        return new GroupDto(group.getId(), group.getGroupName());
    }

    public List<GroupDto> mapToGroupDtoList(List<Group> groups) {
        List<GroupDto> groupsDto = new ArrayList<>();
        for (Group group : groups) {
            groupsDto.add(mapToGroupDto(group));
        }
        return groupsDto;
    }
}
