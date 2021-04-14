<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=9" />
        <title>ARC Curling - Management</title>
        <link href="./stylesheet.css" type="text/css" rel="stylesheet">
        <link rel="preconnect" href="https://fonts.gstatic.com">
        <link href="https://fonts.googleapis.com/css2?family=Merriweather:wght@700&family=Open+Sans&display=swap" rel="stylesheet">
    </head>
    <body>
        <div class="header">
            <jsp:include page="./subpages/arcBanner.jsp"></jsp:include>
            <div class="navbar">
                <jsp:include page="./subpages/navbarAdmin.jsp"></jsp:include> 
            </div>
        </div>

        <div class="sidebar"></div>
        <div class="sidebar2"></div>
        <div class="background"></div>
        <div class="pageTitle"><h2>Management</h2></div>
        <table class="content managementContent">
            <tr>
                <td class="manageLeft">
                    <form method="get" action="management">
                        <button type="submit" name="display" value="manageRegistration">Verify registration</button><br>
                        <button type="submit" name="display" value="manageAccounts">User accounts</button><br>
                        <button type="submit" name="display" value="manageTeams">Teams</button><br>
                        <button type="submit" name="display" value="manageLeagues">Leagues</button><br>
                        <button type="submit" name="display" value="manageUsers">Scores</button><br>
                        <button type="submit" name="display" value="managePosts">News posts</button><br>
                        <button type="submit" name="display" value="manageSpareRequests">Spare requests</button><br>
                    </form>
                </td>
                <c:choose>
                    <c:when test="${display == 'manageRegistration'}">
                        <td class="manageRight" style="vertical-align: top">
                            <jsp:include page="./subpages/management/manageRegistration.jsp"></jsp:include>
                        </td>
                    </c:when>   
                    
                    <c:when test="${display == 'verifyGroupRegs'}">
                        <td class="manageRight vg" style="vertical-align: top;">
                            <jsp:include page="./subpages/management/verifyGroupRegs.jsp"></jsp:include>
                        </td>
                    </c:when>
                        
                    <c:when test="${display == 'verifyIndivRegs'}">
                        <td class="manageRight vg" style="vertical-align: top;">
                            <jsp:include page="./subpages/management/verifyIndivRegs.jsp"></jsp:include>
                        </td>
                    </c:when>
                        
                    <c:when test="${display == 'registrationSetup'}">   
                        <td class="manageRight att">
                            <jsp:include page="./subpages/management/registrationSetup.jsp"></jsp:include>
                        </td>
                    </c:when>
                        
                    <c:when test="${display == 'manageAccounts'}">
                        <td class="manageRight acc" style="vertical-align: top; padding-left: 10px;">
                            <jsp:include page="./subpages/management/manageAccounts.jsp"></jsp:include>
                        </td>
                    </c:when>
                        
                    <c:when test="${display == 'manageTeams'}">
                        <td class="manageRight acc" style="vertical-align: top; padding-left: 10px;">
                            <jsp:include page="./subpages/management/manageTeams.jsp"></jsp:include>
                        </td>
                    </c:when>
                        
                    <c:when test="${display == 'manageLeagues'}">
                        <td class="manageRight acc" style="vertical-align: top; padding-left: 10px;">
                           <jsp:include page="./subpages/management/manageLeagues.jsp"></jsp:include>
                        </td>
                    </c:when>
                        
                    <c:when test="${display == 'manageSpareRequests'}">
                        <td class="manageRight acc" style="vertical-align: top; padding-left: 10px;">
                           <jsp:include page="./subpages/management/manageSpareRequests.jsp"></jsp:include>
                        </td>
                    </c:when>
                    <c:when test="${display == 'viewSpareRequest'}">
                        <td class="manageRight acc" style="vertical-align: top; padding-left: 10px;">
                           <jsp:include page="./subpages/management/assignSpare.jsp"></jsp:include>
                        </td>
                    </c:when>                        
                            
                </c:choose>
            </tr>
        </table>
        <script>
            var selectAll = document.getElementById("selectAll");
            var checks = document.getElementsByClassName("checks");
            var selectedNum = document.getElementById("selectedNum");
            var ctButton = document.getElementById("chooseTeamButton");
            var previous = "null";
            
            verifyClick();
            function clicked() {
                if (selectAll.checked) {
                    selectAll.nextElementSibling.innerHTML = "(deselect all)";
                    for (i = 0; i < checks.length; i++) 
                        checks[i].checked = "checked";
                    
                    adjustNum(checks.length);
                }
                else {
                    selectAll.nextElementSibling.innerHTML = "(select all)";
                    for (i = 0; i < checks.length; i++) 
                        checks[i].checked = "";
                    
                    adjustNum(0);
                }
            }
            
            function verifyClick() {
                var count = 0;
                for (i = 0; i < checks.length; i++) {
                    if (checks[i].checked) 
                        count++;
                }
                
                if (count === checks.length) {
                    selectAll.checked = "checked";
                    selectAll.nextElementSibling.innerHTML = "(deselect all)";
                }
                else {
                    selectAll.nextElementSibling.innerHTML = "(select all)";
                    selectAll.checked = "";
                }
                adjustNum(count);
            }
            
            function adjustNum(num) {
                if (num === 0) {
                    selectedNum.removeChild(selectedNum.children[0]);
                    selectedNum.appendChild(document.createElement("i"));
                    
                    ctButton.disabled = "disabled";
                    ctButton.style.cursor = "not-allowed";
                    ctButton.style.background = "#bdbdbd";
                    ctButton.onmouseover = function() {ctButton.style.background = "#bdbdbd"};
                    ctButton.onmouseout = function() {ctButton.style.background = "#bdbdbd"};
                }
                else {
                    selectedNum.removeChild(selectedNum.children[0]);
                    selectedNum.appendChild(document.createElement("b"));
                    
                    ctButton.disabled = "";
                    ctButton.style.cursor = "pointer";
                    ctButton.style.background = "#CC3333";
                    ctButton.onmouseover = function() {ctButton.style.background = "#0078d7"};
                    ctButton.onmouseout = function() {ctButton.style.background = "#CC3333"};
                }
                selectedNum.children[0].innerHTML = num;
            }
            
            function closeWindow(thisWindow) {
                document.getElementById(thisWindow).style.display = "none";
                document.getElementById("errorMsg").style.display = "none";
            }
            
            function addLeague() {
                var count = 0;
                var selectLeaguesCheck = document.getElementsByClassName("selectLeaguesCheck");
                
                for (i = 0; i < selectLeaguesCheck.length; i++) {
                    if (selectLeaguesCheck[i].checked)
                        count++;
                }
                
                if (count > 0)
                    document.getElementById("chooseLeaguesButton").style.display = "block";
                else 
                    document.getElementById("chooseLeaguesButton").style.display = "none";
            }
            
            
            
            function displayEdit(value) {
                if (previous !== "null") {
                    document.getElementById(previous + "Div").style.display = "none";
                    var revert = previous.substring(0, previous.indexOf("edit"));
                    document.getElementById(revert + "row").style = "background: none; font-weight: normal";
                }
                
                document.getElementById(value + "Div").style = "display: block; background: white; padding: 10px; border-radius: 2px;";
                var changed = value.substring(0, value.indexOf("edit"));
                document.getElementById(changed + "row").style = "background: white; font-weight: bold";

                previous = value;
            }
            
        </script>
    </body>
