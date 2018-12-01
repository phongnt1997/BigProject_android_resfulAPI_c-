package com.pro.salon.cattocdi.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class AppointmentListHome implements Serializable {

    @SerializedName("ListToday")
    private List<Appointment> nextAppointments;
    @SerializedName("ListNotApprove")
    private List<Appointment> notApproveAppointments;
    @SerializedName("Capacity")
    private int capacity;
    @SerializedName("ListCancel")
    private List<Appointment> cancelAppointments;


    public List<Appointment> getNextAppointments() {
        return nextAppointments;
    }

    public void setNextAppointments(List<Appointment> nextAppointments) {
        this.nextAppointments = nextAppointments;
    }

    public List<Appointment> getNotApproveAppointments() {
        return notApproveAppointments;
    }

    public void setNotApproveAppointments(List<Appointment> notApproveAppointments) {
        this.notApproveAppointments = notApproveAppointments;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public List<Appointment> getCancelAppointments() {
        return cancelAppointments;
    }

    public void setCancelAppointments(List<Appointment> cancelAppointments) {
        this.cancelAppointments = cancelAppointments;
    }
}
