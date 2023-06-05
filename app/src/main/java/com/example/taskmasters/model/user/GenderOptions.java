package com.example.taskmasters.model.user;

public enum GenderOptions {
    MALE(1), FEMALE(2), OTHER(3);
    public final int type;

    GenderOptions(int type) {
        this.type = type;
    }
}
