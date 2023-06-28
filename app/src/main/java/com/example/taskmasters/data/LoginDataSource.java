package com.example.taskmasters.data;

import com.example.taskmasters.data.model.LoggedInUser;
import com.example.taskmasters.model.user.User;
import com.example.taskmasters.model.user.UserType;
import com.example.taskmasters.model.user.dao.UserDAO;

import java.io.IOException;
import java.util.List;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    private final UserDAO userDAO;

    public LoginDataSource(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public Result<LoggedInUser> login(String username, String password) {
        try {
            User authenticatedUser = authenticateUser(username, password);
            if (authenticatedUser != null) {
                LoggedInUser loggedInUser = new LoggedInUser(authenticatedUser.getId(), (authenticatedUser.getName() + " " +  authenticatedUser.getSurname()), authenticatedUser.getUserType());
                return new Result.Success<>(loggedInUser);
            } else {
                return new Result.Error(new IOException("Invalid username or password"));
            }
        } catch (Exception e) {
            System.out.println(e);
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
    }

    private User authenticateUser(String username, String password) {
        List<User> users = userDAO.getAllUsers();
        System.out.println(users);
        for (User user : users) {
            String userEmail = user.getEmail();
            String userPassword = user.getPassword();
            if (userEmail != null && userPassword != null && userEmail.equals(username) && userPassword.equals(password)) {
                return user;
            }
        }
        return null;
    }
}
