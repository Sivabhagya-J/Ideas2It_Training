<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add new project</title>
        <style>
		.text-field {
			margin:15px;
			height:30px;
		}
        </style>
    </head>
    <body>
        <form method="POST" action='EmployeeController' name="formAddEmployee">
            <% String action = request.getParameter("action");%>
            <% if (action.equalsIgnoreCase("edit")) {%>
            <input type="hidden" name="empId"
                               value="<c:out value="${employee.empId}" />" />
            <%}%>
            <p><b>Personal Information</b></p>
            Employee Name : <input class="text-field" type="text" name="name"
                               value="<c:out value="${employee.name}" />"  required /> <br />
            Register No : <input
                class="text-field" type="text" name="regNo"
                value="<c:out value="${employee.regNo}" />"  required /> <br /> 
            Date of Join : <input
                class="text-field" type="date" name="dateofJoin"
                value="<c:out value="${employee.dateofJoin}" />"  required /> <br /> 
            Role : <input
                class="text-field" type="text" name="role"
                value="<c:out value="${employee.role}" />"  required /> <br /> 
            Experience : <input
                class="text-field" type="number" name="experience"
                value="<c:out value="${employee.experience}" />"  required /> <br /> 
            Phone : <input
                class="text-field" type="number" name="phone"
                value="<c:out value="${employee.phone}" />" required/> <br /> 
            Salary : <input
                class="text-field" type="number" name="salary"
                value="<c:out value="${employee.salary}" />" required/> <br /> 
            <p><b>Primary Address</b></p>
            <% if (action.equalsIgnoreCase("edit")) {%>
            <input type="hidden" name="primaryAddressId"
                               value="<c:out value="${employee.addresslist[0].addressId}" />" />
            <%}%>
            Door No : <input
                class="text-field" type="text" name="primaryDoorNo"
                value="<c:out value="${employee.addresslist[0].doorNo}" />"  required /> <br /> 
            Street Name : <input
                class="text-field" type="text" name="primaryStreetName"
                value="<c:out value="${employee.addresslist[0].streetName}" />" required/> <br /> 
            City : <input
                class="text-field" type="text" name="primaryCity"
                value="<c:out value="${employee.addresslist[0].city}" />" required/> <br /> 
            State : <input
                class="text-field" type="text" name="primaryState"
                value="<c:out value="${employee.addresslist[0].state}" />" required/> <br /> 
            Pincode : <input
                class="text-field" type="text" name="primaryPincode"
                value="<c:out value="${employee.addresslist[0].pincode}" />" required/> <br /> 
            <p><b>Secondary Address</b></p>
             <% if (action.equalsIgnoreCase("edit")) {%>
            <input type="hidden" name="secondaryAddressId"
                               value="<c:out value="${employee.addresslist[1].addressId}" />" />
            <%}%>
            Door No : <input
                class="text-field" type="text" name="secondaryDoorNo"
                value="<c:out value="${employee.addresslist[1].doorNo}" />" required/> <br /> 
            Street Name : <input
                class="text-field" type="text" name="secondaryStreetName"
                value="<c:out value="${employee.addresslist[1].streetName}" />" required/> <br /> 
            City : <input
                class="text-field" type="text" name="secondaryCity"
                value="<c:out value="${employee.addresslist[1].city}" />" required/> <br /> 
            State : <input
                class="text-field" type="text" name="secondaryState"
                value="<c:out value="${employee.addresslist[1].state}" />" required/> <br /> 
            Pincode : <input
                class="text-field" type="text" name="secondaryPincode"
                value="<c:out value="${employee.addresslist[1].pincode}" />" required/> <br /> 
         	<c:choose>
				<c:when test = "${projectlist != null && !projectlist.isEmpty()}">
					Projects: <select name="projectList" multiple="multiple">
                    	<c:forEach var="item" items="${projectlist}">
                        	<option value="${item.id}" <c:if test="${item.getAssigned() == true}"> selected </c:if>><c:out value="${item.name}"/></option>
                    	</c:forEach>
            		</select> <br>
				</c:when>
				<c:otherwise>
					Projects: No Projects Available
				</c:otherwise>
			</c:choose>
			<div>
				<input  type="submit" value="Submit" />
            	<a href="EmployeeController?action=listEmployee">Cancel</a>
			</div>
        </form>
    </body>
</html>