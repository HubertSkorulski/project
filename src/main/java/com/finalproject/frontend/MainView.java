package com.finalproject.frontend;

import com.finalproject.service.ConfirmationService;
import com.finalproject.domain.Cart;
import com.finalproject.service.*;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Create Order")
@Route(value = "createOrder")
public class MainView extends FormLayout {

    private final CartDbService cartDbService;
    private final CustomerDbService customerDbService;
    private final OrderDbService orderDbService;
    private final ConfirmationService confirmationService;

    Cart cart = new Cart();
    CreateCustomerForm form;
    ListOfDishesWithAddButtons listOfDishesWithAddButtons;
    CartSummary cartSummary;

    public MainView(DishDbService dishDbService, CartDbService cartDbService,
                    CustomerDbService customerDbService, OrderDbService orderDbService, ConfirmationService confirmationService) {
        this.cartDbService = cartDbService;
        this.customerDbService = customerDbService;
        this.orderDbService = orderDbService;
        this.confirmationService = confirmationService;

        cartDbService.save(cart);

        form = new CreateCustomerForm(customerDbService,cartDbService,orderDbService, confirmationService);
        form.setVisible(false);
        cartSummary = new CartSummary();
        cartSummary.prepareMoveButton(form);
        listOfDishesWithAddButtons = new ListOfDishesWithAddButtons(dishDbService, cartDbService);

        add(listOfDishesWithAddButtons.prepareLayout(cartSummary));

        VerticalLayout gridWithButton = new VerticalLayout();
        gridWithButton.add(cartSummary,form);
        add(gridWithButton);
    }
}






