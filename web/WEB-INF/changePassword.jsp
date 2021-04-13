<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
      
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">       
        <link href="./stylesheet.css" type="text/css" rel="stylesheet">
        <link rel="preconnect" href="https://fonts.gstatic.com">
        <link href="https://fonts.googleapis.com/css2?family=Merriweather:wght@700&family=Open+Sans&display=swap" rel="stylesheet">
        <title>Reset Password</title>
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
        <h2 class="loginH2">Change Password</h2>
        <p>${user.email}</p>
        
        <c:if test="${display == 'unauthorized'}">
            <h3>You are not authorized to view this page.</h3>
        </c:if>
        <c:if test="${display == 'granted'}">
            <form method="post" action="changePassword">
                <table class="loginTable">
                    <tr>
                        <td>
                            <lable>New Password:</lable>
                        </td>    

                        <td>
                            <input type="password" name="password"> 
                        </td>

                    </tr>
                    <tr>
                        <td>
                            <lable>Confirm New Password: &nbsp;</lable>
                        </td>
                        <td>
                             <input type="password" name="confPassword">
                        </td>
                    </tr>
                  </table><br>

                <button type="submit">Change Password</button>

                <div>

                    <c:if test="${message == 'nullFields'}">
                        <p>Fields cannot be blank.</p>
                    </c:if>
                    <c:if test="${message == 'passMismatch'}">
                        <p>Passwords must match.</p>
                    </c:if> 
                    <c:if test="${message == 'passUnchanged'}">
                        <p>New password must be different from previous password.</p>
                    </c:if>
                    <c:if test="${message == 'passTooShort'}">
                        <p>Password must be at least 8 characters in length.</p>
                    </c:if> 
                </div>
            </form>          
        </c:if>
        <c:if test="${display == 'changeSuccess'}">
            <h4>Your password has been successfully changed. You may now <a href="login">log in</a> with your new password.</h4>
        </c:if>
    </body>
</html>
