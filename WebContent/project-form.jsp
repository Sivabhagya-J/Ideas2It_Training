<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add new project</title>
    </head>
    <body>
        <form method="POST" action='ProjectController' name="formAddProject">
            <% String action = request.getParameter("action");%>
            <% if (action.equalsIgnoreCase("edit")) {%>
            <input type="hidden" name="id"
                               value="<c:out value="${project.id}" />" />
            <%}%>
            Project Name : 
            <input type="text" name="name" value="<c:out value="${project.name}" />" /> <br />
            Project Id : 
            <input type="text" name="projectId" value="<c:out value="${project.projectId}" />" /> <br /> 
            Domain : 
            <input type="text" name="domain" value="<c:out value="${project.domain}" />" /> <br /> 
            Technology : 
            <input type="text" name="technology" value="<c:out value="${project.technology}" />" /> <br /> 
            <c:choose>
				<c:when test = "${employeeList != null && !employeeList.isEmpty()}">
					Members: <select name="employeelist" multiple="multiple">
                    	<c:forEach var="item" items="${employeeList}">
                        	<option value="${item.empId}" <c:if test="${item.getAssigned() == true}"> selected </c:if>><c:out value="${item.name}"/></option>
                    	</c:forEach>
            		</select> <br>
				</c:when>
				<c:otherwise>
					Members: No Employees Available
				</c:otherwise>
			</c:choose>
			<div>
				<input  type="submit" value="Submit" />
            	<a href="ProjectController?action=listProject">Cancel</a>
			</div>
        </form>
    </body>
</html>