package com.example.taskmasters.ui.mainFragments.tasks.placeholder;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.taskmasters.model.task.Category;
import com.example.taskmasters.model.task.Task;
import com.example.taskmasters.model.task.dao.TaskDAO;
import com.example.taskmasters.model.user.UserType;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.List;

public class PlaceholderContent {

    public static final List<TaskPlaceholder> ITEMS = new ArrayList<>();

    public static void loadTasksFromDatabase(Context context, PlaceholderCallback callback) {
        TaskDAO taskDAO = new TaskDAO();

        SharedPreferences sharedPreferences = context.getSharedPreferences("login", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("user_id", "none");
        int userType = sharedPreferences.getInt("user_type", 1);

        if (userType == UserType.CONSUMER.type) {
//            tasks = taskDAO.getTasksByUserId(userId);
        } else {
            taskDAO.getAllTasks(new TaskDAO.TaskListCallback() {
                @Override
                public void onUserListLoaded(List<Task> taskList) {
                    ITEMS.clear();
                    for (Task task : taskList) {
                        TaskPlaceholder item = new TaskPlaceholder(
                                String.valueOf(task.getPrice()),
                                task.getTitle(),
                                task.getDetails(),
                                task.getCategory(),
                                task.getId());
                        addItem(item);
                    }
                    callback.onTasksLoaded();
                }

                @Override
                public void onUserListError(DatabaseError databaseError) {
                    callback.onTasksError(databaseError);
                }
            });
        }
    }

    public interface PlaceholderCallback {
        void onTasksLoaded();
        void onTasksError(DatabaseError databaseError);
    }

    private static void addItem(TaskPlaceholder item) {
        ITEMS.add(item);
    }

    public static class TaskPlaceholder {
        public final String price;
        public final String title;
        public final String description;
        public final Category category;

        public final String id;

        public TaskPlaceholder(String price, String title, String description, Category category, String id) {
            this.price = price;
            this.title = title;
            this.description = description;
            this.category = category;
            this.id = id;
        }

        @Override
        public String toString() {
            return "TaskPlaceholder{" +
                    "price='" + price + '\'' +
                    ", title='" + title + '\'' +
                    ", description='" + description + '\'' +
                    ", category='" + category + '\'' +
                    '}';
        }
    }
}
