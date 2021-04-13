<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table>
    <tr>
        <td style="vertical-align: top">
            <table class="leftGr">
                <tr>
                    <td colspan="2" id="tm">New Player Information</td>
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
                        <input type="text" name="postal" size="8" value="${member.postal}" pattern="[A-Za-z][0-9][A-Za-z][0-9][A-Za-z][0-9]" maxlength="6"</td>
                </tr>
                <tr>
                    <td>Email: <br>
                        <input type="email" name="email" size="25" value="${member.email}"></td>
                </tr>
                <tr>
                    <td>Phone number: <br>
                        <input type="tel" name="phone" size="12" value="${member.phone}"></td>
                </tr>
            </table>
        </td>

        <td style="vertical-align: top; padding-bottom: 0px;">
            <table class="midGr">
                <tr>
                    <td style="padding-bottom: 4px; padding-top: 1px;">
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
                        <input type="checkbox" name="isFlexPos" value="${isFlexPos}" ${isFlexPos == true ? 'checked="checked"' : ''}>
                        <span style="font-size: 13px; vertical-align: top;">Willing to play a different <br>&ensp;&ensp;&ensp; position if needed</span>
                    </td>
                </tr>
                <tr>
                    <td style="padding-bottom: 4px;">League:<br>
                        <c:forEach var="leagues" items="${leagues}">
                            <input type="checkbox" value="${leagues.weekday}" ${leagues.weekday == selectedLeague ? 'checked="checked"' : ''}> 
                            ${leagues.weekday}&ensp;
                        </c:forEach>
                    </td>
                </tr>
                <tr>
                    <td>
                        <input type="checkbox" name="bothLeagues" value="bothLeagues" ${bothLeagues == true ? 'checked="checked"' : ''}>
                        <span style="font-size: 13px; vertical-align: top;">Sign me up once for EACH league</span>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>