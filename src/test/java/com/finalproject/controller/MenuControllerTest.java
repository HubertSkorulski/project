package com.finalproject.controller;

import com.finalproject.dto.DishDto;
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

import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(MenuController.class)
public class MenuControllerTest {

    @MockBean
    private MenuController menuController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void prepareDishesTest() throws Exception{
        //Given
        when(menuController.prepareMenu()).thenReturn(List.of(
                new DishDto(1L,"First dish", 12.00,1L),
                new DishDto(2L,"Second dish",13.00,1L)
        ));

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders.get("/v1/menu")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));
    }

}
