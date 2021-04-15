<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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
            <div style="padding: 3px; background: #B4B4B4; border-radius: 3px">
                <table class="manageLeft" style="width: auto;">
                    <tr style="background: #596b94; color: white">
                        <td style="font-size: 20px; border-right: 1px solid white"><b>League</b></td>
                        <td style="font-size: 20px"><b>&ensp;Schedule</b></td>
                    </tr>
                    <tr>
                        <td style="background: white; border-right: 1px solid #bdbdbd">
                            <c:forEach var="league" items="${leagues}">
                                <form method="get" action="schedule"> 
                                    <button type="submit" name="viewScheduleButton" value="${league.leagueID}">${league.weekday} (${league.leagueID})</button>
                                    <input type="hidden" name="action" value="selectLeague">
                                </form>
                            </c:forEach>
                        </td>
                            <c:if test="${leagueGames != null && fn:length(leagueGames) > 0}">
                                <td style="background: white; padding: 0px; min-width: 600px">
                                    <table style="border-collapse: collapse">
                                        <tr style="background: #efefef">
                                            <th style="width: 200px">Game date</th>
                                            <th style="width: 200px">Home team</th>
                                            <th style="width: 200px">Away team</th>
                                        </tr>
                                        <c:forEach var="game" items="${leagueGames}">
                                            <tr>
                                                <td><fmt:formatDate type="date" value="${game.date}"/></td>
                                                <td>${game.homeTeam.teamName}</td>
                                                <td>${game.awayTeam.teamName}</td>
                                            </tr>
                                        </c:forEach>
                                    </table>
                                </td>
                            </c:if>
                            <c:if test="${leagueGames == null && fn:length(leagueGames) == 0}">
                                <td style="background: white; padding: 0px; min-width: 600px; text-align: center"><br>Select a league from the list to view the game schedule</td>
                            </c:if>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </body>
</html>

<style scoped>
    .manageLeft {
        border-collapse: collapse;
        text-align: left;
    }
    
    .manageLeft tr th {
        width: 200px;
        padding: 10px;
    }
    
    .manageLeft tr td {
        vertical-align: top;
        padding: 10px;
        border-bottom: 1px solid #bdbdbd;
    }
    
    .manageLeft button {
        margin-bottom: 5px;
        background: #efefef;
        color: #ab0000;
    }
    .manageLeft button:hover{
        background: #B4B4B4;
    }
</style>
