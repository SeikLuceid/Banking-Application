package com.revature.projectone.models;

import java.util.Arrays;

public class Customer extends User
{
    private String firstName;
    private String lastName;
    private int userId;
    private Account[] accounts;

    public Customer(int userId, String firstName, String lastName) {

        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Customer() {

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Account[] getAccounts() {
        return accounts;
    }

    public void setAccounts(Account[] accounts)
    {
        this.accounts = accounts;
    }

    @Override
    public String toString() {
        return "<p><b>Customer ID:</b> " + userId +
        "<b> * Name:</b> " + firstName + " " + lastName + "<br>" +
        "<b>**Accounts**</b></p>" + toAccountString(accounts);
    };

    public String toAccountString(Account[] accounts)
    {
        String returner = "";
        for (Account account : accounts) {
            returner+=account.toString();
        }
        return returner;
    }
}
