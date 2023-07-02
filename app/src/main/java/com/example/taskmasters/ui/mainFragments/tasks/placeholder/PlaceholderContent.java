package com.example.taskmasters.ui.mainFragments.tasks.placeholder;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.example.taskmasters.model.DatabaseClient;
import com.example.taskmasters.model.task.Category;
import com.example.taskmasters.model.task.Task;
import com.example.taskmasters.model.task.dao.TaskDAO;
import com.example.taskmasters.model.user.UserType;

import java.util.ArrayList;
import java.util.List;

public class PlaceholderContent {

    public static final List<TaskPlaceholder> ITEMS = new ArrayList<>();

    public static void loadTasksFromDatabase(Context context) {
        DatabaseClient databaseClient = DatabaseClient.getInstance(context);
        TaskDAO taskDAO = databaseClient.getAppDatabase().taskDao();

        SharedPreferences sharedPreferences = context.getSharedPreferences("login", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("user_id", 0);
        int userType = sharedPreferences.getInt("user_type", 1);

        System.out.println(userId);
        System.out.println(userType);

        List<Task> tasks;

        if (userType == UserType.CONSUMER.type) {
            tasks = taskDAO.getTasksByUserId(userId);
        } else {
            tasks = taskDAO.getAllTasks();
        }

        ITEMS.clear();

        for (Task task : tasks) {
            TaskPlaceholder item = new TaskPlaceholder(
                    String.valueOf(task.getPrice()),
                    task.getTitle(),
                    task.getDetails(),
                    task.getCategory()
            );
            addItem(item);
        }
    }

    private static void addItem(TaskPlaceholder item) {
        ITEMS.add(item);
    }

    public static class TaskPlaceholder {
        public final String price;
        public final String title;
        public final String description;
        public final Category category;

        public TaskPlaceholder(String price, String title, String description, Category category) {
            this.price = price;
            this.title = title;
            this.description = description;
            this.category = category;
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
