package com.example.taskmasters.model.user;

public class User {

    private String id;
    private UserType userType;
    private String name;
    private String surname;
    private int age;
    private GenderOptions gender;
    private String email;
    private String password;
    private String image;

    public User(UserType userType, String name, String surname, String email, String password, String image) {
        this.userType = userType;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.image = image;
    }

    public User() {
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public GenderOptions getGender() {
        return gender;
    }

    public void setGender(GenderOptions gender) {
        this.gender = gender;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", userType=" + userType +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ", gender=" + gender +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", image=" + image +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
