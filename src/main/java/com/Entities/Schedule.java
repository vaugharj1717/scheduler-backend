package com.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.xml.stream.Location;
import java.sql.Date;
import java.util.HashSet;
import java.util.Objects;
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
    private ScheduleGroup scheduleGroup;

    public void addMeeting(Meeting meeting){
        if(this.meetings == null){
            this.meetings = new HashSet<Meeting>();
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

    public Set<Meeting> getMeetings() {
        return meetings;
    }

    public void setMeetings(Set<Meeting> meetings) {
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

    public ScheduleGroup getScheduleGroup() {
        return scheduleGroup;
    }

    public void setScheduleGroup(ScheduleGroup scheduleGroup) {
        this.scheduleGroup = scheduleGroup;
        if(scheduleGroup != null){
            scheduleGroup.getSchedules().add(this);
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

