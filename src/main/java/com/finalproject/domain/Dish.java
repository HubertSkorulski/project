package com.finalproject.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @GeneratedValue
    @Column(name = "DISH_ID", unique = true)
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

    @ManyToOne(
            cascade = {/*CascadeType.PERSIST,*/ CascadeType.REFRESH}
    )
    @JoinColumn(name = "GROUP_ID")
    private Group group;

    @ManyToMany(
            cascade = {CascadeType.PERSIST, CascadeType.REFRESH},
            mappedBy = "chosenDishes",
            fetch = FetchType.EAGER //doczytać
    )
    private List<Cart> carts = new ArrayList<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dish dish = (Dish) o;

        return Objects.equals(name, dish.name);
    }

}
