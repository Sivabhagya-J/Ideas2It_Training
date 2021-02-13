package com.ideas2it.project.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ideas2it.employee.model.Employee;
import com.ideas2it.project.model.Project;
import com.ideas2it.project.service.ProjectService;
import com.ideas2it.util.common.Constants;
import com.ideas2it.util.exception.EmployeeManagementSystemException;
import com.ideas2it.util.logger.EmployeeManagementSystemLogger;

/**
 * @description : project controller class to initiate the project service class
 * @author      : Sivabhagya Jawahar
 */
public class ProjectController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static String INSERT_OR_EDIT = "/project-form.jsp";
    private static String LIST_PROJECT = "/listproject.jsp";
    
	ProjectService projectService = new ProjectService();
	Scanner scanner = new Scanner(System.in);
	
	/**
     * @description: This method will get the request from the user to access the basic operations
     * @param      : request Request will be a url which holds the value with the parameter
     * @param      : response Response will be send back as a result
     */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward="";
        String action = request.getParameter("action");
        switch(action) {
	    	case "delete":
				try {
					forward = deleteAction(request);
				} catch (EmployeeManagementSystemException e1) {
					EmployeeManagementSystemLogger.error(Constants.EXCEPTION_MESSAGE_DELETE_PROJECT, e1);
				}
	    		break;
	    	case "restore":
				try {
					forward = restoreAction(request);
				} catch (EmployeeManagementSystemException e1) {
					EmployeeManagementSystemLogger.error(Constants.EXCEPTION_MESSAGE_RESTORE_PROJECT, e1);
				}
	    		break;
	    	case "edit":
				try {
					forward = editAction(request);
				} catch (EmployeeManagementSystemException e) {
					EmployeeManagementSystemLogger.error(Constants.EXCEPTION_MESSAGE_UPDATE_PROJECT, e);
				}
	    		break;
	    	case "listProject":
				try {
					forward = listAction(request);
				} catch (EmployeeManagementSystemException e1) {
					EmployeeManagementSystemLogger.error(Constants.EXCEPTION_MESSAGE_VIEWBY_STATUS_PROJECT, e1);
				}
	    		break;
	    	default:
				try {
					forward = createAction(request);
				} catch (EmployeeManagementSystemException e) {
					EmployeeManagementSystemLogger.error(Constants.EXCEPTION_MESSAGE_ADD_PROJECT, e);
				}
	    }
        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }
	
	/**
     * @description: This method will get the request from the user to perform create operation
     * @param request create
     * @return forward
	 * @throws EmployeeManagementSystemException 
     */
	private String createAction(HttpServletRequest request) throws EmployeeManagementSystemException {
    	String forward = INSERT_OR_EDIT;
        request.setAttribute("employeeList", getEmployeeByStatus("active"));
    	return forward;
    }
	
	/**
     * @description: This method will get the request from the user to perform delete operation
     * @param request delete
     * @return forward
	 * @throws EmployeeManagementSystemException 
     */
	private String deleteAction(HttpServletRequest request) throws EmployeeManagementSystemException {
    	String forward = "";
    	String id = request.getParameter("id");
    	if(deleteProject(id) == true ) {
    		forward = LIST_PROJECT;
            request.setAttribute("projectList", getProjectByStatus("active"));
            request.setAttribute("status", "active");
    	}
    	return forward;
    }
    
	/**
     * @description: This method will get the request from the user to perform restore operation
     * @param request restore
     * @return forward
	 * @throws EmployeeManagementSystemException 
     */
    private String restoreAction(HttpServletRequest request) throws EmployeeManagementSystemException {
    	String forward = "";
    	String id = request.getParameter("id");
    	if (restoreProject(id) == true ) {
    		forward = LIST_PROJECT;
            request.setAttribute("projectList", getProjectByStatus("inactive"));
            request.setAttribute("status", "inactive");
    	}
    	return forward;
    }
    
    /**
     * @description: This method will get the request from the user to perform edit operation
     * @param request edit
     * @return forward
     * @throws EmployeeManagementSystemException 
     */
    private String editAction(HttpServletRequest request) throws EmployeeManagementSystemException {
    	String forward = INSERT_OR_EDIT;
        String id = request.getParameter("id");
        Project project = getProjectById(id);
        request.setAttribute("project", project);
        List<Employee> employeeList = getEmployeeByStatus("active");
        if (employeeList != null && !employeeList.isEmpty()) {
        	for(Employee employee : employeeList) {
        		if (employee.getProjectlist() != null && !employee.getProjectlist().isEmpty() && 
        				employee.getProjectlist().stream().filter(o -> (o.getId() == project.getId())).findFirst().isPresent()) {
        			employee.setAssigned(true);
        		}
        	}
        }
        request.setAttribute("employeeList", employeeList);
        request.setAttribute("status", "active");
    	return forward;
    }
    
    /**
     * @description: This method will get the request from the user to perform list operation
     * @param request list
     * @return forward
     * @throws EmployeeManagementSystemException 
     */
    private String listAction(HttpServletRequest request) throws EmployeeManagementSystemException {
    	String forward = LIST_PROJECT;
        String status = request.getParameter("status");
        String projectId = request.getParameter("projectId");
        if (projectId != null && !projectId.isEmpty()) {
        	request.setAttribute("projectList", getProjectByProjectId(projectId, status)); 
        	request.setAttribute("searchValue", projectId);
        } else {
        	request.setAttribute("projectList", getProjectByStatus(status)); 
        }
        request.setAttribute("status", status);
    	return forward;
    }
	
    /**
     * @description: This method will get the request from the user to access the basic operations
     * @param      : request Request will be a url which holds the value with the parameter
     * @param      : response Response will be send back as a result
     */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		String projectId = request.getParameter("projectId");
		String name = request.getParameter("name");
		String domain = request.getParameter("domain");
		String technology = request.getParameter("technology");
		List<Employee> employeeList = new ArrayList<Employee>();
		try {
			employeeList = getSelectedEmployee(request.getParameterValues("employeelist"));
		} catch (EmployeeManagementSystemException e) {
			e.printStackTrace();
		}
		if (id == null || id.isEmpty()) {
			try {
				Project project = addProject(projectId, name, domain, technology);
				if (project != null && project.getId() > 0) {
					try {
						updateProject(project.getId(), projectId, name, domain, technology, employeeList);
					} catch (EmployeeManagementSystemException e) {
						EmployeeManagementSystemLogger.error(Constants.EXCEPTION_MESSAGE_UPDATE_PROJECT, e);
					}
				}
			} catch (EmployeeManagementSystemException e) {
				EmployeeManagementSystemLogger.error(Constants.EXCEPTION_MESSAGE_ADD_PROJECT, e);
			}
		} else {
			try {
				updateProject(Integer.parseInt(id), projectId, name, domain, technology, employeeList);
			} catch (EmployeeManagementSystemException e) {
				EmployeeManagementSystemLogger.error(Constants.EXCEPTION_MESSAGE_UPDATE_PROJECT, e);
			}
		}
        RequestDispatcher view = request.getRequestDispatcher(LIST_PROJECT);
        try {
			request.setAttribute("projectList", getProjectByStatus("active"));
		} catch (EmployeeManagementSystemException e) {
			EmployeeManagementSystemLogger.error(Constants.EXCEPTION_MESSAGE_VIEWBY_STATUS, e);
		}
        request.setAttribute("status", "active");
        view.forward(request, response);
    }
	
	/**
 	 * @description: Method to invoke the project service for create new project record
 	 * @param      : projectId
     * @param      : name 
     * @param      : domain
     * @param      : technology
 	 * @return     : true/false boolean
     * @author     : Sivabhagya Jawahar
	 * @throws EmployeeManagementSystemException 
     */
	public Project addProject(String projectId, String name, String domain, String technology) throws EmployeeManagementSystemException{
		return projectService.addProject(projectId, name, domain, technology);
	}
	
	/**
 	 * @description: Method to invoke the project service for update the record
 	 * @author     : Sivabhagya Jawahar
 	 * @param      : id String
 	 * @param      : projectId
     * @param      : name 
     * @param      : domain
     * @param      : technology
 	 * @return     : true/false boolean
	 * @throws EmployeeManagementSystemException 
     */
    public boolean updateProject(int id, String projectId, String name, String domain, String technology, List<Employee> employeeList) throws EmployeeManagementSystemException{
	    return projectService.updateProject(id, projectId, name, domain, technology, employeeList);
    }
    
    /**
 	 * @description: Method to invoke the project service for delete the record
 	 * @author     : Sivabhagya Jawahar
 	 * @param      : id String
 	 * @return     : true/false boolean
     * @throws EmployeeManagementSystemException 
     * @throws SQLException 
     */
	public boolean deleteProject(String id) throws EmployeeManagementSystemException{
		return projectService.deleteProject(id);
    }
	
	/**
 	 * @description: Method to invoke the project service for restore the record
 	 * @author     : Sivabhagya Jawahar
 	 * @param      : id String
 	 * @return     : true/false boolean
	 * @throws EmployeeManagementSystemException 
     * @throws SQLException 
     */
	public boolean restoreProject(String id) throws EmployeeManagementSystemException{
		return projectService.restoreProject(id);
    }
	
	/**
 	 * @description: Method to invoke the project service to get the project by id
 	 * @author     : Sivabhagya Jawahar
 	 * @param      : id String
     * @return     : project Project
	 * @throws EmployeeManagementSystemException 
	 * @throws SQLException 
     */
	public Project getProjectById(String id) throws EmployeeManagementSystemException {
		return projectService.getProjectById(id);
    }
	
	/**
	 * @description: Method to invoke the project service to get the project by project id
	 * @param      : projectId String
	 * @param      : status String
	 * @return     : projectList
	 * @throws EmployeeManagementSystemException 
	 */
	public List<Project> getProjectByProjectId(String projectId, String status) throws EmployeeManagementSystemException{
    	return projectService.getProjectByProjectId(projectId, status);
    }
	
	/**
	 * @description: Method to invoke the project service to get the project by status
	 * @param      : status String
	 * @return     : projectList List<Project>
	 * @throws EmployeeManagementSystemException 
	 */
	public List<Project> getProjectByStatus(String status) throws EmployeeManagementSystemException{
    	return projectService.getProjectByStatus(status);
    }
	
	/**
     * @description: Method to get the employee by id
     * @author     : Sivabhagya Jawahar
     * @param      : empId String 
     * @return     : Employee
     * @throws EmployeeManagementSystemException 
     */ 
    public Employee getEmployeeById(String empId) throws EmployeeManagementSystemException{
    	return projectService.getEmployeeById(empId);
    }
	
	/**
     * @description: Method to get employee by status
     * @author     : Sivabhagya Jawahar
     * @param      : status String
     * @return     : List<Employee> Employeelist
     * @throws EmployeeManagementSystemException 
     */ 
    public List<Employee> getEmployeeByStatus(String status) throws EmployeeManagementSystemException{
    	return projectService.getEmployeeByStatus(status);
    }
    
    /**
     * @description: method to get selected projects for the employee
     * @param projectArray
     * @return projectList
     * @throws EmployeeManagementSystemException 
     */
    private List<Employee> getSelectedEmployee(String[] employeeArray) throws EmployeeManagementSystemException{
    	List<Employee> employeeList = new ArrayList<Employee>();
		if (employeeArray != null) {
			for (int index = 0; index < employeeArray.length; index++) {
    			Employee employee = getEmployeeById(employeeArray[index]);
				if (employee != null) {
					employeeList.add(employee);
				}
		    }
		}
		return employeeList;
    }
}