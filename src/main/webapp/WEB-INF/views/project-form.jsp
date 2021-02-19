<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add new project</title>
    </head>
    <body>
    	<div align="center">
        	<h1>Project Form</h1>
        		<form:form action="save" method="post" modelAttribute="project">
			        <table>
			            <form:hidden path="id"/>
			            <tr>
			                <td>Project Name:</td>
			                <td><form:input path="name" /></td>
			            </tr>
			            <tr>
			                <td>Project Id:</td>
			                <td><form:input path="projectId" /></td>
			            </tr>
			            <tr>
			                <td>Domain:</td>
			                <td><form:input path="domain" /></td>
			            </tr>
			            <tr>
			                <td>Technology:</td>
			                <td><form:input path="technology" /></td>
			            </tr>
			            <tr>
			            <c:choose>
							<c:when test = "${employeeList != null && !employeeList.isEmpty()}">
							<td>Members:</td>
							<td><form:select path="employeeList" multiple="multiple">
			                    	<c:forEach var="item" items="${employeeList}">
			                        	<form:options value="${item.empId}" <c:if test="${item.getAssigned() == true}"> selected </c:if>><c:out value="${item.name}"/></form:options>
			                    	</c:forEach>
			            		</form:select> <br>
			            	</td>
							</c:when>
							<c:otherwise>
								Members: No Employees Available
							</c:otherwise>
						</c:choose>
			            <tr>
			                <td colspan="2" align="center"><input type="submit" value="Save"></td>
			                <td><a href="listProject">Cancel</a></td>
			            </tr>
			        </table>
	        	</form:form>
	    	</div>
   	 </body>
</html>

