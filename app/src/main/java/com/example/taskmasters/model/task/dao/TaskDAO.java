package com.example.taskmasters.model.task.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.taskmasters.model.task.Task;

import java.util.List;

@Dao
public interface TaskDAO {
    @Insert
    void insertTask(Task task);

    @Query("SELECT * FROM tasks")
    List<Task> getAllTasks();

    @Query("SELECT * FROM tasks WHERE userId = :id")
    List<Task> getTasksByUserId(int id);
}