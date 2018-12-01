package com.salon.cattocdi.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class AppointmentCreateModel implements Serializable {

    @SerializedName("SalonId")
    private int salonId;
    @SerializedName("Services")
    private List<Integer> servicesId;
    @SerializedName("StartTime")
    private String StartTime;
    @SerializedName("Duration")
    private int Duration;
    @SerializedName("Indexes")
    private List<Integer> Indexes;
    @SerializedName("SlotId")
    private int SlotId;

    public int getSalonId() {
        return salonId;
    }

    public void setSalonId(int salonId) {
        this.salonId = salonId;
    }

    public List<Integer> getServicesId() {
        return servicesId;
    }

    public void setServicesId(List<Integer> servicesId) {
        this.servicesId = servicesId;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public int getDuration() {
        return Duration;
    }

    public void setDuration(int duration) {
        Duration = duration;
    }

    public List<Integer> getIndexes() {
        return Indexes;
    }

    public void setIndexes(List<Integer> indexes) {
        Indexes = indexes;
    }

    public int getSlotId() {
        return SlotId;
    }

    public void setSlotId(int slotId) {
        SlotId = slotId;
    }
}
