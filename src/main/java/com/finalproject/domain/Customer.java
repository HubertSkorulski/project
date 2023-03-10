package com.finalproject.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CUSTOMER")
    @GenericGenerator(
            name = "SEQ_CUSTOMER",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    @Column(name = "CUSTOMER_ID")
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
            fetch = FetchType.EAGER
    )
    private List<Order> orders = new ArrayList<>();

    public Customer(String name, String surname, String emailAddress) {
        this.name = name;
        this.surname = surname;
        this.emailAddress = emailAddress;
    }



}
