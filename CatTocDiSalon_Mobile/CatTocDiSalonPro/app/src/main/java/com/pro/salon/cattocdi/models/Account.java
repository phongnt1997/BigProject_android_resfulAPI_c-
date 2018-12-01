package com.pro.salon.cattocdi.models;

import com.google.gson.annotations.SerializedName;

public class Account {

    @SerializedName("Username")
    private String username;
    @SerializedName("Password")
    private String password;
    private String grant_type;
    @SerializedName("access_token")
    private String token;

    public Account(String username, String password, String grant_type) {
        this.username = username;
        this.password = password;
        this.grant_type = grant_type;
    }

    public Account(String username, String password, String grant_type, String token) {
        this.username = username;
        this.password = password;
        this.grant_type = grant_type;
        this.token = token;
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

    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
