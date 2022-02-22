package com.finalproject.controller;

import com.finalproject.dto.GroupDto;
import com.finalproject.service.GroupDbService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(GroupController.class)
class GroupControllerTest {

    @MockBean
    private GroupController groupController;

    @Autowired
    private MockMvc mockMvc;

    private String urlStart = "/v1/group/";

    @Test
    void getGroups() throws Exception {
        when(groupController.getAllGroups()).thenReturn(
                List.of(new GroupDto(1L,"First"), new GroupDto(2L,"Second")));

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders.get(urlStart)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].groupName", Matchers.is("Second")));
    }

}