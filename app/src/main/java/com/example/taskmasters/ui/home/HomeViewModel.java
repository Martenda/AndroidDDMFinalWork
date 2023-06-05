package com.example.taskmasters.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.taskmasters.model.AppDatabase;
import com.example.taskmasters.model.DatabaseClient;
import com.example.taskmasters.model.user.User;
import com.example.taskmasters.model.user.dao.UserDAO;

import java.util.List;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private UserDAO userDao;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void setUserDAO(UserDAO userDao) {
        this.userDao = userDao;
    }

    public void fetchUserData() {
        List<User> userList = userDao.getAllUsers();
        mText.setValue(userList.toString());
    }
}