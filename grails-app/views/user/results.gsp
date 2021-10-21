<%--
  Created by IntelliJ IDEA.
  User: bijay.thapa
  Date: 10/1/21
  Time: 8:03 AM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Search Results</title>
    <meta name="layout" content="main">
</head>

<body>
<h2>Results</h2>

<p>
    Searched ${totalUsers} records for items matching <em>${term}</em>.
    Found <strong>${users.size()}</strong> hits.
</p>
<ul>
    <g:each in="${users}" var="user">
        <li>${user.loginId}</li>
    </g:each>
</ul>
<g:link action="search">Search Again</g:link>
</body>
</html>