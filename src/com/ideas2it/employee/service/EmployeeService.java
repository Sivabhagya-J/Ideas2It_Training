package com.ideas2it.employee.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ideas2it.employee.model.Address;
import com.ideas2it.employee.model.Employee;
import com.ideas2it.project.model.Project;
import com.ideas2it.project.service.ProjectService;
import com.ideas2it.util.exception.EmployeeManagementSystemException;
import com.ideas2it.employee.dao.EmployeeDAO;
import com.ideas2it.employee.dao.EmployeeDAOImpl;

/**
 * @description: EmployeeService used to perform view, remove, add, update and restore operations in employee data
 * @author     : Sivabhagya Jawahar
 */
public class EmployeeService {
    EmployeeDAO employeeDAO = new EmployeeDAOImpl();
    ProjectService projectService = new ProjectService();
    
    /**
     * @description: Method to create a new employee record
     * @param      : regNo String 
     * @param      : name String 
     * @param      : dateofJoin Date
     * @param      : projectId int
     * @param      : role String
     * @param      : experience int
     * @param      : phone long
     * @param      : salary long
     * @param      : Address primaryAddress
     * @param      : Address secondaryAddress
     * @author     : Sivabhagya Jawahar
     * @return     : employee Employee
     * @throws EmployeeManagementSystemException 
     */  
    public Employee addEmployee(String regNo, String name, String dateofJoin, String role, int experience, Long phone, Long salary,
    		                    Address primaryAddress, Address secondaryAddress) throws EmployeeManagementSystemException {
    	 Employee employee = new Employee(name, regNo, dateofJoin, role, experience, phone, salary);
         primaryAddress.setEmployee(employee);
         secondaryAddress.setEmployee(employee);
         List<Address> addressList = new ArrayList<Address> ();
    	 addressList.add(primaryAddress);
    	 addressList.add(secondaryAddress);
    	 employee.setAddresslist(addressList);
         return employeeDAO.addEmployee(employee);
    }
        
    /**
     * @description: Method to create and get a address object
     * @author     : Sivabhagya Jawahar
     * @param      : doorNo
     * @param      : streetName
     * @param      : city
     * @param      : state
     * @param      : pincode
     * @return     : Address
     */
    public Address getAddressObject(String addressId, String doorNo, String streetName, String city, String state, int pincode) {
    	Address address = new Address(doorNo, streetName, city, state, pincode);
    	if (addressId != null) {
    		address.setAddressId(Integer.parseInt(addressId));
    	}
    	return address;
	}
    
    /**
     * @description: Method to update a employee record
     * @author     : Sivabhagya Jawahar
     * @param      : regNo String 
     * @param      : name String 
     * @param      : dateofJoin Date
     * @param      : projectId int
     * @param      : role String
     * @param      : experience int
     * @param      : phone long
     * @param      : salary long
     * @param      : Address primaryAddress
     * @param      : Address secondaryAddress
     * @param      : List<Project> projectList
     * @return     : true/false boolean
     * @throws EmployeeManagementSystemException 
     */  
    public boolean updateEmployee(int empId, String regNo, String name, String dateofJoin, String role, int experience, Long phone, Long salary,
    		                      Address primaryAddress, Address secondaryAddress, List<Project> projectList) throws EmployeeManagementSystemException {
    	Employee employee = new Employee(name, regNo, dateofJoin, role, experience, phone, salary);
    	employee.setEmpId(empId);
        primaryAddress.setEmployee(employee);
        secondaryAddress.setEmployee(employee);
        List<Address> addressList = new ArrayList<Address> ();
   	 	addressList.add(primaryAddress);
   	 	addressList.add(secondaryAddress);
   	 	employee.setAddresslist(addressList);
   	 	employee.setProjectlist(setEmployeeInProject(projectList, employee));
		return employeeDAO.updateEmployee(employee);
    } 
    
