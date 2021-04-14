<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=9" />
        <title>Request a Spare</title>
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
                    <c:otherwise>
                        <jsp:include page="./subpages/navbarPlayer.jsp"></jsp:include>
                    </c:otherwise>
                </c:choose> 
             </div>
        </div>
            <div class="sidebar"></div>
            <div class="sidebar2"></div>
            <div class="background"></div>
            <div class="pageTitle"><h2>Request a Spare</h2></div>
            <div class="content spareRequest">

            <c:choose>
                <c:when test="${review eq 1}">
                    <form method="post" action="spareRequest">
                        <table>
                            <tr> 
                                <td style="padding-bottom: 10px;"><b>Date of request:</b></td>
                                <td style="padding-bottom: 10px;">${today}</td>
                            </tr>
                            <tr> 
                                <td><b>Team:</b></td>
                                <td>
                                    <c:if test="${isAdmin == true}">
                                        <select name="teamSelect">
                                            <c:forEach var="team" items="${teams}">
                                                <option value="${team.teamID}">${fn:replace(team.teamName, "\\", "")}</option>
                                            </c:forEach>
                                        </select>
                                    </c:if>
                                    <c:if test="${isAdmin == null || isAdmin == false}">
                                        <select name="teamSelect">
                                            <c:forEach var="player" items="${user.playerList}">
                                                <option value="${player.teamID.teamID}">${fn:replace(player.teamID.teamName, "\\", "")}</option>
                                            </c:forEach>
                                        </select>
                                    </c:if>
                                </td>
                            </tr>
                            <tr>
                                <td><b>Position: </b></td>
                                <td><select name="positionSelect">
                                        <option hidden value>--select--</option>
                                        <c:forEach var="position" items="${positions}">
                                            <option value="${position.positionID}">${position.positionName}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td><b>Upcoming game: &emsp;</b></td>
                                <td>
                                    <select name="gameSelect">
                                        <option hidden value>--select--</option>
                                        <c:forEach var="games" items="${playerGames}">
                                            <option value="${games.gameID}" ${games.gameID == selectedGame ? 'selected="selected"' : ''}><span class="thisOption">
                                            <fmt:formatDate type="date" value="${games.date}"/>: ${fn:replace(games.homeTeam.teamName, "\\", "")} <i>vs.</i> ${fn:replace(games.awayTeam.teamName, "\\", "")}</span></option>
                                        </c:forEach>
                                    </select>
                                </td>
                            </tr>
                        </table>                

                        <table class="tableButtons">

                            <tr>
                                <td>
                                    <button type="button" name="cancelButton" value="cancel" class="cancelButton" onclick="window.history.back();">Cancel</button>&ensp;
                                </td>
                                <td class="proceed">
                                    <input type="hidden" name="action" value="review">
                                    <button type="submit" name="confirmSpareRequest">Proceed to overview</button> 
                                </td>
                        </table>
                    </form>
                    <c:if test="${nullValue eq true}">
                        <p style="color: red">Please provide information for all fields.</p>
                    </c:if>
                    <c:if test="${differentTeam eq true}">
                        <p style="color: red">This team is not playing in the game you have selected.</p>
                    </c:if>
                                      
                </c:when>
                  <c:when test="${review eq 2}">
                        <form action="spareRequest" method="post">
                        <table>
                            <tr>
                                <td colspan="2" style="font-size: 18px; color: #002c91; padding-bottom: 5px; border-bottom: 1px solid #CC3333"><b>Spare Request Review</b></td>
                            </tr>
                            <tr>
                                <td><b>Game: </b></td>
                                <td>${fn:replace(game.homeTeam.teamName, "\\", "")} <i>vs.</i> ${fn:replace(game.awayTeam.teamName, "\\", "")}</td>
                            </tr>
                            <tr>
                                <td><b>Game date: </b></td>
                                <td>${game.date} </td>
                            </tr>
                            <tr>
                                <td><b>League: </b></td>
                                <td>${team.leagueID.leagueID} (${team.leagueID.weekday})</td>
                            </tr>                            
                            <tr>
                                <td><b>Team: </b></td>
                                <td>${fn:replace(team.teamName, "\\", "")}</td>
                            </tr>
                            <tr>
                                <td><b>Position: </b></td>
                                <td>${position.positionName}</td>
                            </tr>
                            <tr>
                                <td><b>Date of request:&emsp;</b></td>
                                <td>${today} </td>
                            </tr>
                        </table>

                        <table class="tableButtons">
                            <tr>
                                <td>
                                    <button type="button" name="cancelButton" value="cancel" class="cancelButton" onclick="window.history.back();">Cancel</button>&ensp;
                                </td>
                                <td class="proceed">
                                    <input type="hidden" name="action" value="submit">
                                    <button type="submit" name="submitSpareRequest" >Send Request</button> 
                                </td>
                        </table>
                    </form>
                  </c:when>
                <c:otherwise>
                    <h3 style="margin-top: 0px;">Your request was submitted successfully.</h3>
                    <p>The available spares have been notified, and a League Executive will<br> contact you shortly regarding the status of your request.</p>
                </c:otherwise>
            </c:choose>    

        </div>



    </body>

</html>
