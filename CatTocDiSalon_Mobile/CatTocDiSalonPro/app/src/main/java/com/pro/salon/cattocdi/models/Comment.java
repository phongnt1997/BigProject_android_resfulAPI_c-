package com.pro.salon.cattocdi.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Comment implements Serializable{
    @SerializedName("ReviewId")
    private String id;
    @SerializedName("CustomerName")
    private String customerName;
    @SerializedName("RateNumber")
    private int rating;
    @SerializedName("Comment")
    private String content;
    @SerializedName("Date")
    private String postDateStr;
    private Timestamp postDate;

    public Comment(int rating, String content) {
        this.rating = rating;
        this.content = content;
    }




    public Timestamp getPostDate() {
        String value = postDateStr.replace("T", " ");

        try {
            postDate =  new Timestamp(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(value).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return postDate;
    }
    public String getPostDateStr(){
        return new SimpleDateFormat("dd-MM-yyyy").format(getPostDate());
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
