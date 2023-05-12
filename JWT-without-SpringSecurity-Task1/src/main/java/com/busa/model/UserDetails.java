package com.busa.model;

import lombok.Getter;
import lombok.Setter;


public class UserDetails {

    private int id;
    private String username;
    private String password;
    private String mobno;
    private String token;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getMobno() {
        return mobno;
    }

    public void setMobno(String mobno) {
        this.mobno = mobno;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "UserDetails{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", mobno='" + mobno + '\'' +
                ", token='" + token + '\'' +
                '}';
    }

    public UserDetails(int id, String username, String password, String mobno, String token) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.mobno = mobno;
        this.token = token;
    }

    public UserDetails() {
        super();
    }
}


