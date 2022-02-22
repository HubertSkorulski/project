package com.finalproject.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringJUnitWebConfig
@WebMvcTest(MenuController.class)
public class MenuControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MenuController menuController;

    @Test
    void prepareDishesTest() throws Exception{

        //Given

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders.get("/v1/menu")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
