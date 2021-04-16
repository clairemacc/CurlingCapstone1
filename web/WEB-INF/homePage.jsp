<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
   <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Show Team Standings</title>
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
                    
                    <c:otherwise>
                        <jsp:include page="./subpages/navbarGeneral.jsp"></jsp:include>
                    </c:otherwise>
                </c:choose>    
            </div>
        </div>

        <div class="sidebar"></div>
        <div class="sidebar2"></div>
        <div class="background"></div>
        <div class="pageTitle"><h2>Upcoming Events and Announcements</h2></div>
        <div class="content newsContent">
            <c:forEach var="news" items="${newsPosts}">
                <table class="postTitle">
                    <tr>
                        <td>${news.title}</td>
                        <td class="postDate"><fmt:formatDate type="date" value="${news.postDate}"/></td>
                    </tr>
                </table>
                <div class="postContent">
                    <p>${news.body}</p>
                </div><hr class="newsHr">
            </c:forEach>
        </div>
        
    </body>
</html>
