package com.example.taskmasters.ui.mainFragments.tasks.placeholder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class PlaceholderContent {

    /**
     * An array of sample (placeholder) items.
     */
    public static final List<TaskPlaceholder> ITEMS = new ArrayList<>();


    private static final int COUNT = 25;

    static {
        for (int i = 1; i <= COUNT; i++) {
            addItem(createPlaceholderItem(i));
        }
    }

    private static void addItem(TaskPlaceholder item) {
        ITEMS.add(item);
    }

    private static TaskPlaceholder createPlaceholderItem(int position) {
        return new TaskPlaceholder(String.valueOf(position), "Item " + position, makeDetails(position), "Serviços gerais");
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A placeholder item representing a piece of content.
     */
    public static class TaskPlaceholder {
        public final String price;
        public final String title;
        public final String description;
        public final String category;

        public TaskPlaceholder(String price, String title, String description, String category) {
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