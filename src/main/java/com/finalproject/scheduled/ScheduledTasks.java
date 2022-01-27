package com.finalproject.scheduled;

import com.finalproject.service.ConfirmationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScheduledTasks {
    private final ConfirmationService confirmationService;

    @Scheduled(fixedDelay = 30000)
    public void sendAllCarts() {
        confirmationService.sendNumberOfOrders();
    }

}
