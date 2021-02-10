package com.revature.projectone.servlets;

import com.revature.projectone.helpers.UserFactory;
import com.revature.projectone.models.Customer;
import com.revature.projectone.models.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginSrv extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username").toUpperCase();
        String password = request.getParameter("password");

        User user = UserFactory.getUser(username, password);

        String targetURL = "index.jsp";

        if(user != null)
        {
            if(UserFactory.isCustomer(user))
            {
                HttpSession session = request.getSession();
                targetURL = "customer.jsp";
                Customer customer = (Customer)user;
                session.setAttribute("customer", customer);
                session.setAttribute("accountIndex", 0);
            }
            else
            {
                targetURL = "employee.jsp";
            }
        }
        System.out.println(Runtime.getRuntime() + "FORWARDING RESULTS");
        response.sendRedirect(targetURL);
    }
}

