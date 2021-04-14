<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<table>
    <tr>
        <td colspan="5" class="contactHeader"><b>Filter </b><span style="font-size: 14px">&ensp;<i>(leave blank to include all)</i></span></td>
    </tr>
    <tr>
        <td class="t">
            <table style="display: inline-block; vertical-align: bottom;">
                <tr>
                    <td class="t5">League:&nbsp;</td>
                    <td class="t5"><button type="button" name="leagueButton" id="leaguesB" onclick="display('leagues')">Select...&emsp;&emsp;</button>
                        <div id="leaguesID">
                            <form id="leaguesForm" method="get" action="contact">
                                <c:forEach var="league" items="${leagues}">
                                    <span class="anOption" onclick="selected('${league.leagueID}')">
                                        <input type="checkbox" name="${league.leagueID}" id="${league.leagueID}ch" onclick="selected(this.value)"
                                            ${fn:contains(updatedLeagues, league.leagueID) ? 'checked="checked"' : ''} value="${league.leagueID}">
                                            ${league.weekday}</span><br>
                                </c:forEach>
                                <input type="hidden" name="filter" value="league">
                            </form>
                        </div>
                    </td>
                </tr>
            </table>
        </td>
        <td class="t">
            <table style="display: inline-block; vertical-align: bottom">
                <tr>
                    <td class="t5">Team:&nbsp;</td>
                    <td class="t5" style="padding-right: 18px;"><button type="button" name="teamButton" id="teamsB" onclick="display('teams')">Select...&emsp;&emsp;</button>
                        <div id="teamsID">
                            <form id="teamsForm" method="get" action="contact">
                                <c:forEach var="team" items="${teams}">
                                    <span class="anOption" onclick="selected('${team.teamID}')">
                                        <input type="checkbox" name="${team.teamID}" id="${team.teamID}ch" onclick="selected(this.value)" 
                                            ${fn:contains(updatedTeams, team.teamID) ? 'checked="checked"' : ''} value="${team.teamID}">
                                        ${fn:replace(team.teamName, "\\", "")}</span><br>
                                </c:forEach>
                                <input type="hidden" name="filter" value="team">
                           </form>
                        </div>
                    </td>
                </tr>
            </table>
        </td>
        <td class="t">
            <table style="display: inline-block; vertical-align: bottom">
                <tr>
                    <td class="t5">Position:&nbsp;</td>
                    <td class="t5"><button type="button" name="positionButton" id="positionsB" onclick="display('positions')">Select...&emsp;&emsp;</button>
                        <div id="positionsID">
                            <form id="positionsForm" method="get" action="contact">
                                <c:forEach var="position" items="${positions}">
                                    <span class="anOption" onclick="selected('${position.positionName}')">
                                        <input type="checkbox" name="${position.positionName}" id="${position.positionName}ch" onclick="selected(this.value)"
                                            ${fn:contains(updatedPositions, position) ? 'checked="checked"' : ''} value="${position.positionName}">
                                        ${position.positionName}</span><br>
                                </c:forEach>
                                <input type="hidden" name="filter" value="position">
                           </form>
                        </div>
                    </td>
                </tr>
            </table>
        </td>
        <td class="t">
            <table style="display: inline-block; vertical-align: bottom">
                <tr>
                    <td class="t5">Role:&nbsp;</td>
                    <td class="t5">
                        <button type="button" name="roleButton" id="rolesB" onclick="display('roles')">Select...&emsp;&emsp;</button>
                        <div id="rolesID" style="width: 160px">
                            <form id="rolesForm" method="get" action="contact">
                                <c:forEach var="role" items="${roles}">
                                    <span class="anOption" onclick="selected('${role.roleID}')">
                                        <input type="checkbox" name="${role.roleID}" id="${role.roleID}ch" onclick="selected(this.value)" 
                                            ${fn:contains(updatedRoles, role) ? 'checked="checked"' : ''} value="${role.roleID}">
                                        ${role.roleName}</span><br>
                                </c:forEach>
                                <input type="hidden" name="filter" value="role">
                           </form>
                        </div>
                    </td>
                </tr>
            </table>
        </td>
        <td class="t">
            <table style="display: inline-block; vertical-align: bottom">
                <tr>
                    <td class="t5">Spares:&nbsp;</td>
                    <td class="t5">
                        <button type="button" name="sparesButton" id="sparesB" onclick="display('spares')">Select...&emsp;&emsp;</button>
                        <div id="sparesID" style="width: 190px">
                            <form id="sparesForm" method="get" action="contact">
                                <span class="anOption" onclick="selected('sparesNotInclude')">
                                    <input type="radio" name="spares" id="sparesNotIncludech" onclick="selected(this.value)" 
                                        ${sparesIncl == null || sparesIncl == 'no' ? 'checked="checked"' : ''} value="no">
                                        Don't include spares
                                </span><br>
                                <span class="anOption" onclick="selected('sparesInclude')">
                                    <input type="radio" name="spares" id="sparesIncludech" onclick="selected(this.value)" 
                                        ${sparesIncl == 'yes' ? 'checked="checked"' : ''} value="yes">
                                        Include spares
                                </span><br>
                                <span class="anOption" onclick="selected('sparesOnly')">
                                    <input type="radio" name="spares" id="sparesOnlych" onclick="selected(this.value)" 
                                        ${sparesIncl == 'only' ? 'checked="checked"' : ''} value="only">
                                        Search spares only 
                                </span>
                                <input type="hidden" name="filter" value="spares">
                           </form>
                        </div>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td colspan="5" style="padding: 0px;">
            <div class="resultsInfo">
                <table>
                    <tr>
                        <td style="width: 65px">&nbsp;</td>
                        <td style="width: 80px;">
                            <c:if test="${fn:length(updatedLeagues) == fn:length(leagues) || updatedLeagues == null || fn:length(updatedLeagues) == 0}">All</c:if>
                            <c:if test="${fn:length(updatedLeagues) < fn:length(leagues)}">
                                <c:forEach var="league" items="${updatedLeagues}">${league.weekday} <br></c:forEach>
                            </c:if>
                        </td>
                        <td style="width: 90px">&nbsp;</td>
                        <td style="width: 180px">
                            <c:choose>
                                <c:when test="${sparesIncl == 'only'}"><i>Spares only</i></c:when>
                                <c:when test="${fn:contains(updatedRoles, 1) && !fn:contains(updatedRoles, 2)}"><i>Executives only</i></c:when>
                                <c:otherwise>
                                        <c:if test="${fn:length(updatedTeams) == fn:length(teams) || updatedTeams == null || fn:length(updatedTeams) == 0}">All</c:if>
                                        <c:if test="${fn:length(updatedTeams) < fn:length(teams)}">
                                            <c:forEach var="team" items="${updatedTeams}">${fn:replace(team.teamName, "\\", "")} <br></c:forEach>
                                        </c:if>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="width: 29px">&nbsp;</td>
                        <td style="width: 100px">
                            <c:choose>
                                <c:when test="${fn:contains(updatedRoles, 1) && !fn:contains(updatedRoles, 2)}"><i>Executives only</i></c:when>
                                <c:otherwise>
                                    <c:if test="${fn:length(updatedPositions) == fn:length(positions) || updatedPositions == null || fn:length(updatedPositions) == 0}">All</c:if>
                                    <c:if test="${fn:length(updatedPositions) < fn:length(positions)}">
                                        <c:forEach var="position" items="${updatedPositions}">${position.positionName} <br></c:forEach>
                                    </c:if>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="width: 62px">&nbsp;</td>
                        <td style="width: 140px">
                            <c:choose>
                                <c:when test="${sparesIncl == 'only'}"><i>Spares only</i></c:when>
                                <c:otherwise>
                                        <c:if test="${fn:length(updatedRoles) == fn:length(roles) || updatedRoles == null || fn:length(updatedRoles) == 0}">All</c:if>
                                        <c:if test="${fn:length(updatedRoles) < fn:length(roles)}">
                                            <c:forEach var="role" items="${updatedRoles}">${role.roleName} <br></c:forEach>
                                        </c:if>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="width: 37px">&nbsp;</td>
                        <td>
                            <c:if test="${sparesIncl == null || sparesIncl == 'no'}">Not included</c:if>
                            <c:if test="${sparesIncl == 'yes'}">Included</c:if>
                            <c:if test="${sparesIncl == 'only'}">Spares only</c:if>
                        </td>
                    </tr>
                </table>
            </div>
        </td>
    </tr>
    <tr>
        <td colspan="5" class="contactHeader" style="padding-right: 3px;">
            <form method="get" action="contact">
                <b>Search&emsp;</b><button type="submit" name="action" value="resetSearch">Reset search</button>
            </form>
        </td>
    </tr>
    <tr>
        <td class="searchBy" colspan="5" style="padding-left: 0px">
            <form method="get" action="contact">
                <table>
                    <tr>
                        <td style="width: 380px"><input type="text" placeholder='e.g., "Smith"' name="searchField" value="${searchField}"></td>
                        <td>Search by:
                            <select name="searchBy">
                                <option value="lastName" ${searchBy == 'lastName' ? 'selected="selected"' : ''}>Last name</option>
                                <option value="firstName" ${searchBy == 'firstName' ? 'selected="selected"' : ''}>First name</option>
                                <option value="email" ${searchBy == 'email' ? 'selected="selected"' : ''}>Email</option>
                            </select>
                        </td>
                        <td>Sort by: 
                            <select name="sortBy">
                                <option value="lastNameAZ" ${sortBy == 'lastNameAZ' ? 'selected="selected"' : ''}>Last name (A-Z)</option>
                                <option value="lastNameZA" ${sortBy == 'lastNameZA' ? 'selected="selected"' : ''}>Last name (Z-A)</option>
                                <option value="firstNameAZ" ${sortBy == 'firstNameAZ' ? 'selected="selected"' : ''}>First name (A-Z)</option>
                                <option value="firstNameZA" ${sortBy == 'firstNameZA' ? 'selected="selected"' : ''}>First name (Z-A)</option>
                            </select>
                        </td>
                        <td class="confirmContact"><button type="submit" name="action" value="search">Search</button></td>
                    </tr>
                </table>
            </form>
        </td>
    </tr>
    <tr>
        <td colspan="5" style="padding: 0px; padding-top: 25px">
            <div class="resultsTable">
                <form method="get" action="contact">
                    <table>
                        <tr style="background: #596b94; color: white; font-weight: bold;">
                            <td style="width: 200px; border-top: 3px solid black">Name</td>
                            <td style="width: 250px; border-top: 3px solid black">Email</td>
                            <td colspan="2" style="border-top: 3px solid black">Select</td>
                        </tr>
                        <c:set var="contCount" value="0"/>
                        <c:forEach var="upContact" items="${updatedContacts}">
                            <c:set var="contCount" value="${contCount + 1}"/>
                            <c:if test="${contCount % 2 != 0}"><tr style="background: #E6E6E6"></c:if>
                            <c:if test="${contCount % 2 == 0}"><tr style="background: #F3F3F3"></c:if>
                                <td>${upContact.firstName} ${upContact.lastName}</td>
                                <td>${upContact.email}</td>
                                <td colspan="2">
                                    <input type="checkbox" name="${upContact.contactID}cont" value="${upContact.contactID}" id="${upContact.contactID}check" onclick="count(this.value)" checked>
                                </td>
                            </tr>
                        </c:forEach>
                        <tr>
                            <td colspan="2">&nbsp;</td>
                            <td style="padding-right: 0px; width: 100px">Selected:&ensp;<b id="selectedCount">${contCount}</b></td>
                            <td class="confirmContact">
                                <c:if test="${contCount != 0}">
                                    <button id="confirmContactBtn" type="submit" name="action" value="confirmContacts">Confirm selection</button>
                                </c:if>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
        </td>
    </tr>
</table>