<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=9" />
        <title>Submit Game Results</title>
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
        <div class="pageTitle"><h2>Submit Game Results</h2></div>
        <div class="content submitContent">
            <c:choose>    
                <c:when test="${display == 'displayEnterScore'}">
                    <form method="get" action="submitGameResults">
                        <p class="chosenGame">Submitting results for the following game: <br>
                            <span class="icon">&nbsp;►&ensp;</span><b><fmt:formatDate type="date" value="${game.date}"/>: ${fn:replace(game.homeTeam.teamName, "\\", "")} <i>vs.</i> ${fn:replace(game.awayTeam.teamName, "\\", "")}</b><br>
                        <table class="enterScoreTable">
                            <tr>
                                <td colspan="4" style="padding-bottom: 13px"><hr class="hrTd"></td>
                            </tr>
                            <tr>
                                <td><b>Home team: &nbsp;</b></td>
                                <td class="teamName">${fn:replace(game.homeTeam.teamName, "\\", "")}</td>
                                <td align="right" class="outer"><b>Score: &nbsp;</b></td>
                                <td><input type="text" placeholder="e.g., 11" name="homeScore" value="${homeScore}"></td>
                            </tr>
                            <tr>
                                <td><b>Away team: &nbsp;</b></td>
                                <td class="teamName">${fn:replace(game.awayTeam.teamName, "\\", "")}</td>
                                <td align="right" class="outer"><b>Score: &nbsp;</b></td>
                                <td><input type="text" placeholder="e.g., 11" name="awayScore" value="${awayScore}"></td>
                            </tr>
                            <tr>
                                <td colspan="2" class="bottom">
                                    <button type="submit" name="cancelButton" class="cancelButton" value="cancel">Back to game selection</button>
                                </td>
                                <td colspan="2" class="proceed">
                                    <button type="submit" name="scoreConfirm" value="${game.gameID}">Proceed to overview</button>
                                    <input type="hidden" name="action" value="confirmScore">
                                </td>
                            </tr>
                        </table>
                    </form>
                    <c:if test="${message == 'nullScore'}">
                        <p>Error: Score fields cannot be blank. </p>
                    </c:if>
                    <c:if test="${message == 'negativeScore'}">
                        <p>Error: Scores cannot be negative numbers.</p>
                    </c:if>
                    <c:if test="${message == 'scoreNAN'}">
                        <p>Error: Score entries must be numeric values.</p>
                    </c:if>
                    <table class="steps">
                        <tr>
                            <td>Step 1: Select game</td>
                            <td>∙∙∙∙∙∙∙∙∙∙∙</td>
                            <td><b>Step 2: Enter scores</b></td>
                            <td>∙∙∙∙∙∙∙∙∙∙∙</td>
                            <td>Step 3: Review submission</td>
                        </tr>
                    </table>
                </c:when>

                <c:when test="${display == 'displayReview'}">
                    
                    <form method="post" action="submitGameResults">
                        <h4>Review Submission Details</h4><br>
                        <table class="reviewTable">
                            <tr>
                                <td><b>Date: &nbsp;</b></td>
                                <td><fmt:formatDate type="date" value="${game.date}"/></td>
                            </tr>
                            <tr>
                                <td><b>Teams: &nbsp;</b></td>
                                <td>${homeTeamFinal} <i>vs. </i> ${awayTeamFinal}</td>
                            </tr>
                            <tr>
                                <td><b>Score: &nbsp;</b></td>
                                <td>${homeScoreFinal}⁠–⁠${awayScoreFinal}</td>
                            </tr>
                            <tr>
                                <td><b>Winner: &nbsp;</b></td>
                                <td>${winner}</td>
                            </tr>
                            <tr>
                                <td><b>Submitter: &nbsp;</b></td>
                                <td>${user.contactID.firstName} ${user.contactID.lastName}</td>
                            </tr>
                        </table>
                        <table class="tableButtons">
                            <tr>
                                <td>
                                    <button type="submit" name="cancelButton" value="cancel" class="cancelButton">Cancel</button>&ensp;
                                    <button type="submit" name="goBackButton" value="goBack" class="cancelButton">Back</button> 
                                </td>
                                <td class="proceed">
                                    <button type="submit" name="submitScoreButton">Submit Score</button> 
                                    <input type="hidden" name="action" value="submitScoreFinal">
                                </td>
                        </table>
                    </form> 
                    <table class="steps">
                        <tr>
                            <td>Step 1: Select game</td>
                            <td>∙∙∙∙∙∙∙∙∙∙∙</td>
                            <td>Step 2: Enter scores</td>
                            <td>∙∙∙∙∙∙∙∙∙∙∙</td>
                            <td><b>Step 3: Review submission</b></td>
                        </tr>
                    </table>
                </c:when>

                <c:when test="${display == 'displaySubmitComplete'}">
                    <h4>Thank you!</h4>
                    <p>You have successfully submitted the game results.</p><br>
                    <h4>Submission Details</h4><br>
                    <table class="reviewTable submitTable">
                        <tr>
                            <td><b>Game date: &nbsp;</b></td>
                            <td><fmt:formatDate type="date" value="${score.game.date}"/></td>
                        </tr>
                        <tr>
                            <td><b>Teams: &nbsp;</b></td>
                            <td>${fn:replace(score.game.homeTeam.teamName, "\\", "")} <i>vs. </i> ${fn:replace(score.game.homeTeam.teamName, "\\", "")}</td>
                        </tr>
                        <tr>
                            <td><b>Score: &nbsp;</b></td>
                            <td>${score.homeScore}⁠–⁠${score.awayScore}</td>
                        </tr>
                        <tr>
                            <td><b>Winner: &nbsp;</b></td>
                            <td>${score.winner.teamName}</td>
                        </tr>
                        <tr>
                            <td><b>Submitter: &nbsp;</b></td>
                            <td>${score.submitter.contactID.firstName} ${score.submitter.contactID.lastName}</td>
                        </tr>
                    </table>
                    <form method="get" action="submitGameResults">
                        <br><br>
                        <button type="submit" name="submitOtherGames" class="cancelButton" value="submitOtherGames">Back to Submit Results</button>
                        <input type="hidden" value="Submit other game results">
                    </form>
                </c:when>

                <c:otherwise>
                    <c:choose>
                        <c:when test="${noGames == true}">
                            <p>All game results have been submitted.<p>
                        </c:when>
                        <c:otherwise>
                            <form method="get" action="submitGameResults">
                                <p class="selectGameP">Select a game from the list below:</p>
                                <div class="gameSelect">
                                    <select name="gameSelect">
                                        <option hidden value>--select--</option>
                                        <c:forEach var="games" items="${games}">
                                            <option value="${games.gameID}" ${games.gameID == selectedGame ? 'selected="selected"' : ''}><span class="thisOption">
                                                <fmt:formatDate type="date" value="${games.date}"/>: ${fn:replace(games.homeTeam.teamName, "\\", "")} <i>vs.</i> ${fn:replace(games.awayTeam.teamName, "\\", "")}</span></option>
                                        </c:forEach>
                                    </select><br>
                                </div>
                                <div class="confirmButton">
                                    <button type="submit" name="Confirm" value="Confirm">Confirm</button>
                                    <input type="hidden" name="action" value="confirmGame"><br>
                                </div>
                            </form>

                            <c:if test="${message == 'nullGame'}">
                                <p>Error: Please select a game to continue. </p>
                            </c:if>
                            <table class="steps">
                                <tr>
                                    <td><b>Step 1: Select game</b></td>
                                    <td>∙∙∙∙∙∙∙∙∙∙∙</td>
                                    <td>Step 2: Enter scores</td>
                                    <td>∙∙∙∙∙∙∙∙∙∙∙</td>
                                    <td>Step 3: Review & submit</td>
                                </tr>
                            </table>
                        </c:otherwise>
                    </c:choose>
                </c:otherwise>
            </c:choose>
        </div>
    
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
