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
        when(dishController.updateDish(1L,"test",1.22,2L))
                .thenReturn(new DishDto(1L,"test",1.22,2L));

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put(urlStart + "1/test/1.22/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void createDishTest() throws Exception {
        //Given
        when(dishController.createDish("Test",9.99,1L))
                .thenReturn(new DishDto(1L,"Test",9.99,1L));

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post(urlStart + "Test/9.99/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("Test")));
    }
}