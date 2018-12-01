package com.salon.cattocdi.models;

import com.google.gson.annotations.SerializedName;

public class ResponseMessage {
    @SerializedName("Succeeded")
    private boolean success;
    @SerializedName("Errors")
    private String[] errors;

    public ResponseMessage(boolean success, String[] errors) {
        this.success = success;
        this.errors = errors;
    }

    public ResponseMessage() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String[] getErrors() {
        return errors;
    }

    public void setErrors(String[] errors) {
        this.errors = errors;
    }
}
