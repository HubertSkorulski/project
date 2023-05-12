package com.finalproject.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.*;


@NoArgsConstructor
@Entity
@Table(name = "RESTAURANT_USERS")
@Getter
@Setter
public class RestaurantUser {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_USER")
    @GenericGenerator(
            name = "SEQ_USER",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    @Column(name = "USER_ID")
    private Long id;

    @Column(name = "EMAIL")
    private String username;

    @Column(name = "USER_PASSWORD")
    private String password;
    @Column(name = "ROLE")
    private String role;



    @OneToMany(
            targetEntity = Order.class,
            mappedBy = "restaurantUser",
            fetch = FetchType.EAGER
    )
    private List<Order> orders = new ArrayList<>();

    public RestaurantUser(String username, String role) {
        this.username = username;
        this.role = role;
    }

}
