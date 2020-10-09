package com.Entities;

import com.Entities.enumeration.MeetingType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.*;

@Entity
public class Meeting implements DataObject{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Transient
    private Integer transientId;

    @JsonIgnoreProperties("meetings")
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private Location location;
    @JsonIgnoreProperties("meetings")
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private Schedule schedule;
    @JsonIgnoreProperties("meeting")
    @JoinColumn(name="MEETING_ID")
    @OneToMany(cascade = {CascadeType.REMOVE, CascadeType.MERGE, CascadeType.PERSIST}, orphanRemoval = true)
    private Set<Participation> participations;
    private Date startTime;
    private Date endTime;
    private MeetingType meetingType;

    public Meeting(){
        this.participations = new HashSet<Participation>();
    }
    public void addParticipation(Participation participation){
        if(this.participations == null){
            this.participations = new HashSet<Participation>();
        }
        this.participations.add(participation);
        participation.setMeeting(this);
    }

    public void removeParticipation(Participation participation){
        if(this.participations != null){
            this.participations.remove(participation);
            participation.setMeeting(null);
        }
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
        if(schedule != null){
            if(schedule.getMeetings() == null){
                schedule.setMeetings(new HashSet<Meeting>());
            }
            if(!schedule.getMeetings().contains(this)){
                schedule.getMeetings().add(this);
            }
        }
    }

    public Set<Participation> getParticipations() {
        return participations;
    }

    public void setParticipations(Set<Participation> participations) {
        this.participations = participations;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public MeetingType getMeetingType() {
        return meetingType;
    }

    public void setMeetingType(MeetingType meetingType) {
        this.meetingType = meetingType;
    }





    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTransientId() {
        return transientId;
    }

    public void setTransientId(Integer transientId) {
        this.transientId = transientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meeting meeting = (Meeting) o;
        return Objects.equals(id, meeting.id) &&
                Objects.equals(transientId, meeting.transientId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, transientId);
    }
}
