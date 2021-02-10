package com.revature.projectone.models;

import java.text.NumberFormat;
import java.util.Formatter;

public class Transfer {
    private double amount;
    private int transferId;
    private long origin;
    private long destination;

    public Transfer(int transferId, long origin, long destination, double amount) {
        this.transferId = transferId;
        this.origin = origin;
        this.destination = destination;
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public long getOrigin() {
        return origin;
    }

    public void setOrigin(long origin) {
        this.origin = origin;
    }

    public long getDestination() {
        return destination;
    }

    public void setDestination(long destination) {
        this.destination = destination;
    }

    @Override
    public String toString() {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        return "[<b>Origin:</b> " + origin + "]<b> * </b>" +
                "[<b>Destination:</b> " + destination + "]<b> * </b>" +
                "[<b>Amount:</b> " + formatter.format(amount) + "]<br>";
    }
}
