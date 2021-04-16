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
            <c:choose>
                <c:when test="${editingPost == true}">
                    <form method="post" action="management">
                        <div name="editNewsPost" class="editNewsPost" id="editNewsPost">
                            <table>
                                <tr>
                                    <td><b>Title: </b>&emsp;<input type="text" size="40" style="font-size: 16px;"name="title" value="${thisPost.title}"></td>
                                </tr>
                                <tr>
                                    <td><textarea class="textareaClass" name="body" value="${thisPost.body}" rows="13" cols="80" placeholder="Write your message here">${thisPost.body}</textarea></td>
                                </tr>
                                <c:if test="${message == 'nullFields'}">
                                    <tr>
                                        <td style="color: #CC3333"><b>Error: Fields cannot be null.</b></td>
                                    </tr>
                                </c:if>
                            </table>
                            <button type="submit" name="savePostButton" value="${thisPost.postID}">Save</button>&ensp;
                            <button type="submit" name="discard">Discard</button>
                        </div>
                        <input type="hidden" name="postAction" value="savePost">
                    </form>
                </c:when>
                <c:when test="${addingPost == true}">
                    <form method="post" action="management">
                        <div name="createNewsPost" class="editNewsPost" id="createNewsPost">
                            <table>
                                <tr>
                                    <td><b>Title: </b>&emsp;<input type="text" size="40" style="font-size: 16px;"name="title" value="${newTitle}"></td>
                                </tr>
                                <tr>
                                    <td><textarea class="textareaClass" name="body" value="${newBody}" rows="13" cols="80" placeholder="Write your new post here"></textarea></td>
                                </tr>
                                <c:if test="${message == 'nullFields'}">
                                    <tr>
                                        <td style="color: #CC3333"><b>Error: Fields cannot be null.</b></td>
                                    </tr>
                                </c:if>
                            </table>
                            <button type="submit" name="postAction" value="createNewsPost">Create post</button>&ensp;
                            <button type="submit" name="discard">Discard</button>
                        </div>
                    </form>
                </c:when>
                
                <c:otherwise>
                </td></tr>
                <tr>
                    <td class="addNewPost"><form method="get" action="management"><button type="submit" name="postAction" value="addPost">Write a new post</button></form></td>
                </tr>
                    <form method="get" action="management">
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
                                            <button type="submit" name="editPostButton" value="${news.postID}">Edit</button>
                                            <button type="button" onclick="displayPostDelete('${news.title}', this.value)" name="deletePostButton" value="${news.postID}">Delete</button>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                        <input type="hidden" name="postAction" value="editPost">
                    </form>
                </c:otherwise>
            </c:choose>
            
            <form method="post" action="management">
                <div name="deletePost" class="deleteTeam" id="deleteThisPost">
                    <h4></h4>
                    <button type="submit" name="realDeletePostButton" id="realDeletePostButton">Delete</button>&ensp;
                    <button type="button" name="cancelDelete" onclick="closeDelete()">Cancel</button>
                </div>
                <input type="hidden" name="postAction" value="deletePost">
            </form>
            
        </td>
    </tr>
</table>

<script type="text/javascript">
    function displayPostDelete(title, postID) {
        document.getElementById("deleteThisPost").children[0].innerHTML = '';
        var text = 'Are you sure you want to permanently delete the post \"' + title + '\"?';
        document.getElementById("deleteThisPost").children[0].innerHTML = text;
        document.getElementById("deleteThisPost").style.display = "block";
        document.getElementById("realDeletePostButton").value = postID;
    }
    
    function closeDelete() {
        document.getElementById("deleteThisPost").style.display = "none";
    }
</script>

<style>
    .addNewPost button {
        background: #596b94;
        color:white;
        border: none;
    }
    
    .addNewPost button:hover {
        background: #afbbc7;
        color: #000;
    }
</style>