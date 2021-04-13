<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<div class="groupInfo">
    <c:if test="${isIndiv == null && isSpare == null}">
        <table> 
            <tr>
                <td style="margin-bottom: 6px; border-bottom: 1px dotted gray"><b>Registration Info</b></td>
            <c:set var="countMem" value="1"/>
            <c:forEach var="thisReg" items="${regs}">
                <tr>
                    <td><b>${countMem}.&emsp;${thisReg.contact.firstName} ${thisReg.contact.lastName}</b></td>
                </tr>
                <tr>
                    <td style="padding-bottom: 5px">
                        &emsp;&emsp;${thisReg.position.positionName}
                        <c:if test="${thisReg.flexibleP == true}"><span style="font-size: 12px"><i>(can switch)</i></span></c:if>
                    </td>
                </tr>
                <c:set var="countMem" value="${countMem + 1}"/>
            </c:forEach>
        </table>
    </c:if>
    <c:if test="${isIndiv == true}">
        <table style="margin: 10px;"> 
            <tr>
                <td style="padding-bottom: 6px; border-bottom: 1px dotted gray"><b>Applicant</b></td>
            <tr>
                <td style="padding-top: 6px; padding-bottom: 3px;"><b>Name:</b><br>${reg.contact.firstName} ${reg.contact.lastName}</td>
            </tr>
            <tr>
                <td><b>Registration type: </b><br>Player (Individual)</td>
            </tr>
            <tr>
                <td>
                    <b>Position:</b><br>${reg.position.positionName}
                    <c:if test="${reg.flexibleP == true}"><span style="font-size: 12px"><i>(can switch)</i></span></c:if>
                </td>
            </tr>
        </table>
    </c:if>
    <c:if test="${isSpare == true}">
        <table style="margin: 10px;"> 
            <tr>
                <td style="padding-bottom: 6px; border-bottom: 1px dotted gray"><b>Applicant</b></td>
            <tr>
                <td style="padding-top: 6px; padding-bottom: 3px;"><b>Name:</b><br>${reg.contact.firstName} ${reg.contact.lastName}</td>
            </tr>
            <tr>
                <td style="padding-bottom: 3px;">
                    <b>Position:</b><br>${reg.position.positionName}
                    <c:if test="${reg.flexibleP == true}"><span style="font-size: 12px"><i>(can switch)</i></span></c:if>
                </td>
            </tr>
            <tr>
                <td><b>Registration type: </b><br>Spare</td>
            </tr>
        </table>
    </c:if>
</div>