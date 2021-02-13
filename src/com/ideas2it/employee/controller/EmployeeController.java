package com.ideas2it.employee.controller;

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

import com.ideas2it.employee.model.Address;
import com.ideas2it.employee.model.Employee;
import com.ideas2it.employee.service.EmployeeService;
import com.ideas2it.project.model.Project;
import com.ideas2it.util.common.Constants;
import com.ideas2it.util.exception.EmployeeManagementSystemException;
import com.ideas2it.util.logger.EmployeeManagementSystemLogger;

/**
 * @description: Provide the class necessary to create an controller class to communicate with user and the service 
 * @author     : Sivabhagya Jawahar
 */
public class EmployeeController extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private static String INSERT_OR_EDIT = "/employee-form.jsp";
    private static String LIST_EMPLOYEE = "/listemployee.jsp";
    
	EmployeeService employeeService = new EmployeeService();
    Scanner scanner = new Scanner(System.in);

    /**
     * @description: This method will get the request from the user to access the basic operations
     * @param      : request Request will be a url which holds the value with the parameter
     * @param      : response Response will be send back as a result
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String forward = "";
        String action = request.getParameter("action");
        switch(action) {
        	case "delete":
				try {
					forward = deleteAction(request);
				} catch (EmployeeManagementSystemException e) {
					EmployeeManagementSystemLogger.error(Constants.EXCEPTION_MESSAGE_DELETE, e);
				}
        		break;
        	case "restore":
				try {
					forward = restoreAction(request);
				} catch (EmployeeManagementSystemException e) {
					EmployeeManagementSystemLogger.error(Constants.EXCEPTION_MESSAGE_RESTORE, e);
				}
			    break;
        	case "edit":
				try {
					forward = editAction(request);
				} catch (EmployeeManagementSystemException e) {
					EmployeeManagementSystemLogger.error(Constants.EXCEPTION_MESSAGE_UPDATE, e);
				}
				break;
        	case "listEmployee":
				try {
					forward = listAction(request);
				} catch (EmployeeManagementSystemException e) {
					EmployeeManagementSystemLogger.error(Constants.EXCEPTION_MESSAGE_VIEWBY_STATUS, e);
				}
				break;
        	default:
				try {
					forward = createAction(request);
				} catch (EmployeeManagementSystemException e) {
					EmployeeManagementSystemLogger.error(Constants.EXCEPTION_MESSAGE_ADD, e);
				}
        }
        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }
    
    /**
     * @description: This method will get the request from the user to perform delete operation
     * @param request delete
     * @return forward
     * @throws EmployeeManagementSystemException 
     */
    private String deleteAction(HttpServletRequest request) throws EmployeeManagementSystemException {
    	String forward = "";
    	String empId = request.getParameter("empId");
    	if(deleteEmployee(empId) == true ) {
    		forward = LIST_EMPLOYEE;
            request.setAttribute("employeeList", getEmployeeByStatus("active"));
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
    	String empId = request.getParameter("empId");
    	if (restoreEmployee(empId) == true ) {
    		forward = LIST_EMPLOYEE;
            request.setAttribute("employeeList", getEmployeeByStatus("inactive"));
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
        String empId = request.getParameter("empId");
        Employee employee = getEmployeeById(empId);
        request.setAttribute("employee", employee);
        List<Project> projectList = getAllActiveProject();
        if (projectList != null && !projectList.isEmpty()) {
        	for(Project project : projectList) {
        		if (project.getEmployeeList() != null && !project.getEmployeeList().isEmpty() && 
        				project.getEmployeeList().stream().filter(o -> (o.getEmpId() == employee.getEmpId())).findFirst().isPresent()) {
        			project.setAssigned(true);
        		}
        	}
        }
        request.setAttribute("projectlist", projectList);
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
    	String forward = LIST_EMPLOYEE;
        String status = request.getParameter("status");
        String regNo = request.getParameter("regNo");
        if (regNo != null && !regNo.isEmpty()) {
        	request.setAttribute("employeeList", getEmployeeByRegNo(regNo, status)); 
        	request.setAttribute("searchValue", regNo);
        } else {
        	request.setAttribute("employeeList", getEmployeeByStatus(status)); 
        }
        request.setAttribute("status", status);
    	return forward;
    }
    
	/**
	 * @description: This method will get the request from the user to perform create operation
	 * @param request create
	 * @return forward
	 * @throws EmployeeManagementSystemException 
	 */
    private String createAction(HttpServletRequest request) throws EmployeeManagementSystemException {
    	String forward = INSERT_OR_EDIT;
        request.setAttribute("projectlist", getAllActiveProject());
    	return forward;
    }
    
    /**
     * @description: This method will get the request from the user to access the basic operations
     * @param      : request Request will be a url which holds the value with the parameter
     * @param      : response Response will be send back as a result
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String empId = request.getParameter("empId");
		String regNo = request.getParameter("regNo");
		String name = request.getParameter("name");
		String dateofJoin = request.getParameter("dateofJoin");
		String role = request.getParameter("role");
		int experience = Integer.parseInt(request.getParameter("experience"));
		long phone = Long.parseLong(request.getParameter("phone"));
		long salary = Long.parseLong(request.getParameter("salary"));
		String primaryAddressId = request.getParameter("primaryAddressId");
		String primaryDoorno = request.getParameter("primaryDoorNo");
		String primaryStreetName = request.getParameter("primaryStreetName");
		String primaryCity = request.getParameter("primaryCity");
		String primaryState = request.getParameter("primaryState");
		int primaryPincode = Integer.parseInt(request.getParameter("primaryPincode"));
		String secondaryAddressId = request.getParameter("secondaryAddressId");
		String secondaryDoorNo = request.getParameter("secondaryDoorNo");
		String secondaryStreetName = request.getParameter("secondaryStreetName");
		String secondaryCity = request.getParameter("secondaryCity");
		String secondaryState = request.getParameter("secondaryState");
		int secondaryPincode = Integer.parseInt(request.getParameter("secondaryPincode"));
		Address primaryAddress = getAddressObject(primaryAddressId, primaryDoorno, primaryStreetName, primaryCity, primaryState, primaryPincode);
		Address secondaryAddress = getAddressObject(secondaryAddressId, secondaryDoorNo, secondaryStreetName, secondaryCity, secondaryState, secondaryPincode);
		List<Project> projectList = new ArrayList<Project>();
		try {
			projectList = getSelectedProject(request.getParameterValues("projectList"));
		} catch (EmployeeManagementSystemException e1) {
			EmployeeManagementSystemLogger.error(Constants.EXCEPTION_MESSAGE_VIEWBY_ID_PROJECT, e1);
		}
		if (empId == null || empId.isEmpty()) {
			try {
				Employee employee = addEmployee(regNo, name, dateofJoin, role, experience, phone, salary, primaryAddress, secondaryAddress);
				if (employee != null && employee.getEmpId() > 0) {
					updateEmployee(employee.getEmpId(), regNo, name, dateofJoin, role, experience, phone, salary, primaryAddress, secondaryAddress, projectList);
				}
			} catch (EmployeeManagementSystemException e) {
				EmployeeManagementSystemLogger.error(Constants.EXCEPTION_MESSAGE_ADD, e);
			} catch (SQLException e) {
				EmployeeManagementSystemLogger.error(Constants.SQL_EXCEPTION_MESSAGE_ADD, e);
			}
		} else {
			try {
				updateEmployee(Integer.parseInt(empId), regNo, name, dateofJoin, role, experience, phone, salary, primaryAddress, secondaryAddress, projectList);
			} catch (EmployeeManagementSystemException e) {
				EmployeeManagementSystemLogger.error(Constants.EXCEPTION_MESSAGE_UPDATE, e);
			} catch (SQLException e) {
				EmployeeManagementSystemLogger.error(Constants.SQL_EXCEPTION_MESSAGE_UPDATE, e);
			} catch (NumberFormatException e) {
				EmployeeManagementSystemLogger.error(Constants.NUMBER_FORMAT_EXCEPTION_MESSAGE_UPDATE, e);
			}
		}
        RequestDispatcher view = request.getRequestDispatcher(LIST_EMPLOYEE);
        try {
			request.setAttribute("employeeList", getEmployeeByStatus("active"));
		} catch (EmployeeManagementSystemException e) {
			EmployeeManagementSystemLogger.error(Constants.EXCEPTION_MESSAGE_VIEWBY_STATUS, e);
		}
        request.setAttribute("status", "active");
        view.forward(request, response);
    }
    
    /**
     * @description: method to get selected projects for the employee
     * @param projectArray
     * @return projectList
     * @throws EmployeeManagementSystemException 
     */
    private List<Project> getSelectedProject(String[] projectArray) throws EmployeeManagementSystemException{
    	List<Project> projectList = new ArrayList<Project>();
		if (projectArray != null) {
			for (int index = 0; index < projectArray.length; index++) {
				Project project = getProjectById(projectArray[index]);
				if (project != null) {
					projectList.add(project);
				}
		    }
		}
		return projectList;
    }
    
    /**
 	 * @description: Method to invoke the employee service for create new employee 
     * @author     : Sivabhagya Jawahar
     * @param      : regNo String
     * @param      : String name
     * @param      : String dateofJoin
     * @param      : String role
     * @param      : int experience
     * @param      : Long phone
     * @param      : Long salary
     * @param      : Address primaryAddress
     * @param      : Address secondaryAddress
     * @return     : Employee employee
     * @throws SQLException 
     * @throws EmployeeManagementSystemException 
     */
    public Employee addEmployee(String regNo, String name, String dateofJoin, String role, int experience, Long phone, Long salary, Address primaryAddress, Address secondaryAddress) throws SQLException, EmployeeManagementSystemException{
        return employeeService.addEmployee(regNo, name, dateofJoin, role, experience, phone, salary, primaryAddress, secondaryAddress);
    }
    
    /**
     * @description: Method to invoke the employee service for get employee by RegNo 
     * @param regNo
     * @param status
     * @return employee
     * @throws EmployeeManagementSystemException 
     */
    public List<Employee> getEmployeeByRegNo(String regNo, String status) throws EmployeeManagementSystemException{
    	return employeeService.getEmployeeByRegNo(regNo, status);
    }
    
    /**
     * @description: Method to invoke the employee service for get project by id 
     * @param id
     * @return Project
     * @throws EmployeeManagementSystemException 
     */
    public Project getProjectById(String id) throws EmployeeManagementSystemException{
    	return employeeService.getProjectById(id);
    }
    
    /**
 	 * @description: Method to invoke the employee service to get address object
     * @author     : Sivabhagya Jawahar
     * @param      : doorNo String
     * @param      : streetName String
     * @param      : city String
     * @param      : state String
     * @param      : pincode int
     * @return     : Address
     */
    public Address getAddressObject(String addressId, String doorNo, String streetName, String city, String state, int pincode) {
        return employeeService.getAddressObject(addressId, doorNo, streetName, city, state, pincode);
	}

	/**
 	 * @description: Method to invoke the employee service for update the record
     * @author     : Sivabhagya Jawahar
     * @param      : Employee employee
     * @return     : true/false boolean
	 * @throws SQLException 
	 * @throws EmployeeManagementSystemException 
     */
    public boolean updateEmployee(int empId, String regNo, String name, String dateofJoin, String role, int experience, Long phone, Long salary, Address primaryAddress, Address secondaryAddress, List<Project> projectList) throws SQLException, EmployeeManagementSystemException{
        return employeeService.updateEmployee(empId, regNo, name, dateofJoin, role, experience, phone, salary, primaryAddress, secondaryAddress, projectList);
    }

    /**
 	 * @description: Method to invoke the employee service for delete the record
     * @author     : Sivabhagya Jawahar
     * @param      : empId String
     * @return     : true/false boolean
     * @throws EmployeeManagementSystemException 
     */
	public boolean deleteEmployee(String empId) throws EmployeeManagementSystemException{
		return employeeService.deleteEmployee(empId);
    }
	
	/**
 	 * @description: Method to invoke the employee service for restore the record
     * @author     : Sivabhagya Jawahar
     * @param      : empId String
     * @return     : true/false boolean
	 * @throws EmployeeManagementSystemException 
     */
	public boolean restoreEmployee(String empId) throws EmployeeManagementSystemException{
		return employeeService.restoreEmployee(empId);
    }
	
	/**
 	 * @description: Method to invoke the employee service for view the employee record by id
     * @author     : Sivabhagya Jawahar
     * @param      : regNo String
     * @return     : employee Employee
	 * @throws EmployeeManagementSystemException 
     */
	public Employee getEmployeeById(String empId) throws EmployeeManagementSystemException{
        return employeeService.getEmployeeById(empId);
    }
	
	/**
     * @description: Method to get all active project
     * @author     : Sivabhagya Jawahar
     * @return     : projectList List<Project>
	 * @throws EmployeeManagementSystemException 
     */ 
    public List<Project> getAllActiveProject() throws EmployeeManagementSystemException{
    	return employeeService.getAllActiveProject();
    }
    
    /**
     * @description: Method to get employee by status
     * @author     : Sivabhagya Jawahar
     * @return     : List<Employee> Employeelist
     * @throws EmployeeManagementSystemException 
     */ 
    public List<Employee> getEmployeeByStatus(String status) throws EmployeeManagementSystemException{
    	return employeeService.getEmployeeByStatus(status);
    }
    
}