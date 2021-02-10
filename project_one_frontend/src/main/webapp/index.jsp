<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">

    <!-- Bootstrap CSS -->
<%--    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">--%>
    <title>BS Bank - Login</title>
</head>
<body align="center" style="background-color:#bccad6">
<div>
<script>
    function showLogin()
    {
        var loginForm = document.getElementById("loginForm");
        loginForm.style.display = "block";
        var registerForm = document.getElementById("registerForm");
        registerForm.style.display = "none";
    }

    function showRegister()
    {
        var loginForm = document.getElementById("loginForm");
        loginForm.style.display = "none";
        var registerForm = document.getElementById("registerForm");
        registerForm.style.display = "block";
    }
</script>
<h1>
    BS Bank
</h1>
<h2>
    The Game-Over screen for money.
        </h2>
        <br/>
        <button onclick="showLogin()">Login</button>
        <button onclick="showRegister()">Register</button>
        <form id = "loginForm" style="display:none" action="/frontend/login" method="post">
            <label for ="loginForm"><h3>Login:</h3></label>
            <label for="username">Username: </label>
            <input name="username" id="username" type="text" minlength="4"/>
            <br/>
            <label for="newPassword">Password:</label>
            <label for="password"></label>
            <input name="password" id="password" type="password" minlength="8"/>
            <br/>
            <input type="submit" value="Submit"/>
        </form>
        <form id = "registerForm" style="display:none" action="/frontend/register" method="post">
        <label for ="registerForm"><h3>Register:</h3></label>
        <label for="newUsername">Username: </label>
        <input id="newUsername" name="username" type="text" minlength="4"/>
        <br/>
        <label for="newPassword">Password:</label>
        <input id="newPassword" name="password" type="password" minlength="8"/>
        <br/>
        <label for="firstName">First Name:</label>
        <input id="firstName" name="firstName" type="text" minlength="1"/>
        <br/>
        <label for="lastName">Last Name:</label>
        <input id="lastName" name="lastName" type="text" minlength="1"/>
        <br/>
        <label for="ssn">SSN: </label>
        <input id="ssn" name="ssn" type="text" minlength="11" maxlength="11" placeholder="###-##-####">;
        <br/>
        <input type="submit"/>
        </form>
    </div>
<%--    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW" crossorigin="anonymous"></script>--%>
</body>
</html>