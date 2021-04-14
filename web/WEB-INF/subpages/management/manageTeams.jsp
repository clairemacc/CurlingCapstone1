<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<script type="text/javascript">
    var previousTeam = "null";
    var newCount = "null";
    
    function setPrev(teamValue) {
        previousTeam = teamValue;
    }
</script>
<table style="width: 100%; position: relative">
    <tr>
        <td style="font-size: 20px; color: #CC3333; padding-bottom: 10px;"><b>Manage Teams</b></td>
    </tr>
    <tr>
        <td>
            <table class="manageAccountsTable teamsTable" style="border-collapse: collapse" >
                <tr>
                    <th colspan="5" style="border: none; padding: 5px; border-radius: 3px; font-weight: normal; background: #afbbc7">Search:&ensp;
                        <input type="text" size="30"></td>
                </tr>
                <tr>
                    <th>Team ID</th>
                    <th>Team name</th>
                    <th>League</th>
                    <th colspan="2" style="width:300px">Members</th>
                </tr>
                <c:forEach var="team" items="${allTeams}">
                    <tr class="thisTeamRow" id="${team.teamID}row">
                        <td style="padding-top: 10px">${team.teamID}</td>
                        <td style="padding-top: 10px">${fn:replace(team.teamName, "\\", "")}</td>
                        <td style="padding-top: 10px">${team.leagueID.weekday}</td>
                        <td style="padding-top: 10px">
                            &ensp;${fn:length(team.playerList)} &emsp;<button type="button" class="showPasswordBtn" name="showMembersBtn" id="${team.teamID}ID" value="${team.teamID}" onclick="displayMems(this.value)">show</button>
                        </td>
                        <td style="width: 120px; padding-top: 10px;">
                            <button type="button" onclick="displayTeamEdit(this.value)" name="editTeamButton" value="${team.teamID}">Edit</button>&nbsp;
                            <button type="button" onclick="displayTeamDelete('${team.teamName}', this.value)" name="deleteTeamButton" value="${team.teamID}">Delete</button>&nbsp;
                        </td>
                    </tr>
                    <tr>
                        <td colspan="5" style="border: none; padding: 0px">
                            <div id="${team.teamID}mems" style="display: none; padding-left: 408px; font-size: 13px">
                                <table class="memstbl">
                                    <tr>
                                        <td style="border-top: none"><b>Name</b></td>
                                        <td style="border-top: none"><b>Position</b></td>
                                    </tr>
                                <c:forEach var="member" items="${team.playerList}">
                                    <tr>
                                        <td>${member.userID.contactID.firstName} ${member.userID.contactID.lastName}</td>
                                        <td>${member.position.positionName}</td>
                                    </tr>
                                </c:forEach>
                                </table>
                            </div>
                            <c:if test="${editing == team.teamID}"><div class="editingTeamDiv" id="${team.teamID}edit" style="display: block"><script>setPrev("${team.teamID}");</script></c:if>
                            <c:if test="${editing != team.teamID}"><div class="editingTeamDiv" id="${team.teamID}edit" style="display: none;"></c:if>
                                <table style="background: white;">
                                    <tr>
                                        <td>
                                            <form method="post" action="management">
                                                <table class="teamSettings">
                                                    <tr>
                                                        <td colspan="2"><b>Team Settings</b></td>
                                                    </tr>
                                                    <tr>
                                                        <td>Team name: </td>
                                                        <td><input type="text" style="width: 90%" name="${team.teamID}teamName" value="${fn:replace(team.teamName, "\\'", "'")}"></td>
                                                    </tr>
                                                    <tr>
                                                        <td>League: </td>
                                                        <td>
                                                            <select name="${team.teamID}league">
                                                                <c:forEach var="league" items="${allLeagues}">
                                                                    <option value="${league.leagueID}"
                                                                        ${league.leagueID == team.leagueID.leagueID ? 'selected="selected"' : ''}>${league.weekday}</option>
                                                                </c:forEach>
                                                            </select>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td>Members: </td>
                                                        <td>
                                                            <table id="${team.teamID}tbl" style="border-collapse: collapse">
                                                                <c:set var="countMembers" value="1"/>
                                                                <c:forEach var="member" items="${updatedList}">
                                                                    <c:if test="${member.teamID.teamID == team.teamID}">
                                                                        <tr class="teamMemberEdit">
                                                                            <td style="padding-top: 2px; padding-bottom: 2px; min-width: 180px" id="${member.playerID}name">${countMembers}. ${member.userID.contactID.firstName} ${member.userID.contactID.lastName}</td>
                                                                            <td><button type="submit" name="removeBtn" value="${team.teamID};${member.playerID}" class="removeTeammateBtn">Remove</button></td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td colspan="2" style="padding-top: 0px; font-size: 13px; padding-bottom: 10px;">Position: 
                                                                                <select name="${member.playerID}pos">
                                                                                    <c:forEach var="position" items="${allPositions}">
                                                                                        <option value="${position.positionName}"
                                                                                            ${member.position.positionName == position.positionName ? 'selected="selected"' : ''}>
                                                                                        ${position.positionName}</option>
                                                                                    </c:forEach>
                                                                                </select>
                                                                            </td>
                                                                        </tr>
                                                                        <c:set var="countMembers" value="${countMembers + 1}"/>
                                                                    </c:if>
                                                                </c:forEach>
                                                            </table>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td>&nbsp;</td>
                                                        <td id="btns">
                                                            <button type="submit" name="saveTeamSettingsBtn" value="${team.teamID}">Save</button>&nbsp;
                                                            <button id="discardBtn" type="submit" name="cancel">Discard</button>
                                                        </td>
                                                    </tr>
                                                </table>
                                                <input type="hidden" name="teamAction" value="saveTeamSettings">
                                            </form>
                                        </td>
                                        <td>
                                            <form method="get" action="management">
                                                <div style="background: #ebf2fa; border-radius: 1px; padding: 6px; margin-top: 5px;">
                                                    <table class="addTeamMember">
                                                        <tr>
                                                            <td colspan="2" style="padding-bottom: 10px"><b>Add Team Member</b></td>
                                                        </tr>
                                                        <c:if test="${fn:length(team.playerList) < 6}">
                                                            <tr>
                                                                <td style="width:90px">Select user:</td>
                                                                <td>
                                                                    <select name="${team.teamID}newUser">
                                                                        <option hidden value>---select---</option>
                                                                        <c:forEach var="usr" items="${allUsers}">
                                                                            <option value="${usr.userID}">${usr.contactID.firstName} ${usr.contactID.lastName}</option>
                                                                        </c:forEach>
                                                                    </select>
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td style="padding-top: 10px;">Set position:</td>
                                                                <td style="padding-top: 10px;">
                                                                    <c:forEach var="pos" items="${allPositions}">
                                                                        <span class="posCh" onclick="selectPos('${team.teamID}${pos.positionName}')">
                                                                            <input type="radio" id="${team.teamID}${pos.positionName}rd" name="${team.teamID}newPos" value="${pos.positionName}">${pos.positionName}
                                                                        </span><br>
                                                                    </c:forEach>
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td colspan="2" style="padding-top: 20px; text-align: right">
                                                                    <button type="submit" name="addToTeamBtn" value="${team.teamID}">Add to team</button>
                                                                </td>
                                                            </tr>
                                                        </c:if>
                                                    </table>
                                                    <table style="width: 100%">
                                                        <c:forEach var="newMem" items="${newMems}">
                                                            <c:if test="${newMem.teamID.teamID == team.teamID}">
                                                                <tr>
                                                                    <td style="font-size: 14px; text-align: right">
                                                                        ${newMem.userID.contactID.firstName} ${newMem.userID.contactID.lastName} — 
                                                                        <span style="font-size: 12px;">Position: ${newMem.position.positionName}</span>
                                                                    </td>
                                                                </tr>
                                                            </c:if>
                                                        </c:forEach>
                                                    </table>
                                                </div>
                                                <input type="hidden" name="teamAction" value="addToTeam">
                                            </form>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <form method="post" action="management">
                <div name="deleteTeam" class="deleteTeam" id="deleteThisTeam">
                    <h4></h4>
                    <button type="submit" name="realDeleteTeamButton" id="realDeleteTeamButton">Delete</button>&ensp;
                    <button type="button" name="cancelDelete" onclick="closeDelete()">Cancel</button>
                </div>
                <input type="hidden" name="teamAction" value="deleteTeam">
            </form>
        </td>
    </tr>
