package com.pro.salon.cattocdi.models;

import com.google.gson.annotations.SerializedName;

public class ResponseMsg {
    @SerializedName("Succeeded")
    private boolean succeeded;
    @SerializedName("Errors")
    private String[] errors;

    public ResponseMsg(boolean succeeded, String[] errors) {
        this.succeeded = succeeded;
        this.errors = errors;
    }

    public boolean isSucceeded() {
        return succeeded;
    }

    public void setSucceeded(boolean succeeded) {
        this.succeeded = succeeded;
    }

    public String[] getErrors() {
        return errors;
    }

    public void setErrors(String[] errors) {
        this.errors = errors;
    }
}
