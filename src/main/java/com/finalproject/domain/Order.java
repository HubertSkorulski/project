package com.finalproject.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@NoArgsConstructor
@Entity
@Table(name = "ORDERS")
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "ORDER_ID", unique = true)
    private Long id;


    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CART_ID")
    private Cart cart;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;

    public Order (Cart cart, Customer customer) {
        this.cart = cart;
        this.customer = customer;
        this.status = "Open";
    }

    @Column(name = "STATUS") //zrobić enumy
    private String status;

    public Long getId() {
        return id;
    }

    public Cart getCart() {
        return cart;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status=status;
    }
}
