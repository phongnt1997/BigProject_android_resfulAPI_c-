package com.pro.salon.cattocdi.models.enums;

public enum PromotionStatus {
    CONTINUTE(0),
    CANCEL(1);

    private int status;

    PromotionStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
