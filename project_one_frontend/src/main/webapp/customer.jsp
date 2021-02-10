<%@ page import="com.revature.projectone.models.Account" %>
<%@ page import="com.revature.projectone.models.Customer" %>
<%@ page import="com.revature.projectone.models.Transfer" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" session="false"%>
<!DOCTYPE html>
<html>
    <head>
        <title>BS Bank - Customer</title>
        <style>
            .slider > div {
                width: 300px;
                height: 200px;
            }
            .manageAccount
            {
                width: 300px;
                height: 75px;
            }
            button {
                margin : 4px 2px;
            }
        </style>
    </head>
    <body align="center" style="background-color:#bccad6">
        <%
            HttpSession session = request.getSession(false);
            Customer customer = null;
            Account[] accounts = null;
            int accountIndex = 0;
            String accountNumber = "";
            String routingNumber = "";
            String balanceAmount = "";
            String accountNumberString = "No Active Accounts.";
            String routingNumberString = "";
            String balanceAmountString = "";
            boolean hasAccount = false;
            boolean hasOutgoingTransfer = false;
            boolean hasIncomingTransfer = false;
            String incomingTransferList = "No pending transfers to this account.";
            String outgoingTransferList = "No pending transfers from this account.";

            Object attribute = null;
            if(session != null && (attribute = session.getAttribute("customer"))!= null)
            {
                customer = (Customer)attribute;
                accounts = customer.getAccounts();
                hasAccount = customer.getAccounts().length > 0;
                if(hasAccount)
                {
                    attribute = session.getAttribute("accountIndex");
                    if(attribute != null)
                    {
                        accountIndex = (int)attribute;

                        if(accountIndex >= accounts.length)
                            accountIndex = 0;
                        if(accountIndex < 0)
                            accountIndex = accounts.length -1;
                    }
                    if(accountIndex >= accounts.length)
                        accountIndex = 0;
                    if(accountIndex < 0)
                        accountIndex = accounts.length -1;
                    accountNumber = "" + accounts[accountIndex].getAccountNumber();
                    routingNumber = "" + accounts[accountIndex].getRoutingNumber();
                    balanceAmount = "" + accounts[accountIndex].getBalance();
                    accountNumberString = "Accounts: " + accounts[accountIndex].getAccountNumber();
                    routingNumberString = "Routing: " + accounts[accountIndex].getRoutingNumber();
                    balanceAmountString = "Balance: " + accounts[accountIndex].getBalance();
                    hasOutgoingTransfer = customer.getAccounts()[accountIndex].getTransfersFrom().length > 0;
                    hasIncomingTransfer = customer.getAccounts()[accountIndex].getTransfersTo().length > 0;

                    if(hasIncomingTransfer)
                    {
                        incomingTransferList = "<h3>Incoming Transfers:</h3>";
                        for(Transfer transfer : customer.getAccounts()[accountIndex].getTransfersTo())
                        {
                            incomingTransferList += "<div style='display:inline-block; border:1px solid black; margin-right: 1px;'>";
                            incomingTransferList += "<input type='checkbox' style='display:inline-block' name='transfer' value='";
                            incomingTransferList += transfer.getTransferId();
                            incomingTransferList += "'> <div style='display:inline-block'>";
                            incomingTransferList += "Transfer: " + transfer.getTransferId() + "</div>";
                            incomingTransferList += "<div>" + transfer.toString() + "</div></div>";
                        }
                    }

                    if(hasOutgoingTransfer)
                    {
                        outgoingTransferList = "<h3>Outgoing Transfers:</h3>";
                        for(Transfer transfer : customer.getAccounts()[accountIndex].getTransfersFrom())
                        {
                            outgoingTransferList += "<div style='display:inline-block; border:1px solid black; margin-right: 1px;'>";
                            outgoingTransferList += "<input type='checkbox' style='display:inline-block' name='transfer' value='";
                            outgoingTransferList += transfer.getTransferId();
                            outgoingTransferList += "'> <div style='display:inline-block'>";
                            outgoingTransferList += "Transfer: " + transfer.getTransferId() + "</div>";
                            outgoingTransferList += "<div>" + transfer.toString() + "</div></div>";
                        }
                    }
                }
            }
        %>
        <script>
            function closeAll()
            {
                closeCreateAccount();
                if(<%=hasAccount%>)
                {
                    closeDeposit();
                    closeNewTransfer();
                    closeWithdraw();
                }

                if(<%=hasIncomingTransfer%>)
                {
                    closeRefuseTransfer();
                    closeAcceptTransfer();
                }
                if(<%=hasOutgoingTransfer%>)
                    closeCancelTransfer();
            }

            function openDeposit() {
                closeAll();
                document.getElementById("depositForm").style.display = "block";
            }

            function openWithdraw() {
                closeAll();
                document.getElementById("withdrawForm").style.display = "block";
            }

            function  openNewTransfer() {
                closeAll();
                document.getElementById("newTransferForm").style.display = "block";
            }

            function openCancelTransfer() {
                closeAll();
                document.getElementById("cancelTransferForm").style.display = "block";
            }

            function openRefuseTransfer() {
                closeAll();
                document.getElementById("refuseTransferForm").style.display = "block";
            }

            function openAcceptTransfer() {
                closeAll();
                document.getElementById("acceptTransferForm").style.display = "block";
            }

            function openCreateAccount(){
                closeAll();
                document.getElementById("createAccountForm").style.display = "block";
            }

            function closeDeposit() {document.getElementById("depositForm").style.display = "none";}

            function closeWithdraw() {document.getElementById("withdrawForm").style.display = "none";}

            function closeNewTransfer() {document.getElementById("newTransferForm").style.display = "none";}

            function closeCancelTransfer() {document.getElementById("cancelTransferForm").style.display = "none";}

            function closeRefuseTransfer() {document.getElementById("refuseTransferForm").style.display = "none";}

            function closeAcceptTransfer() {document.getElementById("acceptTransferForm").style.display = "none";}

            function closeCreateAccount() {document.getElementById("createAccountForm").style.display = "none"; }
        </script>

        <div id="accountView" class="slider"  align="center">
            <form action="/frontend/updateCustomer" method="POST" style="display:inline-block">
                <input type="hidden" name="accountIndex" value="<%out.print((accountIndex-1));%>">
                <input type="submit" value="<">
            </form>
            <div align="center" style = "display:inline-block">
                <h1><%out.print(accountNumberString);%> </h1>
                <h3><%out.print(balanceAmountString);%> </h3>
                <h3><%out.print(routingNumberString);%> </h3>
            </div>
            <form action="/frontend/updateCustomer" method="POST" style="display:inline-block">
                <input type="hidden" name="accountIndex" value="<%out.print((accountIndex+1));%>">
                <input type="submit" style="display:inline-block" value=">">
            </form>
        </div>

        <div align="center" class="manageAccount" style="margin:auto">

            <div id="buttonSection" align="center">
                <div>
                    <button id="depositButton" onclick="openDeposit()" style="display:<%out.print(hasAccount ? "inline-block" : "none");%>">Deposit</button>

                    <button id="withdrawButton" onclick="openWithdraw()" style="display:<%out.print(hasAccount ? "inline-block" : "none");%>">Withdraw</button>
                </div>
                <div>
                    <button id="newTransferButton" onclick="openNewTransfer()" style="display:<%out.print(hasAccount ? "inline-block" : "none");%>">Transfer</button>
                    <button id="cancelTransferButton" onclick="openCancelTransfer()" style="display:<%out.print(hasOutgoingTransfer ? "inline-block" : "none");%>">Cancel Transfer</button>
                </div>
                <div>
                    <button id="acceptTransferButton" onclick="openAcceptTransfer()" style="display:<%out.print(hasIncomingTransfer ? "inline-block" : "none");%>">Accept Transfer</button>
                    <button id="refuseTransferButton" onclick="openRefuseTransfer()" style="display:<%out.print(hasIncomingTransfer ? "inline-block" : "none");%>">Refuse Transfer</button>
                </div>

                <button id="createAccountButton" onclick="openCreateAccount()">Create New Account</button>
                <form><input type="submit" formmethod="post" formaction="/frontend/logout" value="Logout"></form>
            </div>

            <div id="formSection" align="center">
                <form id = "depositForm" method="post" action="/frontend/deposit" style="display:none">
                    <h3>Please input amount to deposit.</h3>
                    <label>Amount: $<input type="number" placeholder="0.00" name="amount" step="0.01" min="0.01"/></label>
                    <input type="hidden" name="accountNumber" value="<%out.print(accountNumber);%>" style="display:none"/>
                    <button type="submit">Initiate Deposit</button>
                    <button type="button" onclick="closeDeposit()">Cancel</button>
                </form>

                <form id = "withdrawForm" method="post" action="/frontend/withdraw" style="display:none">
                    <h3>Please input amount to withdraw.</h3>
                    <label>Amount: $<input type="number" placeholder="0.00" name="amount" step="0.01" min="0.01"/></label>
                    <input type="hidden" name="accountNumber" value="<%out.print(accountNumber);%>" style="display:none"/>
                    <button type="submit">Initiate Withdrawal</button>
                    <button type="button" onclick="closeWithdraw()">Cancel</button>
                </form>

                <form id = "newTransferForm" method="post" action="/frontend/proposeTransfer" style="display:none">
                    <h3>Please input destination and amount to transfer.</h3>
                    <input type="hidden" name="originAccount" value="<%out.print(accountNumber);%>" style="display:none"/>
                    <label>Destination:
                        <input type="number" placeholder="Destination Account Number:" name="destinationAccount"/>
                    </label>
                    <label>Amount: $<input type="number" placeholder="0.00" name="amount" step="0.01" min="0.01"/></label>
                    <button type="submit">Create Transfer</button>
                    <button type="button" onclick="closeNewTransfer()">Cancel</button>
                </form>

                <form id="cancelTransferForm" method="post" action="/frontend/cancelTransfer" style="display:none">
                    <%out.print(outgoingTransferList);%>
                    <br>
                    <button type="submit">Undo Transfer</button>
                    <button type="button" onclick="closeCancelTransfer()">Cancel</button>
                </form>

                <form id="acceptTransferForm" method="post" action="/frontend/approveTransfer" style="display:none">
                    <%out.print(incomingTransferList);%>
                    <br>
                    <button type="submit">Accept Transfer</button>
                    <button type="button" onclick="closeAcceptTransfer()">Cancel</button>
                </form>

                <form id="refuseTransferForm" method="post" action="/frontend/cancelTransfer" style="display:none">
                    <%out.print(incomingTransferList);%>
                    <br>
                    <button type="submit">Refuse Transfer</button>
                    <button type="button" onclick="closeRefuseTransfer()">Cancel</button>
                </form>
                <form id="createAccountForm" method="post" action="/frontend/createNewAccount" style="display:none">
                    <h3>Please input desired initial account balance.</h3>
                    <input type="hidden" name="userId" value="<%out.print(customer.getUserId());%>" style="display:none"/>
                    <label>Amount: $<input type="number" placeholder="0.00" name="amount" step="0.01" min="0.01"/></label>
                    <button type="submit">Submit</button>
                    <button type="button" onclick="closeCreateAccount()">Cancel</button>
                </form>
            </div>
        </div>
    </body>
</html>