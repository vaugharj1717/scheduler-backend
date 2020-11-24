package com.Security;

import com.Entities.Department;

import java.util.List;


    import java.util.List;

public class JwtResponse {
    private String accessToken;
    private String type = "Bearer";
    private Integer id;
    private String email;
    private String role;
    private Department department;

    public JwtResponse(String accessToken, Integer id, String email, String role, Department department) {
        this.accessToken = accessToken;
        this.id = id;
        this.email = email;
        this.role = role;
        this.department = department;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return this.role;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
