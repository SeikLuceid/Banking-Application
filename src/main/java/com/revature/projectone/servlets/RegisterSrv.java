package com.revature.projectone.servlets;

import com.revature.projectone.helpers.Database;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterSrv extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username").toUpperCase();
        System.out.println(username);
        String password = req.getParameter("password");
        System.out.println(password);
        String firstName = req.getParameter("firstName");
        System.out.println(firstName);
        String lastName = req.getParameter("lastName");
        System.out.println(lastName);
        String ssn = req.getParameter("ssn");
        System.out.println(ssn);
        System.out.println(ssn.charAt(3) != '-' && ssn.charAt(6) != '-');
        if(ssn.charAt(3) != '-' && ssn.charAt(6) != '-')
            return;

        Database.createNewAccount(username, password, firstName, lastName, ssn);
        req.getRequestDispatcher("/login").forward(req, resp);
    }
}
