<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<script type="text/javascript">
    var previousLeague = "null";
    var newCount = "null";
    
    function setPrev(leagueValue) {
        previousLeague = leagueValue;
    }
</script>
<table style="width: 100%; position: relative">
    <tr>
        <td style="font-size: 20px; color: #CC3333; padding-bottom: 10px;"><b>Manage Leagues</b></td>
    </tr>
    <tr>
        <td>
            <table class="manageAccountsTable teamsTable" style="border-collapse: collapse" >
                <tr>
                    <th>League ID</th>
                    <th>Weekday</th>
                    <th>League Executive</th>
                    <th>&nbsp;</th>
                </tr>
                <c:forEach var="league" items="${allLeagues}">
                    <tr class="thisTeamRow" id="${league.leagueID}row">
                        <td style="padding-top: 10px">${league.leagueID}</td>
                        <td style="padding-top: 10px">${league.weekday}</td>
                        <td style="padding-top: 10px">${league.executiveList[0].user.contactID.firstName} ${league.executiveList[0].user.contactID.lastName}</td>
                        <td style="width: 120px; padding-top: 10px;"><button type="button" onclick="displayLeagueEdit(this.value)" name="displayLeagueEdit" value="${league.leagueID}">Edit</button>&nbsp;
                            <button type="button" onclick="displayLeagueDelete(this.value)" name="deleteLeagueButton" value="${league.leagueID}">Delete</button>&nbsp;
                        </td>
                    </tr>
                    <tr>
                        <td colspan="4" style="border: none; padding: 0px">
                            <c:if test="${editing == league.leagueID}"><div class="editingTeamDiv" id="${league.leagueID}edit" style="display: block"><script>setPrev("${league.leagueID}");</script></c:if>
                            <c:if test="${editing != league.leagueID}"><div class="editingTeamDiv" id="${league.leagueID}edit" style="display: none;"></c:if>
                                <table style="background: white;">
                                    <tr>
                                        <td>
                                            <form method="post" action="management" name="thisForm">
                                                <table class="teamSettings">
                                                    <tr>
                                                        <td colspan="2" style="padding-bottom: 10px;"><b>${league.leagueID} - League Settings</b></td>
                                                    </tr>
                                                    <tr>
                                                        <td style="padding-bottom: 8px;">Weekday </td>
                                                        <td style="padding-bottom: 8px;" style="padding-bottom: 6px;">
                                                            <select name="${league.leagueID}wk">
                                                                <option value="Sunday" ${league.weekday == 'Sunday' ? 'selected="selected"' : ''}>${league.weekday}</option>
                                                                <option value="Monday" ${league.weekday == 'Monday' ? 'selected="selected"' : ''}>${league.weekday}</option>
                                                                <option value="Tuesday" ${league.weekday == 'Tuesday' ? 'selected="selected"' : ''}>${league.weekday}</option>
                                                                <option value="Wednesday" ${league.weekday == 'Wednesday' ? 'selected="selected"' : ''}>${league.weekday}</option>
                                                                <option value="Thursday" ${league.weekday == 'Thursday' ? 'selected="selected"' : ''}>${league.weekday}</option>
                                                                <option value="Friday" ${league.weekday == 'Friday' ? 'selected="selected"' : ''}>${league.weekday}</option>
                                                                <option value="Saturday" ${league.weekday == 'Saturday' ? 'selected="selected"' : ''}>${league.weekday}</option>
                                                            </select>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td style="padding-bottom: 8px;">League Executive: </td>
                                                        <td style="padding-bottom: 8px;">
                                                            <select name="${league.leagueID}ex">
                                                                <c:forEach var="exec" items="${allExecutives}">
                                                                    <option value="${exec.userID}"
                                                                        ${exec.leagueID.leagueID == league.leagueID ? 'selected="selected"' : ''}>${exec.user.contactID.firstName} ${exec.user.contactID.lastName}</option>
                                                                </c:forEach>
                                                            </select>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td>&nbsp;</td>
                                                        <td id="btns"  style="padding-bottom: 6px;">
                                                            <button type="submit" name="saveLeagueSettingsBtn" value="${league.leagueID}">Save</button>&nbsp;
                                                            <button id="discardBtn" type="submit" name="cancel">Discard</button>
                                                        </td>
                                                    </tr>
                                                </table>
                                                <input type="hidden" name="leagueAction" value="saveLeagueSettings">
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
                <div name="deleteLeague" class="deleteTeam" id="deleteThisLeague">
                    <h4></h4>
                    <button type="submit" name="realDeleteLeagueButton" id="realDeleteLeagueButton">Delete</button>&ensp;
                    <button type="button" name="cancelDelete" onclick="closeDelete()">Cancel</button>
                </div>
                <input type="hidden" name="teamAction" value="deleteLeague">
            </form>
        </td>
    </tr>
</table>


<script type="text/javascript">
    var newCount = "null";
    function setPrev(teamValue) {
        alert(teamValue);
    }
    
    
    function displayLeagueEdit(leagueValue) {
        if (previousLeague !== "null" && previousLeague !== leagueValue) {
            document.getElementById(previousLeague + "edit").style.display = "none";
        }
        previousLeague = leagueValue;
        document.getElementById(leagueValue + "edit").style.display = "block";
    }
    
    function displayLeagueDelete(leagueValue) {
        document.getElementById("deleteThisLeague").children[0].innerHTML = "";
        var text = "Are you sure you want to permanently delete the league \"" + leagueValue + "\"?";
        document.getElementById("deleteThisLeague").children[0].innerHTML = text;
        document.getElementById("deleteThisLeague").style.display = "block";
        document.getElementById("realDeleteLeagueButton").value = leagueValue;
    }
    
    function closeDelete() {
        document.getElementById("deleteThisLeague").style.display = "none";
    }
    
    
</script>