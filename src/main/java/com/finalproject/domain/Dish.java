package com.finalproject.domain;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@Entity
@Table(name ="DISHES")
public class Dish {

    @Id
    @GeneratedValue
    @Column(name = "DISH_ID", unique = true)
    private Long id;

    @Column(name = "DISH_NAME", unique = true)
    private String name;

    @Column(name = "PRICE")
    private Double price;

    public Dish(String name, Double price, Group group) {
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


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public Group getGroup() {
        return group;
    }

    public List<Cart> getCarts() {
        return carts;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dish dish = (Dish) o;

        return Objects.equals(name, dish.name);
    }

}
