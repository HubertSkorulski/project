package com.finalproject.service;

import com.finalproject.domain.Order;
import com.finalproject.exception.OrderNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConfirmationService {


    private final JavaMailSender javaMailSender;
    private final OrderDbService orderDbService;

    public SimpleMailMessage prepareConfirmation(Order order) {
        SimpleMailMessage mail = new SimpleMailMessage();
        //mail.setTo(order.getCustomer().getEmailAddress());
        String message = order.getCart().cartToString();
        mail.setTo("skorulski.hubert@gmail.com");
        mail.setSubject("Potwierdzenie złożenia zamówienia nr: " + order.getId());
        mail.setText(message);
        return mail;
    }

    public String sendConfirmation(Long orderId) throws OrderNotFoundException{
        Order order = orderDbService.getOrder(orderId).orElseThrow(OrderNotFoundException::new);
        SimpleMailMessage message = prepareConfirmation(order);
        javaMailSender.send(message);
        return message.getText();
    }

    public void sendNumberOfOrders() {
        int orders = orderDbService.getAllOrders().size();
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo("skorulski.hubert@gmail.com");
        mail.setSubject("All orders");
        mail.setText("Liczba zamówień w bazie danych: " + orders);
        javaMailSender.send(mail);
    }
}
