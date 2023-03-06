package com.finalproject.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Entity
@Table(name = "CARTS")
@Getter
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CART")
    @GenericGenerator(
            name = "SEQ_CART",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    @Column(name = "CART_ID")
    private Long id;

    @ManyToMany(
            fetch = FetchType.EAGER
    )
    @JoinTable(
            name = "DISHES_IN_CARTS",
            joinColumns = {@JoinColumn(name = "CART_ID", referencedColumnName = "CART_ID")},
            inverseJoinColumns = {@JoinColumn(name = "DISH_ID", referencedColumnName = "DISH_ID")}
    )
    private List<Dish> chosenDishes = new ArrayList<>();

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<CartRow> prepareCartDisplay() {
        List<Dish> singularDishes = findDistinctDishes();
        List<CartRow> cartRows = new ArrayList<>();
        for (Dish dish : singularDishes) {
            int quantity = countServings(dish);
            cartRows.add(new CartRow(dish,quantity));
        }
        return cartRows;
    }

    public int countServings(Dish dish) {
        return (int)chosenDishes.stream()
                .filter(e -> e.getName().equals(dish.getName()))
                .count();
    }

    public List<Dish> findDistinctDishes() {
        return getChosenDishes().stream().distinct().collect(Collectors.toList());
    }

    public double totalCost() {
        List<CartRow> cartRows = prepareCartDisplay();
        return cartRows.stream()
                .map(CartRow::getPrice)
                .reduce((double) 0,Double::sum);
    }

    public void removeDishQuantity(Dish dish, int quantity) {
        for (int i=0; i < quantity; i++) {
            getChosenDishes().remove(dish);
            dish.getCarts().remove(this);
        }
    }

    public void removeAllServingsOfDish(Dish dish) {
        while (getChosenDishes().contains(dish)) {
            getChosenDishes().remove(dish);
        }
    }

    public String cartToString() {
        List<CartRow> cartRows = prepareCartDisplay();
        String cartInString = "";

        for(CartRow cartRow : cartRows) {
            cartInString += cartRow.getDish().getName() + " " + cartRow.getQuantity() + " " + cartRow.getPrice() + "\n";
        }
        return cartInString += "Całkowity koszt: " + totalCost();
    }

    public void addDish(Dish dish, int quantity) {
        for (int i=0; i < quantity; i++) {
            getChosenDishes().add(dish);
            dish.getCarts().add(this);
        }
    }

    public void updateQuantityInCart(Dish dish, int newQuantity) {
        int oldQuantity = countServings(dish);
        if (newQuantity > oldQuantity) {
            addDish(dish, newQuantity - oldQuantity);
        } else if (newQuantity < oldQuantity) {
            removeDishQuantity(dish,oldQuantity-newQuantity);
        }
    }


}
