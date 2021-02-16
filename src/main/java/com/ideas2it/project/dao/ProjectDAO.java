package main.java.com.ideas2it.project.dao;

import java.util.List;

import main.java.com.ideas2it.employee.model.Employee;
import main.java.com.ideas2it.project.model.Project;
import main.java.com.ideas2it.util.exception.EmployeeManagementSystemException;

/**
 * @description: Interface class that have the methods for CRUD operation
 * @author     : Sivabhagya Jawahar
 */
public interface ProjectDAO {
	
	/**
	 * @description: Method to add project details
	 * @author     : Sivabhagya Jawahar
	 * @param      : project
	 * @return     : true/false boolean
	 * @throws EmployeeManagementSystemException 
	 */
    Project addProject(Project project) throws EmployeeManagementSystemException;
    
    /**
	 * @description: Method to update project details
	 * @author     : Sivabhagya Jawahar
	 * @param      : project
	 * @return     : true/false boolean
     * @throws EmployeeManagementSystemException 
	 */
    boolean updateProject(Project project) throws EmployeeManagementSystemException;
    
    /**
	 * @description: Method to delete project details
	 * @author     : Sivabhagya Jawahar
	 * @param      : id String
	 * @return     : true/false boolean
     * @throws EmployeeManagementSystemException 
	 */
    boolean deleteProject(String id) throws EmployeeManagementSystemException;
    
    /**
	 * @description: Method to restore project details
	 * @author     : Sivabhagya Jawahar
	 * @param      : id String
	 * @return     : true/false boolean
     * @throws EmployeeManagementSystemException 
	 */
    boolean restoreProject(String id) throws EmployeeManagementSystemException;
    
    /**
	 * @description: Method to get project details by id
	 * @author     : Sivabhagya Jawahar
	 * @param      : id String
	 * @return     : project Project
     * @throws EmployeeManagementSystemException 
	 */
    Project getProjectById(String id) throws EmployeeManagementSystemException;
    
    /**
	 * @description: Method to get project details by project id
	 * @author     : Sivabhagya Jawahar
	 * @param      : projectId String
	 * @param      : status String
	 * @return     : projectList Project
     * @throws EmployeeManagementSystemException 
	 */
    List<Project> getProjectByProjectId(String projectId, String status) throws EmployeeManagementSystemException;
    
    /**
	 * @description: Method to get project details by status
	 * @author     : Sivabhagya Jawahar
	 * @param      : status String
	 * @return     : projectList List<Project>
     * @throws EmployeeManagementSystemException 
	 */
    List<Project> getProjectByStatus(String status) throws EmployeeManagementSystemException;
    
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
    Employee getEmployeeById(String empId) throws EmployeeManagementSystemException;
}
