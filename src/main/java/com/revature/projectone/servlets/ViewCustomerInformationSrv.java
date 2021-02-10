package com.revature.projectone.servlets;

import com.revature.projectone.helpers.Database;
import com.revature.projectone.helpers.UserFactory;
import com.revature.projectone.models.Account;
import com.revature.projectone.models.Customer;
import com.revature.projectone.models.PendingAccount;
import com.revature.projectone.models.Transaction;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ViewCustomerInformationSrv extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String userIdString = req.getParameter("userId");
        int userId = Integer.parseInt(userIdString);
        Customer customer = Database.getCustomer(userId);
        Customer[] customers = {UserFactory.updateCustomer(customer)};



        HttpSession session = req.getSession();
        session.setAttribute("pendingAccounts", new PendingAccount[0]);
        session.setAttribute("customers", customers);
        session.setAttribute("transactions", new Transaction[0]);

        req.getRequestDispatcher("/updateEmployee").forward(req, resp);
    }
}
