package com.finalproject.controller;

import com.finalproject.domain.Menu;
import com.finalproject.dto.DishDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/menu")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class MenuController {

    private Menu menu;

    @GetMapping
    public List<DishDto> prepareMenu() {
        return menu.prepareDishes();
    }
}
