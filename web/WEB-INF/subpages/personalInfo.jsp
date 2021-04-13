<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<table border="1px">
    <tr>
        <td>Name</td>
        <c:choose>
            <c:when test="${editing == 'name'}">
                <td class="myAccEditTd">
                    <i>Editing</i>
                    <table>
                        <tr><td>First: </td><td><input type="text" name="firstName" value="${user.contactID.firstName}"></td></tr>
                        <tr><td>Last: </td><td><input type="text" name="lastName" value="${user.contactID.lastName}"></td></tr>
                    </table>
                </td>
                <td class="myAccEditTd"><button type="submit" name="action" value="saveName">Save</button></td>
            </c:when>
            <c:otherwise>
                <td>${user.contactID.firstName} ${user.contactID.lastName}</td>
                <td><button type="submit" name="action" value="editName"
                    ${editInProgress == true ? 'disabled="disabled"' : ''}>Edit</button></td>
            </c:otherwise>
        </c:choose>
    </tr>

    <tr>
        <td>Address</td>
        <c:choose>
            <c:when test="${editing == 'address'}">
                <td class="myAccEditTd">
                    <i>Editing</i>
                    <table>
                        <tr><td>Address: </td><td><input type="text" name="address" value="${user.contactID.address}"></td></tr>
                        <tr><td>City: </td><td><input type="text" name="city" value="${user.contactID.city}"></td></tr>
                        <tr><td>Postal code: </td><td><input type="text" name="postal" value="${user.contactID.postal}"></td></tr>
                    </table>
                </td>
                <td class="myAccEditTd"><button type="submit" name="action" value="saveAddress">Save</button></td>
            </c:when>
            <c:otherwise>
                <td>
                    ${user.contactID.address}<br>
                    ${user.contactID.city}<br>
                    ${user.contactID.postal}
                </td>
                <td><button type="submit" name="action" value="editAddress"
                            ${editInProgress == true ? 'disabled="disabled"' : ''}>Edit</button></td>
            </c:otherwise>
        </c:choose>
    </tr>

    <tr>
        <td>Email</td>
        <c:choose>
            <c:when test="${editing == 'email'}">
                <td class="myAccEditTd">
                    <i>Editing</i>
                    <table><tr><td>Email: </td><td><input type="email" name="email" value="${user.email}"></td></tr></table>
                </td>
                <td><button type="submit" name="action" value="saveEmail">Save</button></td>
            </c:when>
            <c:otherwise>
                <td>${user.email}</td>
                <td><button type="submit" name="action" value="editEmail"
                            ${editInProgress == true ? 'disabled="disabled"' : ''}>Edit</button></td>
            </c:otherwise>
        </c:choose>
    </tr>
    <tr>
        <td>Phone number</td>
        <c:choose>
            <c:when test="${editing == 'phone'}">
                <td class="myAccEditTd">
                    <i>Editing</i>
                    <table><tr><td>Phone number: </td><td><input type="text" name="phone" value="${user.contactID.phone}"></td></tr></table>
                </td>
                <td><button type="submit" name="action" value="savePhone">Save</button>
            </c:when>
            <c:otherwise>
                <td>${user.contactID.phone}</td>
                <td><button type="submit" name="action" value="editPhone"
                            ${editInProgress == true ? 'disabled="disabled"' : ''}>Edit</button></td>
            </c:otherwise>
        </c:choose>
    </tr>
</table>