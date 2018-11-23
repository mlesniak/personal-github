package com.mlesniak.authentication;

public class User {
    private String username;
    private String accessToken;

    public boolean isAuthenticated() {
        return accessToken != null;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", accessToken='" + accessToken + '\'' +
                '}';
    }
}
