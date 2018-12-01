package com.pro.salon.cattocdi.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Service implements Serializable{
    @SerializedName("Id")
    private int salonServiceId;
    @SerializedName("CategoryId")
    private int id;
    @SerializedName("ServiceId")
    private int serviceId;
    @SerializedName("ServiceName")
    private String name;
    @SerializedName("Price")
    private double price;
    @SerializedName("AvarageTime")
    private int durantion;
    @SerializedName("CategoryName")
    private String categoryName;


    public Service(int id, String name, double price, int durantion) {
        this.serviceId = id;
        this.name = name;
        this.price = price;
        this.durantion = durantion;
    }

    public Service(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Service() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public int getDurantion() {
        return durantion;
    }

    public void setDurantion(int durantion) {
        this.durantion = durantion;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public int getSalonServiceId() {
        return salonServiceId;
    }

    public void setSalonServiceId(int salonServiceId) {
        this.salonServiceId = salonServiceId;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
