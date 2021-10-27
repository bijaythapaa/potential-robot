<%--
  Created by IntelliJ IDEA.
  User: bijay.thapa
  Date: 10/26/21
  Time: 8:44 AM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Upload Image</title>
    <meta name="layout" content="main">
</head>

<body>
<h2>Upload an Image</h2>
<g:uploadForm action="upload">
    User Id:
    <g:select name="loginId" from="${userList}" optionKey="loginId" optionValue="loginId"/>
    <p/>
    Photo: <input name="photo" type="file">
    <g:submitButton name="upload" value="Upload"/>
</g:uploadForm>
</body>
</html>