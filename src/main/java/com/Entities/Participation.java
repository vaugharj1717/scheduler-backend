package com.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;

@Entity
public class Participation implements DataObject{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @JsonBackReference
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private User user;
    @JsonBackReference
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private Meeting meeting;

    private boolean alert;
    private String alertType;
    private boolean canLeaveFeedback;
    private boolean canViewFeedback;
    private boolean canMakeDecision;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        if(user != null){
            if(user.getParticipations() == null){
                user.setParticipations(new HashSet<Participation>());
            }
            user.getParticipations().add(this);
        }
    }

    public Meeting getMeeting() {
        return meeting;
    }

    public void setMeeting(Meeting meeting) {
        this.meeting = meeting;
        if(meeting != null){
            if(meeting.getParticipations() == null){
                meeting.setParticipations(new HashSet<Participation>());
            }
            meeting.getParticipations().add(this);
        }
    }

    public boolean isAlert() {
        return alert;
    }

    public void setAlert(boolean alert) {
        this.alert = alert;
    }

    public String getAlertType() {
        return alertType;
    }

    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }

    public boolean isCanLeaveFeedback() {
        return canLeaveFeedback;
    }

    public void setCanLeaveFeedback(boolean canLeaveFeedback) {
        this.canLeaveFeedback = canLeaveFeedback;
    }

    public boolean isCanViewFeedback() {
        return canViewFeedback;
    }

    public void setCanViewFeedback(boolean canViewFeedback) {
        this.canViewFeedback = canViewFeedback;
    }

    public boolean isCanMakeDecision() {
        return canMakeDecision;
    }

    public void setCanMakeDecision(boolean canMakeDecision) {
        this.canMakeDecision = canMakeDecision;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Participation that = (Participation) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}