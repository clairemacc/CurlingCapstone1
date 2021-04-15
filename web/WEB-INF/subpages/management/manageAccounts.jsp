<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<table>
    <tr>
        <td style="font-size: 20px; color: #CC3333; padding-bottom: 10px;"><b>Manage Accounts</b></td>
    </tr>
    <tr>
        <td>
            <table class="manageAccountsTable">
                <tr>
                    <th colspan="6" style="border: none; padding: 5px; border-radius: 3px; font-weight: normal; background: #afbbc7;">Search:&ensp;
                        <input type="text" size="30"></td>
                </tr>
                <tr>
                    <th>User ID</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Role</th>
                    <th>Status</th>
                    <th>&nbsp;</th>
                </tr>
                <c:forEach var="user" items="${allUsers}">
                    <tr class="thisUserRow" id="${user.userID}row">
                        <td>${user.userID}</td>
                        <td>${user.contactID.firstName} ${user.contactID.lastName}</td>
                        <td>${user.email}</td>
                        <td>${user.role.roleName}</td>
                        <td><c:if test="${user.isActive}">Active</c:if><c:if test="${!user.isActive}">Inactive</c:if></td>
                        <td style="padding-left: 30px">
                            <button type="button" onclick="displayEdit(this.value)" name="editUserButton" value="${user.userID}edit">Edit</button>&nbsp;
                            <button type="button" onclick="displayDelete('${user.contactID.firstName} ${user.contactID.lastName}', this.value)" name="deleteUserButton" value="${user.userID}">Delete</button>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="6" style="border:none; padding: 0px">
                            <div id="${user.userID}editDiv" style="display: none;">
                                <form method="post" action="management">
                                <table class="innerEdit">
                                        <tr>
                                            <td>
                                                <table>
                                                    <tr>
                                                        <td colspan="2"><b>Personal Information</b></td>
                                                    </tr>
                                                    <tr>
                                                        <td>First name: </td>
                                                        <td><input type="text" name="firstName" value="${user.contactID.firstName}"</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Last name :</td>
                                                        <td><input type="text" name="lastName" value="${user.contactID.lastName}"</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Address: </td>
                                                        <td><input type="text" name="address" value="${user.contactID.address}"</td>
                                                    </tr>
                                                    <tr>
                                                        <td>City: </td>
                                                        <td><input type="text" name="city" value="${user.contactID.city}"</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Postal code: </td>
                                                        <td><input type="text" name="postal" value="${user.contactID.postal}"</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Phone number: </td>
                                                        <td><input type="text" name="phone" value="${user.contactID.phone}"</td>
                                                    </tr>
                                                </table>
                                            </td>
                                            <td style="vertical-align: top">
                                                <table>
                                                    <tr>
                                                        <td colspan="2"><b>Account Information</b></td>
                                                    </tr>
                                                    <tr>
                                                        <td>Email: </td>
                                                        <td><input type="text" name="email" value="${user.contactID.email}"></td>
                                                    </tr>
                                                    <tr>
                                                        <td>Password: </td>
                                                        <td style="width: 196px"><input type="text" name="password" value="${user.password}"></td>
                                                    </tr>
                                                    <tr>
                                                        <td>Role: </td>
                                                        <td>
                                                            <select name="userRole" style="width: 145px">
                                                                <c:forEach var="role" items="${roles}">
                                                                    <option value="${role.roleID}" ${role.roleID == user.role.roleID ? 'selected="selected"' : ''}>${role.roleName}</option>
                                                                </c:forEach>
                                                            </select>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td>Status: </td>
                                                        <td>
                                                            <input type="radio" name="${user.userID}active" value="active" ${user.isActive ? 'checked="checked"' : ''}>Active&emsp;
                                                            <input type="radio" name="${user.userID}active" value="inactive" ${!user.isActive ? 'checked="checked"' : ''}>Inactive
                                                        </td>
                                                    </tr>
                                                </table>
                                                <table style="background: none; height: 48px; width: 100%; text-align: right">
                                                    <tr>
                                                        <td style="vertical-align: bottom;">
                                                            <button class="saveUserBtn" type="submit" name="saveUserButton" value="${user.userID}">Save changes</button>&nbsp;
                                                            <button type="submit" name="cancelButton">Cancel</button>
                                                        </td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                    </table>
                                    <input type="hidden" name="action" value="saveUser">
                                </form>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </td>
    </tr>
</table>
<form method="post" action="management">
    <div name="deleteUser" class="deleteTeam" id="deleteThisUser">
        <h4></h4>
        <button type="submit" name="realDeleteUserButton" id="realDeleteUserButton">Delete</button>&ensp;
        <button type="button" name="cancelDelete" onclick="closeDeleteUser()">Cancel</button>
    </div>
    <input type="hidden" name="action" value="deleteUser">
</form>

<script>
    function displayDelete(name, userID) {
        document.getElementById("deleteThisUser").children[0].innerHTML = "";
        var text = "Are you sure you want to permanently delete the user \"" + name + "\"?";
        document.getElementById("deleteThisUser").children[0].innerHTML = text;
        document.getElementById("deleteThisUser").style.display = "block";
        document.getElementById("realDeleteUserButton").value = userID;
        document.getElementById("deleteThisUser").scrollIntoView(false);
    }
    
    function closeDeleteUser() {
        document.getElementById("deleteThisUser").style.display = "none";
    }
</script>