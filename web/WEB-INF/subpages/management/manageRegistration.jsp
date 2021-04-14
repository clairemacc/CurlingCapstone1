<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<span class="newRegsNum">Pending Registrations: &ensp;&nbsp;<b>${num}</b></span>
<c:if test="${num > 0}">
    <form method="get" action="management">
        <table>
            <tr>
                <td style="padding-top: 10px">&emsp;Group Registrations: </td>
                <td style="padding-top: 10px" class="regNumber"><b>${groupNum}</b></td>
                <c:if test="${groupNum > 0}">
                    <td style="padding-left: 8px; padding-top: 10px">
                        <button class="viewButton" type="submit" name="viewGroupRegs" value="viewGroupRegs">View</button>
                    </td>
                </c:if>
            </tr>
            <tr>
                <td>&emsp;Individual Registrations: </td>
                <td class="regNumber"><b>${indivNum}</b></td>
                <c:if test="${indivNum > 0}">
                    <td style="padding-left: 8px">
                        <button class="viewButton" type="submit" name="viewIndivRegs" value="viewIndivRegs">View</button>
                    </td>
                </c:if>
            </tr>
            <tr>
                <td>&emsp;Spare Registrations: </td>
                <td class="regNumber"><b>${spareNum}</b></td>
                <c:if test="${spareNum > 0}">
                    <td style="padding-left: 8px">
                        <button class="viewButton" type="submit" name="viewSpareRegs" value="viewSpareRegs">View</button>
                    </td>
                </c:if>
            </tr>
        </table>
        <input type="hidden" name="action" value="viewRegs"> 
    </form>
</c:if>