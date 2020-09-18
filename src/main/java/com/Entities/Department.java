package com.Entities;

import javax.persistence.*;
import java.util.List;

@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    public String name;

    @OneToMany
    public List<Position> positionList;

    public List<Position> getPositionList() {
        return positionList;
    }


    public void setPositionList(List<Position> positionList) {
        this.positionList = positionList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
