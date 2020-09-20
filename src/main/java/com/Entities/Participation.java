package com.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
public class Participation implements DataObject{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
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
    }

    public Meeting getMeeting() {
        return meeting;
    }

    public void setMeeting(Meeting meeting) {
        this.meeting = meeting;
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


}
