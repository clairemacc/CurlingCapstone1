<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<span style="display:none" name="editing" id="editing">${editing}</span>
<span style="display: none" name="editThis" id="editThis">${editThis}</span>
<script>
    var prev = "null";
    var editbtns = document.getElementsByName("editButton");
</script>

<c:set var="msg" value=""/>
<c:if test="${message == 'nullFields'}"><c:set var="msg" value="Error: fields cannot be null."/></c:if>
        
<table style="border-collapse: collapse">
    <tr>
        <th colspan="3" style="padding-bottom: 12px; border-bottom: 1px solid gray">Personal Information</th>
    </tr>
    <tr class="row">
        <td style="padding-top: 10px"><b>First name</b></td>
        <td style="width: 150px; padding-top: 10px">
            <span id="firstNameTxt">${user.contactID.firstName}</span>
            <span id="firstNameInput" style="display: none"><input type="text" name="firstName" value="${user.contactID.firstName}"></span>
        </td>
        <td style="padding-top: 10px">
            <span id="firstNameEdit"><button type="button" name="editButton" onclick="showEdit('firstName')">Edit</button></span>
            <span id="firstNameSave" style="display: none"><button type="submit" name="saveFirstName">Save</button> 
                <button type="button" onclick="closeEdit('firstName')" name="cancel">Cancel</button><br>${msg}</span>
        </td>
    </tr>
    <tr class="row">
        <td><b>Last name</b></td>
        <td>
            <span id="lastNameTxt">${user.contactID.lastName}</span>
            <span id="lastNameInput" style="display: none"><input type="text" name="lastName" value="${user.contactID.lastName}"></span>
        </td>
        <td>
            <span id="lastNameEdit"><button type="button" name="editButton" onclick="showEdit('lastName')">Edit</button></span>
            <span id="lastNameSave" style="display: none"><button type="submit" name="saveLastName">Save</button>
            <button type="button" onclick="closeEdit('lastName')" name="cancel">Cancel</button><br>${msg}</span>
        </td>
    </tr>

    <tr class="row">
        <td><b>Address</b></td>
        <td>
            <span id="addressTxt">${user.contactID.address}</span>
            <span id="addressInput" style="display: none"><input type="text" name="address" value="${user.contactID.address}"></span>
        </td>
        <td>
            <span id="addressEdit"><button type="button" name="editButton" onclick="showEdit('address')">Edit</button></span>
            <span id="addressSave" style="display: none"><button type="submit" name="saveAddress">Save</button>
            <button type="button" onclick="closeEdit('address')" name="cancel">Cancel</button><br>${msg}</span>
        </td>
    </tr>
        
    <tr class="row">
        <td><b>City</b></td>
        <td>
            <span id="cityTxt">${user.contactID.city}</span>
            <span id="cityInput" style="display: none"><input type="text" name="city" value="${user.contactID.city}"></span>
        </td>
        <td>
            <span id="cityEdit"><button type="button" name="editButton" onclick="showEdit('city')">Edit</button></span>
            <span id="citySave" style="display: none; color: #CC3333"><button type="submit" name="saveCity">Save</button>
            <button type="button" onclick="closeEdit('city')" name="cancel">Cancel</button><br>${msg}</span>
        </td>
    </tr>

    <tr class="row">
        <td><b>Postal code</b></td>
        <td>
            <span id="postalTxt">${user.contactID.postal}</span>
            <span id="postalInput" style="display: none"><input type="text" name="postal" value="${user.contactID.postal}" pattern="[A-Za-z][0-9][A-Za-z][0-9][A-Za-z][0-9]" maxlength="6""></span>
        </td>
        <td>
            <span id="postalEdit"><button type="button" name="editButton" onclick="showEdit('postal')">Edit</button></span>
            <span id="postalSave" style="display: none"><button type="submit" name="savePostal">Save</button>
            <button type="button" onclick="closeEdit('postal')" name="cancel">Cancel</button><br>${msg}</span>
        </td>
    </tr>
    <tr class="row">
        <td><b>Phone number</b></td>
        <td>
            <span id="phoneTxt">${user.contactID.phone}</span>
            <span id="phoneInput" style="display: none"><input type="text" name="phone" value="${user.contactID.phone}"></span>
        </td>
        <td>
            <span id="phoneEdit"><button type="button" name="editButton" onclick="showEdit('phone')">Edit</button></span>
            <span id="phoneSave" style="display: none"><button type="submit" name="savePhone">Save</button>
            <button type="button" onclick="closeEdit('phone')" name="cancel">Cancel</button><br>${msg}</span>
        </td>
    </tr>
</table>

<script>
    var edit = document.getElementById("editThis").innerHTML;
    if (edit === "") 
        document.getElementById("editThis").innerHTML;
    else if (edit !== null || !edit.equals("")) 
        showEdit(edit);
    
    
    function showEdit(attr) {
        if (prev !== "null") 
            closeEdit(prev);
        
        document.getElementById(attr + "Txt").style.display = "none";
        document.getElementById(attr + "Edit").style.display = "none";
        document.getElementById(attr + "Input").style.display = "block";
        document.getElementById(attr + "Save").style.display = "block";
        
        for (i = 0; i < editbtns.length; i++) 
            editbtns[i].disabled = "disabled";
        prev = attr;
    }
    
    function closeEdit(attr) {
        document.getElementById(attr + "Input").style.display = "none";
        document.getElementById(attr + "Save").style.display = "none";
        document.getElementById(attr + "Txt").style.display = "block";
        document.getElementById(attr + "Edit").style.display = "block";  
        for (i = 0; i < editbtns.length; i++) 
            editbtns[i].disabled = "";
    }
</script>

<style scoped>
    .myAccRight tr td {
        padding-right: 20px;
        padding-left: 15px;
        vertical-align: top;
    }
    
    .myAccRight tr th {
        text-align: left;
        font-size: 18px;
        color: #a38800;
    }
    
    .myAccRight .row button {
        border: 0px;
        padding: 2px 6px 2px 6px;
        font-size: 14px;
        background: #afbbc7;
    }
    
    .myAccRight .row button:hover{
        background: #E6E6E6;
        padding: 2px 6px 2px 6px;
        color: black;
        font-weight: normal;
    }
    
    .row:hover {
        background: #ebf2fa;
    }

 </style>