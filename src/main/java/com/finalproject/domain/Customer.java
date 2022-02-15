package com.finalproject.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@Entity
@Table(name = "CUSTOMERS")
@Getter
@Setter
public class Customer {
    @Id
    @GeneratedValue
    @Column(name = "CUSTOMER_ID", unique = true)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SURNAME")
    private String surname;

    @Column(name = "EMAIL")
    private String emailAddress;

    @OneToMany(
            targetEntity = Order.class,
            mappedBy = "customer",
            fetch = FetchType.EAGER //czy na pewno?
    )
    private List<Order> orders = new ArrayList<>();

    public Customer(String name, String surname, String emailAddress) {
        this.name = name;
        this.surname = surname;
        this.emailAddress = emailAddress;
    }



}
