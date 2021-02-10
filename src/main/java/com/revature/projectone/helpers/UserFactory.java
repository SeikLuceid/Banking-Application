package com.revature.projectone.helpers;

import com.revature.projectone.helpers.Database;
import com.revature.projectone.models.*;

public class UserFactory {
    public static User getUser(String username, String password) {
        System.out.println("GETTING USER");
        User user = Database.getUser(username, password);

        if (user != null && isCustomer(user))
            return buildCustomer(user);

        return user;
    }

    private static Customer buildCustomer(User user) {
        Customer customer = (Customer) user;

        customer.setAccounts(Database.getAccounts(customer));

        for (Account account : customer.getAccounts())
        {
            account.setTransfersFrom(Database.getTransfersFrom(account));
            account.setTransfersTo(Database.getTransfersTo(account));
        }
        System.out.println(customer.toString());
        return customer;
    }

    public static boolean isCustomer(User user) {
        return user.getClass().isAssignableFrom(Customer.class);
    }

    public static Customer updateCustomer(Customer customer) {
        return buildCustomer(customer);
    }
}
