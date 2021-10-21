<%--
  Created by IntelliJ IDEA.
  User: bijay.thapa
  Date: 10/8/21
  Time: 10:27 AM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Advanced Search</title>
</head>

<body>
<formset>
    <legend>Advanced Search for Friends</legend>
    <table>
        <g:form action="advResults">
            <tr>
                <td>Name</td>
                <td><g:textField name="fullName"/></td>
            </tr>
            <tr>
                <td>Email</td>
                <td><g:textField name="email"/></td>
            </tr>
            <tr>
                <td>Homepage</td>
                <td><g:textField name="homepage"/></td>
            </tr>
            <tr>
                <td>Query</td>
                <td>
                    <g:radioGroup name="queryType"  labels="['AND', 'OR', 'NOT']" values="['and', 'or', 'not']"
                                  value="and">
                        ${it.radio} ${it.label}
                    </g:radioGroup>
                </td>
            </tr>
            <tr>
                <td/>
                <td><g:submitButton name="search" value="Search"/></td>
            </tr>
        </g:form>
    </table>
</formset>
</body>
</html>
