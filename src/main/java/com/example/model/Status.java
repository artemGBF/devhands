package com.example.model;

public enum Status {
    PLANNED,
    DELAYED,
    CANCELLED,
    DONE;

    public static Status[] getForFutureDate() {
        return new Status[]{PLANNED, DELAYED, CANCELLED};
    }

    public static Status[] getForPastDate() {
        return new Status[]{DONE, DELAYED, CANCELLED};
    }
}
