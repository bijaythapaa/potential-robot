<%--
  Created by IntelliJ IDEA.
  User: bijay.thapa
  Date: 10/26/21
  Time: 9:17 AM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
</head>

<body>
<div class="profilePic">
    <g:if test="${profile.photo}">
        <img src="${createLink(controller: "image", action: "renderImage", id: profile.user.loginId)}"/>
    </g:if>

    <p>Profile for <strong>${profile.fullName}</strong></p>

    <p>Bio: ${profile.bio}</p>
</div>
</body>
</html>