package com.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Candidacy implements DataObject{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @JsonIgnoreProperties("candidacy")
    @OneToOne(cascade = {CascadeType.REMOVE, CascadeType.MERGE}, orphanRemoval = true)
    Schedule schedule;
    @JsonIgnoreProperties("candidacies")
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    Position position;
    @JsonIgnoreProperties("candidacies")
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    User candidate;


    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
        if(schedule.getCandidacy() == null || !schedule.getCandidacy().equals(this)){
            schedule.setCandidacy(this);
        }
    }

    public Position getPosition() {
        return position;
    }


    public User getCandidate() {
        return candidate;
    }

    public void setCandidate(User candidate) {
        this.candidate = candidate;
        if(candidate != null){
            if(candidate.getCandidacies() == null){
                candidate.setCandidacies(new ArrayList<Candidacy>());
            }
            if(!candidate.getCandidacies().contains(this)){
                candidate.getCandidacies().add(this);
            }
        }
    }

    public void setPosition(Position position) {
        this.position = position;
        if(position != null){
            if(position.getCandidacies() == null){
                position.setCandidacies(new ArrayList<Candidacy>());
            }
            if(!position.getCandidacies().contains(this)){
                position.getCandidacies().add(this);
            }
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
        Candidacy that = (Candidacy) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
