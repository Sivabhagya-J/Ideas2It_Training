package com.ideas2it.employee.dao;

import java.util.List;

import com.ideas2it.employee.model.Employee;
import com.ideas2it.util.exception.EmployeeManagementSystemException;

/**
 * @description: Interface class that have the methods for CRUD operation
 * @author     : Sivabhagya Jawahar
 */
public interface EmployeeDAO {
	
	/**
	 * @description: Method to add employee details
	 * @author     : Sivabhagya Jawahar
	 * @param      : employee Employee
	 * @return     : generatedKey int 
	 * @throws EmployeeManagementSystemException 
	 */
	Employee addEmployee(Employee employee) throws EmployeeManagementSystemException;
    
    /**
	 * @description: Method to update employee details
	 * @author     : Sivabhagya Jawahar
	 * @param      : employee Employee
	 * @return     : true/false boolean
     * @throws EmployeeManagementSystemException 
	 */
    boolean updateEmployee(Employee employee) throws EmployeeManagementSystemException;
    
    /**
	 * @description: Method to delete employee details
	 * @author     : Sivabhagya Jawahar
	 * @param      : empId String
	 * @return     : true/false boolean
     * @throws EmployeeManagementSystemException 
	 */
    boolean deleteEmployee(String empId) throws EmployeeManagementSystemException;
    
    /**
	 * @description: Method to restore employee
	 * @author     : Sivabhagya Jawahar
	 * @param      : empId String
	 * @return     : true/false boolean
     * @throws EmployeeManagementSystemException 
	 */
    boolean restoreEmployee(String empId) throws EmployeeManagementSystemException;
    
    /**
	 * @description: Method to view employee details by id
	 * @author     : Sivabhagya Jawahar
	 * @param      : empId String
	 * @return     : employee Employee
	 * @throws EmployeeManagementSystemException 
	 */
    Employee getEmployeeById(String empId) throws EmployeeManagementSystemException;
    
    /**
	 * @description: Method to get all employee by status
	 * @author     : Sivabhagya Jawahar
	 * @param      : includeDeleted boolean
	 * @return     : employeeList List<Employee>
	 * @throws EmployeeManagementSystemException 
	 */
    List<Employee> getEmployeeByStatus(String status) throws EmployeeManagementSystemException;
    
    /**
	 * @description: Method to get employee details by reg no
	 * @author     : Sivabhagya Jawahar
	 * @param      : regNo String
	 * @param      : status String
	 * @return     : List<Employee>
	 * @throws EmployeeManagementSystemException 
	 */
    List<Employee> getEmployeeByRegNo(String regNo, String status) throws EmployeeManagementSystemException;
    
}
