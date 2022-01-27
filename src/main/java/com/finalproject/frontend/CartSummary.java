package com.finalproject.frontend;

import com.finalproject.data.CartRow;
import com.finalproject.domain.Cart;
import com.finalproject.service.CartDbService;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import org.springframework.stereotype.Component;

@Component
public class CartSummary extends VerticalLayout {
    Grid<CartRow> grid = new Grid<>(CartRow.class,false);
    TextField textField = new TextField();
    Button move = new Button("Dalej");

    public CartSummary() {
        setGrid();
        add(grid);
        add(horizontalLayout());
    }

    private HorizontalLayout horizontalLayout() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        prepareTextField();
        horizontalLayout.add(textField,move);
        return horizontalLayout;
    }

    private void setGrid() {
        grid.addColumn(cartRow -> cartRow.getDish().getName()).setHeader("Danie");
        grid.addColumn(CartRow::getQuantity).setHeader("Ilość");
        grid.addColumn(cartRow -> cartRow.getDish().getPrice()).setHeader("Cena jednostkowa");
        grid.addColumn(CartRow::getPrice).setHeader("Cena");
    }

    public void updateGrid(CartDbService cartDbService) {
        Cart cart = cartDbService.getAllCarts().get(cartDbService.getAllCarts().size()-1);
        grid.setItems(cart.prepareCartDisplay());
        textField.setValue("Całkowity koszt to:  " + cart.totalCost() + " PLN");
    }

    private void prepareTextField() {
        textField.setReadOnly(true);
        textField.addThemeVariants(TextFieldVariant.LUMO_ALIGN_CENTER);
        textField.setWidth(250, Unit.PIXELS);
        textField.setValue("Całkowity koszt: 0 PLN");
    }

    public void prepareMoveButton(CreateCustomerForm form) {
        move.addClickListener(event -> form.setVisible(true));
    }
}