    /**
     * @description: method to set employee in project list
     * @param projectList
     * @param employee
     * @return projectList
     */
    public List<Project> setEmployeeInProject(List<Project> projectList, Employee employee) {
    	for (Project project : projectList) {
    		List<Employee> tempEmployeeList = new ArrayList<>();
			if (project.getEmployeeList() != null && !project.getEmployeeList().isEmpty()) {
				for (Employee empObj : project.getEmployeeList()) {
					if (empObj != null) {
						tempEmployeeList.add(empObj);
					}
				}
			}
			tempEmployeeList.add(employee);
			project.setEmployeeList(tempEmployeeList);
		}
    	return projectList;
    }
    
    /**
     * @description: Method to delete the record
     * @author     : Sivabhagya Jawahar
     * @param      : empId String 
     * @return     : true/false boolean
     * @throws EmployeeManagementSystemException 
     */ 
    public boolean deleteEmployee(String empId) throws EmployeeManagementSystemException{
    	return employeeDAO.deleteEmployee(empId);
    }

    /**
     * @description: Method to restore the record
     * @author     : Sivabhagya Jawahar
     * @param      : empId String 
     * @return     : true/false boolean
     * @throws EmployeeManagementSystemException 
     */ 
    public boolean restoreEmployee(String empId) throws EmployeeManagementSystemException{
    	return employeeDAO.restoreEmployee(empId);
    }
    
    /**
     * @description: Method to get the employee by id
     * @author     : Sivabhagya Jawahar
     * @param      : empId String 
     * @return     : Employee
     * @throws EmployeeManagementSystemException 
     */ 
    public Employee getEmployeeById(String empId) throws EmployeeManagementSystemException{
    	return employeeDAO.getEmployeeById(empId);
    }
    
    /**
     * @description: Method to get the employee by regNo
     * @author     : Sivabhagya Jawahar
     * @param      : regNo String 
     * @param      : status String
     * @return     : Employee 
     * @throws EmployeeManagementSystemException 
     */ 
    public List<Employee> getEmployeeByRegNo(String regNo, String status) throws EmployeeManagementSystemException{
    	return employeeDAO.getEmployeeByRegNo(regNo, status);
    }
    
    /**
     * @description: Method to get employee by status
     * @author     : Sivabhagya Jawahar
     * @param      : status String
     * @return     : List<Employee> Employeelist
     * @throws EmployeeManagementSystemException 
     */ 
    public List<Employee> getEmployeeByStatus(String status) throws EmployeeManagementSystemException{
    	return employeeDAO.getEmployeeByStatus(status);
    }
    
    /**
     * @description: Method to get all active project
     * @author     : Sivabhagya Jawahar
     * @return     : projectList List<Project>
     * @throws EmployeeManagementSystemException 
     */ 
    public List<Project> getAllActiveProject() throws EmployeeManagementSystemException{
    	return projectService.getProjectByStatus("active");
    }
    
    /**
     * @description: Method to get the project by id
     * @author     : Sivabhagya Jawahar
     * @param      : id String 
     * @return     : Project
     * @throws EmployeeManagementSystemException 
     */
    public Project getProjectById(String id) throws EmployeeManagementSystemException{
    	return projectService.getProjectById(id);
    }
    
    /**
     * @description: Method to check valid project id 
     * @author     : Sivabhagya Jawahar
     * @param      : projectId String 
     * @param      : projectList List<Project>
     * @return     : true/false boolean
     */ 
    public boolean isProjectIdExist(List<Project> projectList, String projectId) {
    	return projectService.isProjectIdExist(projectList, projectId);
    }
    
    /**
     * @description: Method to check valid Reg no
     * @author     : Sivabhagya Jawahar
     * @param      : regNo String 
     * @param      : employeeList List<Employee>
     * @return     : true/false boolean
     */ 
    public boolean isRegNoExist(List<Employee> employeeList, String regNo) {
    	return employeeList.stream().filter(o -> o.getRegNo().equals(regNo)).findFirst().isPresent();
    }
    
    /**
     * @description: Method to check valid phone no
     * @author     : Sivabhagya Jawahar
     * @param      : phone Long 
     * @param      : employeeList List<Employee>
     * @return     : true/false boolean
     */ 
    public boolean isPhoneNoExist(List<Employee> employeeList, Long phone) {
    	return employeeList.stream().filter(o -> (o.getPhone() == phone)).findFirst().isPresent();
    }
}	