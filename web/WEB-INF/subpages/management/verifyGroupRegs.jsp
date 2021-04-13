<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<table>
    <tr style="border-bottom: 1px dotted black;">
        <td style="vertical-align: top; padding-bottom: 20px">
            <form method="get" action="management">
                <table>
                    <c:set var="count1" value="1"/>
                    <c:forEach var="groupID" items="${groupRegIDs}">
                        <tr><td><button class="groupRegButtons" type="submit" name="viewGroup" value="${groupID}">
                                        ${count1}. &emsp; Group ${groupID}</button></td></tr>
                        <c:set var="count1" value="${count1 + 1}"/>
                    </c:forEach>
                </table>
                <input type="hidden" name="action" value="selectGroupID">
            </form>
        </td>
        <c:if test="${innerDisplay == 'viewGroup'}">
            <td style="vertical-align: top; padding-left: 20px;">
                <form method="get" action="management">
                    <table>
                        <tr>
                            <td>
                                <table id="viewGroupTable" style="font-size: 14px;">
                                    <tr>
                                        <td style="vertical-align: top">
                                            <table>
                                                <tr>
                                                    <td><h4 style="font-size: 16px; color: #CC3333;">Group ${thisGroupID}</h4></td>
                                                </tr>
                                                <tr>
                                                    <td><b>League(s):</b><br>
                                                    ${regInfo.leagues}</td>
                                                </tr>
                                                <tr>
                                                    <td><b>Signup for each:&emsp;</b><br>
                                                    ${regInfo.signupAll}</td>
                                                </tr>
                                                <tr>
                                                    <td><b>Team name:</b><br>
                                                    ${regInfo.teamName}</td>
                                                </tr>
                                                <tr>
                                                    <td style="line-height: 5px">&nbsp;</td>
                                                </tr>
                                            </table>
                                        </td>
                                        <td style="padding-left: 20px; vertical-align: top">
                                            <table>
                                                <tr>
                                                    <td style="line-height: 23px;">&nbsp;</td>
                                                </tr>
                                                <tr>
                                                    <td colspan="2"><b>Members: </b></td>
                                                    <td style="padding-left: 15px; width: 100px">
                                                        <input onclick="clicked(this.value)" type="checkbox" name="selectAll" id="selectAll">
                                                        <span style="font-size: 11px">(select all)</span>
                                                    </td>
                                                </tr>
                                                <c:set var="count2" value="1"/>
                                                <c:forEach var="reg" items="${regs}">
                                                    <tr id="${reg.contactID}" class="listOfMembers">
                                                        <td style="text-align: left; width: 200px">
                                                            ${count2}. &emsp; ${reg.contact.firstName} ${reg.contact.lastName}&emsp;
                                                        </td>
                                                        <td>
                                                            <button class="indivButtons" type="submit" name="viewMember" value="${reg.contactID}">View</button>
                                                        </td>
                                                        <td style="padding-left: 15px;">
                                                            <input onclick="verifyClick()" type="checkbox" class="checks" name="${reg.contactID}" id="${reg.contactID}">
                                                        </td>
                                                    </tr>
                                                    <c:set var="count2" value="${count2 + 1}"/>
                                                </c:forEach>
                                                <tr>
                                                    <td class="selectTeam" style="vertical-align: bottom; font-size: 13px; text-align: right" colspan="3">
                                                        Selected: <span id="selectedNum"><i>0</i></span><br>
                                                        <button type="submit" id="chooseTeamButton" name="assignTeamButton">Assign team</button>
                                                    </td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                    <input type="hidden" name="action" value="selectMember">
                </form>
            </td>
            <c:if test="${innerDisplayMember == 'viewMember'}"> 
                </tr>
                <tr>
                    <td style="padding-top: 20px;" colspan="2">
                        <div class="memberTable" id="memberTable" style="display: block; font-size: 14px">
                            <table>
                                <tr>
                                    <td class="firstTd"><b>Name: </b></td>
                                    <td>${groupMember.contact.firstName} ${groupMember.contact.lastName}</td>
                                </tr>
                                <tr>
                                    <td style="vertical-align: top;"><b>Address: </b></td>
                                    <td>${groupMember.contact.address}<br>
                                        ${groupMember.contact.city}<br>
                                        ${groupMember.contact.postal}</td>
                                </tr>
                                <tr>
                                    <td><b>Email: </b></td>
                                    <td>${groupMember.contact.email}</td>
                                </tr>
                                <tr>
                                    <td><b>Phone: </b></td>
                                    <td>${groupMember.contact.phone}</td>
                                </tr>
                                <tr>
                                    <td><b>Position: </b></td>
                                    <td>${groupMember.position.positionName}</td>
                                </tr>
                                <tr>
                                    <td><b>Can switch: </b></td>
                                    <td>${groupMember.flexibleP}</td>
                                </tr>
                                <tr>
                                    <td colspan="2" style="padding-top: 10px;">
                                        <button type="button" name="closeButton" onclick="closeWindow('memberTable')">Close</button>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </td>
            </c:if>
        </c:if>
    </tr>
</table>