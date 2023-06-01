package com.example.taskmasters.model;

import com.example.taskmasters.exceptions.AgeException;
import com.example.taskmasters.exceptions.NameSizeException;

public class User {
    private UserType userType;
    private String name;
    private String surname;
    private int age;
    private GenderOptions gender;
    private String email;
    private String password;

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws NameSizeException {
        if (name.length() <= NameSizeException.MINIMUM_LENGTH || name.length() >= NameSizeException.MAXIMUM_LENGTH){
            throw new NameSizeException(name);
        }
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) throws NameSizeException {
        if (surname.length() <= NameSizeException.MINIMUM_LENGTH || surname.length() >= NameSizeException.MAXIMUM_LENGTH)
            throw new NameSizeException(surname);
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) throws AgeException {
        if (age < 18)
            throw new AgeException();
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
}
