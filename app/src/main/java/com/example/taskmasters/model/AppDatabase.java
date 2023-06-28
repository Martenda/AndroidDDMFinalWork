package com.example.taskmasters.model;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.taskmasters.model.task.Task;
import com.example.taskmasters.model.task.dao.TaskDAO;
import com.example.taskmasters.model.user.User;
import com.example.taskmasters.model.user.dao.UserDAO;

@Database(entities = {Task.class, User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDAO userDao();
    public abstract TaskDAO taskDao();
}
