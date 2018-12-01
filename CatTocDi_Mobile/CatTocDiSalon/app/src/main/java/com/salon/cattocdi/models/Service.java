package com.salon.cattocdi.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Service implements Serializable{
    @SerializedName("SalonServiceId")
    private int id;
    @SerializedName("ServiceId")
    private int serviceId;
    @SerializedName("ServiceName")
    private String name;
    @SerializedName("Price")
    private float price;
    @SerializedName("AverageTime")
    private int minutes;
    @SerializedName("CategoryId")
    private int categoryId;
    @SerializedName("CategoryName")
    private String categoryName;

    public Service(int id, String name, float price, int minutes) {
        this.serviceId = id;
        this.name = name;
        this.price = price;
        this.minutes = minutes;
    }

    public Service() {
    }

    public int getId() {
        return id;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
