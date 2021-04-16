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
                        <button type="submit" name="mgmtDisplay" value="manageRegistration">Verify registration</button><br>
                        <button type="submit" name="mgmtDisplay" value="manageAccounts">User accounts</button><br>
                        <button type="submit" name="mgmtDisplay" value="manageTeams">Teams</button><br>
                        <button type="submit" name="mgmtDisplay" value="manageLeagues">Leagues</button><br>
                        <button type="submit" name="mgmtDisplay" value="manageSchedules">Schedules</button><br>
                        <button type="submit" name="mgmtDisplay" value="manageScores">Scores</button><br>
                        <button type="submit" name="mgmtDisplay" value="managePosts">News posts</button><br>
                        <button type="submit" name="mgmtDisplay" value="manageSpareRequests">Spare requests</button><br>
                    </form>
                </td>
                <c:choose>
                    <c:when test="${mgmtDisplay == 'manageRegistration'}">
                        <td class="manageRight" style="vertical-align: top">
                            <jsp:include page="./subpages/management/manageRegistration.jsp"></jsp:include>
                        </td>
                    </c:when>   
                    
                    <c:when test="${mgmtDisplay == 'verifyGroupRegs'}">
                        <td class="manageRight vg" style="vertical-align: top;">
                            <jsp:include page="./subpages/management/verifyGroupRegs.jsp"></jsp:include>
                        </td>
                    </c:when>
                        
                    <c:when test="${mgmtDisplay == 'verifyIndivRegs'}">
                        <td class="manageRight vg" style="vertical-align: top;">
                            <jsp:include page="./subpages/management/verifyIndivRegs.jsp"></jsp:include>
                        </td>
                    </c:when>
                        
                    <c:when test="${mgmtDisplay == 'registrationSetup'}">   
                        <td class="manageRight att">
                            <jsp:include page="./subpages/management/registrationSetup.jsp"></jsp:include>
                        </td>
                    </c:when>
                        
                    <c:when test="${mgmtDisplay == 'manageAccounts'}">
                        <td class="manageRight acc" style="vertical-align: top; padding-left: 10px;">
                            <jsp:include page="./subpages/management/manageAccounts.jsp"></jsp:include>
                        </td>
                    </c:when>
                        
                    <c:when test="${mgmtDisplay == 'manageTeams'}">
                        <td class="manageRight acc" style="vertical-align: top; padding-left: 10px;">
                            <jsp:include page="./subpages/management/manageTeams.jsp"></jsp:include>
                        </td>
                    </c:when>
                        
                    <c:when test="${mgmtDisplay == 'manageLeagues'}">
                        <td class="manageRight acc" style="vertical-align: top; padding-left: 10px;">
                           <jsp:include page="./subpages/management/manageLeagues.jsp"></jsp:include>
                        </td>
                    </c:when>
                        
                    <c:when test="${mgmtDisplay == 'manageSpareRequests'}">
                        <td class="manageRight acc" style="vertical-align: top; padding-left: 10px;">
                           <jsp:include page="./subpages/management/manageSpareRequests.jsp"></jsp:include>
                        </td>
                    </c:when>
                    <c:when test="${mgmtDisplay == 'viewSpareRequest'}">
                        <td class="manageRight acc" style="vertical-align: top; padding-left: 10px;">
                           <jsp:include page="./subpages/management/assignSpare.jsp"></jsp:include>
                        </td>
                    </c:when>  
                    <c:when test="${mgmtDisplay == 'manageSchedules'}">
                        <td class="manageRight acc" style="vertical-align: top;">
                            <jsp:include page="./subpages/management/manageSchedules.jsp"></jsp:include>
                        </td>
                    </c:when>  
                    <c:when test="${mgmtDisplay == 'manageScores'}">
                        <td class="manageRight acc" style="vertical-align: top;">
                            <jsp:include page="./subpages/management/manageScores.jsp"></jsp:include>
                        </td>
                    </c:when>
                    <c:when test="${mgmtDisplay == 'managePosts'}">
                        <td class="manageRight acc" style="vertical-align: top;">
                            <jsp:include page="./subpages/management/managePosts.jsp"></jsp:include>
                        </td>
                    </c:when>   
                            
                </c:choose>
            </tr>
        </table><br><br>
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