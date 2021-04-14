<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<table>
    <tr style="border-bottom: 1px dotted black;">
        <td style="vertical-align: top; padding-bottom: 20px">
            <form method="get" action="management">
                <table>
                    <c:set var="count1" value="1"/>
                    <c:choose>
                        <c:when test="${isIndiv == true}">
                            <c:forEach var="indivReg" items="${indivRegs}">
                                <tr>
                                    <td><button class="groupRegButtons" style="width: auto; padding-right: 10px" type="submit" name="viewReg" value="${indivReg.contactID}">
                                                ${count1}. &emsp; ${indivReg.contact.firstName} ${indivReg.contact.lastName}</button>
                                    </td>
                                    <td>
                                        <c:choose><c:when test="${innerDisplay != 'viewIndiv'}">&emsp;—&emsp;${indivReg.regDate}</c:when>
                                        <c:otherwise>
                                        </c:otherwise></c:choose>
                                    </td>
                                    </td>
                                </tr>
                                <c:set var="count1" value="${count1 + 1}"/>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="spareReg" items="${spareRegs}">
                                <tr>
                                    <td><button class="groupRegButtons" style="width: auto; padding-right: 10px" type="submit" name="viewReg" value="${spareReg.contactID}">
                                                ${count1}. &emsp; ${spareReg.contact.firstName} ${spareReg.contact.lastName}</button>&emsp;—&emsp;
                                    </td>
                                    <td>
                                        <c:choose><c:when test="${innerDisplay != 'viewIndiv'}">&emsp;—&emsp;${spareReg.regDate}</c:when>
                                        <c:otherwise>
                                        </c:otherwise></c:choose>
                                    </td>
                                </tr>
                                <c:set var="count1" value="${count1 + 1}"/>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </table>
                <input type="hidden" name="action" value="selectRegID">
            </form>
        </td>
        <c:if test="${innerDisplay == 'viewIndiv'}">
            <td style="vertical-align: top; padding-left: 20px;">
                <form method="get" action="management">
                    <table>
                        <tr>
                            <td>
                                <table id="viewRegTable" style="font-size: 14px;">
                                    <tr>
                                        <td style="vertical-align: top">
                                            <table class="indivTable">
                                                <tr>
                                                    <td colspan="2"><h4 style="font-size: 15px; color: #CC3333;">${reg.contact.firstName} ${reg.contact.lastName}</h4></td>
                                                </tr>
                                                <tr>
                                                    <td><b>Address:</b></td>
                                                    <td>
                                                        ${reg.contact.address}<br>
                                                        ${reg.contact.city} <br>
                                                        ${reg.contact.postal}
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td><b>Email: </b></td>
                                                    <td>${reg.contact.email}</td>
                                                </tr>
                                                <tr>
                                                    <td><b>Phone: </b></td>
                                                    <td>${reg.contact.phone}</td>
                                                </tr>

                                                <tr>
                                                    <td><b>League(s): </b></td>
                                                    <td>${reg.leagues}</td>
                                                </tr>
                                                <tr>
                                                    <td><b>Signup for each: </b></td>
                                                    <td>${reg.signupAll}</td>
                                                </tr>
                                                <tr>
                                                    <td><b>Position:</b></td>
                                                    <td>${reg.position.positionName}</td>
                                                </tr>
                                                <tr>
                                                    <td style="border: none;"><b>Can switch: </b></td>
                                                    <td style="border: none;">${reg.flexibleP}</td>
                                                </tr>
                                            </table>
                                        </td>
                                        <td style="padding-left: 35px; vertical-align: bottom">
                                            <c:if test="${isIndiv == true}"><button type="submit" name="assignTeamButton">Assign team</button></c:if>
                                            <c:if test="${isSpare == true}"><button type="submit" name="continueSpare">Continue</button></c:if>
                                            
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                    <input type="hidden" name="action" value="selectMember">
                </form>
            </td>
        </c:if>
    </tr>
</table>