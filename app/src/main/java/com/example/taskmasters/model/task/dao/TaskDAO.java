package com.example.taskmasters.model.task.dao;

import androidx.annotation.NonNull;

import com.example.taskmasters.model.task.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TaskDAO {
    private DatabaseReference taskRef;

    public interface TaskListCallback {
        void onUserListLoaded(List<Task> taskList);
        void onUserListError(DatabaseError databaseError);
    }

    public interface TaskCallback {
        void onTaskLoad(Task task);
        void onTaskLoadError(DatabaseError databaseError);
    }


    public TaskDAO() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        taskRef = database.getReference("tasks");
    }

    public void insertTask(Task task) {
        String taskId = taskRef.push().getKey();
        task.setId(taskId);
        assert taskId != null;
        taskRef.child(taskId).setValue(task);
    }

    public void updateTask(Task task) {
        taskRef.child(task.getId()).setValue(task);
    }

    public void getAllTasks(final TaskListCallback callback) {
        List<Task> tasks = new ArrayList<>();
        taskRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot taskSnapshot : dataSnapshot.getChildren()) {
                    Task task = taskSnapshot.getValue(Task.class);
                    tasks.add(task);
                }
                callback.onUserListLoaded(tasks);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onUserListError(databaseError);
            }
        });
    }

    public List<Task> getTasksByUserId(String userId) {
        List<Task> tasks = new ArrayList<>();
        Query query = taskRef.orderByChild("userId").equalTo(userId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot taskSnapshot : dataSnapshot.getChildren()) {
                    Task task = taskSnapshot.getValue(Task.class);
                    tasks.add(task);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
            }
        });
        return tasks;
    }

    public void getTaskById(String taskId, final TaskCallback callback) {
        final Task[] task = new Task[1];
        Query query = taskRef.child(taskId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                task[0] = dataSnapshot.getValue(Task.class);
                callback.onTaskLoad(task[0]);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onTaskLoadError(databaseError);
            }
        });
    }
}
