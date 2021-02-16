package main.java.com.ideas2it.employee.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import main.java.com.ideas2it.connection.HibernateUtil;
import main.java.com.ideas2it.employee.model.Employee;
import main.java.com.ideas2it.util.common.Constants;
import main.java.com.ideas2it.util.exception.EmployeeManagementSystemException;
import main.java.com.ideas2it.util.logger.EmployeeManagementSystemLogger;

/**
 * @description: class that implements EmployeeDAO for CRUD operations
 * @author     : Sivabhagya Jawahar
 */
public class EmployeeDAOImpl implements EmployeeDAO {	
		
	/**
	 * @description: Method to add employee details
	 * @author     : Sivabhagya Jawahar
	 * @param      : employee Employee
	 * @return     : employee 
	 */
	public Employee addEmployee(Employee employee) throws EmployeeManagementSystemException{
	    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			int id = (Integer) session.save(employee);
			employee.setEmpId(id);
			transaction.commit();
			return employee;
		} catch (HibernateException ex) {
			if (transaction != null)
				transaction.rollback();
			EmployeeManagementSystemLogger.error(Constants.EXCEPTION_MESSAGE_ADD, ex);
			throw new EmployeeManagementSystemException(Constants.EXCEPTION_MESSAGE_ADD);
		} finally {
			session.close();
		}
	}
	
	/**
	 * @description: Method to update employee details
	 * @author     : Sivabhagya Jawahar
	 * @param      : employee Employee
	 * @return     : true/false boolean
	 * @throws EmployeeManagementSystemException 
	 */
	public boolean updateEmployee(Employee employee) throws EmployeeManagementSystemException{
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.saveOrUpdate(employee);
			transaction.commit(); 
			return true;
		} catch (HibernateException ex) {
			if (transaction != null)
				transaction.rollback();
			EmployeeManagementSystemLogger.error(Constants.EXCEPTION_MESSAGE_UPDATE, ex);
			throw new EmployeeManagementSystemException(Constants.EXCEPTION_MESSAGE_UPDATE);
		} finally {
			session.close();
		}
	}
	
	/**
	 * @description: Method to delete employee details
	 * @author     : Sivabhagya Jawahar
	 * @param      : empId String
	 * @return     : true/false boolean
	 * @throws EmployeeManagementSystemException 
	 */
	public boolean deleteEmployee(String empId) throws EmployeeManagementSystemException {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			Query<?> query = session.createQuery("update Employee set isDeleted = 1 where empId = :empId");  
			query.setParameter("empId", Integer.parseInt(empId));
			int status = query.executeUpdate();  
			if (status == 1) {
				return true;	
			}
			transaction.commit(); 
		} catch (HibernateException ex) {
			if (transaction != null)
				transaction.rollback();
			EmployeeManagementSystemLogger.error(Constants.EXCEPTION_MESSAGE_DELETE, ex);
			throw new EmployeeManagementSystemException(Constants.EXCEPTION_MESSAGE_DELETE);
		} finally {
			session.close();
		}
		return false;
	}
	
	/**
	 * @description: Method to restore employee details
	 * @author     : Sivabhagya Jawahar
	 * @param      : empId String
	 * @return     : true/false boolean
	 * @throws EmployeeManagementSystemException 
	 */
	public boolean restoreEmployee(String empId) throws EmployeeManagementSystemException {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			Query<?> query = session.createQuery("update Employee set isDeleted = 0 where empId = :empId");  
			query.setParameter("empId", Integer.parseInt(empId));
			int status = query.executeUpdate();  
			if (status == 1) {
				return true;	
			}
			transaction.commit(); 
		} catch (HibernateException ex) {
			if (transaction != null)
				transaction.rollback();
			ex.printStackTrace();
			EmployeeManagementSystemLogger.error(Constants.EXCEPTION_MESSAGE_RESTORE, ex);
			throw new EmployeeManagementSystemException(Constants.EXCEPTION_MESSAGE_RESTORE);
		} finally {
			session.close();
		}
		return false;
	}
	
	/**
	 * @description: Method to get employee details by id
	 * @author     : Sivabhagya Jawahar
	 * @param      : empId String
	 * @return     : employee Employee
	 * @throws EmployeeManagementSystemException 
	 */
	public Employee getEmployeeById(String empId) throws EmployeeManagementSystemException {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			List<Employee> employeeList = session.createQuery("From Employee where isDeleted=0 and empId = :empId").setParameter("empId", Integer.parseInt(empId)).getResultList();
			transaction.commit();
			return employeeList.get(0);
		} catch (HibernateException ex) {
			if (transaction != null)
				transaction.rollback();
			EmployeeManagementSystemLogger.error(Constants.EXCEPTION_MESSAGE_VIEWBY_ID, ex);
			throw new EmployeeManagementSystemException(Constants.EXCEPTION_MESSAGE_VIEWBY_ID);
		} finally {
			session.close();
		}	
	}
	
	/**
	 * @description: Method to get employee details by regNo
	 * @author     : Sivabhagya Jawahar
	 * @param      : regNo String
	 * @param      : status String
	 * @return     : List<Employee> employeeList
	 * @throws EmployeeManagementSystemException 
	 */
	public List<Employee> getEmployeeByRegNo(String regNo, String status) throws EmployeeManagementSystemException{
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			String queryString = "From Employee";
			if (status != null && status.equals("inactive")) {
				queryString += " WHERE isDeleted = 1";
			} else {
				queryString += " WHERE isDeleted = 0";
			}
			queryString += " and regNo = :regNo";
			List<Employee> employeeList = session.createQuery(queryString).setParameter("regNo", regNo).getResultList();
			transaction.commit();
			return employeeList;
		} catch (HibernateException ex) {
			if (transaction != null)
				transaction.rollback();
			EmployeeManagementSystemLogger.error(Constants.EXCEPTION_MESSAGE_VIEWBY_REGNO, ex);
			throw new EmployeeManagementSystemException(Constants.EXCEPTION_MESSAGE_VIEWBY_REGNO);
		} finally {
			session.close();
		}
	}

	/**
	 * @description: Method to get employee details by status
	 * @author     : Sivabhagya Jawahar
	 * @param      : status String
	 * @return     : List<Employee> employeeList
	 * @throws EmployeeManagementSystemException 
	 */
	public List<Employee> getEmployeeByStatus(String status) throws EmployeeManagementSystemException{
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			String queryString = "From Employee";
			if (status != null && status.equals("inactive")) {
				queryString += " WHERE isDeleted = 1";
			} else {
				queryString += " WHERE isDeleted = 0";
			}
			List<Employee> employeeList = session.createQuery(queryString).getResultList();
			transaction.commit();
			return employeeList;
		} catch (HibernateException ex) {
			if (transaction != null)
				transaction.rollback();
			EmployeeManagementSystemLogger.error(Constants.EXCEPTION_MESSAGE_VIEWBY_STATUS, ex);
			throw new EmployeeManagementSystemException(Constants.EXCEPTION_MESSAGE_VIEWBY_STATUS);
		} finally {
			session.close();
		}
	}		
}

