package com.pro.salon.cattocdi.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FirebaseModel implements Serializable {
    @SerializedName("downloadTokens")
    private String token;

    public FirebaseModel(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
