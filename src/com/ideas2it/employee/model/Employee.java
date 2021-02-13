package com.ideas2it.employee.model;

import java.util.List;

import com.ideas2it.project.model.Project;

/**
 * @description: Class to get and set employee personal information  
 * @author     : Sivabhagya Jawahar
 */
public class Employee {
	private int empId;
    private String name;
    private String regNo;
    private String dateofJoin;
    private String role;
    private int experience;
    private long phone;
    private long salary;
    private List<Address> addresslist;
    private List<Project> projectlist;
    private boolean isDeleted;
    private boolean isAssigned;
	
    /** Default Constructor */
    public Employee() {
    }
    
    /** Parameterized constructor */
    public Employee(String name, String regNo, String dateofJoin, String role, int experience, long phone, long salary) {
        this.name = name;
        this.regNo = regNo; 
        this.dateofJoin = dateofJoin;
        this.role = role;
        this.experience = experience;
        this.phone = phone;
        this.salary = salary;
	}
    
    public int getEmpId() {
		return empId;
	}

	public void setEmpId(int id) {
		this.empId = id;
	}
	
    public String getName() {
    	return name;
    }

    public void setName(String name) {
    	this.name = name;
    }

    public String getRegNo() {
    	return regNo;
    }

    public void setRegNo(String regNo) {
    	this.regNo = regNo;
    }

    public String getDateofJoin() {
    	return dateofJoin;
    }

    public void setDateofJoin(String dateofJoin) {
    	this.dateofJoin = dateofJoin;
    }

    public String getRole() {
    	return role;
    }

    public void setRole(String role) {
    	this.role = role;
    }

    public int getExperience() {
    	return experience;
    }

    public void setExperience(int experience) {
    	this.experience = experience;
	}

    public long getPhone() {
    	return phone;
    }

    public void setPhone(long phone) {
    	this.phone = phone;
    }

    public long getSalary() {
    	return salary;
    }

    public void setSalary(long salary) {
    	this.salary = salary;
    }
    
    public List<Address> getAddresslist() {
		return addresslist;
	}

	public void setAddresslist(List<Address> addressList) {
		this.addresslist = addressList;
	}
	
	public List<Project> getProjectlist() {
		return projectlist;
	}

	public void setProjectlist(List<Project> projectlist) {
		this.projectlist = projectlist;
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
	return "\nEmployeeId: " + getEmpId() + "\n EmployeeName : " + getName() + "\n Employee Register Number : " + getRegNo() + "\n DateofJoin : " 
		+ getDateofJoin() + "\n Designation : " + getRole() + "\n Experience: " + getExperience() + "\n Phone Number: " + getPhone()  + "\n Salary: "
		+ getSalary() + "\n Address: " + getAddresslist();
    }

}

