package com.revature.projectone.models;

public class Employee extends User{
    int userId;

    public Employee(int userId, String lastName) {
        this.userId = userId;
    }
}
