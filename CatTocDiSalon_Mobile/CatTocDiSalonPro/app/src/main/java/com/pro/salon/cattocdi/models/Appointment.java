package com.pro.salon.cattocdi.models;

import com.google.gson.annotations.SerializedName;
import com.pro.salon.cattocdi.models.enums.AppointmentStatus;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class Appointment implements Serializable {
    @SerializedName("AppointmentId")
    private int appointmentId;
    private Salon salon;
    @SerializedName("Status")
    private int status;
    private Timestamp start;
    private Timestamp end;
    @SerializedName("Services")
    private List<Service> services;
    private int discount;
    @SerializedName("BookedDate")
    private String bookedDateStr;
    @SerializedName("Duration")
    private int duration;
    @SerializedName("Customer")
    private Customer customer;
    @SerializedName("StartTime")
    private String startStr;
    @SerializedName("CancelledReason")
    private String cancelReason;

    public Appointment() {
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getBookedDateStr() {
        return bookedDateStr;
    }

    public void setBookedDateStr(String bookedDateStr) {
        this.bookedDateStr = bookedDateStr;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public Salon getSalon() {
        return salon;
    }

    public void setSalon(Salon salon) {
        this.salon = salon;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Timestamp getstart() {
        String value = startStr.replace("T", " ");
        try {
            start = new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(value).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return start;
    }

    public void setstart(Timestamp start) {
        this.start = start;
    }

    public Timestamp getend() {
        String value = startStr.replace("T", " ");
        try {
            end = new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(value).getTime() + duration * 60 * 1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return end;
    }

    public void setend(Timestamp end) {
        this.end = end;
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

    public String getStartToEnd() {
        return new SimpleDateFormat("HH:mm").format(getstart()) + " - " + new SimpleDateFormat("HH:mm").format(getend());
    }

    public String getServicesName() {
        String result = "";
        for (Service service :
                services) {
            result += service.getName() + ", ";
        }
        return result.substring(0, result.length() - 2);
    }

    public int getSlotIndex() {
        String value = startStr.replace("T", " ");
        int slot = 0;
        Timestamp startTmp = getstart();
        slot = (startTmp.getHours() * 60 + startTmp.getMinutes()) / 15;

        return slot;
    }

    public int getTotalSlot() {
        int slotTmp = duration / 15;
        if (duration > slotTmp * 15) {
            slotTmp++;
        }
        return slotTmp;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }
}
