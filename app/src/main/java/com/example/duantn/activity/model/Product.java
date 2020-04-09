package com.example.duantn.activity.model;

import java.io.Serializable;

public class Product implements Serializable {
    private String name;
    private String category;
    private String imageLink;
    private double price;
    private String description;
    private String status;
    private String userEmail;
    public String key = "";

    public Product(){

    }

    public Product(String name, String category, String imageLink, double price, String description, String status, String userEmail) {
        this.name = name;
        this.category = category;
        this.imageLink = imageLink;
        this.price = price;
        this.description = description;
        this.status = status;
        this.userEmail = userEmail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
