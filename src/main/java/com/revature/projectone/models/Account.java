package com.revature.projectone.models;

import java.util.Arrays;

public class Account {
    private long accountNumber;

    private static final long routingNumber = 123456789;
    private double balance;
    private Transfer[] transfersFrom;
    private Transfer[] transfersTo;

    public Account(long accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public long getRoutingNumber() {
        return routingNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Transfer[] getTransfersFrom() {
        return transfersFrom;
    }

    public void setTransfersFrom(Transfer[] transfersFrom) {
        this.transfersFrom = transfersFrom;
    }

    public Transfer[] getTransfersTo() {
        return transfersTo;
    }

    public void setTransfersTo(Transfer[] transfersTo) {
        this.transfersTo = transfersTo;
    }

    @Override
    public String toString() {
        return "[<b>AccountNumber:</b> " + accountNumber + "]<b> * </b>" +
                "[<b>Balance:</b> " + balance + "]<br>";
//                "[<b>**Outgoing Transfers**]</b><br>" + toTransferString(transfersFrom) + "]<br>" +
//                "[<b>**Incoming Transfers**]</b><br>" + toTransferString(transfersTo) + "<br>";
    }

    private String toTransferString(Transfer[] transfers)
    {
        String returner = "";
        for (Transfer transfer : transfers){
            returner += transfer.toString();
        }
        return returner;
    }
}

