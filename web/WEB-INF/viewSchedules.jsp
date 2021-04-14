<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ARC Curling - Schedules</title>
        <link href="./stylesheet.css" type="text/css" rel="stylesheet">
        <link rel="preconnect" href="https://fonts.gstatic.com">
        <link href="https://fonts.googleapis.com/css2?family=Merriweather:wght@700&family=Open+Sans&display=swap" rel="stylesheet">
    </head>
    <body>
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
        <div class="pageTitle"><h2>Schedules</h1></div>

        <div class="content submitContent">
            <h4>Check a Schedule</h4>
            <br/>
            <form action="viewSchedule" method="POST">
                <c:choose>
                    <c:when test="${fileNames != null}">
                    <select name="selectFile">
                        <c:forEach items="${fileNames}" var="fileName">
                            <option value="${fileName}">${fileName}</option>
                        </c:forEach>
                    </select>
                    <br/><br/>
                    <input type="hidden" name="formType" value="2">
                    <button type="submit" value="viewButton">View Item</button>
                    </c:when>
                    <c:otherwise>
                        <span>Oops! There are no files to select...</span>
                    </c:otherwise>
                </c:choose>
            </form>
            <br/>
            <c:if test="${cells != null}">
                <table class="scheduleTable">
                    <tr>
                    <c:forEach items="${cells}" var="cell">
                        <c:choose>
                            <c:when test="${cell == '/nl'}">
                                <tr/>
                            </c:when>
                            <c:otherwise>
                                <td>
                                    ${cell}
                                </td>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                    
                </table>
            </c:if>
        </div>
    </body>
</html>
