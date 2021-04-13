<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<table>
    <tr>
        <td>
            <c:if test="${isIndiv == null && isSpare == null}">Group ${thisGroupID} </c:if>
            <c:if test="${isIndiv == true}">Contact ID: ${reg.contactID} <span style="font-size: 14px;"><i>(Player)</i></span></c:if>
            <c:if test="${isSpare == true}">Contact ID: ${reg.contactID} <span style="font-size: 14px;"><i>(Spare)</i></span></c:if>
            <c:if test="${displayFormingTeam == true}">— Forming team in league ${thisLeague.leagueID}</c:if>
            <c:if test="${displayAddingSpare == true}">— Adding new spare in league ${thisLeague.leagueID}</c:if>
            <c:if test="${displayTeamCreated == true}">— Team "${thisTeam.teamName}" in league ${thisLeague.leagueID}</c:if>
        </td>
    </tr>
    <tr>
        <td>
            <div class="registrationSetupDiv">
                <c:if test="${innerDisplay == 'selectLeague'}">
                    <form method="get" action="management">
                        <table>
                            <tr>
                                <td colspan="2" style="background: #dce9f7; padding: 4px">
                                    <table style="font-size: 13px">
                                        <tr>
                                            <td><b>Leagues selected during registration:</b></td>
                                            <td>&emsp;${leagueIDs} </td>
                                        </tr>
                                        <tr>
                                            <td><b>Signup option selected during registration: </b></td>
                                            <td>&emsp;${leagueOpt}</td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <tr>
                                <c:set var="pg" value="group"/>
                                <c:if test="${isIndiv == true}"><c:set var="pg" value="player"/></c:if>
                                <c:if test="${isSpare == true}"><c:set var="pg" value="spare"/></c:if>
                                <td colspan="2" style="padding-bottom: 8px; padding-top: 20px"><b>Select the leagues in which this ${pg} will be registered: </b></td>
                            </tr>
                            <c:forEach var="league" items="${leagues}">
                                <tr>
                                    <td style="padding-bottom: 10px; width: 140px">
                                        &ensp;${league.leagueID}.&ensp;${league.weekday}
                                    </td>
                                    <td style="padding-left: 0px; vertical-align: top">
                                        <input type="checkbox" class="selectLeaguesCheck" name="lea${league.leagueID}" onclick="addLeague()" value="${league.leagueID}">
                                    </td>
                                </tr>
                            </c:forEach>
                            <tr>
                                <td>&nbsp;</td>
                                <td>
                                    <div style="display: none" id="chooseLeaguesButton">
                                        <button type="submit" name="action" value="chooseLeagueButton">Proceed</button>
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </form>
                    <jsp:include page="regInfo.jsp"></jsp:include>
                    <c:choose>
                        <c:when test="${isSpare == null}">
                            <table class="steps">
                                <tr>
                                    <td><b>Step 1: Select league</b></td>
                                    <td>∙∙∙∙∙∙</td>
                                    <td>Step 2: Select team</td>
                                    <td>∙∙∙∙∙∙</td>
                                    <td>Step 3: Assign positions</td>
                                    <td>∙∙∙∙∙∙</td>
                                    <td>Step 4: Review details</td>
                                </tr>
                            </table>
                        </c:when>
                        <c:otherwise>
                            <table class="steps">
                                <tr>
                                    <td><b>Step 1: Select league</b></td>
                                    <td>∙∙∙∙∙∙∙∙∙∙∙</td>
                                    <td>Step 2: Assign position</td>
                                    <td>∙∙∙∙∙∙∙∙∙∙∙</td>
                                    <td>Step 3: Review details</td>
                                </tr>
                            </table>
                        </c:otherwise>
                    </c:choose>
                </c:if>
                <c:if test="${innerDisplay == 'selectTeam'}">
                    <form method="get" action="management">
                        <table>
                            <tr>
                                <td colspan="2" style="background: #dce9f7; padding: 4px">
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2" style="padding-bottom: 8px; padding-top: 20px"><b>Select a team option: </b></td>
                            </tr>
                            <tr>
                                <td style="padding-right: 10px;" id="chooseLeaguesButton">
                                    <button type="submit" name="newTeamButton">Create new team</button>
                                </td>
                                <td id="chooseLeaguesButton">
                                    <button type="submit" name="existingTeamButton">Add to existing team</button>
                                </td>
                            </tr>
                            <input type="hidden" name="action" value="chooseTeamTypeButton">
                        </table>
                    </form>
                    <jsp:include page="regInfo.jsp"></jsp:include>
                    <table class="steps">
                        <tr>
                            <td>Step 1: Select league</td>
                            <td>∙∙∙∙∙∙</td>
                            <td><b>Step 2: Select team</b></td>
                            <td>∙∙∙∙∙∙</td>
                            <td>Step 3: Assign positions</td>
                            <td>∙∙∙∙∙∙</td>
                            <td>Step 4: Review details</td>
                        </tr>
                    </table>
                </c:if>

                <c:if test="${innerDisplay == 'createNewTeam'}">
                    <form method="post" action="management">
                        <table>
                            <tr>
                                <td colspan="2"><b>New Team</b></td>
                            <tr>
                                <td>League: </td>
                                <td>${thisLeague.leagueID}</td>
                            </tr>
                            <tr>
                                <td>Team name: &ensp;</td>
                                <td width="400px;"><input type="text" name="newTeamName" value="${newTeamName}"></td>
                            </tr>
                            <tr>
                                <td colspan="2" style="padding-top: 10px"><button type="submit" name="action" value="createNewTeam">Create team</button></td>
                            </tr>
                            <c:if test="${message == 'nullTeamName'}">
                                <tr><td colspan="2" style="color: #CC3333; padding-top: 10px;"><b>Error: Team name cannot be blank</b></td></tr>
                            </c:if>
                        </table>
                    </form>
                    <jsp:include page="regInfo.jsp"></jsp:include>
                    <table class="steps">
                        <tr>
                            <td>Step 1: Select league</td>
                            <td>∙∙∙∙∙∙</td>
                            <td><b>Step 2: Select team</b></td>
                            <td>∙∙∙∙∙∙</td>
                            <td>Step 3: Assign positions</td>
                            <td>∙∙∙∙∙∙</td>
                            <td>Step 4: Review details</td>
                        </tr>
                    </table>
                </c:if>

                <c:if test="${innerDisplay == 'addToExistingTeam'}">
                    <form method="get" action="management">
                        <table>
                            <tr>
                                <td>
                                    <table style="margin-bottom: 10px;">
                                        <tr>
                                            <td style="padding-bottom: 10px;">
                                                Select a team from league ${thisLeague.leagueID}:
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <select name="selectedTeam" onchange="closeWindow('selectTeam')" >
                                                    <c:forEach var="team" items="${teams}">
                                                        <option value="${team.teamID}" ${team.teamID == thisTeam.teamID ? 'selected="selected"' : ''}>${team.teamName}: (${6 - fn:length(team.playerList)} open spots)</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                            <td>&emsp;<button style="font-size: 14px" class="indivButtons" type="submit" name="displayTeamButton">View team</button></td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>   
                            <c:if test="${displayThisTeam == 'display'}">
                                <tr>
                                    <td style="padding-bottom: 10px;">
                                        <div id="selectTeam" class="selectedTeam">
                                            <table>
                                                <tr>
                                                    <td style="vertical-align: top"><b>Members: &emsp;</b></td>
                                                    <td>
                                                        <table>
                                                            <tr>
                                                            <c:set var="count" value="1"/>
                                                            <c:forEach var="player" items="${thisTeam.playerList}">
                                                                <c:if test="${count % 2 != 0}"></tr><tr></c:if>
                                                                <td style="padding-right: 20px; padding-bottom: 10px;">
                                                                    ${count}. ${player.userID.contactID.firstName} ${player.userID.contactID.lastName}<br>
                                                                    <span style="font-size: 13px">Position: ${player.position.positionName}</span>
                                                                </td>
                                                                <c:set var="count" value="${count + 1}"/>
                                                            </c:forEach>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                </tr>
                                            </table>
                                        </div>
                                    </td>
                                </tr>
                            </c:if>
                            <tr>
                                <td>
                                    <button style="border: none;" type="submit" name="addThisToTeam" value="${thisTeam.teamID}">Add to team</button>
                                    <c:if test="${message == 'notEnoughSpots'}">
                                        <span id="errorMsg" style="color: #CC3333; font-size: 15px">&emsp;<b>Error: Not enough available spots on this team</b></span>
                                    </c:if>
                                </td>
                            </tr>
                        </table>
                        <input type="hidden" name="action" value="chooseTeamTypeButton">
                    </form>
                    <jsp:include page="regInfo.jsp"></jsp:include>
                    <table class="steps">
                        <tr>
                            <td>Step 1: Select league</td>
                            <td>∙∙∙∙∙∙</td>
                            <td><b>Step 2: Select team</b></td>
                            <td>∙∙∙∙∙∙</td>
                            <td>Step 3: Assign positions</td>
                            <td>∙∙∙∙∙∙</td>
                            <td>Step 4: Review details</td>
                        </tr>
                    </table>            
                </c:if>
                
                <c:if test="${innerDisplay == 'spareSetup'}">
                    <form method="get" action="management">
                        <table style="margin-bottom: 20px;">
                            <tr>
                                <td style="font-size: 17px; color: #CC3333;"><b>Assign Position</b></td>
                            </tr>
                            <tr>
                                <td style="padding: 10px; font-size: 16px"><b>${reg.contact.firstName} ${reg.contact.lastName}</b><br>
                                    <span style="font-size: 16px;">Position: 
                                        <select name="${reg.contactID}pos">
                                            <c:forEach var="position" items="${positions}">
                                                <option value="${position.positionID}" ${position.positionID == reg.position.positionID ? 'selected="selected"' : ''}>${position.positionName}</option>
                                            </c:forEach>
                                        </select>
                                    </span>
                                </td>
                            </tr>
                        </table>
                        <button style="padding: 2px; font-size: 16px; " type="submit" name="action" value="teamOverview">Proceed to overview</button>
                    </form>
                    <jsp:include page="regInfo.jsp"></jsp:include>
                    <table class="steps">
                        <tr>
                            <td>Step 1: Select league</td>
                            <td>∙∙∙∙∙∙∙∙∙∙∙</td>
                            <td><b>Step 2: Assign position</b></td>
                            <td>∙∙∙∙∙∙∙∙∙∙∙</td>
                            <td>Step 3: Review details</td>
                        </tr>
                    </table>
                </c:if>

                <c:if test="${innerDisplay == 'teamSetup'}">
                    <form method="get" action="management">
                        <table style="margin-bottom: 20px;">
                            <tr>
                                <td style="font-size: 17px; color: #CC3333;"><b>Team Setup</b></td>
                                <c:set var="countTm" value="1"/>

                                <c:if test="${addToExisting == true}">
                                    <c:forEach var="player" items="${thisTeam.playerList}">
                                        <c:if test="${countTm % 2 != 0}"></tr><tr></c:if>
                                            <td style="padding: 10px; font-size: 15px">${countTm}. ${player.userID.contactID.firstName} ${player.userID.contactID.lastName}<br>
                                            <span style="font-size: 13px;">Position: 
                                                <select name="${player.playerID}pos">
                                                    <c:forEach var="position" items="${positions}">
                                                        <option value="${position.positionID}" ${position.positionID == player.position.positionID ? 'selected="selected"' : ''}>${position.positionName}</option>
                                                    </c:forEach>
                                                </select>
                                            </span></td>
                                        <c:set var="countTm" value="${countTm + 1}"/>
                                    </c:forEach>
                                </c:if>

                                <c:if test="${isIndiv == null}">
                                    <c:forEach var="reg" items="${regs}">
                                        <c:if test="${countTm % 2 != 0}"></tr><tr></c:if>
                                            <td style="padding: 10px; font-size: 15px">${countTm}. ${reg.contact.firstName} ${reg.contact.lastName}<br>
                                            <span style="font-size: 13px;">Position: 
                                                <select name="${reg.contactID}pos">
                                                    <c:forEach var="position" items="${positions}">
                                                        <option value="${position.positionID}" ${position.positionID == reg.position.positionID ? 'selected="selected"' : ''}>${position.positionName}</option>
                                                    </c:forEach>
                                                </select>
                                            </span></td>
                                        <c:set var="countTm" value="${countTm + 1}"/>
                                    </c:forEach>
                                </c:if>
                                <c:if test="${isIndiv == true}">
                                    <c:if test="${countTm % 2 != 0}"></tr><tr></c:if>
                                        <td style="padding: 10px; font-size: 15px">${countTm}. ${reg.contact.firstName} ${reg.contact.lastName}<br>
                                        <span style="font-size: 13px;">Position: 
                                            <select name="${reg.contactID}pos">
                                                <c:forEach var="position" items="${positions}">
                                                    <option value="${position.positionID}" ${position.positionID == reg.position.positionID ? 'selected="selected"' : ''}>${position.positionName}</option>
                                                </c:forEach>
                                            </select>
                                        </span></td>
                                    <c:set var="countTm" value="${countTm + 1}"/>
                                </c:if>                                
                            </tr>
                        </table>
                        <button type="submit" name="action" value="teamOverview">Proceed to overview</button>
                    </form>
                    <jsp:include page="regInfo.jsp"></jsp:include>
                    <table class="steps">
                        <tr>
                            <td>Step 1: Select league</td>
                            <td>∙∙∙∙∙∙</td>
                            <td>Step 2: Select team</td>
                            <td>∙∙∙∙∙∙</td>
                            <td><b>Step 3: Assign positions</b></td>
                            <td>∙∙∙∙∙∙</td>
                            <td>Step 4: Review details</td>
                        </tr>
                    </table>
                </c:if>
                
                <c:if test="${innerDisplay == 'spareOverview'}">
                    <form method="post" action="management">
                        <table>
                            <tr>
                                <td colspan="2" style="font-size: 17px; color: #CC3333; padding-bottom: 10px"><b>Spare Overview</b></td>
                            </tr>
                            <tr>
                                <td>&emsp;<b>Name: </b></td>
                                <td>${reg.contact.firstName} ${reg.contact.lastName}</td>
                            </tr>
                            <tr>
                                <td>&emsp;<b>Position: &ensp;</b></td>
                                <td>${updatedReg.position.positionName}</td>
                            </tr>
                            <tr>
                                <td>&emsp;<b>Can switch: &ensp;</b></td>
                                <td>${reg.flexibleP}</td>
                            </tr>
                            <tr>
                                <td>&emsp;<b>League: </b></td>
                                <td>${thisLeague.leagueID} (${thisLeague.weekday})</td>
                            </tr>
                            <tr>
                                <td colspan="2" style="text-align: left; padding-top: 16px;">&ensp;
                                    <button type="submit" name="action" value="confirmRegistration">Confirm Registration</button>
                                </td>
                            </tr>
                        </table>
                    </form>
                </c:if>

                <c:if test="${innerDisplay == 'teamOverview'}">
                    <form method="post" action="management">
                        <table>
                            <tr>
                                <td colspan="2" style="font-size: 17px; color: #CC3333; padding-bottom: 10px"><b>Team Overview</b></td>
                            </tr>
                            <tr>
                                <td><b>League: </b></td>
                                <td>${thisLeague.leagueID} (${thisLeague.weekday})</td>
                            </tr>
                            <tr>
                                <td style="padding-right: 15px;"><b>Team name: </b></td>
                                <td>${thisTeam.teamName}</td>
                            </tr>
                            <tr>
                                <td style="vertical-align: top"><b>Members: </b></td>
                                <td>
                                    <table>
                                        <tr>
                                        <c:set var="count" value="1"/>
                                        <c:if test="${addToExisting == true}">
                                            <c:forEach var="player" items="${updatedPlayers}">
                                                <c:if test="${count % 2 != 0}"></tr><tr></c:if>
                                                <td style="padding-right: 20px; padding-bottom: 10px;">
                                                    ${count}. ${player.userID.contactID.firstName} ${player.userID.contactID.lastName}<br>
                                                    <span style="font-size: 14px;">Position: ${player.position.positionName}</span>
                                                </td>
                                                <c:set var="count" value="${count + 1}"/>
                                            </c:forEach>
                                        </c:if>
                                        <c:if test="${isIndiv == null}">
                                            <c:forEach var="reg" items="${updatedRegs}">
                                                <c:if test="${count % 2 != 0}"></tr><tr></c:if>
                                                <td style="padding-right: 20px; padding-bottom: 10px;">
                                                    ${count}. ${reg.contact.firstName} ${reg.contact.lastName}<br>
                                                    <span style="font-size: 14px">Position: ${reg.position.positionName}</span>
                                                </td>
                                                <c:set var="count" value="${count + 1}"/>
                                            </c:forEach>
                                        </c:if>
                                        <c:if test="${isIndiv == true}">
                                            <c:if test="${count % 2 != 0}"></tr><tr></c:if>
                                            <td style="padding-right: 20px; padding-bottom: 10px;">
                                                ${count}. ${updatedReg.contact.firstName} ${updatedReg.contact.lastName}<br>
                                                <span style="font-size: 14px">Position: ${updatedReg.position.positionName}</span>
                                            </td>
                                            <c:set var="count" value="${count + 1}"/>
                                        </c:if>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2" style="text-align: right; padding-top: 6px; border-top: 1px solid #006bc2">
                                    <button type="submit" name="action" value="confirmRegistration">Confirm Registration</button>
                                </td>
                            </tr>
                        </table>
                    </form>
                    <jsp:include page="regInfo.jsp"></jsp:include>
                    <table class="steps">
                        <tr>
                            <td>Step 1: Select league</td>
                            <td>∙∙∙∙∙∙</td>
                            <td>Step 2: Select team</td>
                            <td>∙∙∙∙∙∙</td>
                            <td>Step 3: Assign positions</td>
                            <td>∙∙∙∙∙∙</td>
                            <td><b>Step 4: Review details</b></td>
                        </tr>
                    </table>
                </c:if>

                <c:if test="${innerDisplay == 'teamCreatedSuccess'}">
                    <table>
                        <tr>
                            <td>
                                <c:if test="${isSpare != true}">
                                    <h3 style="color: #006bc2">Team created successfully.</h3>
                                </c:if>
                                <c:if test="${isSpare == true}">
                                    <h3 style="color: #006bc2">Spare added successfully.</h3>
                                </c:if>
                            </td>
                        </tr>
                    <c:if test="${addAnotherTeam == true}">
                        <tr>
                            <td style="padding-top: 30px;">
                                <form method="get" action="management">
                                    <button type="submit" name="action" value="chooseLeagueButton">Add to next league</button>
                                </form>
                            </td>
                        </tr>
                    </c:if>
                    </table>
                </c:if>
            </div>
        </td>
    </tr>
</table>