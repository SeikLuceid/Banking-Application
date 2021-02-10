package com.revature.projectone.models;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;

public class Transaction {
    char transactionType;
    int origin;
    int destination;
    double amount;
    int transactionId;
    Timestamp timestamp;

    public Transaction(char transactionType, int origin, int destination, double amount, int transactionId, Timestamp timestamp) {
        this.transactionType = transactionType;
        this.origin = origin;
        this.destination = destination;
        this.amount = amount;
        this.transactionId = transactionId;
        this.timestamp = timestamp;
    }

    public char getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(char transactionType) {
        this.transactionType = transactionType;
    }

    public int getOrigin() {
        return origin;
    }

    public void setOrigin(int origin) {
        this.origin = origin;
    }

    public int getDestination() {
        return destination;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public String toString() {
        return "<p align=\"center\"><b>Origin:</b> " + (origin == 0 ? "CASH" : origin) + "<b> * </b>" +
                "<b>Destination:</b> " + (destination == 0 ? "CASH" : destination) + "<br>" +
                "<b>Amount:</b> " + NumberFormat.getCurrencyInstance().format(amount)+ "<b> * </b>" +
                "<b>Transaction ID:</b> " + transactionId + "<br>" +
                "<b>Timestamp:</b> " + new Date(timestamp.getTime()) + "</p>";
    }
}
