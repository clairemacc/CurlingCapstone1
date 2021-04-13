<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=9" />
        <title>ARC Curling - My Account</title>
        <link href="./stylesheet.css" type="text/css" rel="stylesheet">
        <link rel="preconnect" href="https://fonts.gstatic.com">
        <link href="https://fonts.googleapis.com/css2?family=Merriweather:wght@700&family=Open+Sans&display=swap" rel="stylesheet">
    </head>
    <body>
        <div class="header">
            <jsp:include page="./subpages/arcBanner.jsp"></jsp:include>
            
            <div class="navbar">
                <c:choose>
                    <c:when test="${role.roleID eq 1}">
                        <jsp:include page="./subpages/navbarAdmin.jsp"></jsp:include>
                    </c:when>
                    <c:when test="${role.roleID eq 2}">
                        <jsp:include page="./subpages/navbarPlayer.jsp"></jsp:include>
                    </c:when>
                </c:choose>    
            </div>
        </div>

        <div class="sidebar"></div>
        <div class="sidebar2"></div>
        <div class="background"></div>
        <div class="pageTitle"><h2>My Account</h2></div>
        <table class="content accountContent" border="1px">
            <tr>
                <td class="myAccLeft">
                    <form method="get" action="myAccount">
                        <button type="submit" name="display" value="personalInfo">Personal Information</button><br>
                        <button type="submit" name="display" value="teamInfo">Team Information</button><br>
                        <button type="submit" name="display" value="changeEmail">Change Login Email</button><br>
                        <button type="submit" name="display" value="changePassword">Change Password</button>
                    </form>
                </td>
                <c:choose>
                    <c:when test="${display == 'personalInfo'}">
                        <td class="myAccRight">
                            <form method="get" action="myAccount">
                                <jsp:include page="./subpages/personalInfo.jsp"></jsp:include>
                            </form>
                        </td>
                    </c:when>
                    <c:when test="${display == 'teamInfo'}">
                        <td class="myAccRight">
                            <form method="get" action="myAccount">
                                <table>
                                    <tr>
                                        <td>Team name: </td>
                                        <td>${player.team.teamName}</td>=
                                    </tr>
                                    <tr>
                                        <td>Position: </td>
                                        <td>${player.position.positionName}</td>
                                    </tr>
                                    <tr>
                                        <td>Teammates: </td>
                                        <td>
                                            <c:forEach var="players" items="${players}">
                                                ${players.userID.contactID.firstName} ${players.userID.contactID.firstName} <i>(${players.position.positionName})</i><br>
                                            </c:forEach>
                                        </td>
                                    </tr>
                                </table>
                            </form>
                        </td>
                    </c:when>
                </c:choose>
            </tr>
        </table>
    </body>
</html>