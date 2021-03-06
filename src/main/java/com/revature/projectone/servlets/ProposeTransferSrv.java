package com.revature.projectone.servlets;

import com.revature.projectone.helpers.Database;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProposeTransferSrv extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String originString = request.getParameter("originAccount");
        String destinationString = request.getParameter("destinationAccount");
        String amountString = request.getParameter("amount");

        int origin = Integer.parseInt(originString);
        int destination = Integer.parseInt(destinationString);
        double amount = Double.parseDouble(amountString);

        Database.initiateTransfer(origin, destination, amount);

        request.getRequestDispatcher("/updateCustomer").forward(request, response);
    }
}