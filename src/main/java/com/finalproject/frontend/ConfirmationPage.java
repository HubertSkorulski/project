package com.finalproject.frontend;

import com.finalproject.service.OrderDbService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Confirmation")
@Route(value = "confirmation")
public class ConfirmationPage extends VerticalLayout {

    private final OrderDbService orderDbService;
    TextField textField = new TextField();
    Button button = new Button("Złóż nowe zamówienie");

    public ConfirmationPage(OrderDbService orderDbService) {
        this.orderDbService = orderDbService;
        prepareTextField();
        addClickListener();
        add(textField);
        add(button);
        setAlignment();
    }

    private void prepareTextField() {
        textField.setReadOnly(true);
        textField.addThemeVariants(TextFieldVariant.LUMO_ALIGN_CENTER);
        textField.setWidth(600, Unit.PIXELS);
        Long orderId = orderDbService.getAllOrders().get(orderDbService.getAllOrders().size()-1).getId();
        textField.setValue("Dziękujęmy za złożenie zamówienia. Numer zamówienia to: " + orderId + ".");
    }

    private void setAlignment() {
        this.setAlignItems(Alignment.CENTER);
    }

    private void addClickListener() {
        button.addClickListener(event -> UI.getCurrent().navigate("createOrder"));
    }
}
