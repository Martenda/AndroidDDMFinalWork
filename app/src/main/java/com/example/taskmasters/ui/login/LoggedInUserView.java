package com.example.taskmasters.ui.login;

import com.example.taskmasters.model.user.UserType;

import java.io.Serializable;

/**
 * Class exposing authenticated user details to the UI.
 */
public class LoggedInUserView implements Serializable, AutoCloseable {
    private final String displayName;
    private final UserType userType;
    //... other data fields that may be accessible to the UI

    LoggedInUserView(String displayName, UserType userType) {
        this.displayName = displayName;
        this.userType = userType;
    }

    public String getDisplayName() {
        return displayName;
    }

    public UserType getUserType() {
        return userType;
    }

    @Override
    public void close() throws Exception {

    }
}