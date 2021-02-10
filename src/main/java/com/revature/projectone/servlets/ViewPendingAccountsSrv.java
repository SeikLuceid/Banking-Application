package com.revature.projectone.servlets;

import com.revature.projectone.helpers.Database;
import com.revature.projectone.models.Customer;
import com.revature.projectone.models.PendingAccount;
import com.revature.projectone.models.Transaction;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ViewPendingAccountsSrv extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        String userId = req.getParameter("userId");

        PendingAccount[] accounts = Database.getPendingAccounts(userId);

        session.setAttribute("pendingAccounts", accounts);
        session.setAttribute("customers", new Customer[0]);
        session.setAttribute("transactions", new Transaction[0]);

        req.getRequestDispatcher("/updateEmployee").forward(req, resp);
    }
}
