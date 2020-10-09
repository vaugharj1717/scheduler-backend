package com.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.*;

@Entity
public class Schedule implements DataObject{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @JsonIgnoreProperties("schedule")
    @JoinColumn(name="SCHEDULE_ID")
    @OneToMany(cascade = {CascadeType.REMOVE, CascadeType.MERGE}, orphanRemoval = true)
    private List<Meeting> meetings;

    @JsonIgnoreProperties("schedule")
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private Candidacy candidacy;

    public Schedule(){
        this.meetings = new ArrayList<>();
    }
    public void addMeeting(Meeting meeting){
        if(this.meetings == null){
            this.meetings = new ArrayList<Meeting>();
        }
        this.meetings.add(meeting);
        meeting.setSchedule(this);
    }

    public void removeMeeting(Meeting meeting){
        if(this.meetings != null){
            this.meetings.remove(meeting);
            meeting.setSchedule(null);
        }
    }

    public List<Meeting> getMeetings() {
        return meetings;
    }

    public void setMeetings(List<Meeting> meetings) {
        this.meetings = meetings;
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Candidacy getCandidacy() {
        return candidacy;
    }

    public void setCandidacy(Candidacy candidacy) {
        this.candidacy = candidacy;
        if(candidacy.getSchedule() == null || !candidacy.getSchedule().equals(this)){
            candidacy.setSchedule(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schedule schedule = (Schedule) o;
        return Objects.equals(id, schedule.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

