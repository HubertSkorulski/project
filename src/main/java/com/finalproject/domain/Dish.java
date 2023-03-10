package com.finalproject.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@Entity
@Table(name ="DISHES")
@Getter
@Setter
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DISH")
    @GenericGenerator(
            name = "SEQ_DISH",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    @Column(name = "DISH_ID")
    private Long id;

    @Column(name = "DISH_NAME", unique = true)
    private String name;

    @Column(name = "PRICE")
    private double price;

    public Dish(String name, double price, Group group) {
        this.name = name;
        this.price = price;
        this.group = group;
    }

    @ManyToOne
    @JoinColumn(name = "GROUP_ID")
    private Group group;

    @ManyToMany(
            mappedBy = "chosenDishes",
            fetch = FetchType.EAGER
    )
    private List<Cart> carts = new ArrayList<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dish dish = (Dish) o;

        return Objects.equals(name, dish.name);
    }


    public void prepareCartsForDishDeletion() {
        for (Cart cart : this.getCarts()) {
            cart.removeAllServingsOfDish(this);
        }
    }

    public void update(String name, double price, Group group) {
        setName(name);
        setPrice(price);
        setGroup(group);
    }

    private void removeDishFromCarts(List<Cart> carts) {
        for (Cart cart : carts) {
            cart.removeAllServingsOfDish(this);
        }
    }
}
