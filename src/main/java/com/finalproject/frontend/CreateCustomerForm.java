package com.finalproject.frontend;

import com.finalproject.service.ConfirmationService;
import com.finalproject.domain.Cart;
import com.finalproject.domain.Customer;
import com.finalproject.domain.Order;
import com.finalproject.service.CartDbService;
import com.finalproject.service.CustomerDbService;
import com.finalproject.service.OrderDbService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.stereotype.Component;

@Component
public class CreateCustomerForm extends FormLayout {

    private final CustomerDbService customerDbService;
    private final CartDbService cartDbService;
    private final OrderDbService orderDbService;
    private final ConfirmationService confirmationService;

    TextField name = new TextField("Imię");
    TextField surname = new TextField("Nazwisko");
    EmailField emailAddress = new EmailField("Email");

    Button createOrder = new Button("Złóż zamowienie");
    Button delete = new Button("Delete");
    Button close = new Button("Close");


    public CreateCustomerForm(CustomerDbService customerDbService, CartDbService cartDbService, OrderDbService orderDbService, ConfirmationService confirmationService) {
        this.customerDbService = customerDbService;
        this.cartDbService = cartDbService;
        this.orderDbService = orderDbService;
        this.confirmationService = confirmationService;
        add(
                name,
                surname,
                emailAddress,
                createButtonsLayout()
        );
    }

    private HorizontalLayout createButtonsLayout() {
        createOrder.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        createOrder.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        createOrder.addClickListener(event -> validateAndSave());
        close.addClickListener(event -> setVisible(false));

        HorizontalLayout buttonsLayout = new HorizontalLayout();
        buttonsLayout.add(createOrder,delete,close);
        return buttonsLayout;
    }

    private void validateAndSave() {
        if (!(name.isEmpty() & surname.isEmpty() & emailAddress.isEmpty())) {
            Customer createdCustomer = new Customer(
                    name.getValue(),
                    surname.getValue(),
                    emailAddress.getValue()
            );
            customerDbService.save(createdCustomer);
            Customer lastCustomer = customerDbService.findAll().get(customerDbService.findAll().size()-1);
            Cart lastCart = cartDbService.getAllCarts().get(cartDbService.getAllCarts().size()-1);
            Order order = new Order(lastCart,lastCustomer);
            orderDbService.save(order);
            confirmationService.sendConfirmation(order);
            UI.getCurrent().navigate("confirmation");
        }
    }
}
