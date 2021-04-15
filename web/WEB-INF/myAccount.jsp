<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=9" />
        <title>ARC Curling - My Account</title>
        <link href="./stylesheet.css" type="text/css" rel="stylesheet">
        <link rel="preconnect" href="https://fonts.gstatic.com">
        <link href="https://fonts.googleapis.com/css2?family=Merriweather:wght@700&family=Open+Sans&display=swap" rel="stylesheet">
    </head>
    <body>
        <div class="header">
            <jsp:include page="./subpages/arcBanner.jsp"></jsp:include>
            
            <div class="navbar">
                <c:choose>
                    <c:when test="${role.roleID eq 1}">
                        <jsp:include page="./subpages/navbarAdmin.jsp"></jsp:include>
                    </c:when>
                    <c:when test="${role.roleID eq 2}">
                        <jsp:include page="./subpages/navbarPlayer.jsp"></jsp:include>
                    </c:when>
                </c:choose>    
            </div>
        </div>

        <div class="sidebar"></div>
        <div class="sidebar2"></div>
        <div class="background"></div>
        <div class="pageTitle"><h2>My Account</h2></div>
        <table class="content accountContent">
            <tr>
                <td class="myAccLeft" style="vertical-align: top;">
                    <form method="get" action="myAccount">
                        <button type="submit" name="display" value="personalInfo">Personal Information</button><br>
                        <button type="submit" name="display" value="teamInfo">Team Information</button><br>
                        <button type="submit" name="display" value="changeEmail">Change Login Email</button><br>
                        <button type="submit" name="display" value="changePassword">Change Password</button>
                    </form>
                </td>
                <c:choose>
                    <c:when test="${display == 'personalInfo'}">
                        <td class="myAccRight">
                            <form method="post" action="myAccount">
                                <jsp:include page="./subpages/personalInfo.jsp"></jsp:include>
                                <input type="hidden" name="action" value="savePersonal">
                            </form>
                        </td>
                    </c:when>
                    <c:when test="${display == 'teamInfo'}">
                        <td class="myAccRight" style="vertical-align: top">
                            <jsp:include page="./subpages/teamInfo.jsp"></jsp:include>
                        </td>
                    </c:when>
                    <c:when test="${display == 'changeEmail'}">
                        <td class="myAccRight" style="vertical-align: top">
                            <form method="post" action="myAccount">
                                <table>
                                    <tr>
                                        <th style="text-align: left; font-size: 18px; color: #a38800;" colspan="2">Change Email</th>
                                    </tr>
                                    <tr>
                                        <td style="padding-bottom: 9px; padding-top: 15px;"><b>Current email: </b></td>
                                        <td style="padding-bottom: 9px; padding-top: 15px;">${user.email}</td>
                                    </tr>
                                    <tr>
                                        <td>New email: </td>
                                        <td><input type="email" name="newEmail" size="30" value="${newEmail}"></td>
                                    </tr>
                                    <tr>
                                        <td>Confirm new email: &emsp;</td>
                                        <td><input type="email" size="30" name="confNewEmail"></td>
                                    </tr>  
                                    <tr>
                                        <td colspan="2" style="text-align: right; padding-top: 10px;"><button type="submit" name="action" value="saveNewEmail">Change email</button></td>
                                    </tr> 
                                    <tr>
                                        <td colspan="3" style="color: #CC3333">
                                            <c:if test="${message == 'nullFields'}">
                                                Error: fields cannot be blank.
                                            </c:if>
                                            <c:if test="${message == 'emailMismatch'}">
                                                Error: emails must match.
                                            </c:if>
                                            <c:if test="${message == 'emailExists'}">
                                                Error: an account with this email already exists.
                                            </c:if>                                                  
                                            <c:if test="${message == 'success'}">
                                                <span style="color: black">Your password has been changed. </span>
                                            </c:if>   
                                        </td>
                                    </tr>
                                </table>
                            </form>
                        </td>
                    </c:when>
                    <c:when test="${display == 'changePassword'}">
                        <td class="myAccRight" style="vertical-align: top">
                            <form method="post" action="myAccount">
                                <table>
                                    <tr>
                                        <th style="text-align: left; font-size: 18px; color: #a38800;" colspan="2">Change Password</th>
                                    </tr>
                                    <tr>
                                        <td style="padding-bottom: 15px; padding-top: 15px;"><b>Current password: </b></td>
                                        <td style="padding-bottom: 15px; padding-top: 15px;"><input type="password" name="currPassword">&emsp;</td>
                                    </tr>
                                    <tr>
                                        <td>New password: </td>
                                        <td><input type="password" name="newPassword"value="${newEmail}"></td>
                                    </tr>
                                    <tr>
                                        <td>Confirm new password: &emsp;</td>
                                        <td><input type="password" name="confNewPassword"></td>
                                    </tr>
                                    <tr>
                                        <td colspan="2" style="text-align: right; padding-top: 10px; padding-right: 15px;"><button type="submit" name="action" value="saveNewPassword">Change password</button></td>
                                    </tr>
                                    <tr>
                                        <td colspan="2" style="color: #CC3333">
                                            <c:if test="${message == 'nullFields'}">
                                                Error: fields cannot be blank.
                                            </c:if>
                                            <c:if test="${message == 'passwordMismatch'}">
                                                Error: passwords must match.
                                            </c:if>                                             
                                            <c:if test="${message == 'samePassword'}">
                                                Error: new password must be different from old password.
                                            </c:if> 
                                            <c:if test="${message == 'passwordTooShort'}">
                                                Error: password must be at least 8 characters long.
                                            </c:if>                                                  
                                            <c:if test="${message == 'wrongPassword'}">
                                                Error: the password you entered is incorrect.
                                            </c:if>  
                                            <c:if test="${message == 'success'}">
                                                <span style="color: black">Your password has been changed. </span>
                                            </c:if>                                                 
                                            &nbsp;
                                        </td>
                                    </tr>
                                </table>
                            </form>
                        </td>
                    </c:when>                        
                </c:choose>
            </tr>
        </table>
    </body>
</html>

<style scoped>
    .myAccLeft {
        width: 205px;
    }
    .myAccLeft button {
        width: 200px;
        background: #B4B4B4;
        border: none;
        margin-bottom: 3px;
    }
    .myAccLeft button:hover {
        background: #b8cbe0;
        color: white;
        text-shadow: 1px 1px 1px black;
    }
    
    .myAccRight {
        background: white;
        padding-top: 0px;
    }
    
    .myAccRight button {
        border: none;
        background: #CC3333;
        padding: 2px 6px 2px 6px;
        color: white;
        font-weight: normal;
    }
    .myAccRight button:hover {
        background: #ab0000;
        font-weight: bold;
        padding: 2px 4px 2px 4px;
    }
    
    
</style>