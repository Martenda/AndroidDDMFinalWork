package com.example.taskmasters.model.task;

import androidx.annotation.NonNull;

public class Task {
    private String id;
    private String title;
    private String details;
    private Category category;
    private double price;
    private String userId;

    public Task(String title, String details, Category category, double price, String userId) {
        this.title = title;
        this.details = details;
        this.category = category;
        this.price = price;
        this.userId = userId;
    }

    public Task() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @NonNull
    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", details='" + details + '\'' +
                ", category=" + category +
                ", price=" + price +
                ", userId=" + userId +
                '}';
    }
}
