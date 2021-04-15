<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<table>
    <tr>
        <td style="vertical-align: top">
            <table class="leftGr">
                <tr>
                    <td colspan="2" id="tm">
                        <c:choose>
                            <c:when test="${indivTitle == true}">
                                New Player Information
                            </c:when>
                            <c:when test="${editing == true}">
                                Editing Team Member ${member.number}
                            </c:when>
                            <c:when test="${spareTitle == true}">
                                New Spare Information
                            </c:when>
                            <c:otherwise>
                                Team Member ${num}
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
                <tr>
                    <td>First name: <br>
                        <input type="text" name="firstName" size="25" value="${member.firstName}"></td>
                </tr>
                <tr>
                    <td>Last name: <br>
                        <input type="text" name="lastName" size="25" value="${member.lastName}"></td>
                </tr>
                <tr>
                    <td>Address: <br>
                        <input type="text" name="address" size="25" value="${member.address}"></td>
                </tr>
                <tr>
                    <td>City: <br>
                        <input type="text" name="city" size="15" value="${member.city}"</td>
                </tr>                
                <tr>
                    <td>Postal Code: <br>
                        <input type="text" name="postal" size="8" value="${member.postal}" pattern="[A-Za-z][0-9][A-Za-z][0-9][A-Za-z][0-9]" maxlength="6"></td>
                </tr>
            </table>
        </td>
        
        <c:if test="${indivTitle == true || spareTitle == true}">
            <td style="vertical-align: top; padding-bottom: 0px;">
        </c:if>
        <c:if test="${indivTitle == null && spareTitle == null}">
            <td style="vertical-align: top;">
        </c:if>
            <table class="midGr">
                <tr>
                    <td colspan="2" id="tm">&nbsp;</td>
                </tr>
                <tr>
                    <td>Email: <br>
                        <input type="email" name="email" size="25" value="${member.email}"></td>
                </tr>
                <tr>
                    <td>Phone number: <br>
                        <input type="tel" name="phone" size="12" value="${member.phone}"></td>
                </tr>
                <tr>
                    <c:if test="${indivTitle == true || spareTitle == true}">
                        <td style="padding-bottom: 4px; padding-top: 1px;">
                    </c:if>
                    <c:if test="${indivTitle == null && spareTitle == null}">
                        <td style="padding-bottom: 4px; padding-top: 10px">
                    </c:if>
                        Position: <br>
                        <select name="positionName" style="width: 107px;">
                            <option hidden value>--select--</option>
                            <c:forEach var="positions" items="${positions}">
                                <option value="${positions.positionName}" ${positions.positionName == selectedPos ? 'selected="selected"' : ''}>
                                    ${positions.positionName}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td style="padding-bottom: 14px">
                        <input type="checkbox" name="flexibleP" value="${flexibleP}" ${flexibleP == true ? 'checked="checked"' : ''}>
                        <span style="font-size: 13px; vertical-align: top;">Willing to play a different <br>&ensp;&ensp;&ensp; position if needed</span>
                    </td>
                </tr>
                <c:if test="${indivTitle == true || spareTitle == true}">
                    <tr>
                        <td style="padding-bottom: 4px;">League: <br>
                        <c:forEach var="league" items="${leaguesList}">
                            <input style="margin-top: 7px" name="${league.leagueID}" id="${league.leagueID}" type="checkbox" value="${league.leagueID}" 
                                   onchange="clicked()" ${fn:contains(selectedLeagues, league.leagueID) ? 'checked="checked"' : ''}> 
                            ${league.weekday}&ensp;
                        </c:forEach>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div id="leagueReg" style="color: gray">
                                <input type="radio" name="leagueReg" value="all" ${leagueReg == 'all' ? 'checked="checked"' : ''}>
                                <span style="font-size: 12.5px; vertical-align: 2px">Sign me up for <b>all</b> selected leagues</span><br style="line-height: 25px">
                                <input type="radio" name="leagueReg" value="one" ${leagueReg == 'one' ? 'checked="checked"' : ''}>
                                <span style="font-size: 12.5px; vertical-align: top;">Sign me up for <b>only one</b> of the<br>
                                    <span style="line-height: 12px; vertical-align: top">&ensp;&ensp;&emsp;selected leagues</span></span>
                            </div>
                        </td>
                    </tr>
                </c:if>
            </table>
        </td>
    </tr>
</table>