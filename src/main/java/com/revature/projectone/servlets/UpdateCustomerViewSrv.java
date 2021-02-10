package com.revature.projectone.servlets;

import com.revature.projectone.helpers.UserFactory;
import com.revature.projectone.models.Customer;
import sun.misc.Request;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UpdateCustomerViewSrv extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("UpdatingCustomerView");
        HttpSession session = req.getSession();

        updateAccountNumber(req, session);

        updateCustomerAccounts(session);

        resp.sendRedirect("customer.jsp");
    }

    private void updateCustomerAccounts(HttpSession session) {
        Customer customer = (Customer) session.getAttribute("customer");
        customer = UserFactory.updateCustomer(customer);
        session.setAttribute("customer", customer);
    }

    private void updateAccountNumber(HttpServletRequest req, HttpSession session) {
        String value = req.getParameter("accountIndex");
        int accountIndex = (int) session.getAttribute("accountIndex");
        if(value != null)
        {
            accountIndex = Integer.parseInt(value);
        }
        session.setAttribute("accountIndex", accountIndex);
    }
}
