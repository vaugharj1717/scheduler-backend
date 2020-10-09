package com.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.*;

@Entity
public class Position implements DataObject{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    Department department;

    @JsonIgnoreProperties("position")
    @JoinColumn(name="POSITION_ID")
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    List<Candidacy> candidacies;

    String positionName;

    public Position(){
        this.candidacies = new ArrayList<Candidacy>();
    }
    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public Department getDepartment() {
        return department;
    }

    public List<Candidacy> getCandidacies() {
        return candidacies;
    }

    public void setCandidacies(List<Candidacy> candidacies) {
        this.candidacies = candidacies;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public void addCandidacy(Candidacy candidacy){
        if(this.candidacies == null){
            this.candidacies = new ArrayList<Candidacy>();
        }
        this.candidacies.add(candidacy);
        candidacy.setPosition(this);
    }

    public void removeCandidacy(Candidacy candidacy){
        if(this.candidacies != null){
            this.candidacies.remove(candidacy);
            candidacy.setPosition(null);
        }
    }


    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return Objects.equals(id, position.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
