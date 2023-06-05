package com.example.taskmasters.ui.login;

import com.example.taskmasters.model.user.UserType;

/**
 * Class exposing authenticated user details to the UI.
 */
class LoggedInUserView {
    private final String displayName;
    private final UserType userType;
    //... other data fields that may be accessible to the UI

    LoggedInUserView(String displayName, UserType userType) {
        this.displayName = displayName;
        this.userType = userType;
    }

    String getDisplayName() {
        return displayName;
    }
}