package com.ideas2it.project.model;

import java.util.List;

import com.ideas2it.employee.model.Employee;

/**
 * @description: Class to get and set employee project details  
 *
 * @author     : Sivabhagya Jawahar
 */
public class Project {
	private int id;
	private String projectId;
	private String name;
	private String domain;
	private String technology;
	private List<Employee> employeeList;
	private boolean isDeleted;
	private boolean isAssigned;
	
	/** Default Constructor */
	public Project() {
		
	}
	
	/** Parameterized constructor */
	public Project(String projectId, String name, String domain, String technology) {
		this.projectId = projectId;
		this.name = name;
		this.domain = domain;
		this.technology = technology;
	}
	
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDomain() {
		return domain;
	}
	
	public void setDomain(String domain) {
		this.domain = domain;
	}
	
	public String getTechnology() {
		return technology;
	}
	
	public void setTechnology(String technology) {
		this.technology = technology;
	}
	
	public List<Employee> getEmployeeList() {
		return employeeList;
	}

	public void setEmployeeList(List<Employee> employeeList) {
		this.employeeList = employeeList;
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public boolean getisDeleted() {
		return isDeleted;
	}

	public void setisDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	

	public boolean getAssigned() {
		return isAssigned;
	}

	public void setAssigned(boolean isAssigned) {
		this.isAssigned = isAssigned;
	}
	
	@Override
    public String toString() {
	return "\n Project Id : " + getProjectId() + "\n Project Name : " + getName() + "\n Project Domain : " 
            + getDomain() + "\n Technology : " + getTechnology() + "\n Employee List : " + getEmployeeList(); 
    }

}