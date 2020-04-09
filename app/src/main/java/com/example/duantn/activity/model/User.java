package com.example.duantn.activity.model;

import java.util.Date;

public class User {
    private int id;
    private String email;
    private String password;
    private Date birthday;
    private String avatar;
    private String indetifyCard;
    private String phoneNumber;
    private String address;
    private String status;

    public User() {
    }

    public User(String email, String password, Date birthday, String avatar, String indetifyCard, String phoneNumber, String address, String status) {
        this.email = email;
        this.password = password;
        this.birthday = birthday;
        this.avatar = avatar;
        this.indetifyCard = indetifyCard;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getIndetifyCard() {
        return indetifyCard;
    }

    public void setIndetifyCard(String indetifyCard) {
        this.indetifyCard = indetifyCard;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
