package main.java.com.ideas2it.project.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import main.java.com.ideas2it.connection.HibernateUtil;
import main.java.com.ideas2it.employee.model.Employee;
import main.java.com.ideas2it.project.model.Project;
import main.java.com.ideas2it.util.common.Constants;
import main.java.com.ideas2it.util.exception.EmployeeManagementSystemException;
import main.java.com.ideas2it.util.logger.EmployeeManagementSystemLogger;

/**
 * @description: class that implements ProjectDAO for CRUD operations
 * @author     : Sivabhagya Jawahar
 */
@Repository
public class ProjectDAOImpl implements ProjectDAO {
	
	@Autowired
    private SessionFactory sessionFactory;

	/**
	 * @description: Method to add project details
	 * @author     : Sivabhagya Jawahar
	 * @param      : project Project
	 * @return     : true/false boolean
	 * @throws EmployeeManagementSystemException 
	 */
	public Project addProject(Project project) throws EmployeeManagementSystemException{
		sessionFactory.getCurrentSession().saveOrUpdate(project);
	}
	
	/**
	 * @description: Method to update project details
	 * @author     : Sivabhagya Jawahar
	 * @param      : project Project
	 * @return     : true/false boolean
	 * @throws EmployeeManagementSystemException 
	 */
	public boolean updateProject(Project project) throws EmployeeManagementSystemException{
		sessionFactory.getCurrentSession().update(project);
	}
	
	/**
	 * @description: Method to delete project details
	 * @author     : Sivabhagya Jawahar
	 * @param      : id String
	 * @return     : true/false boolean
	 * @throws EmployeeManagementSystemException 
	 */
	public boolean deleteProject(String id) throws EmployeeManagementSystemException{
		Session session = this.sessionFactory.getCurrentSession();
		Query<?> query=session.createQuery("update Project set isDeleted=:is_deleted where id=:id");  
		query.setParameter("is_deleted",true);  
		query.setParameter("id",Integer.parseInt(id));
		int status=query.executeUpdate();  
		if (status == 1) {
			return true;	
		} else {
			return false;
		}
    }
	
	/**
	 * @description: Method to restore project details
	 * @author     : Sivabhagya Jawahar
	 * @param      : id String
	 * @return     : true/false boolean
	 * @throws EmployeeManagementSystemException 
	 */
	public boolean restoreProject(String id) throws EmployeeManagementSystemException{
		Session session = this.sessionFactory.getCurrentSession();
		Query<?> query=session.createQuery("update Project set isDeleted=:is_deleted where id=:id");  
		query.setParameter("is_deleted",false);  
		query.setParameter("id",Integer.parseInt(id));
		int status=query.executeUpdate();  
		if (status == 1) {
			return true;	
		} else {
			return false;
		}
	}
	
	/**
	 * @description: Method to get project details by id
	 * @author     : Sivabhagya Jawahar
	 * @param      : id String
	 * @return     : project Project
	 * @throws EmployeeManagementSystemException 
	 */
	public Project getProjectById(String id) throws EmployeeManagementSystemException{
		return (Project) this.sessionFactory.getCurrentSession().createQuery(
		    "From Project where isDeleted = 0 and id = :id").setParameter("id", Integer.parseInt(id)).getSingleResult();
	}
	
	/**
	 * @description: Method to get project by projectId
	 * @author     : Sivabhagya Jawahar
	 * @param      : projectId String
	 * @param      : status String
	 * @return     : projectList 
	 * @throws EmployeeManagementSystemException 
	 */
	public List<Project> getProjectByProjectId(String projectId, String status) throws EmployeeManagementSystemException{
		Session session = this.sessionFactory.getCurrentSession();
		String queryString = "From Project";
		if (status != null && status.equals("inactive")) {
			queryString += " WHERE isDeleted = 1";
		} else {
			queryString += " WHERE isDeleted = 0";
		}
		queryString += " and projectId = :projectId";
		List<Project> projectList = session.createQuery(queryString).setParameter("projectId", projectId).getResultList();
		return projectList;
	}

	/**
	 * @description: Method to get project by project status
	 * @author     : Sivabhagya Jawahar
	 * @param      : status String
	 * @return     : projectList List<Project>
	 * @throws EmployeeManagementSystemException 
	 */
	public List<Project> getProjectByStatus(String status) throws EmployeeManagementSystemException{
		Session session = this.sessionFactory.getCurrentSession();
		String queryString = "From Project";
		if (status != null && status.equals("inactive")) {
			queryString += " WHERE isDeleted = 1";
		} else {
			queryString += " WHERE isDeleted = 0";
		}
		List<Project> projectList = session.createQuery(queryString).getResultList();
		return projectList;
	}	
	
	/**
	 * @description: Method to get employee details by id
	 * @author     : Sivabhagya Jawahar
	 * @param      : empId String
	 * @return     : employee Employee
	 * @throws EmployeeManagementSystemException 
	 */
	public Employee getEmployeeById(String empId) throws EmployeeManagementSystemException {
		Session session = this.sessionFactory.getCurrentSession();
		List<Employee> employeeList = session.createQuery("From Employee where isDeleted=0 and empId = :empId").setParameter("empId", Integer.parseInt(empId)).getResultList();
		return employeeList.get(0);
	}
	
	/**
	 * @description: Method to get employee details by status
	 * @author     : Sivabhagya Jawahar
	 * @param      : status String
	 * @return     : List<Employee> employeeList
	 * @throws EmployeeManagementSystemException 
	 */
	public List<Employee> getEmployeeByStatus(String status) throws EmployeeManagementSystemException{
		Session session = this.sessionFactory.getCurrentSession();
		String queryString = "From Employee";
		if (status != null && status.equals("inactive")) {
			queryString += " WHERE isDeleted = 1";
		} else {
			queryString += " WHERE isDeleted = 0";
		}
		List<Employee> employeeList = session.createQuery(queryString).getResultList();
		return employeeList;
	}
}