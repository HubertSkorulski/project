package com.finalproject.controller;

import com.finalproject.exception.OrderNotFoundException;
import com.finalproject.service.ConfirmationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("v1/confirmation")
public class ConfirmationController {

    private final ConfirmationService confirmationService;

    @GetMapping("/{orderId}")
    public String sendConfirmation(@PathVariable Long orderId) throws OrderNotFoundException {
        return confirmationService.sendConfirmation(orderId);
    }


}
