package com.salon.cattocdi.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Promotion implements Serializable{
    @SerializedName("PromotionId")
    private int id;
    @SerializedName("StartTime")
    private String startPeriodStr;
    @SerializedName("EndTime")
    private String endPeriodStr;
    @SerializedName("Description")
    private String description;
    @SerializedName("DiscountPercent")
    private int discount;
    private Timestamp startPeriod;
    private Timestamp endPeriod;

    public int getId() {
        return id;
    }


    public Timestamp getStartPeriod() {
        try {

            startPeriod = new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startPeriodStr.replace("T"," ")).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return startPeriod;
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

    public void setStartPeriod(Timestamp startPeriod) {
        this.startPeriod = startPeriod;
    }

    public Timestamp getEndPeriod() {
        try {
            endPeriod = new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endPeriodStr.replace("T"," ")).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
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

    public String getStartDateStr(){
        return new SimpleDateFormat("dd/MM/yyyy").format(getStartPeriod());
    }

    public String getEndDateStr(){
        return new SimpleDateFormat("dd/MM/yyyy").format(getEndPeriod());
    }
}
