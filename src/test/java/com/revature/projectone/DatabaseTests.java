package com.revature.projectone;

import com.revature.projectone.helpers.UserFactory;
import com.revature.projectone.helpers.Database;
import com.revature.projectone.models.Customer;
import com.revature.projectone.models.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DatabaseTests {
    @Test
    void TestDBConnection()
    {
        assertTrue(Database.testDatabase());
    }

    @Test
    void TestCustomerLogin()
    {
        User potentialCustomer = UserFactory.getUser("seikluceid", "sl-admin" );
        assertNotNull(potentialCustomer);
        assertTrue(UserFactory.isCustomer(potentialCustomer));
    }

    @Test
    public void UsernameIsNotCaseSensitive()
    {
        User potentialCustomer = UserFactory.getUser("seikluceid", "sl-admin" );
        assertNotNull(potentialCustomer);
        assertTrue(UserFactory.isCustomer(potentialCustomer));

        User samePotentialCustomer = UserFactory.getUser("SEIKLUCEID", "sl-admin" );
        assertNotNull(samePotentialCustomer);
        assertTrue(UserFactory.isCustomer(samePotentialCustomer));

        Customer customer = (Customer)potentialCustomer;
        Customer sameCustomer = (Customer)samePotentialCustomer;

        assertTrue(customer.getUserId() == sameCustomer.getUserId());
    }

    @Test
    public void PasswordIsCaseSensitive()
    {
        User validUser = UserFactory.getUser("seikluceid", "sl-admin" );
        assertNotNull(validUser);
        assertTrue(UserFactory.isCustomer(validUser));

        User invalidUser = UserFactory.getUser("seikluceid", "SL-ADMIN" );
        assertNull(invalidUser);
    }

    @Test
    public void UsernameIsUnique()
    {
        Database.createNewAccount("seikluceid", "sl-notadmin", "notSeik", "notLuceid", "000-11-9999");

        User invalidUser = UserFactory.getUser("seikluceid", "sl-notadmin" );
        assertNull(invalidUser);
    }

    @Test
    public void SSNIsUnique()
    {
        User validUser = UserFactory.getUser("seikluceid", "sl-admin" );
        assertNotNull(validUser);
        assertTrue(UserFactory.isCustomer(validUser));

        String existingSSN = "999-99-9999";

        Database.createNewAccount("notseikluceid", "sl-notadmin", "notSeik", "notLuceid", existingSSN);

        User invalidUser = UserFactory.getUser("notseikluceid", "sl-notadmin" );
        assertNull(invalidUser);
    }
}