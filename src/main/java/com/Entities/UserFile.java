package com.Entities;

import com.Entities.enumeration.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class UserFile implements DataObject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String filename;

    @JsonIgnoreProperties("userFiles")
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private User user;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        if(user != null){
            if(user.getUserFiles() == null){
                user.setUserFiles(new HashSet<UserFile>());
            }
            user.getUserFiles().add(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserFile userFile = (UserFile) o;
        return Objects.equals(id, userFile.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
