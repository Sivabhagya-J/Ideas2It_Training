package main.java.com.ideas2it.project.service;

import java.util.ArrayList;
import java.util.List;

import main.java.com.ideas2it.project.model.Project;
import main.java.com.ideas2it.util.exception.EmployeeManagementSystemException;
import main.java.com.ideas2it.employee.model.Employee;
import main.java.com.ideas2it.project.dao.ProjectDAO;
import main.java.com.ideas2it.project.dao.ProjectDAOImpl;

/**
 * @description: Project Service used to perform view, remove, add, update and restore operations in project data
 * @author     : Sivabhagya Jawahar
 */
@Service
@Transactional
public class ProjectService {
	
	@Autowired
	ProjectDAO projectDAO;
	
	/**
     * @description: Method to create a project 
     * @author     : Sivabhagya Jawahar
     * @param      : projectId
     * @param      : name
     * @param      : domain
     * @param      : technology
     * @return     : true/false boolean
	 * @throws EmployeeManagementSystemException 
     */  
	@Override
    @Transactional
    public Project addProject(Project project) throws EmployeeManagementSystemException{
        return projectDAO.addProject(project);
    }
    
    /**
     * @description: Method to update a project 
     * @author     : Sivabhagya Jawahar
     * @param      : id
     * @param      : projectId
     * @param      : name
     * @param      : domain
     * @param      : technology
     * @return     : true/false boolean
     * @throws EmployeeManagementSystemException 
     */  
	@Override
    @Transactional
    public boolean updateProject(Project project) throws EmployeeManagementSystemException{
    	return projectDAO.updateProject(project);	
    } 
    
    
    /**
     * @description: Method to delete the project
     * @author     : Sivabhagya Jawahar
     * @param      : id String 
     * @return     : true/false boolean
     * @throws EmployeeManagementSystemException 
     */ 
	@Override
    @Transactional
    public boolean deleteProject(String id) throws EmployeeManagementSystemException {
    	return projectDAO.deleteProject(id);
    }
    
    /**
     * @description: Method to restore the record
     * @author     : Sivabhagya Jawahar
     * @param      : id String 
     * @return     : true/false boolean
     * @throws EmployeeManagementSystemException 
     */ 
	@Override
    @Transactional
    public boolean restoreProject(String id) throws EmployeeManagementSystemException{
    	return projectDAO.restoreProject(id);
    }

    /**
     * @description: Method to get project by id
     * @author     : Sivabhagya Jawahar
     * @param      : id String 
     * @return     : project Project
     * @throws EmployeeManagementSystemException 
     */ 
	@Override
    @Transactional
    public Project getProjectById(String id) throws EmployeeManagementSystemException{
    	return projectDAO.getProjectById(id);
    }
    
    /**
     * @description: Method to get project by project id
     * @author     : Sivabhagya Jawahar
     * @param      : projectId String 
     * @param      : status String
     * @return     : projectList 
     * @throws EmployeeManagementSystemException 
     */ 
	@Override
    @Transactional
    public List<Project> getProjectByProjectId(String projectId, String status) throws EmployeeManagementSystemException{
    	return projectDAO.getProjectByProjectId(projectId, status);
    }
    
    /**
     * @description: Method to get all project by status
     * @param      : status
     * @return     : projectList List<Project>
     * @throws EmployeeManagementSystemException 
     */
	@Override
    @Transactional
    public List<Project> getProjectByStatus(String status) throws EmployeeManagementSystemException{
    	return projectDAO.getProjectByStatus(status);
    }
    
    /**
     * @description: Method to get employee by status
     * @author     : Sivabhagya Jawahar
     * @param      : status String
     * @return     : List<Employee> Employeelist
     * @throws EmployeeManagementSystemException 
     */ 
	@Override
    @Transactional
    public List<Employee> getEmployeeByStatus(String status) throws EmployeeManagementSystemException{
    	return projectDAO.getEmployeeByStatus(status);
    }
    
    /**
     * @description: Method to get the employee by id
     * @author     : Sivabhagya Jawahar
     * @param      : empId String 
     * @return     : Employee
     * @throws EmployeeManagementSystemException 
     */ 
	@Override
    @Transactional
    public Employee getEmployeeById(String empId) throws EmployeeManagementSystemException{
    	return projectDAO.getEmployeeById(empId);
    }
    
    /**
     * @description: Method to check project id is exist or not 
     * @author     : Sivabhagya Jawahar
     * @param      : projectId String 
     * @param      : projectList List<Project>
     * @return     : true/false boolean
     */ 
    public boolean isProjectIdExist(List<Project> projectList, String projectId) {
    	return projectList.stream().filter(o -> o.getProjectId().equals(projectId)).findFirst().isPresent();
    }
}