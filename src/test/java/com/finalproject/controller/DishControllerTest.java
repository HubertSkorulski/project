package com.finalproject.controller;

import com.finalproject.dto.DishDto;
import com.google.gson.Gson;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@SpringJUnitWebConfig
@WebMvcTest(DishController.class)
class DishControllerTest {

    @MockBean
    private DishController dishController;

    @Autowired
    private MockMvc mockMvc;

    private String urlStart = "/v1/dish/";

    @Test
    void updateDishTest() throws Exception {
        //Given
        Gson gson = new Gson();
        DishDto dishDto = new DishDto(1L,"test",1.22,2L);
        when(dishController.updateDish(dishDto))
                .thenReturn(new DishDto(1L,"test",1.22,2L));
        String dishJson = gson.toJson(dishDto);
        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put(urlStart)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dishJson))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void createDishTest() throws Exception {
        //Given
        Gson gson = new Gson();
        DishDto dishDto = new DishDto(null,"Test",9.99, 1L);
        when(dishController.createDish(any(DishDto.class)))
                .thenReturn(new DishDto(1L,"Test",9.99,1L));
        String dishDtoJson = gson.toJson(dishDto);
        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post(urlStart)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dishDtoJson)
                        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("Test")));
    }

    @Test
    void deleteDishTest() throws Exception {
        //Given
        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                .delete(urlStart+ "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}