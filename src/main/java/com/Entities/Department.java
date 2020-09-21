package com.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    public String departmentName;

    @JsonIgnoreProperties("department")
    @OneToMany(cascade = CascadeType.MERGE)
    public Set<Position> positions;

    public Set<Position> getPositions() {
        return positions;
    }

    public void addPosition(Position position){
        if(this.positions == null){
            this.positions = new HashSet<Position>();
        }
        this.positions.add(position);
        position.setDepartment(this);
    }

    public void removePosition(Position position){
        if(this.positions != null){
            this.positions.remove(position);
            position.setDepartment(null);
        }
    }

    public void setPositions(Set<Position> positionList) {
        this.positions = positionList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String name) {
        this.departmentName = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
