package com.salon.cattocdi.models;

import java.io.Serializable;
import java.util.Date;

public class CloseDate implements Serializable{
    private int salonId;
    private Date date;
    private String description;

    public int getSalonId() {
        return salonId;
    }

    public void setSalonId(int salonId) {
        this.salonId = salonId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
