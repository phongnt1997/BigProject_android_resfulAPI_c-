package com.salon.cattocdi.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class Comment implements Serializable{
    @SerializedName("AppointmentId")
    private int appointmentId;
    private String customerName;
    @SerializedName("RateNumber")
    private int rating;
    @SerializedName("Comment")
    private String content;
    private Date date;

    public Comment(int appointmentId, int rating, String content) {
        this.appointmentId = appointmentId;
        this.rating = rating;
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
