<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Show All Projects</title>
<script type="text/javascript">
    function changeStatus(){
    	var dropdown = document.getElementById("statusId");
    	var link = document.createElement("a");
    	link.href = "ProjectController?action=listProject";
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
    
	function searchProject(e){
		setStatusParam(e);
    	e.href += "&projectId=" + document.getElementById('searchBox').value;
	}
</script>
</head>
<body>
	<div style="margin:10px 0">
    	<select id="statusId" onchange='changeStatus()'>
  			<option value="active" <c:if test="${status == null || !status.equals('inactive')}"> selected </c:if>>Active Projects</option>
  			<option value="inactive" <c:if test="${status != null && status.equals('inactive')}"> selected </c:if>>InActive Projects</option>
		</select>
    </div>
    <div style="margin-bottom:10px">
    	 <input type="text" placeholder="Enter Project Id here.." id="searchBox" value="${searchValue}" />
    	 <a href="ProjectController?action=listProject" onclick="searchProject(this)">Search</a>
         <a href="ProjectController?action=listProject" onclick="setStatusParam(this)" style="margin-left:10px">Clear Search</a>
    </div>
    <table border=1>
        <thead>
            <tr>
            	<th>Id</th>
                <th>Project Name</th>
                <th>Project Id</th>
                <th>Domain</th>
                <th>Technology</th>
                <th>Members</th>
                <th colspan=2>Action</th>
            </tr>
        </thead>
        <tbody>
            <c:choose>
    			<c:when test="${projectList != null && projectList.size() > 0}">
        			<c:forEach items="${projectList}" var="project">
		                <tr>
		                	<td><c:out value="${project.id}" /></td>
		                	<td><c:out value="${project.name}" /></td>
		                    <td><c:out value="${project.projectId}" /></td>
		                    <td><c:out value="${project.domain}" /></td>
		                    <td><c:out value="${project.technology}" /></td>
		                    <td>
		                    <c:choose>
								<c:when test = "${project.employeeList != null && !project.employeeList.isEmpty()}">
									<c:forEach items="${project.employeeList}" var="member">
										<c:if test = "${member.isDeleted == false}">
						                    <c:out value="${member.name}" /><br>
						                </c:if>
									</c:forEach>
								</c:when>
								<c:otherwise>
									No Members Assigned
								</c:otherwise>
							</c:choose>
							</td>
		                    <c:if test = "${project.isDeleted == true}">
		                    <td><a href="ProjectController?action=restore&id=<c:out value="${project.id}"/>">Restore</a></td>
		                    </c:if>
		                    <c:if test = "${project.isDeleted == false}">
		                    <td><a href="ProjectController?action=edit&id=<c:out value="${project.id}"/>">Update</a></td>
		                    <td><a href="ProjectController?action=delete&id=<c:out value="${project.id}"/>">Delete</a></td>
		                    </c:if>
		                </tr>
            		</c:forEach>
    			</c:when>
    			<c:otherwise>
        			<tr><td colspan=6 style="text-align:center">No Results Found</td></tr>
    			</c:otherwise>
			</c:choose>
        </tbody>
    </table>
    <p><a href="ProjectController?action=insert">Add Project</a></p>
     <p><a href="index.jsp">Back to Home</a></p>
</body>
</html>