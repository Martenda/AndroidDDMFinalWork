package com.example.taskmasters.data.model;

import com.example.taskmasters.model.user.UserType;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    private final int userId;
    private final String displayName;

    private final UserType userType;

    public LoggedInUser(int userId, String displayName, UserType userType) {
        this.userId = userId;
        this.displayName = displayName;
        this.userType = userType;
    }

    public int getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public UserType getUserType() {
        return userType;
    }
}