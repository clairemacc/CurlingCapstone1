<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<table style="width: 100%; position: relative;">
    <tr>
        <td style="font-size: 20px; color: #CC3333; padding-bottom: 10px;"><b>Manage Schedules</b></td>
    </tr>
    <tr>
        <td>
            <table class="manageAccountsTable sched" style="border-bottom: 1px solid black; padding-bottom: 10px; margin-bottom: 20px">
                <tr style="background: white">
                    <td style="color: #002c91; font-size: 15px"><b>Upload new schedule&ensp;—</b></td>
                    <td>Select a file:</td>
                    <td>
                        <form method="post" action="schedule" enctype="multipart/form-data">
                        <input style="border: 1px solid gray; background: white" type="file" name="fileSelect">
                            <button type="submit" value="uploadButton">Upload</button>
                            <input type="hidden" name="formType" value="1">
                        </form>
                    </td>
                </tr>
                <c:choose>
                    <c:when test="${uploadMessage == 'success'}">
                        <tr>
                            <td colspan="3"><b>File uploaded successfully.</b></td>
                        </tr>
                    </c:when>
                    <c:when test="${uploadMessage == 'wrongFileType'}">
                        <tr>
                            <td colspan="3"><b style="color: #CC3333">Error: File must be in <i>.csv</i> or <i>.xlsx</i> format.</b></td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="3">&nbsp;</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </table>
        </td>
    </tr>
    <tr>
        <td>
            <div style="background: white; border-radius: 3px; padding: 15px; position: relative">
                <table class="gamesCl" style="font-size: 14px;">
                    <c:forEach var="league" items="${leagues}">
                        <tr>
                            <th colspan="5" style="text-align: left; font-size: 16px; padding-bottom: 7px; padding-top: 7px; background:#ebf2fa">League ${league.leagueID} — ${league.weekday}&emsp;</th>
                        </tr>
                        <tr>
                            <td style="padding-right: 20px"><b>Game ID</b></td>
                            <td style="min-width: 200px"><b>Home team</b></td>
                            <td style="min-width: 200px"><b>Away team</b></td>
                            <td style="padding-right: 35px"><b>Game date</b></td>
                            <td>&nbsp;</td>
                        </tr>
                        <c:set var="count" value="1"/>
                        <c:forEach var="game" items="${games}">
                            <c:if test="${game.homeTeam.leagueID.leagueID == league.leagueID}">
                                <tr class="thisTeamRow">
                                    <td>${game.gameID}</td>
                                    <td>${game.homeTeam.teamName}</td>
                                    <td>${game.awayTeam.teamName}</td>
                                    <td><fmt:formatDate type="date" value="${game.date}"/></td>
                                    <td>
                                        <button type="button" onclick="displayGameDelete(this.value)" name="deleteGameButton" value="${game.gameID}">Delete</button>&nbsp;
                                    </td>
                                </tr>
                                <c:set var="count" value="${count + 1}"/>
                            </c:if>
                        </c:forEach>
                                <tr>
                                    <td style="border: none">&nbsp;</td>
                                </tr>
                    </c:forEach>
                </table>
            </div>
            <form method="post" action="management">
                <div name="deleteGame" class="deleteTeam" id="deleteThisGame">
                    <h4></h4>
                    <button type="submit" name="realDeleteGameButton" id="realDeleteGameButton">Delete</button>&ensp;
                    <button type="button" name="cancelDelete" onclick="closeDelete()">Cancel</button>
                </div>
                <input type="hidden" name="gameAction" value="deleteGame">
            </form>
        </td>
    </tr>
</table>   
<style>
    .gamesCl {
        border-collapse: collapse;
    }
    
    .gamesCl tr td {
        border-bottom: 1px solid #b8cbe0;
        padding-top: 6px;
        padding-bottom: 6px;
    }
    
    .gamesCl button {
        background: #888;
        border: none;
        color: white;
        padding: 3px;
        font-size: 13.5px;
        font-weight: normal;
    }
    
    .gamesCl button:hover {
        background: #B4B4B4;
        color: black;
    }
</style>

<script>
    function displayGameDelete(gameID) {
        document.getElementById("deleteThisGame").children[0].innerHTML = "";
        var text = "Are you sure you want to permanently delete the game \"" + gameID + "\"?";
        document.getElementById("deleteThisGame").children[0].innerHTML = text;
        document.getElementById("deleteThisGame").style.display = "block";
        document.getElementById("realDeleteGameButton").value = gameID;
    }
    
    function closeDelete() {
        document.getElementById("deleteThisGame").style.display = "none";
    }
</script>