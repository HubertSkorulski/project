package com.finalproject.controller;

import com.finalproject.domain.Menu;
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
    public void prepareMenu() {
        menu.prepareDishes();
    }
}
