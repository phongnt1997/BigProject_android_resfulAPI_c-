package com.salon.cattocdi.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class Appointment implements Serializable{
    @SerializedName("AppointmentId")
    private int appointmentId;
    @SerializedName("SalonID")
    private int salonId;
    @SerializedName("Status")
    private int status;
    private Timestamp startTime;
    private Timestamp endTime;
    @SerializedName("Services")
    private List<Service> services;
    @SerializedName("DiscountPercent")
    private int discount;
    @SerializedName("BookDate")
    private String bookDate;
    @SerializedName("TimeSlot")
    private String timeSlot;
    @SerializedName("StartTime")
    private String startTimeStr;
    @SerializedName("Duration")
    private int duration;
    @SerializedName("Reviews")
    private Comment review;
    @SerializedName("CancelledReason")
    private String reason;



    public Appointment() {
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStartTimeStr() {
        return startTimeStr;
    }

    public void setStartTimeStr(String startTimeStr) {
        this.startTimeStr = startTimeStr;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public int getSalonId() {
        return salonId;
    }

    public void setSalonId(int salonId) {
        this.salonId = salonId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Timestamp getStartTime() {
        String value = startTimeStr.replace("T"," ");
        try {
            startTime = new Timestamp( new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(value).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {

        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        endTime = new Timestamp(startTime.getTime() + duration*60*1000);
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getBookDate() {
        return bookDate;
    }

    public void setBookDate(String bookDate) {
        this.bookDate = bookDate;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public Comment getReview() {
        return review;
    }

    public void setReview(Comment review) {
        this.review = review;
    }
}
