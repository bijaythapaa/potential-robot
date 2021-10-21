<%--
  Created by IntelliJ IDEA.
  User: bijay.thapa
  Date: 10/8/21
  Time: 11:15 AM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Advanced Search Results</title>
    <meta name="layout" content="main">
</head>

<body>
<h2>Advanced Results</h2>

<p>Searched for items matching <em>${term}</em>.
Found <strong>${profiles.size()}</strong> hits.
<ul>
    <g:each var="profile" in="${profiles}">
        <li>
            <g:link controller="profile" action="show" id="${profile.id}">${profile.fullName}</g:link>
        </li>
    </g:each>
</ul>
</p>
</body>
</html>