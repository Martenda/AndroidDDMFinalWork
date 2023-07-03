package com.example.taskmasters.data.model;

import com.example.taskmasters.model.user.UserType;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    private final String userId;
    private final String displayName;

    private final UserType userType;

    public LoggedInUser(String userId, String displayName, UserType userType) {
        this.userId = userId;
        this.displayName = displayName;
        this.userType = userType;
    }

    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public UserType getUserType() {
        return userType;
    }
}