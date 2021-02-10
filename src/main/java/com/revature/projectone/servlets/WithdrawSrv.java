package com.revature.projectone.servlets;

import com.revature.projectone.helpers.Database;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WithdrawSrv extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String accountNumberString = request.getParameter("accountNumber");
        String amountString = request.getParameter("amount");

        int accountNumber = Integer.parseInt(accountNumberString);
        double amount = Double.parseDouble(amountString);

        Database.sendWithdrawal(accountNumber, amount);

        request.getRequestDispatcher("/updateCustomer").forward(request, response);
    }
}
