package com.example.taskmasters.ui.main.home;

import androidx.lifecycle.ViewModel;

import com.example.taskmasters.model.user.dao.UserDAO;

public class HomeViewModel extends ViewModel {

//    private final MutableLiveData<String> mText;
    private UserDAO userDao;

    public HomeViewModel() {
//        mText = new MutableLiveData<>();
//        mText.setValue("This is home fragment");
    }

//    public LiveData<String> getText() {
//        return mText;
//    }

    public void setUserDAO(UserDAO userDao) {
        this.userDao = userDao;
    }

    public void fetchUserData() {
//        List<User> userList = userDao.getAllUsers();
//        mText.setValue(userList.toString());
    }
}