package com.finalproject.domain;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Entity
@Table(name = "CARTS")
public class Cart {

    @Id
    @GeneratedValue
    @Column(name = "CART_ID", unique = true)
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

    public Long getId() {
        return id;
    }

    public List<Dish> getChosenDishes() {
        return chosenDishes;
    }

    public Order getOrder() {
        return order;
    }

    public List<CartRow> prepareCartDisplay() {
        List<Dish> singularDishes = findDistinctDishes();
        List<CartRow> cartRows = new ArrayList<>();
        for (Dish dish : singularDishes) {
            int quantity = (int)chosenDishes.stream()
                    .filter(e -> e.getName().equals(dish.getName()))
                    .count();
            cartRows.add(new CartRow(dish,quantity));
        }
        return cartRows;
    }

    public int countIndividualDishes(Dish dish) {
        return (int)chosenDishes.stream()
                .filter(e -> e.getName().equals(dish.getName()))
                .count();
    }

    public List<Dish> findDistinctDishes() {
        List<Dish> distinctDishes = new ArrayList<>();
        for(Dish dish : getChosenDishes()) {
            if (!distinctDishes.contains(dish)) {
                distinctDishes.add(dish);
            }
        }
        return distinctDishes;
    }

    public double totalCost() {
        List<CartRow> cartRows = prepareCartDisplay();

        return cartRows.stream()
                .map(CartRow::getPrice)
                .reduce((double) 0,Double::sum);
    }

    public String cartToString() {
        List<CartRow> cartRows = prepareCartDisplay();
        String cartInString = "";

        for(CartRow cartRow : cartRows) {
            cartInString += cartRow.getDish().getName() + " " + cartRow.getQuantity() + " " + cartRow.getPrice() + "\n";
        }
        return cartInString += "Całkowity koszt: " + totalCost();
    }

}
