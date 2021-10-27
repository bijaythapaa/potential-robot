<%--
  Created by IntelliJ IDEA.
  User: bijay.thapa
  Date: 10/27/21
  Time: 3:35 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Sign In</title>
    <meta name="layout" content="main">
</head>

<body>
<h2>Sign In</h2>
<g:form action="signIn">
    <fieldset class="form">
        <div class="fieldcontain required">
            <label for="loginId">Login ID</label>
            <g:textField name="loginId" value="${loginId}"/>
        </div>

        <div class="fieldcontain required">
            <label for="password">Password</label>
            <g:textField name="password"/>
        </div>
    </fieldset>
    <fieldset class="buttons">
        <g:submitButton name="singIn" value="SignIn"/>
    </fieldset>
</g:form>
</body>
</html>