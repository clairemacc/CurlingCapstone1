<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<table style="border-collapse: collapse">
    <tr>
        <th colspan="3" style="padding-bottom: 15px;">Team Information</th>
    </tr>
    <c:forEach var="player" items="${user.playerList}">
        <tr style="border-top: 1px solid black">
            <th colspan="2" class="ptn">${player.teamID.leagueID.weekday} (${player.teamID.leagueID.leagueID})</th>
        </tr>
        <tr style="background: #E6E6E6;">
            <td style="width: 110px"><b>Team name: </b></td>
            <td>${player.teamID.teamName}</td>
        </tr>
        <tr style="background: #F3F3F3;">
            <td><b>Your position: </b></td>
            <td>${player.position.positionName}</td>
        </tr>
        <tr>
            <td colspan="2" style="padding-bottom: 15px; padding-top: 6px; font-size: 15px"><b>Team members</b><br>
                <table style="font-size: 13.5px">
                    <tr>
                        <td style="width: 170px"><b>Name</b></td>
                        <td><b>Position</b></td>    
                        <td><b>Phone number</b></td>
                        <td><b>Email</b></td>
                    </tr>
                    <c:forEach var="member" items="${player.teamID.playerList}">
                        <c:if test="${member.userID != player.userID}">
                            <tr>
                                <td>${member.userID.contactID.firstName} ${member.userID.contactID.lastName}</td>
                                <td>${member.position.positionName}</td>
                                <td>${member.userID.contactID.phone}</td>                                
                                <td>${member.userID.contactID.email}</td>
                            </tr>
                        </c:if>
                    </c:forEach>
                </table>
            </td>
        </tr>
    </c:forEach>
</table>

<style scoped>
    .myAccRight tr td {
        padding-right: 10px;
        padding-left: 10px;
        vertical-align: top;
    }
    
    .myAccRight tr th {
        text-align: left;
        font-size: 18px;
        color: #a38800;
    }

    .myAccRight .ptn {
        font-size: 16px;
        color: #002c91;
        padding-top: 8px;
        padding-bottom: 8px;
    }
 </style>