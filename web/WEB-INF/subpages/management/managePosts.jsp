<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<table style="width: 100%; position: relative">
    <tr>
        <td style="font-size: 20px; color: #CC3333; padding-bottom: 10px;"><b>Manage News Posts</b></td>
    </tr>
    <tr>
        <td>
            <form method="post" action="management">
                <table class="manageAccountsTable teamsTable score" style="border-collapse: collapse">
                    <tr>
                        <th style="width: 80px">Post ID</th>
                        <th style="width: 120px;">Date</th>
                        <th style="width: 250px">Title</th>
                        <th style="width: 170px">Author</th> 
                        <th></th>
                    </tr>
                    <c:forEach var="news" items="${newsPosts}">
                        <tr>
                            <td>${news.postID}</td>
                            <td><fmt:formatDate type="date" value="${news.postDate}"/></td>                        
                            <td>${fn:replace(news.title, "\\'", "'")}</td>
                            <td>${news.userID.contactID.firstName} ${news.userID.contactID.lastName}</td>
                            <td>
                                <div id="edit${news.postID}">
                                    <button type="submit" onclick="displayPostEdit(this.value)" name="editPostButton" value="${news.postID}">Edit</button>
                                    <button type="button" onclick="displayPostDelete(this.value)" name="deletePostButton" value="${news.postID}">Delete</button>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
                <input type="hidden" name="postAction" value="editPost">
            </form>
            
            <c:if test="${editingPost == true}">
                <form method="post" action="management">
                    <div name="editNewsPost" class="editNewsPost" id="editNewsPost">
                        <table>
                            <tr>
                                <td><b>Title: </b>&emsp;<input type="text" name="postTitle" value="${thisPost.title}"></td>
                            </tr>
                            <tr>
                                <td><textarea class="textareaClass" name="body" value="${thisPost.body}" rows="10" cols="50" placeholder="Write your message here"></textarea></td>
                            </tr>
                        </table>
                        <button type="submit" name="saveNewsPost" id="realDeleteScoreButton">Save</button>&ensp;
                        <button type="button" name="discardPost" onclick="discard()">Discard</button>
                    </div>
                    <input type="hidden" name="scoreAction" value="deleteScore">
                </form>
            </c:if>
            
            <form method="post" action="management">
                <div name="deleteScore" class="deleteTeam" id="deleteThisScore">
                    <h4></h4>
                    <button type="submit" name="realDeleteScoreButton" id="realDeleteScoreButton">Delete</button>&ensp;
                    <button type="button" name="cancelDelete" onclick="closeDelete()">Cancel</button>
                </div>
                <input type="hidden" name="scoreAction" value="deleteScore">
            </form>
            
        </td>
    </tr>
</table>

<script type="text/javascript">
    var editingThis = document.getElementById("editingScore").innerHTML;
    if (editingThis === "")
        document.getElementById("editingScore").innerHTML;
    else {
        displayScoreEdit(editingThis);
    }
    
    function displayScoreEdit(gameID) {
        document.getElementById(gameID + "input").style.display = "block";
        document.getElementById(gameID + "txt").style.display = "none";
        
        for (i = 0; i < editbtns.length; i++) {
            editbtns[i].disabled = "disabled";
            deletebtns[i].disabled = "disabled";
        } 
        
        document.getElementById("edit" + gameID).style.display = "none";
        document.getElementById("save" + gameID).style.display = "block";
    }

    function displayScoreDelete(gameID) {
        document.getElementById("deleteThisScore").children[0].innerHTML = '';
        var text = 'Are you sure you want to permanently delete the score of the game \"' + gameID + '\"?';
        document.getElementById("deleteThisScore").children[0].innerHTML = text;
        document.getElementById("deleteThisScore").style.display = "block";
        document.getElementById("realDeleteScoreButton").value = gameID;
    }
    
    function discardScore(gameID) {
        document.getElementById("edit" + gameID).style.display = "block";
        document.getElementById("save" + gameID).style.display = "none";
        
        document.getElementById(gameID + "txt").style.display = "block";
        document.getElementById(gameID + "input").style.display = "none";
        
        for (i = 0; i < editbtns.length; i++) {
            editbtns[i].disabled = "";
            deletebtns[i].disabled = "";
        } 
        
        document.getElementById("errorRow").style.display = "none";
    }

    function closeDelete() {
        document.getElementById("deleteThisScore").style.display = "none";
    }
</script>

<style>
    .score button {
        
    }
    .score button:hover {
        font-weight: normal;
    }
    
    .score tr td {
        padding: 0px;
        padding-right: 20px;
        padding-top: 5px;
        padding-bottom: 5px;
    }
    .score tr th {
        padding: 0px;
        padding-right: 15px;
    }
</style>