<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<span class="newRegsNum" style="padding-bottom: 10px; position: absolute">Pending Spare Requests: &ensp;&nbsp;<b>${num}</b></span>
<c:if test="${num > 0}">
    <form method="get" action="management">
        <table style="margin-top: 40px">
            <tr>
                <td style="border-bottom: 1px solid gray"><b>&emsp;Team</b></td>
                <td style="border-bottom: 1px solid gray"><b>Position&emsp;&emsp;</b></td>
                <td style="border-bottom: 1px solid gray"><b>Request date</b></td>
                <td style="border-bottom: 1px solid gray">&nbsp;</td>
            <c:set var="count" value="1"/>
            <c:forEach var="req" items="${requests}">
                <tr>
                    <td class="pendingSR">&emsp;${count}. ${req.teamID.teamName}</td>
                    <td class="pendingSR">${req.position.positionName}</td>
                    <td class="pendingSR"><fmt:formatDate type="date" value="${req.requestDate}"/></td>
                    <td class="pendingSR" style="padding-left: 8px;">
                        <button class="viewButton" type="submit" name="viewRequest" value="${req.requestID}">View details</button>
                    </td>
                </tr>
                <c:set var="count" value="${count + 1}"/>
            </c:forEach>
            
        </table>
        <input type="hidden" name="reqAction" value="viewRequest"> 
    </form>
</c:if>