</table>

<script type="text/javascript">
    var newCount = "null";
    function setPrev(teamValue) {
        alert(teamValue);
    }
    function displayMems(teamValue) {
        if (previousTeam !== "null" && previousTeam !== teamValue) {
            document.getElementById(previousTeam + "mems").style.display = "none";
            document.getElementById(previousTeam + "ID").innerHTML = "show";
        }
        
        if (document.getElementById(teamValue + "mems").style.display === "none") {
            document.getElementById(teamValue + "mems").style.display = "block";
            document.getElementById(teamValue + "ID").innerHTML = "hide";
            previousTeam = teamValue;
        }
        else if (document.getElementById(teamValue + "mems").style.display === "block") {
            document.getElementById(teamValue + "mems").style.display = "none";
            document.getElementById(teamValue + "ID").innerHTML = "show";
        }
    }
    
    function displayTeamEdit(teamValue) {
        if (previousTeam !== "null" && previousTeam !== teamValue) {
            document.getElementById(previousTeam + "edit").style.display = "none";
            document.getElementById(previousTeam + "mems").style.display = "none";
        }
        
        document.getElementById(teamValue + "mems").style.display = "none";
        document.getElementById(teamValue + "ID").disabled = "disabled";
        document.getElementById(teamValue + "ID").innerHTML = "show";
        previousTeam = teamValue;
        document.getElementById(teamValue + "edit").style.display = "block";
    }
    
    function selectPos(posValue) {
        document.getElementById(posValue + "rd").checked = "checked";
    }
    
    function displayTeamDelete(teamName, teamValue) {
        console.log(teamName + " " + teamValue);
        document.getElementById("deleteThisTeam").children[0].innerHTML = '';
        var text = 'Are you sure you want to permanently delete the team \"' + teamName + '\"?';
        document.getElementById("deleteThisTeam").children[0].innerHTML = text;
        document.getElementById("deleteThisTeam").style.display = "block";
        document.getElementById("realDeleteTeamButton").value = teamValue;
    }
    
    function closeDelete() {
        document.getElementById("deleteThisTeam").style.display = "none";
    }
    
    
</script>