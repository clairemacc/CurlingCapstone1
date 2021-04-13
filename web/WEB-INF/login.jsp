<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>        
        <link href="./stylesheet.css" type="text/css" rel="stylesheet">
        <link rel="preconnect" href="https://fonts.gstatic.com">
        <link href="https://fonts.googleapis.com/css2?family=Merriweather:wght@700&family=Open+Sans&display=swap" rel="stylesheet">
    </head>
    <body>
        <div class="header">
            <jsp:include page="./subpages/arcBanner.jsp"></jsp:include>
            
            <div class="navbar">
                <jsp:include page="./subpages/navbarGeneral.jsp"></jsp:include>  
            </div>
        </div>

        <div class="sidebar"></div>
        <div class="sidebar2"></div>
        <div class="background backgroundLogin"></div>
        <div class="content loginContent">
            <h2 class="loginH2">Login</h2>
            <form method="post" action="login">
                <table class="loginTable">
                    <tr>
                        <td class="emailPass">Email:</td>
                        <td><input type="text" name="email" size="30px" value="${email}"></td>
                    </tr>
                    <tr>
                        <td height="5px"></td>
                    </tr>
                    <tr>
                        <td class="emailPass">Password: &nbsp;</td>
                        <td><input type="password" name="password"  size="30px"></td>
                    </tr>
                    <tr>
                        <td height="5px"></td>
                    </tr>
            </table><br>
                <button type="submit" name="action" value="login" class="loginButton">Login</button>
            </form>
            <c:if test="${message == 'invalid'}">
                <p>Invalid login.</p>
            </c:if>
            <c:if test="${message == 'nullField'}">
                <p>Fields cannot be blank.</p>
            </c:if>
            <c:if test="${message == 'logout'}">
                <p>You have successfully logged out.</p>
            </c:if>
            <br><hr><br>
            <div class="forgot">
                <table>
                    <tr>
                        <td><a href="forgot">Forgot password?</a></td>
                    </tr>
                    <tr>
                        <td height="1px"></td>
                    </tr>
                    <tr>
                        <td><a href="register">Register</a></td>
                    </tr>
                </table>
            </div>
        </div>
    </body>
</html>
