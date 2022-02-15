
package com.finalproject.controller;

import com.finalproject.dto.CartDto;
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
@WebMvcTest(CartController.class)
class CartControllerTest {

    @MockBean
    private CartController cartController;

    @Autowired
    private MockMvc mockMvc;

    private String urlStart = "/v1/cart/";

    @Test
    void createCartTest() throws Exception {
        //Given
        when(cartController.createCart()).thenReturn(new CartDto(1L));

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders.get(urlStart + "create")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)));
    }

    @Test
    void getProductsTest() throws Exception {
        //Given
        when(cartController.getProducts(1L)).thenReturn(List.of(
                new DishDto(1L,"First dish", 12.00,1L),
                new DishDto(2L,"Second dish",13.00,1L)
        ));

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders.get( urlStart + "getProducts/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name", Matchers.is("Second dish")));
    }






























}
