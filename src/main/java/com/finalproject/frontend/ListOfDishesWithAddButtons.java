package com.finalproject.frontend;

import com.finalproject.domain.Cart;
import com.finalproject.domain.Dish;
import com.finalproject.service.CartDbService;
import com.finalproject.service.DishDbService;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ListOfDishesWithAddButtons extends VerticalLayout {

    List<HorizontalLayout> horizontalLayouts = new ArrayList<>();
    List<Label> labels = new ArrayList<>();
    List<IntegerField> integerFields = new ArrayList<>();
    List<Button> buttons = new ArrayList<>();
    int rowNumber = 24;
    private final DishDbService dishDbService;
    private final CartDbService cartDbService;


    public ListOfDishesWithAddButtons(DishDbService dishDbService, CartDbService cartDbService) {
        this.dishDbService = dishDbService;
        this.cartDbService = cartDbService;

    }

    public VerticalLayout prepareLayout(CartSummary cartSummary) {
        VerticalLayout verticalLayout = new VerticalLayout();
        createHorizontals();
        createLabels();
        createIntegerFields();
        createButtons();
        setLabels();
        setIntegerFields();
        addClickListenersToAddButtons(cartSummary);
        setHorizontalLayouts();
        for (HorizontalLayout horizontalLayout1 : horizontalLayouts){
            verticalLayout.add(horizontalLayout1);
        }
        return verticalLayout;
    }

    private void addClickListenersToAddButtons(CartSummary cartSummary) {
        List<Dish> dishes = dishDbService.getAllDishes();
        for (int i = 0; i < dishes.size(); i++) {
            Button button = buttons.get(i);
            button.addClickListener(event -> ths(button, cartSummary));
        }
    }


    private void ths(Button button, CartSummary cartSummary){
        Cart cart = cartDbService.getAllCarts().get(cartDbService.getAllCarts().size()-1);
        int index = buttons.indexOf(button);
        IntegerField filledField = integerFields.get(index);
        int quantity = filledField.getValue();

        String dishName = labels.get(index).getText();
        Dish dish = dishDbService.getDishByName(dishName);
        for (int i = 0; i < quantity; i++) {
            cart.getChosenDishes().add(dish);
            dish.getCarts().add(cart);
        }
        dishDbService.save(dish);
        cartDbService.save(cart);
        cartSummary.updateGrid(cartDbService);
        filledField.setValue(0);
    }


    private void createLabels() {
        for (int i = 0; i < rowNumber; i++) {
            labels.add(new Label());
        }
    }

    private void createIntegerFields() {
        for (int i = 0; i < rowNumber; i++) {
            integerFields.add(new IntegerField());
        }
    }

    private void createButtons() {
        for (int i = 0; i < rowNumber; i++) {
            buttons.add(new Button("Dodaj"));
        }
    }

    private void setLabels() {
        List<Dish> dishes = dishDbService.getAllDishes();
        for (int i = 0; i < dishes.size(); i++) {
            Label label = labels.get(i);
            label.setText(dishes.get(i).getName());
            label.setWidth(300, Unit.PIXELS);
        }
    }

    private void setIntegerFields() {
        List<Dish> dishes = dishDbService.getAllDishes();
        for (int i = 0; i < dishes.size(); i++) {
            IntegerField integerField = integerFields.get(i);
            integerField.setValue(0);
            integerField.setHasControls(true);
            integerField.setMin(0);
            integerField.setMax(20);
            integerField.setWidth(100, Unit.PIXELS);
        }
    }

    private void setHorizontalLayouts() {
        List<Dish> dishes = dishDbService.getAllDishes();
        for (int i = 0; i < dishes.size(); i++) {
            horizontalLayouts.get(i).add(
                    labels.get(i),
                    integerFields.get(i),
                    buttons.get(i)
            );
        }
    }

    private void createHorizontals() {
        for (int i = 0; i < rowNumber; i++) {
            horizontalLayouts.add(new HorizontalLayout());
        }
    }

}
