package com.finalproject.data;

import com.finalproject.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class ConfirmationService {

    @Autowired
    private JavaMailSender javaMailSender;

    public SimpleMailMessage prepareConfirmation(Order order) {
        SimpleMailMessage mail = new SimpleMailMessage();
        //mail.setTo(order.getCustomer().getEmailAddress());
        String message = order.getCart().cartToString();
        mail.setTo("skorulski.hubert@gmail.com");
        mail.setSubject("Potwierdzenie złożenia zamówienia nr: " + order.getId());
        mail.setText(message);
        return mail;
    }

    public void sendConfirmation(Order order) {
        javaMailSender.send(prepareConfirmation(order));
    }
}
