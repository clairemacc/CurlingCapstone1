<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
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
        <div class="pageTitle"><h2>Standings</h2></div>
        <div class="content standingsContent">
            <table>
                <tr>
                    <td class="t t1">Team</td>
                    <td class="t">GP</td>
                    <td class="t">W</td>
                    <td class="t">L</td>
                </tr>
                <c:forEach var="standings" items="${standings}">
                    <tr class="teamNames">
                        <td class="t1">${standings.team.teamName}</td>
                        <td>${standings.gamesPlayed}</td>
                        <td>${standings.gamesWon}</td>
                        <td>${standings.gamesLost}</td>
                    </tr>
                </c:forEach>
            </table><br>
            <p>
                <b>GP&nbsp; - &nbsp;</b>Games Played &emsp;&emsp;&emsp; 
                <b>W&nbsp; - &nbsp;</b>Wins &emsp;&emsp;&emsp;
                <b>L&nbsp; - &nbsp;</b>Losses
            </p>
        </div>
        
        
        <script type="text/javascript">
            var teams = document.getElementsByClassName("teamNames");
            
            
            for (i = 0; i < teams.length; i++) {
                if (i % 2 === 0) 
                    teams[i].classList.add("one");
                else 
                    teams[i].classList.add("two");
            }
        </script>
        
        <!--<div class="footer">
            <table align="center" width="100%">
                <tr>
                    <td width="60%">
                        <h1></h1> 
                    </td>
                    <td align="right" width="20%" class="address"><b>Acadia Recreation Complex</b><br>
                        240 90 Ave Southeast<br>
                        Calgary, AB T2J 0A3<br>
                        (403) 255-1252</td>
                </tr>
            </table>
            
        </div>-->

    </body>
</html>
