package com.revature.projectone.models;

public class PendingAccount {
    double balance;
    int customerId;
    int pendingId;

    public PendingAccount(double balance, int customerId, int pendingId) {

        this.balance = balance;
        this.customerId = customerId;
        this.pendingId = pendingId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getPendingId() {
        return pendingId;
    }

    public void setPendingId(int pendingId) {
        this.pendingId = pendingId;
    }

    @Override
    public String toString() {
        return "Pending Account: " + pendingId + "<br>Balance: " + balance + "<br>Customer ID: " + customerId;
    }
}
