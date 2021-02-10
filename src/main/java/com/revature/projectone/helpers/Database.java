package com.revature.projectone.helpers;

import com.revature.projectone.models.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Locale;

public class Database {

    static final String URL = "jdbc:postgresql://localhost:5432/bankapp";
    static final String USER = "postgres";
    static final String PASS = "db-admin";

    private static Connection conn;

    private static CallableStatement ssnExists;
    private static CallableStatement usernameExists;
    private static CallableStatement registerUser;
    private static CallableStatement checkPassword;
    private static PreparedStatement selectAllUsers;
    private static PreparedStatement selectUserById;
    private static PreparedStatement selectCustomerById;
    private static PreparedStatement selectEmployeeById;
    private static PreparedStatement selectAccountsById;
    private static PreparedStatement selectTransfersByOrigin;
    private static PreparedStatement selectTransfersByDestination;
    private static PreparedStatement selectAccountByAccountNumber;
    private static PreparedStatement updateAccountBalanceByAccountNumber;
    private static PreparedStatement insertTransfer;
    private static PreparedStatement deleteTransferById;
    private static PreparedStatement selectTransferById;
    private static PreparedStatement insertPendingAccount;
    private static PreparedStatement selectPendingAccountsByCustomerId;
    private static PreparedStatement selectPendingAccountsByPendingId;
    private static PreparedStatement selectPendingAccounts;
    private static PreparedStatement deletePendingAccountByPendingId;
    private static PreparedStatement selectTransactionsByDestinationId;
    private static PreparedStatement insertAccount;

    private static ResultSet rs;

