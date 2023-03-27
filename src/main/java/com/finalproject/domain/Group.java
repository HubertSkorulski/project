package com.finalproject.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "DISH_GROUPS")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GROUP")
    @GenericGenerator(
            name = "SEQ_GROUP",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    @Column(name = "GROUP_ID")
    private Long id;

    @NotNull
    @Column(name = "GROUP_NAME")
    private String groupName;

    @OneToMany(
            targetEntity = Dish.class,
            mappedBy = "group",
            fetch = FetchType.EAGER
    )
    private List<Dish> dishList = new ArrayList<>();

    public Group(String groupName) {
        this.groupName = groupName;
    }

    public void addDishToGroup (Dish dish) {
        dishList.add(dish);
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void clearDishList() {
        this.dishList = new ArrayList<>();
    }
}
