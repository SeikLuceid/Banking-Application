package com.revature.projectone.servlets;

import com.revature.projectone.helpers.Database;
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

public class ViewTransactionsSrv extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userIdString = req.getParameter("userId");
        Transaction[] transactions = Database.viewTranslationLog(userIdString);

        HttpSession session = req.getSession();
        session.setAttribute("pendingAccounts", new PendingAccount[0]);
        session.setAttribute("customer", new Customer[0]);
        session.setAttribute("transactions", transactions);

        req.getRequestDispatcher("/updateEmployee").forward(req, resp);
    }
}
