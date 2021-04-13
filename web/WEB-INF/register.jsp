<%-- 
    Document   : registration
    Created on : Dec 1, 2020, 8:15:25 PM
    Author     : 829942
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
   <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>        
        <link href="./stylesheet.css" type="text/css" rel="stylesheet">
        <link rel="preconnect" href="https://fonts.gstatic.com">
        <title>Registration Page</title>
        <link href="https://fonts.googleapis.com/css2?family=Merriweather:wght@700&family=Open+Sans&display=swap" rel="stylesheet">
    </head>

    <body>
        
        <div class="header">
            <jsp:include page="./subpages/arcBanner.jsp"></jsp:include>
            
            <div class="navbar">
                <jsp:include page="./subpages/navbarGeneral.jsp"></jsp:include>  
            </div>
        </div>
        
        <div class="sidebar"></div>
        <div class="sidebar2"></div>
        <div class="background backgroundRegister"></div>
        
        <h3 class="registerTitle">${regTitle}</h3>
        <c:choose> 
            <c:when test="${display == null}">
                <div class="content">
                    <form method="get" action="register">
                        <h4 class="regh4">Select registration type:</h4>
                        <span style="line-height: 40px;">Team registration:</span><br>
                        <button type="submit" value="indiv" name="regTypeButton">Individual (1 player)</button>&nbsp;
                        <button type="submit" value="group" name="regTypeButton">Group (2-6 players)</button><br><br><br>
                        <span style="line-height: 40px;">Spare registration:</span><br>
                        <button type="submit" value="spare" name="regTypeButton">Individual (1 spare)</button>&nbsp;
                        <input type="hidden" name="action" value="selectRegType">
                    </form>
                </div>
            </c:when>

            <c:when test="${display == 'displayIndivReg'}">
                <table class="registerContent">
                    <tr>
                        <td class="membersInput" style="width: 510px; height: 450px">
                            <form method="post" action="register">
                                <jsp:include page="./subpages/teamMember.jsp"></jsp:include>
                                <div class="errors">
                                    <c:choose>
                                        <c:when test="${message == 'nullFields'}">
                                            <p class="regP2">Error: Fields cannot be blank</p>
                                        </c:when>
                                        <c:when test="${message == 'playerExists'}">
                                            <p class="regP2" id="g">Error: An account with this email is already registered.</p>
                                        </c:when>
                                        <c:otherwise>
                                            <p class="regP2">&nbsp;</p>
                                        </c:otherwise>
                                    </c:choose>
                                    <button class="regButton in" type="submit" name="indivRegSubmit" value="indivRegister">Submit Registration</button>
                                    <input type="hidden" name="action" value="indivSubmit">
                                </div>
                            </form>
                        </td>
                    </tr>
                </table>
            </c:when>

            <c:when test="${display == 'displayGroupReg'}">
                <table class="registerContent">
                    <tr>
                        <td class="membersInput">
                            <form method="get" id="regForm" action="register">
                                <jsp:include page="./subpages/teamMember.jsp"></jsp:include>
                                <div class="errors">
                                    <c:choose>
                                        <c:when test="${message == 'nullFields'}">
                                            <p class="regP2">Error: Fields cannot be blank</p>
                                        </c:when>
                                        <c:when test="${message == 'memberExists'}">
                                            <p class="regP2" id="g">Error: This email has already been added.</p>
                                        </c:when>
                                        <c:when test="${message == 'playerExists'}">
                                            <p class="regP2" id="g">Error: An account with this email address already exists.</p>
                                        </c:when>
                                        <c:otherwise>
                                            <p class="regP2">&nbsp;</p>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <c:choose>
                                    <c:when test="${editing == true}">
                                        <div class="saveDiscardButtons">
                                            <button type="submit" name="saveMemCh" value="${member.number}">Save</button>
                                            <button type="submit" name="discardMemCh" value="${member.number}">Discard Changes</button>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="addButton">
                                            <button type="submit" name="addMember" value="addMember">Add Team Member</button>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                                <input type="hidden" name="action" value="addMember">
                            </form>
                        </td>
                        <td class="membersList">
                            <form method="post" id="regId" action="register">
                                <table style="border-collapse: collapse">
                                    <tr>
                                        <td colspan="3" class="teamDetails"><b>Team Details</b></td>
                                    </tr>
                                    <tr>
                                        <td colspan="3"style="padding-bottom: 0px;">League: <br>
                                        <c:forEach var="league" items="${leaguesList}">
                                            <input style="margin-top: 7px" name="${league.leagueID}" id="${league.leagueID}" type="checkbox" value="${league.leagueID}" 
                                                   onchange="clicked()" ${fn:contains(selectedLeagues, league.leagueID) ? 'checked="checked"' : ''}> 
                                            ${league.weekday}&ensp;
                                        </c:forEach>
                                        </td>
                                    </tr>
                                    <tr style="height: 50px; vertical-align: top">
                                        <td colspan="3">
                                            <div id="leagueReg" style="color: gray;">
                                                <input type="radio" name="leagueReg" value="all" ${leagueReg == 'all' ? 'checked="checked"' : ''}>
                                                <span style="font-size: 12.5px; vertical-align: 2px">Sign up for <b>all</b> selected leagues</span><br>
                                                <input type="radio" name="leagueReg" value="one" ${leagueReg == 'one' ? 'checked="checked"' : ''}>
                                                <span style="font-size: 12.5px; vertical-align: top;">Sign up for <b>only one</b> of the selected leagues</span>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="3" style="padding-bottom: 8px; vertical-align: top" class="memberNum">Team Name:&emsp;&emsp;
                                        <input type="text"name="teamName" value="${teamName}"><br><i style="font-size: 12px">(optional) </i></td>
                                    </tr>
                                    <tr>
                                        <td colspan="3" style="padding: 0; margin: 0"><hr style="color: #CC3333"></td>
                                    </tr>
                                    <c:forEach var="members" items="${members}">
                                        <tr>
                                            <td width="20px"class="teamMembers">${members.number}.</td>
                                            <td class="teamMembers">${members.firstName} ${members.lastName}</td>
                                            <td width="107px" class="teamMembers">
                                                <button class="editRemoveBtnMem" type="submit" name="editButton" value="${members.number}">Edit</button>
                                                <button class="editRemoveBtnMem" type="submit" name="removeButton" value="${members.number}">Remove</button>
                                            </td>
                                        </tr> 
                                    </c:forEach>
                                </table>
                                <button class="regButton" type="submit" name="groupRegSubmit" value="groupRegister">Register</button>
                                <input type="hidden" name="action" value="groupRegSubmit">
                        </form><br><br>
                        </td>
                    </tr>
                    <tr>
                    <c:choose>
                        <c:when test="${message == 'nullMembers'}">
                            <td colspan="2" style="text-align: right; padding-bottom: 0; margin-bottom: 0;">Two or more members required.</td>
                        </c:when>
                        <c:when test="${message == 'notEnoughMems'}">
                            <td colspan="2" style="text-align: right;">Two or more members required.</td>
                        </c:when>
                        <c:when test="${message == 'nullLeague'}">
                            <td colspan="2" style="text-align: right;">Please select a league to continue.</td>
                        </c:when>
                        <c:otherwise> 
                            <td>&nbsp;</td>
                        </c:otherwise>
                    </c:choose>
                    </tr>
                </table>
            </c:when>
            
            <c:when test="${display == 'displaySpareReg'}">
              <table class="registerContent">
                    <tr>
                        <td class="membersInput" style="width: 510px; height: 450px">
                            <form method="post" action="register">
                                <jsp:include page="./subpages/teamMember.jsp"></jsp:include>
                                <div class="errors">
                                    <c:choose>
                                        <c:when test="${message == 'nullFields'}">
                                            <p class="regP2">Error: Fields cannot be blank</p>
                                        </c:when>
                                        <c:otherwise>
                                            <p class="regP2">&nbsp;</p>
                                        </c:otherwise>
                                    </c:choose>
                                    <button class="regButton in" type="submit" name="spareRegSubmit" value="spareRegister">Submit Registration</button>
                                    <input type="hidden" name="action" value="spareSubmit">
                                </div>
                            </form>
                        </td>
                    </tr>
                </table>  
            </c:when>
            
            <c:when test="${display == 'displaySuccess'}">
                <div class="content">
                    Thank you for registering! <br><br>
                    <span style="font-size: 15px; color: black">A league executive will review your registration and contact you as soon as possible. </span>
                    <c:forEach var="regs" items="${regs}">
                        ${regs.contact.firstName} ${regs.contact.lastName}, ${regs.league.weekday} <br>
                    </c:forEach>
                </div>
            </c:when>
        </c:choose>
        
    <script>
        var L01 = false;
        var L02 = false;
        clicked();
        function clicked() {
            if (document.getElementById("L01").checked) 
                L01 = true;
            else 
                L01 = false;

            if (document.getElementById("L02").checked) 
                L02 = true;
            else
                L02 = false;

            if (L01 && L02) {
                document.getElementById("leagueReg").style.color = "#000";
                document.getElementById("leagueReg").children[0].disabled = "";
                document.getElementById("leagueReg").children[3].disabled = "";
                if (!document.getElementById("leagueReg").children[0].checked && !document.getElementById("leagueReg").children[3].checked) 
                    document.getElementById("leagueReg").children[3].checked = "checked";
            }
            else {
                document.getElementById("leagueReg").style.color = "gray";
                document.getElementById("leagueReg").children[0].disabled = "disabled";
                document.getElementById("leagueReg").children[0].checked = "";
                document.getElementById("leagueReg").children[3].disabled = "disabled";
                document.getElementById("leagueReg").children[3].checked = "";
            }
        }
    </script>
    </body>    
</html>