    static {
        try{
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(URL, USER, PASS);

            usernameExists = conn.prepareCall("{? = CALL username_exists(?)}");
            ssnExists = conn.prepareCall("{? = CALL ssn_exists(?)}");
            registerUser = conn.prepareCall("CALL register_customer(?, ?, ?, ?, ?)");
            checkPassword = conn.prepareCall("{? = CALL check_password(?, ?)}");
            selectAllUsers = conn.prepareStatement("SELECT * FROM users WHERE user_id = ?;");
            selectUserById = conn.prepareStatement("SELECT * FROM users");
            selectCustomerById = conn.prepareStatement("SELECT * FROM customers WHERE user_id = ?");
            selectEmployeeById = conn.prepareStatement("SELECT * FROM employees WHERE user_id = ?");
            selectAccountsById = conn.prepareStatement("SELECT * FROM accounts WHERE customer_id = ?");
            selectTransfersByOrigin = conn.prepareStatement("SELECT * FROM transfers WHERE origin_account = ?");
            selectTransfersByDestination = conn.prepareStatement("SELECT * FROM transfers WHERE destination_account = ?");
            selectAccountByAccountNumber = conn.prepareStatement("SELECT * FROM accounts WHERE account_number = ?");
            updateAccountBalanceByAccountNumber = conn.prepareStatement("UPDATE accounts SET balance = balance + ? WHERE account_number = ?");
            insertTransfer = conn.prepareStatement("INSERT INTO transfers (origin_account, destination_account, amount) VALUES(?,?,?)");
            deleteTransferById = conn.prepareStatement("DELETE FROM transfers WHERE transfer_id = ?");
            selectTransferById = conn.prepareStatement("SELECT * FROM transfers WHERE transfer_id = ?");
            insertPendingAccount = conn.prepareStatement("INSERT INTO pending_accounts VALUES(?, ?)");
            selectPendingAccountsByCustomerId = conn.prepareStatement("SELECT * FROM pending_accounts WHERE customer_id = ?");
            selectPendingAccountsByPendingId = conn.prepareStatement("SELECT * FROM pending_accounts WHERE pending_id = ?");
            selectPendingAccounts = conn.prepareStatement("SELECT * FROM pending_accounts");
            deletePendingAccountByPendingId = conn.prepareStatement("DELETE FROM pending_accounts WHERE pending_id = ?");
            insertAccount = conn.prepareStatement("INSERT INTO accounts (balance, customer_id) VALUES(?, ?)");
            selectTransactions = conn.prepareStatement("SELECT * FROM transactions");
            selectTransactionsBySourceId = conn.prepareStatement("SELECT * FROM transactions WHERE source = ?");
            selectTransactionsByDestinationId = conn.prepareStatement("SELECT * FROM transactions WHERE destination = ?");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private static PreparedStatement selectTransactions;
    private static PreparedStatement selectTransactionsBySourceId;


    private static Connection getConnection() throws ClassNotFoundException, SQLException
    {
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public static User getUser(String username, String password) {
        User user = null;

        String call = "{? = CALL check_password(?, ?)}";
        try(Connection conn = getConnection(); CallableStatement callableStatement = conn.prepareCall(call))
        {
            callableStatement.registerOutParameter(1, Types.INTEGER);
            callableStatement.setString(2, username.toUpperCase(Locale.ROOT));
            callableStatement.setString(3, password);
            callableStatement.execute();
            int userId = callableStatement.getInt(1);

            String sql = "SELECT * FROM users WHERE user_id = ?;";
            try ( PreparedStatement statement = conn.prepareStatement(sql) )
            {
                statement.setInt(1, userId);
                try( ResultSet resultSet = statement.executeQuery() )
                {
                    if(resultSet.next())
                    {
                        boolean isEmployee = resultSet.getBoolean(4);
                        user = isEmployee ? getEmployee(userId) : getCustomer(userId);
                    }
                }
            }
        }
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public static boolean testDatabase()
    {
        String sql = "SELECT * FROM users";
        try(Connection conn = getConnection(); PreparedStatement statement = conn.prepareStatement(sql))
        {
            try(ResultSet resultSet = statement.executeQuery())
            {
                boolean successful = false;
                while(resultSet.next())
                {
                    successful = true;
                    System.out.println(resultSet.getString(1));
                }
                return successful;
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Customer getCustomer(int userId)
    {
        System.out.println("CREATING CUSTOMER");
        Customer customer = null;
        String sql = "SELECT * FROM customers WHERE user_id = ?";
        try(Connection conn = getConnection(); PreparedStatement statement = conn.prepareStatement(sql))
        {
            statement.setInt(1, userId);
            try(ResultSet resultSet = statement.executeQuery())
            {
                if(resultSet.next())
                {
                    String lastName = resultSet.getString(2);
                    String firstName = resultSet.getString(1);
                    customer = new Customer(userId, firstName, lastName);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return customer;
    }

    public static Employee getEmployee(int userId)
    {
        Employee employee = null;
        String sql = "SELECT * FROM employees WHERE user_id = ?";
        try(Connection conn = getConnection(); PreparedStatement statement = conn.prepareStatement(sql))
        {
            statement.setInt(1, userId);
            try(ResultSet resultSet = statement.executeQuery())
            {
                if(resultSet.next())
                {
                    String lastName = resultSet.getString(2);
                    employee = new Employee(userId, lastName);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return employee;
    }

    public static Account[] getAccounts(Customer customer) {
        ArrayList<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM accounts WHERE customer_id = ?";
        try(Connection conn = getConnection(); PreparedStatement statement = conn.prepareStatement(sql))
        {
            statement.setInt(1, customer.getUserId());
            try(ResultSet resultSet = statement.executeQuery())
            {
                while(resultSet.next())
                {
                    int accountNumber = resultSet.getInt(1);
                    double balance = resultSet.getDouble(2);
                    accounts.add(new Account(accountNumber, balance));
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return accounts.toArray(new Account[0]);
    }

    public static Transfer[] getTransfersFrom(Account account) {
        ArrayList<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT * FROM transfers WHERE origin_account = ?";
        try(Connection conn = getConnection(); PreparedStatement statement = conn.prepareStatement(sql))
        {
            statement.setLong(1, account.getAccountNumber());
            try(ResultSet resultSet = statement.executeQuery())
            {
                while(resultSet.next())
                {
                    int transferId = resultSet.getInt(1);
                    long origin = resultSet.getInt(2);
                    long destination = resultSet.getInt(3);
                    double amount = resultSet.getDouble(4);
                    transfers.add(new Transfer(transferId, origin, destination, amount));
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return transfers.toArray(new Transfer[0]);
    }

    public static Transfer[] getTransfersTo(Account account) {
        ArrayList<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT * FROM transfers WHERE destination_account = ?";
        try(Connection conn = getConnection(); PreparedStatement statement = conn.prepareStatement(sql))
        {
            statement.setLong(1, account.getAccountNumber());
            try(ResultSet resultSet = statement.executeQuery())
            {
                while(resultSet.next())
                {
                    int transferId = resultSet.getInt(1);
                    long origin = resultSet.getInt(2);
                    long destination = resultSet.getInt(3);
                    double amount = resultSet.getDouble(4);
                    transfers.add(new Transfer(transferId, origin, destination, amount));
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return transfers.toArray(new Transfer[0]);
    }


    public static void sendDeposit(int accountNumber, double amount) {
        if(amount <= 0)
            return;
        if(accountExists(accountNumber))
        {
           try{
               updateAccountBalanceByAccountNumber.setDouble(1, amount);
               updateAccountBalanceByAccountNumber.setInt(2, accountNumber);
               updateAccountBalanceByAccountNumber.executeUpdate();
           } catch (SQLException e) {
               e.printStackTrace();
           }
        }
    }

    private static boolean accountExists(int accountNumber) {
        boolean exists = false;
        try{
            selectAccountByAccountNumber.setInt(1, accountNumber);
            rs = selectAccountByAccountNumber.executeQuery();
             exists = rs.next();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists;
    }

    public static void sendWithdrawal(int accountNumber, double amount)
    {
        if(amount <= 0)
            return;
        if(accountExistsAndHasSufficientFunds(accountNumber, amount))
        {
            try{
                updateAccountBalanceByAccountNumber.setDouble(1, -amount);
                updateAccountBalanceByAccountNumber.setInt(2, accountNumber);
                updateAccountBalanceByAccountNumber.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean accountExistsAndHasSufficientFunds(int accountNumber, double amount) {

        boolean hasFunds = false;
        try{
            selectAccountByAccountNumber.setInt(1, accountNumber);
            rs = selectAccountByAccountNumber.executeQuery();
            double balance = 0;
            if(rs.next())
            {
                balance = rs.getDouble(2);
            }
            hasFunds = amount <= balance;
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hasFunds;
    }

    public static void initiateTransfer(int origin, int destination, double amount) {

        if(amount <= 0)
            return;
        if(accountExists(destination) && accountExistsAndHasSufficientFunds(origin, amount))
        {
            try {
                insertTransfer.setInt(1, origin);
                insertTransfer.setInt(2, destination);
                insertTransfer.setDouble(3, amount);
                insertTransfer.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void cancelTransfer(int transferId) {
        try{
            deleteTransferById.setInt(1, transferId);
            deleteTransferById.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void applyTransfer(int transferId)
    {
        try{
            conn.setAutoCommit(false);
            conn.setSavepoint();

            selectTransferById.setInt(1, transferId);
            rs = selectTransferById.executeQuery();
            if(rs.next())
            {
                int origin = rs.getInt(2);
                int destination = rs.getInt(3);
                double amount = rs.getDouble(4);
                if(accountExists(destination) && accountExistsAndHasSufficientFunds(origin, amount))
                {
                    updateAccountBalanceByAccountNumber.setDouble(1, -amount);
                    updateAccountBalanceByAccountNumber.setInt(2, origin);
                    updateAccountBalanceByAccountNumber.executeUpdate();

                    updateAccountBalanceByAccountNumber.setDouble(1, amount);
                    updateAccountBalanceByAccountNumber.setInt(2, destination);
                    updateAccountBalanceByAccountNumber.executeUpdate();

                    deleteTransferById.setInt(1, transferId);
                    deleteTransferById.executeUpdate();
                }
            }

            rs.close();
            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
                conn.setAutoCommit(true);
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
    }

    public static void createNewPendingAccount(int userId, double amount) {
        try{
            insertPendingAccount.setDouble(1, amount);
            insertPendingAccount.setInt(2, userId);
            insertPendingAccount.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static PendingAccount[] getPendingAccounts(String userIdString) {
        ArrayList<PendingAccount> pendingAccounts = new ArrayList<>();
        try {
            if(userIdString.equals("")) {
                rs = selectPendingAccounts.executeQuery();
            }
            else{
                int userId = Integer.parseInt(userIdString);
                selectPendingAccountsByCustomerId.setInt(1, userId);
                rs = selectPendingAccountsByCustomerId.executeQuery();
            }
            while (rs.next()) {
                pendingAccounts.add(new PendingAccount(rs.getDouble(1), rs.getInt(2), rs.getInt(3)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pendingAccounts.toArray(new PendingAccount[0]);
    }

    public static void rejectPendingAccountById(int pendingId) {
        try{
            deletePendingAccountByPendingId.setInt(1, pendingId);
            deletePendingAccountByPendingId.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void approvePendingAccountById(int pendingId) {
        try{
            conn.setAutoCommit(false);
            conn.setSavepoint();
            selectPendingAccountsByPendingId.setInt(1, pendingId);
            rs = selectPendingAccountsByPendingId.executeQuery();
            if(rs.next())
            {
                double balance = rs.getDouble(1);
                int customerId = rs.getInt(2);
                insertAccount.setDouble(1, balance);
                insertAccount.setInt(2, customerId);
                insertAccount.executeUpdate();

                deletePendingAccountByPendingId.setInt(1, pendingId);
                deletePendingAccountByPendingId.executeUpdate();
            }
            rs.close();
            conn.commit();
            conn.setAutoCommit(true);

        } catch (SQLException e) {
            e.printStackTrace();
            try{
                conn.rollback();
                conn.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public static Transaction[] viewTranslationLog(String userIdString) {
        ArrayList<Transaction> transactions = new ArrayList<>();
        try{
            if(userIdString.equals(""))
            {
                rs = selectTransactions.executeQuery();
                while(rs.next())
                {
                    char transactionType = rs.getString(1).charAt(0);
                    int source = rs.getInt(2);
                    int destination = rs.getInt(3);
                    double amount = rs.getDouble(4);
                    int transactionId = rs.getInt(5);
                    Timestamp timestamp = rs.getTimestamp(6);

                    transactions.add(new Transaction(transactionType, source, destination, amount, transactionId, timestamp));
                }
            }
            else{
                int userId = Integer.parseInt(userIdString);
                selectAccountsById.setInt(1, userId);
                rs = selectTransferById.executeQuery();
                while(rs.next())
                {
                    int accountNumber = rs.getInt(1);
                    selectTransactionsBySourceId.setInt(1, accountNumber);
                    ResultSet results = selectTransactionsBySourceId.executeQuery();
                    while(results.next())
                    {
                        char transactionType = results.getString(1).charAt(0);
                        int source = results.getInt(2);
                        int destination = results.getInt(3);
                        double amount = results.getDouble(4);
                        int transactionId = results.getInt(5);
                        Timestamp timestamp = results.getTimestamp(6);

                        boolean valid = true;
                        for(Transaction transaction : transactions)
                        {
                            if(transaction.getTransactionId() == transactionId)
                            {
                                valid = false;
                                break;
                            }
                        }
                        if(valid)
                            transactions.add(new Transaction(transactionType, source, destination, amount, transactionId, timestamp));
                    }
                    selectTransactionsByDestinationId.setInt(1, accountNumber);
                    results = selectTransactionsByDestinationId.executeQuery();
                    while(results.next())
                    {
                        char transactionType = results.getString(1).charAt(0);
                        int source = results.getInt(2);
                        int destination = results.getInt(3);
                        double amount = results.getDouble(4);
                        int transactionId = results.getInt(5);
                        Timestamp timestamp = results.getTimestamp(6);

                        boolean valid = true;
                        for(Transaction transaction : transactions)
                        {
                            if(transaction.getTransactionId() == transactionId)
                            {
                                valid = false;
                                break;
                            }
                        }
                        if(valid)
                            transactions.add(new Transaction(transactionType, source, destination, amount, transactionId, timestamp));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions.toArray(new Transaction[0]);
    }

    public static void createNewAccount(String username, String password, String firstName, String lastName, String ssn) {
        try{
            System.out.println("CREATING");
            usernameExists.registerOutParameter(1, Types.BOOLEAN);
            usernameExists.setString(2, username);
            usernameExists.execute();
            Boolean exists = usernameExists.getBoolean(1);
            System.out.println(exists + " == false");
            if(exists)
                return;

            ssnExists.registerOutParameter(1, Types.BOOLEAN);
            ssnExists.setString(2, ssn);
            ssnExists.execute();
            exists = ssnExists.getBoolean(1);
            System.out.println(exists + " == false");
            if(exists)
                return;

            registerUser.setString(1, username);
            registerUser.setString(2, password);
            registerUser.setString(3, firstName);
            registerUser.setString(4, lastName);
            registerUser.setString(5, ssn);
            registerUser.execute();
            System.out.println("CREATED");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void finalize() throws Throwable {
        conn.close();
        ssnExists.close();
        usernameExists.close();
        registerUser.close();
        checkPassword.close();
        selectAllUsers.close();
        selectUserById.close();
        selectCustomerById.close();
        selectEmployeeById.close();
        selectAccountsById.close();
        selectTransfersByOrigin.close();
        selectTransfersByDestination.close();
        selectAccountByAccountNumber.close();
        updateAccountBalanceByAccountNumber.close();
        insertTransfer.close();
        deleteTransferById.close();
        selectTransferById.close();
        insertPendingAccount.close();
        selectPendingAccountsByCustomerId.close();
        selectPendingAccountsByPendingId.close();
        selectPendingAccounts.close();
        deletePendingAccountByPendingId.close();
        selectTransactionsByDestinationId.close();
        insertAccount.close();
        rs.close();

    }
}
