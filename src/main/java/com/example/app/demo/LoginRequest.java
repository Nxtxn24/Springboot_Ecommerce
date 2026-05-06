package com.example.app.demo;

public class LoginRequest {

    private String username;
    private String pwd;
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    @Override
    public String toString() {
        return "LoginRequest [username=" + username + ", pwd=" + pwd + "]";
    }
    public String getPwd() {
        return pwd;
    }
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    
}
