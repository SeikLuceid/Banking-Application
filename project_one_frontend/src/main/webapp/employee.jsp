<%@ page import="com.revature.projectone.models.Transaction" %>
<%@ page import="com.revature.projectone.models.PendingAccount" %>
<%@ page import="com.revature.projectone.models.Customer" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" session="false"%>
<html>
    <head>
        <title>BS Bank - Employee</title>
    </head>
    <body align="center" style="background-color:#bccad6">
        <%
            HttpSession session = request.getSession(false);
            Transaction[] transactions = new Transaction[0];
            PendingAccount[] pendingAccounts = new PendingAccount[0];
            Customer[] customers = new Customer[0];

            Object attribute = null;
            if(session != null) {
                if ((attribute = session.getAttribute("transactions")) != null)
                    transactions = (Transaction[]) attribute;
                if ((attribute = session.getAttribute("pendingAccounts")) != null)
                    pendingAccounts = (PendingAccount[]) attribute;
                if ((attribute = session.getAttribute("customers")) != null)
                    customers = (Customer[]) attribute;
            }
        %>
        <script>
            function loadPendingForm() {
                let pendingForm = document.getElementById("pending");
                let transactionForm = document.getElementById("transactions");
                let customerForm = document.getElementById("customers");
                pendingForm.style.display = "block";
                transactionForm.style.display = "none";
                customerForm.style.display = "none";
            }

            function loadTransactionForm() {
                let pendingForm = document.getElementById("pending");
                let transactionForm = document.getElementById("transactions");
                let customerForm = document.getElementById("customers");
                pendingForm.style.display = "none";
                transactionForm.style.display = "block";
                customerForm.style.display = "none";
            }

            function loadCustomerForm() {
                let pendingForm = document.getElementById("pending");
                let transactionForm = document.getElementById("transactions");
                let customerForm = document.getElementById("customers");
                pendingForm.style.display = "none";
                transactionForm.style.display = "none";
                customerForm.style.display = "block";
            }

            function cancelFormFields() {
                let pendingForm = document.getElementById("pending");
                let transactionForm = document.getElementById("transactions");
                let customerForm = document.getElementById("customers");
                pendingForm.style.display = "none";
                transactionForm.style.display = "none";
                customerForm.style.display = "none";
            }
        </script>
        <div style="display:block">
            <button id="pendingButton" style="display:inline-block" onclick="loadPendingForm()">Pending Accounts</button>
            <button id="transactionButton" style="display:inline-block" onclick="loadTransactionForm()">Trans. Log</button>
            <button id="customerButton" style="display:inline-block" onclick="loadCustomerForm()">Find Customer</button>
            <form style="display:inline-block"><input type="submit" formmethod="post" formaction="/frontend/logout" value="Logout"></form>
        </div>
        <div style="display:block">
            <form id="pending" style="display:none" action="/frontend/viewPendingAccounts" method="post">
                <h3>What is the customer ID of the pending bank account?</h3>
                <label for="pendingCustomerID">Customer ID:</label>
                <input id="pendingCustomerID" type="text" name="userId" placeholder="Leave blank for all">
                <button type="submit">Submit</button>
                <button type="button" onclick="cancelFormFields()">Cancel</button>
            </form>
            <form id="transactions" style="display:none" action="/frontend/viewTransactions" method="post">
                <h3>What customer's transactions would you like to view?</h3>
                <label for="pendingCustomerID">Customer ID:</label>
                <input id="transactionId" type="text" name="userId" placeholder="Leave blank for all">
                <button type="submit">Submit</button>
                <button type="button" onclick="cancelFormFields()">Cancel</button>
            </form>
            <form id="customers" style="display:none" action="/frontend/viewCustomerInformation" method="post">
                <h3>What is the customer ID of the customer you are looking for?</h3>
                <label for="customerInfo">Customer ID:</label>
                <input id="customerInfo" type="text" required name="userId" placeholder="Required">
                <button type="submit">Submit</button>
                <button type="button" onclick="cancelFormFields()">Cancel</button>
            </form>
        </div>
        <div id="display" style="display:block">
            <div id="pendingCustomerDisplay" style="display:block">
                <%
                    if(pendingAccounts.length > 0)
                    {
                        out.print("<form method=\"post\" action=\"something\">");
                        for(PendingAccount account : pendingAccounts)
                        {
                            out.print(
                                    "<label><input type=\"checkbox\" name=\"pendingAccounts\" value=\""
                                            + account.getPendingId() + "\"/>" + account.toString() +
                                            "</label><br>"
                            );
                        }
                        out.print("<input type=\"submit\" value=\"Approve\" formaction=\"/frontend/approvePendingAccount\">" +
                                "<input type=\"submit\" value=\"Reject\" formaction=\"/frontend/rejectPendingAccount\"></form>");
                    }
                %>
            </div>
            <div id="transactionLogDisplay" style="display:block">
                <%
                    if(transactions.length > 0)
                        for(Transaction transaction : transactions) { out.print(transaction.toString()); }
                %>
            </div>
            <div id="customerInfoDisplay" style="display:block">
                <%
                    if(customers.length > 0)
                        for(Customer customer : customers)
                            out.print(customer.toString());
                %>
            </div>
        </div>
    </body>
</html>
