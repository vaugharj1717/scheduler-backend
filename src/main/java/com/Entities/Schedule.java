package com.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.xml.stream.Location;
import java.sql.Date;
import java.util.Set;

@Entity
public class Schedule implements DataObject{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Meeting> meetings;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    private Position position;

    public Set<Meeting> getMeetings() {
        return meetings;
    }

    public void setMeetings(Set<Meeting> meetings) {
        this.meetings = meetings;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

}
