<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=9" />
        <title>ARC Curling - Contact</title>
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
                    <c:otherwise>
                        <jsp:include page="./subpages/navbarGeneral.jsp"></jsp:include>
                    </c:otherwise>
                </c:choose>    
            </div>
        </div>

        <div class="sidebar"></div>
        <div class="sidebar2"></div>
        <div class="background"></div>
        <div class="pageTitle"><h2>Contact</h1></div>
        <c:choose>
            <c:when test="${role.roleID eq 1}">
                <c:if test="${display == 'selectContacts'}">
                    <div class="content contactContent" style="position: relative; width: 900px; margin-bottom: 100px;">
                        <div class="contactArea">
                            <jsp:include page="./subpages/selectContacts.jsp"></jsp:include>
                        </div>
                    </div>
                </c:if>
                    
                <c:if test="${display == 'writeMessage'}">
                    <div class="content contactContent otherwise">
                        <table>
                            <tr>
                                <td class="msg" style="vertical-align: top">
                                    <h3 style="margin: 0px; padding: 0px; padding-bottom: 18px;">New Message</h3>
                                    <form method="post" action="contact">
                                        <table>
                                            <tr>
                                                <td>Subject: &ensp;<input placeholder='e.g., "Upcoming game"' type="text" name="subject" value="${message.subject}"></td>
                                            </tr>
                                            <tr>
                                                <td colspan="2"><textarea class="textareaClass" name="body" value="${message.body}" rows="10" cols="50" placeholder="Write your message here"></textarea></td>
                                            </tr>
                                            <tr>
                                                <td colspan="2" style="padding-top: 5px"><button type="submit" name="sendFromExec" value="send">Send Email</button></td>
                                            </tr>
                                        </table>
                                        <input type="hidden" name="action" value="sendEmail">
                                    </form>
                                </td>
                                
                                <td class="execContact">
                                    <h4>Recipients</h4>
                                    <div class="recipients">
                                        <c:forEach var="rec" items="${selectedContacts}">
                                            <span class="recName">${rec.firstName} ${rec.lastName}<br>
                                                <span class="recEmail">${rec.email}</span><br>
                                            </span>
                                        </c:forEach>
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </div>
                </c:if>
                
                <c:if test="${messageSent == true}">
                    <div class="content contactContent otherwise" style="height: 100px; width: 380px">
                        <h4>Your email has been sent.</h4>
                        <a href="contact">Send another email</a><br>
                        <a href="homePage">Back to Home</a><br>
                        <a href="management">Management</a>
                    </div>
                </c:if>
            </c:when>
           
            
            <c:otherwise>
                <div class="content contactContent otherwise">
                    <table>
                        <tr>
                            <td class="msg">
                                <h3 style="margin: 0px; padding: 0px; padding-bottom: 18px;">Contact League Executive</h3>
                                <form method="post" action="contact">
                                    <table>
                                        <tr>
                                            <td width="100px">Your name: </td>
                                            <td>
                                                <c:if test="${role.roleID eq 2}">
                                                    ${user.contactID.firstName} ${user.contactID.lastName}
                                                </c:if>
                                                <c:if test="${role.roleID == null}">
                                                    <input type="text" name="nameEntered" value="${nameEntered}" placeholder="Jane Smith">
                                                </c:if>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>Your email: </td>
                                            <td>
                                                <c:if test="${role.roleID eq 2}">
                                                    ${user.email}
                                                </c:if>
                                                <c:if test="${role.roleID == null}">
                                                    <input type="text" name="emailEntered" value="${emailEntered}" placeholder="example@email.com">
                                                </c:if>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td colspan="2" style="line-height: 15px">&nbsp;</td>
                                        </tr>
                                        <tr>
                                            <td>Subject: </td>
                                            <td><input placeholder='e.g., "Questions about pricing"' type="text" name="subject" value="${message.subject}"></td>
                                        </tr>
                                        <tr>
                                            <td colspan="2"><textarea class="textareaClass" name="body" value="${message.body}" rows="10" cols="50" placeholder="Write your message here"></textarea></td>
                                        </tr>
                                        <tr>
                                            <td colspan="2" style="padding-top: 5px"><button type="submit" name="sendEmailButton" value="send">Send Email</button></td>
                                        </tr>
                                    </table>
                                    <input type="hidden" name="action" value="sendEmail">
                                </form>
                            </td>
                            
                            <td class="execContact">
                                <h4>Executive Contact Information</h4>
                                <table>
                                    <c:forEach var="admin" items="${admins}">
                                        <tr>
                                            <td width="200px"><b>${admin.execTitle}</b></td>
                                        </tr>
                                        <tr>
                                            <td>${admin.user.contactID.firstName} ${admin.user.contactID.lastName}</td>
                                        </tr>
                                        <tr>
                                            <td>${admin.user.contactID.email}</td>
                                        </tr>
                                        <tr>
                                            <td style="padding-bottom: 15px">${admin.user.contactID.phone}</td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </td>
                        </tr>
                    </table>
                </div>
                <c:if test="${messageSent == true}">
                    <div class="content contactContent otherwise">
                        <h4>Your email has been sent.</h4>
                        <p>A league executive will be in touch with you shortly.</p>
                    </div>
                </c:if>
            </c:otherwise>
        </c:choose>
       
        
        <script type="text/javascript">
            var contacts = document.getElementsByClassName("contact");
            var prevCount = 0;
            
            for (i = 0; i < contacts.length; i++) {
                if (i % 2 === 0) 
                    contacts[i].classList.add("one");
                else 
                    contacts[i].classList.add("two");
            }
            
            function display(thisDisplay) {
                if (document.getElementById(thisDisplay + "ID").style.display === "block")
                    hide(thisDisplay);
                else {
                    document.getElementById(thisDisplay + "ID").style.display = "block";
                    document.getElementById(thisDisplay + "B").innerHTML = "Confirm";
                    document.getElementById(thisDisplay + "B").style.background = "#CC3333";
                    document.getElementById(thisDisplay + "B").style.color = "white";
                    
                    document.getElementById(thisDisplay + "B").addEventListener("mouseover", function() {
                        document.getElementById(thisDisplay + "B").style.background = "#d99393";
                        document.getElementById(thisDisplay + "B").style.color = "black"});
                    
                    document.getElementById(thisDisplay + "B").addEventListener("mouseout", function() {
                        document.getElementById(thisDisplay + "B").style.background = "#CC3333";
                        document.getElementById(thisDisplay + "B").style.color = "white"});
                    
                }
            }
            
            function hide(thisDisplay) {
                document.getElementById(thisDisplay + "ID").style.display = "none";
               
                document.getElementById(thisDisplay + "B").innerHTML = "Select...";
                document.getElementById(thisDisplay + "Form").submit();
            }

            function selected(thisID) {
                if (document.getElementById(thisID + "ch").checked) 
                    document.getElementById(thisID + "ch").checked = "";
                else
                    document.getElementById(thisID + "ch").checked = "checked";
            }
            
            
            function count(thisContact) {
                var count = parseInt(document.getElementById("selectedCount").innerHTML);
                prevCount = count;
                
                if (document.getElementById(thisContact + "check").checked) 
                    count = count + 1;
                else 
                    count = count - 1;
                
                if (count === 0) 
                    document.getElementById("confirmContactBtn").style.display = "none";
                else if (prevCount === 0) {
                    document.getElementById("confirmContactBtn").style.display = "block";
                    document.getElementById("confirmContactBtn").style.position = "absolute";
                    document.getElementById("confirmContactBtn").style.bottom = "5.4px";
                    document.getElementById("confirmContactBtn").style.right = "5px";
                }
                
                document.getElementById("selectedCount").innerHTML = count;
            }
        </script>
        
    </body>
</html>