</html>
        
        <!--
            
        <div class="container-lg" style="background-color: white;">
            <div class="row">
                <div class="col-md-2 col-sm-12 pl-0">
                    <div class="list-group">
                        <a href="management?verify=1" class="list-group-item list-group-item-action <c:if test="${type eq 0}">active</c:if>" aria-current="true">
                          Verify Register
                        </a>
                        <a href="management?user=1" class="list-group-item list-group-item-action <c:if test="${type eq 1}">active</c:if>">Manage Users</a>
                        <a href="management?team=1" class="list-group-item list-group-item-action <c:if test="${type eq 2}">active</c:if>">Manage Teams</a>
                        <a href="management?league=1" class="list-group-item list-group-item-action <c:if test="${type eq 3}">active</c:if>">Manage Leagues</a>
                        <a href="management?post=1" class="list-group-item list-group-item-action <c:if test="${type eq 4}">active</c:if>">Manage Posts</a>
                    </div>
                </div>
                <div class="col-md-10 col-sm-12">
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger" role="alert">
                            ${error}
                        </div>
                    </c:if>
                    <c:if test="${not empty info}">
                        <div class="alert alert-success" role="alert">
                            ${info}
                        </div>
                    </c:if>

                    <c:choose>
                        <c:when test = "${type == 0}">
                            <div class="row">
                                <div class="col-2">Email</div>
                                <div class="col-2">First Name</div>
                                <div class="col-2">Last Name</div>
                                <div class="col-2">Phone</div>
                                <div class="col-4">Action</div>
                            </div>

                            <c:forEach items="${users}" var="user">
                                <form method="post" action="management">
                                    <div class="row">
                                        <div class="col-2">${user.email}</div>
                                        <div class="col-2">${user.firstName}</div>
                                        <div class="col-2">${user.lastName}</div>
                                        <div class="col-2">${user.phone}</div>
                                        <input type="text" name="id" value="${user.userID}" hidden/>
                                        <div class="col-4">
                                            <button class="btn btn-sm btn-primary" type="submit" name="action" value="validate">Validate</button>
                                            <button class="btn btn-sm btn-danger" type="submit" name="action" value="delete">Delete</button>
                                        </div>
                                    </div>
                                </form>
                            </c:forEach>
                        </c:when>



                        <c:when test = "${type == 1}">
                            <div class="row">
                                <div class="col-2 pr-0">Email</div>
                                <div class="col-2 pr-0 pl-0">First Name</div>
                                <div class="col-2 pr-0 pl-0">Last Name</div>
                                <div class="col-2 pr-0 pl-0">Phone</div>
                                <div class="col-1 pr-0 pl-0">Role</div>
                                <div class="col-3 pl-0">Action</div>
                            </div>

                            <c:forEach items="${users}" var="user">
                                <form method="post" action="management">
                                    <div class="row">
                                        <div class="col-2 pr-0"><input type="email" style="width:100%;" name="email" value="${user.email}"/></div>
                                        <div class="col-2 pr-0 pl-0"><input type="text" style="width:100%;" name="firstName" value="${user.firstName}"/></div>
                                        <div class="col-2 pr-0 pl-0"><input type="text" style="width:100%;" name="lastName" value="${user.lastName}"/></div>
                                        <div class="col-2 pr-0 pl-0"><input type="tel" style="width:100%;" name="phone" value="${user.phone}"/></div>
                                        <div class="col-1 pr-0 pl-0">
                                            <select name="role" style="width:100%;">
                                                <c:forEach items="${roles}" var="role">
                                                    <option value="${role.roleID}"<c:if test="${role.roleID == user.role.roleID}">selected</c:if> >${role.roleName}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <input type="text" name="id" value="${user.userID}" hidden/>
                                        <div class="col-3 pl-0" >
                                            <button class="btn btn-sm btn-primary" type="submit" name="action" value="modify">Modify</button>
                                            <button class="btn btn-sm btn-danger" type="submit" name="action" value="invalidate">Invalidate</button>
                                        </div>
                                    </div>
                                </form>
                            </c:forEach>

                        </c:when>
                        
                        
                        <c:when test = "${type == 2}">
                            <form>
                                <table class="table table-hover">
                                    <thead>
                                      <tr>
                                        <th scope="col">Team ID</th>
                                        <th scope="col">Team Name</th>
                                        <th scope="col">Action</th>
                                      </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${teams}" var="team">
                                            <tr>
                                                <td>${team.teamID}</td>
                                                <td>${team.teamName}</td>
                                                <td><button type="submit" value="${team.teamID}" name="EditTeam">Edit</button></td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </form>
                            <div class="card">
                                <div>
                                    <label>Team ID:</label>
                                    <input type="text" name="teamID"/>
                                </div>
                                <div>
                                    <label>Team Name:</label>
                                    <input type="text" name="teamName"/>
                                </div>
                                <div><button>Modify</button><button>Delete</button></div>
                            </div>
                            
                     
                        </c:when>
                    </c:choose>
                </div>
            </div>
        </div>
