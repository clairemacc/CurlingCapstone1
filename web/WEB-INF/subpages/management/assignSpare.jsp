<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<table>
    <tr>
        <td colspan="2">Request ID ${spRequest.requestID}</td>
    </tr>
    <tr>
        <td>
            <div class="registrationSetupDiv srDiv">
                <c:if test="${assigned == null || assigned == false}">
                    <div id="reqDetails">
                        <table>
                            <tr>
                                <th colspan="2" style="text-align: left; font-size: 18px; color: #002c91; padding-bottom: 10px">Spare request details</th>
                            </tr>
                            <tr>
                                <td><b>Date of request: &emsp;&emsp;</b></td>
                                <td><fmt:formatDate type="date" value="${spRequest.requestDate}"/></td>
                            </tr>
                            <tr>
                                <td><b>League: </b></td>
                                <td>${spRequest.teamID.leagueID.leagueID} (${spRequest.teamID.leagueID.weekday})</td>
                            </tr>
                            <tr>
                                <td><b>Game date:</b></td>
                                <td><fmt:formatDate type="date" value="${spRequest.gameID.date}"/></td>
                            </tr>
                            <tr>
                                <td><b>Game: </b></td>
                                <td>${fn:replace(spRequest.gameID.homeTeam.teamName, "\\", "")} <i>vs.</i> ${fn:replace(spRequest.gameID.awayTeam.teamName, "\\", "")}</td>
                            </tr>
                            <tr>
                                <td colspan="2"><hr></td>
                            </tr>
                        </table>
                        <table style="font-size: 16px">
                            <tr>
                                <td><b>Team requesting spare: &emsp;</b></td>
                                <td>${fn:replace(spRequest.teamID.teamName, "\\", "")}</td>
                            </tr>
                            <tr>
                                <td><b>Position requested: </b></td>
                                <td>${spRequest.position.positionName}</td>
                            </tr>
                            <tr>
                                <td style="padding-top: 10px;" colspan="2"><button onclick="assignSpare()" type="button" name="assignSpareButton">Assign spare</button></td>
                            </tr>
                        </table>
                    </div> 
                    <div id="assignSpare" style="display: none">
                        <form method="post" action="management">
                            <table>
                                <tr>
                                    <th colspan="2" style="text-align: left; font-size: 18px; color: #a38800; padding-bottom: 10px">Assign spare</th>
                                </tr>
                                <tr>
                                    <td><b>Game date:</b></td>
                                    <td><fmt:formatDate type="date" value="${spRequest.gameID.date}"/></td>
                                </tr>
                                <tr>
                                    <td><b>Team requesting spare: &emsp;</b></td>
                                    <td>${fn:replace(spRequest.teamID.teamName, "\\", "")}</td>
                                </tr>
                                <tr>
                                    <td><b>Position requested: </b></td>
                                    <td>${spRequest.position.positionName}</td>
                                </tr>
                                <tr>
                                    <td colspan="2"><hr></td>
                                </tr>
                                <tr>
                                    <td colspan="2" style="font-size:">Select a spare: &emsp;
                                        <select name="selectedSpare">
                                            <c:forEach var="spare" items="${spares}">
                                                <option value="${spare.spareID}">${spare.contactID.firstName} ${spare.contactID.lastName}</option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td style="padding-top: 20px;" colspan="2"><button type="submit" id="assignSpareButton" name="assignSpareButton" value="${spRequest.requestID}">Confirm spare assignment</button></td>
                                </tr>
                            </table>
                            <input type="hidden" name="srAction" value="assignSpare">
                        </form>
                    </div>
                </c:if>
                <c:if test="${assigned == true}">
                    <h3 style="margin-top: 0px">Spare assigned successfully.</h3>
                </c:if>
            </div>
        </td>
    </tr>
</table>
                    
<script>
    function updateVal(id) {
        document.getElementById("assignSpareButton").value = id;
    }
    function assignSpare() {
        document.getElementById("reqDetails").style.display = "none";
        document.getElementById("assignSpare").style.display = "block";
    }
</script>