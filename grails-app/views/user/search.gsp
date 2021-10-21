<%--
  Created by IntelliJ IDEA.
  User: bijay.thapa
  Date: 10/1/21
  Time: 7:38 AM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Search Hubbub</title>
    <meta name="layout" content="main">
</head>

<body>
<formset>
    <legend>Search for Friends</legend>
    <g:form action="results">
        <label for="loginId">Login Id</label>
        <g:textField name="loginId"/>
        <g:submitButton name="search" value="Search"/>
    </g:form>
</formset>
</body>
</html>