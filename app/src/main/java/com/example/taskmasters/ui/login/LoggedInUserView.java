package com.example.taskmasters.ui.login;

import com.example.taskmasters.model.user.UserType;

import java.io.Serializable;

/**
 * Class exposing authenticated user details to the UI.
 */
public class LoggedInUserView implements Serializable, AutoCloseable {
    private final String displayName;
    private final UserType userType;

    private final int id;

    LoggedInUserView(String displayName, UserType userType, int id) {
        this.displayName = displayName;
        this.userType = userType;
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public UserType getUserType() {
        return userType;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "LoggedInUserView{" +
                "userType=" + userType.type +
                '}';
    }

    @Override
    public void close() throws Exception {

    }
}