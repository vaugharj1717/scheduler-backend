package com.Entities;

import com.Entities.enumeration.Role;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.scheduling.annotation.Schedules;

import javax.persistence.*;
import java.util.*;

@Entity
public class User implements DataObject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String username;
    @JsonIgnore
    private String password;
    private String email;
    private String phone;
    private String name;
    private Role role;
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private Department department;
    @JsonIgnoreProperties("participant")
    @JoinColumn(name="USER_ID")
    @OneToMany(cascade = {CascadeType.REMOVE, CascadeType.MERGE}, orphanRemoval = true)
    private Set<Participation> participations;
    @JsonIgnoreProperties("candidate")
    @JoinColumn(name="CANDIDATE_ID")
    @OneToMany(cascade = {CascadeType.REMOVE, CascadeType.MERGE}, orphanRemoval = true)
    private Set<Candidacy> candidacies;

    @JsonIgnoreProperties("user")
    @JoinColumn(name="USER_ID")
    @OneToMany(cascade = {CascadeType.REMOVE, CascadeType.MERGE}, orphanRemoval = true)
    private Set<UserFile> userFiles;

    public User(){
        this.candidacies = new HashSet<Candidacy>();
        this.participations = new HashSet<Participation>();
        this.userFiles = new HashSet<UserFile>();
    }
    public void addParticipation(Participation participation){
        if(this.participations == null){
            this.participations = new HashSet<Participation>();
        }
        this.participations.add(participation);
        participation.setParticipant(this);
    }

    public void removeParticipation(Participation participation){
        if(this.participations != null){
            this.participations.remove(participation);
            participation.setParticipant(null);
        }
    }

    public void addCandidacy(Candidacy candidacy){
        if(this.candidacies == null){
            this.candidacies = new HashSet<Candidacy>();
        }
        this.candidacies.add(candidacy);
        candidacy.setCandidate(this);
    }

    public void removeParticipation(Candidacy candidacy){
        if(this.candidacies != null){
            this.candidacies.remove(candidacy);
            candidacy.setCandidate(null);
        }
    }



    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Set<Participation> getParticipations() {
        return participations;
    }

    public void setParticipations(Set<Participation> participations) {
        this.participations = participations;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public Set<UserFile> getUserFiles() {
        return userFiles;
    }

    public void setUserFiles(Set<UserFile> userFiles) {
        this.userFiles = userFiles;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Set<Candidacy> getCandidacies() {
        return candidacies;
    }

    public void setCandidacies(Set<Candidacy> candidacies) {
        this.candidacies = candidacies;
    }

    public void addUserFile(UserFile userFile){
        if(this.userFiles == null){
            this.userFiles = new HashSet<UserFile>();
        }
        this.userFiles.add(userFile);
        userFile.setUser(this);
    }

    public void removeUserFile(UserFile userFile){
        if(this.userFiles != null){
            this.userFiles.remove(userFile);
            userFile.setUser(null);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
