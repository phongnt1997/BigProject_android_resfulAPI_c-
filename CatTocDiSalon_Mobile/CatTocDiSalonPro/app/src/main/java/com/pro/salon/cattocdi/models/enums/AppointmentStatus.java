package com.pro.salon.cattocdi.models.enums;

public enum AppointmentStatus {
    NOT_APPROVED(0),
    APPROVED(1),
    CANCEL(2);

    private int status;

    AppointmentStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
