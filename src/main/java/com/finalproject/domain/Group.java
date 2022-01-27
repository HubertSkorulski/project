package com.finalproject.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "DISH_GROUPS")
public class Group {
    @Id
    @GeneratedValue
    @Column(name = "GROUP_ID")
    private Long id;

    @Column(name = "GROUP_NAME")
    private String groupName;

    @OneToMany(
            targetEntity = Dish.class,
            mappedBy = "group",
            cascade = {/*CascadeType.MERGE,*/ CascadeType.REFRESH},
            fetch = FetchType.EAGER//czy na pewno?
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
