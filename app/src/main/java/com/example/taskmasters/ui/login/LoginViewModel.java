package com.example.taskmasters.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.util.Patterns;

import com.example.taskmasters.model.user.dao.LoggedInUser;
import com.example.taskmasters.R;
import com.example.taskmasters.model.user.User;
import com.example.taskmasters.model.user.dao.UserDAO;
import com.google.firebase.database.DatabaseError;

import java.util.List;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();

    LoginViewModel() {
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password) {
        UserDAO userDAO = new UserDAO();
        userDAO.getAllUsers(new UserDAO.UserListCallback() {
            @Override
            public void onUserListLoaded(List<User> userList) {
                for (User user : userList) {
                    String userEmail = user.getEmail();
                    String userPassword = user.getPassword();
                    if (userEmail != null && userPassword != null && userEmail.equals(username) && userPassword.equals(password)) {
                        LoggedInUser loggedInUser = new LoggedInUser(user.getId(), (user.getName() + " " + user.getSurname()), user.getUserType());
                        System.out.println("logged");
                        loginResult.setValue(new LoginResult(new LoggedInUserView(loggedInUser.getDisplayName(), loggedInUser.getUserType(), loggedInUser.getUserId())));
                        return;
                    }
                }
                loginResult.setValue(new LoginResult(R.string.login_failed));
            }

            @Override
            public void onUserListError(DatabaseError databaseError) {

            }
        });
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 6;
    }
}