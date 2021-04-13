<%-- 
    Document   : forgot
    Created on : Feb 21, 2021, 10:40:40 PM
    Author     : 760483
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
      
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">       
        <link href="./stylesheet.css" type="text/css" rel="stylesheet">
        <link rel="preconnect" href="https://fonts.gstatic.com">
        <link href="https://fonts.googleapis.com/css2?family=Merriweather:wght@700&family=Open+Sans&display=swap" rel="stylesheet">
        <title>Forgot Password</title>
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
        <h2 class="loginH2">Forgot Password</h2>
        
        <form method="post" action="forgot">
            <table class="loginTable">
                <tr>
                    <td>
                        <lable>Email address:&ensp;</lable>
                    </td>    
                
                    <td>
                        <input type="email" name="email" value="${email}"/> 
                    </td>
                    
                </tr>
                <tr>
                    <td>
                        <lable>Last name:</lable>
                    </td>
                    <td>
                         <input type="text" name="lastName" value="${lastName}"/>
                    </td>
                   
                </tr>
              </table><br>
            
            <button type="submit">Send</button>

            <div>

                <c:if test="${message == 'nullFields'}">
                    <p>Fields cannot be blank.</p>
                </c:if>
                <c:if test="${message == 'emailSent'}">
                    <p>If <b>${emailEntered}</b> matches our records, a new password will be sent to you. <br>Please check your email.</p>
                </c:if> 
            </div>
        </form>          
    </body>
</html>
