<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<script type="text/javascript">
    var prev = "null";
    var editbtns = document.getElementsByName("editScoreButton");
    var deletebtns = document.getElementsByName("deleteScoreButton");
</script>




<span id="editingScore" style="display: none">${editingScore}</span>

<table style="width: 100%; position: relative">
    <tr>
        <td style="font-size: 20px; color: #CC3333; padding-bottom: 10px;"><b>Manage Scores</b></td>
    </tr>
    <tr>
        <td>
            <form method="post" action="management">
                <table class="manageAccountsTable teamsTable score" style="border-collapse: collapse">
                    <tr>
                        <th style="width: 100px">Game date</th>
                        <th style="width: 200px">Game</th> 
                        <th style="width: 80px">Score</th>
                        <th style="width: 150px">Submitted by</th>
                        <th style="width: 130px;">Submit date</th>
                        <th></th>
                    </tr>
                    <c:forEach var="score" items="${scores}">
                        <tr>
                            <td style="padding-top: 10px"><fmt:formatDate type="date" value="${score.game.date}"/></td>                        
                            <td style="padding-top: 10px;">
                                <c:if test="${score.winner.teamID == score.game.homeTeam.teamID}">
                                    <b>${fn:replace(score.game.homeTeam.teamName, "\\", "")}</b><br><i>vs.</i><br>${fn:replace(score.game.awayTeam.teamName, "\\", "")}
                                </c:if>
                                <c:if test="${score.winner.teamID == score.game.awayTeam.teamID}">
                                    ${fn:replace(score.game.homeTeam.teamName, "\\", "")}<br><i>vs.</i><br><b>${fn:replace(score.game.awayTeam.teamName, "\\", "")}</b>
                                </c:if>
                                <c:if test="${score.homeScore == score.awayScore}">
                                    ${fn:replace(score.game.homeTeam.teamName, "\\", "")}<br><i>vs.</i><br>${fn:replace(score.game.awayTeam.teamName, "\\", "")}
                                </c:if>                                    
                            </td>
                            <td style="padding-top: 10px">
                                <span id="${score.gameID}txt">${score.homeScore} - ${score.awayScore}</span>
                                <span id="${score.gameID}input" style="display: none">H: <input type="text" name="newHomeScore" value="${score.homeScore}" size="2"><br>A: <input type="text" name="newAwayScore" value="${score.awayScore}" size="2"></span>
                            </td>
                            <td style="padding-top: 10px">${score.submitter.contactID.firstName} ${score.submitter.contactID.lastName}</td>
                            <td style="padding-top: 10px"><fmt:formatDate type="date" value="${score.submitDate}"/></td>
                            <td style="width: 120px; padding-top: 10px;">
                                <div id="edit${score.gameID}">
                                    <button type="button" onclick="displayScoreEdit(this.value)" name="editScoreButton" value="${score.gameID}">Edit</button>
                                    <button type="button" onclick="displayScoreDelete(this.value)" name="deleteScoreButton" value="${score.gameID}">Delete</button>
                                </div>
                                <div id="save${score.gameID}" style="display: none">
                                    <button type="submit" name="saveScoreButton" value="${score.gameID}">Save</button>
                                    <button type="button" onclick="discardScore('${score.gameID}')" name="discard">Discard</button>
                                </div>
                            </td>
                        </tr>
                        <c:if test="${invalidInput == score.gameID}">
                            <tr id="errorRow">
                                <td colspan="6" style="text-align: center; color: #CC3333; border-top: 0px"><b>Error: input must be a non-negative numeric value</b></td>
                            </tr>
                        </c:if>
                    </c:forEach>
                </table>
                <input type="hidden" name="scoreAction" value="saveScore">
            </form>
            <form method="post" action="management">
                <div name="deleteScore" class="deleteTeam" id="deleteThisScore">
                    <h4></h4>
                    <button type="submit" name="realDeleteScoreButton" id="realDeleteScoreButton">Delete</button>&ensp;
                    <button type="button" name="cancelDelete" onclick="closeDelete()">Cancel</button>
                </div>
                <input type="hidden" name="scoreAction" value="deleteScore">
            </form>
        </td>
    </tr>
</table>

<script type="text/javascript">
    var editingThis = document.getElementById("editingScore").innerHTML;
    if (editingThis === "")
        document.getElementById("editingScore").innerHTML;
    else {
        displayScoreEdit(editingThis);
    }
    
    function displayScoreEdit(gameID) {
        document.getElementById(gameID + "input").style.display = "block";
        document.getElementById(gameID + "txt").style.display = "none";
        
        for (i = 0; i < editbtns.length; i++) {
            editbtns[i].disabled = "disabled";
            deletebtns[i].disabled = "disabled";
        } 
        
        document.getElementById("edit" + gameID).style.display = "none";
        document.getElementById("save" + gameID).style.display = "block";
    }

    function displayScoreDelete(gameID) {
        document.getElementById("deleteThisScore").children[0].innerHTML = '';
        var text = 'Are you sure you want to permanently delete the score of the game \"' + gameID + '\"?';
        document.getElementById("deleteThisScore").children[0].innerHTML = text;
        document.getElementById("deleteThisScore").style.display = "block";
        document.getElementById("realDeleteScoreButton").value = gameID;
    }
    
    function discardScore(gameID) {
        document.getElementById("edit" + gameID).style.display = "block";
        document.getElementById("save" + gameID).style.display = "none";
        
        document.getElementById(gameID + "txt").style.display = "block";
        document.getElementById(gameID + "input").style.display = "none";
        
        for (i = 0; i < editbtns.length; i++) {
            editbtns[i].disabled = "";
            deletebtns[i].disabled = "";
        } 
        
        document.getElementById("errorRow").style.display = "none";
    }

    function closeDelete() {
        document.getElementById("deleteThisScore").style.display = "none";
    }
</script>

<style>
    .score button {
        
    }
    .score button:hover {
        font-weight: normal;
    }
    
    .score tr td {
        padding: 0px;
        padding-right: 20px;
        padding-top: 5px;
        padding-bottom: 5px;
    }
    .score tr th {
        padding: 0px;
        padding-right: 15px;
    }
</style>