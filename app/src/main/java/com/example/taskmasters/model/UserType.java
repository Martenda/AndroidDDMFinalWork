package com.example.taskmasters.model;

public enum UserType {
    CONSUMER(1), SERVICE_PROVIDER(2);
    public final int type;

    UserType(int type) {
        this.type = type;
    }
}
