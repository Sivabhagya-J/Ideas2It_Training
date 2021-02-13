<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Show All Employees</title>
<script type="text/javascript">
    function changeStatus(){
    	var dropdown = document.getElementById("statusId");
    	var link = document.createElement("a");
    	link.href = "EmployeeController?action=listEmployee";
    	if(dropdown.value == "inactive"){
    		link.href += "&status=inactive";
    	}
    	dropdown.options[dropdown.options.selectedIndex].selected = true;
    	link.click();
    }
    
    function setStatusParam(e){
    	var value = document.getElementById("statusId").value;
    	if(value == "inactive"){
    		e.href += "&status=inactive";
    	}
    }
    
	function searchEmployee(e){
		setStatusParam(e);
    	e.href += "&regNo=" + document.getElementById('searchBox').value;
	}
</script>
</head>
<body>
    <div style="margin:10px 0">
    	<select id="statusId" onchange='changeStatus()'>
  			<option value="active" <c:if test="${status == null || !status.equals('inactive')}"> selected </c:if>>Active Employees</option>
  			<option value="inactive" <c:if test="${status != null && status.equals('inactive')}"> selected </c:if>>InActive Employees</option>
		</select>
    </div>
    <div style="margin-bottom:10px">
    	 <input type="text" placeholder="Enter Employee Reg No here.." id="searchBox" value="${searchValue}" />
    	 <a href="EmployeeController?action=listEmployee" onclick="searchEmployee(this)">Search</a>
         <a href="EmployeeController?action=listEmployee" onclick="setStatusParam(this)" style="margin-left:10px">Clear Search</a>
    </div>
    <table border=1>
        <thead>
            <tr>
            	<th>Id</th>
                <th>Employee Name</th>
                <th>Register Number</th>
                <th>Date of Join</th>
                <th>Role</th>
                <th>Experience</th>
                <th>Phone</th>
                <th>Salary</th>
                <th>Primary Address</th>
                <th>Secondary Address</th>
                <th>Projects</th>
                <th colspan=2>Action</th>
            </tr>
        </thead>
        <tbody>
            <c:choose>
    			<c:when test="${employeeList != null && employeeList.size() > 0}">
        			<c:forEach items="${employeeList}" var="employee">
		                <tr>
		                	<td><c:out value="${employee.empId}" /></td>
							<td><c:out value="${employee.name}" /></td>
							<td><c:out value="${employee.regNo}" /></td>
							<td><c:out value="${employee.dateofJoin}" /></td>
							<td><c:out value="${employee.role}" /></td>
							<td><c:out value="${employee.experience}" /></td>
							<td><c:out value="${employee.phone}" /></td>
							<td><c:out value="${employee.salary}" /></td>
							<td><c:out value="${employee.addresslist[0].doorNo}" />, <c:out value="${employee.addresslist[0].streetName}" />, <c:out value="${employee.addresslist[0].city}" />, <c:out value="${employee.addresslist[0].state}" />, <c:out value="${employee.addresslist[0].pincode}" /></td>
							<td><c:out value="${employee.addresslist[1].doorNo}" />, <c:out value="${employee.addresslist[1].streetName}" />, <c:out value="${employee.addresslist[1].city}" />, <c:out value="${employee.addresslist[1].state}" />, <c:out value="${employee.addresslist[1].pincode}" /></td>
							<td>
							<c:choose>
								<c:when test = "${employee.projectlist != null && !employee.projectlist.isEmpty()}">
									<c:forEach items="${employee.projectlist}" var="empProject">
										<c:if test = "${empProject.isDeleted == false}">
						                    <c:out value="${empProject.name}" /><br>
						                </c:if>
									</c:forEach>
								</c:when>
								<c:otherwise>
									No Projects Assigned
								</c:otherwise>
							</c:choose>
							</td>
							<c:if test = "${employee.isDeleted == true}">
		                    <td><a href="EmployeeController?action=restore&empId=<c:out value="${employee.empId}"/>">Restore</a></td>
		                    </c:if>
		                    <c:if test = "${employee.isDeleted == false}">
		                    <td><a href="EmployeeController?action=edit&empId=<c:out value="${employee.empId}"/>">Update</a></td>
		                    <td><a href="EmployeeController?action=delete&empId=<c:out value="${employee.empId}"/>">Delete</a></td>
		                    </c:if>
		                </tr>
            		</c:forEach>
    			</c:when>
    			<c:otherwise>
        			<tr><td colspan=11 style="text-align:center">No Results Found</td></tr>
    			</c:otherwise>
			</c:choose>
        </tbody>
    </table>
    <p><a href="EmployeeController?action=insert">Add Employee</a></p>
    <p><a href="index.jsp">Back to Home</a></p>
</body>
</html>