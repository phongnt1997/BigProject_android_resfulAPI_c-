package com.pro.salon.cattocdi.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Promotion implements Serializable{
    @SerializedName("SalonId")
    private int salonId;
    @SerializedName("Id")
    private int id;
    private Timestamp startPeriod;
    private Timestamp endPeriod;
    @SerializedName("Description")
    private String description;
    @SerializedName("DiscountPercent")
    private int discount;
    @SerializedName("StartTime")
    private String startPeriodStr;
    @SerializedName("EndTime")
    private String endPeriodStr;
    @SerializedName("Status")
    private int status;

    public Promotion() {
    }

    public Promotion(int id, Timestamp startPeriod, Timestamp endPeriod, String description, int discount) {
        this.id = id;
        this.startPeriod = startPeriod;
        this.endPeriod = endPeriod;
        this.description = description;
        this.discount = discount;
    }

    public int getSalonId() {
        return salonId;
    }

    public void setSalonId(int salonId) {
        this.salonId = salonId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getStartPeriod() {
        String value = startPeriodStr.replace("T", " ");

        try {
            startPeriod =  new Timestamp(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(value).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return startPeriod;
    }

    public void setStartPeriod(Timestamp startPeriod) {
        this.startPeriod = startPeriod;
    }

    public Timestamp getEndPeriod() {
        String value = endPeriodStr.replace("T", " ");

        try {
            endPeriod =  new Timestamp(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(value).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return endPeriod;
    }

    public void setEndPeriod(Timestamp endPeriod) {
        this.endPeriod = endPeriod;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }
    public String getStartToEndstr(){
        return new SimpleDateFormat("dd-MM-yyyy").format(getStartPeriod()) + " - " + new SimpleDateFormat("dd-MM-yyyy").format(getEndPeriod());
    }

    public String getStartPeriodStr() {
        return startPeriodStr;
    }

    public void setStartPeriodStr(String startPeriodStr) {
        this.startPeriodStr = startPeriodStr;
    }

    public String getEndPeriodStr() {
        return endPeriodStr;
    }

    public void setEndPeriodStr(String endPeriodStr) {
        this.endPeriodStr = endPeriodStr;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
