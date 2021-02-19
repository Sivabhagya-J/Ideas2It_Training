package main.java.com.ideas2it.project.controller;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import main.java.com.ideas2it.employee.model.Employee;
import main.java.com.ideas2it.project.model.Project;
import main.java.com.ideas2it.project.service.ProjectService;
import main.java.com.ideas2it.util.common.Constants;
import main.java.com.ideas2it.util.exception.EmployeeManagementSystemException;
import main.java.com.ideas2it.util.logger.EmployeeManagementSystemLogger;

/**
 * @description : project controller class to initiate the project service class
 * @author      : Sivabhagya Jawahar
 */
public class ProjectController {
	
    @Autowired
	ProjectService projectService;
	
    /**
     * @description: This method will get the request from the user to perform create operation
     * @param model
     * @return project-form.jsp
     */
    @RequestMapping(value = "/insert", method = RequestMethod.GET)
    public ModelAndView createProject(ModelAndView model) {
        Project project = new Project();
        model.addObject("project", project);
        model.addObject("employeeList", projectService.getEmployeeByStatus("active"));
        model.setViewName("project-form");
        return model;
    }
    
    /**
     * @description: This method will get the request from the user and perform the save operation
     * @param project
     * @param request
     * @return listproject.jsp
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView saveProject(@ModelAttribute Project project, HttpServletRequest request) {
    	List<Employee> employeeList = new ArrayList<Employee>();
    	employeeList = getSelectedEmployee(request.getParameterValues("employeelist"));
        if (project.getId() == 0) {
           Project createdProject = projectService.addProject(project);	
           if(createdProject != null && createdProject.getId() > 0) {
        	   createdProject.setEmployeeList(setProjectInEmployee(employeeList, createdProject));
               projectService.updateProject(createdProject);
           }
        } else {
            projectService.updateProject(project);
        }
        model.addObject("status", "active");
        return new ModelAndView("redirect:/");
    }
    
    /**
     * @description: method to set project in employee list
     * @param employeeList
     * @param project
     * @return employeeList
     */
    public List<Employee> setProjectInEmployee(List<Employee> employeeList, Project project) {
    	for (Employee employee : employeeList) {
    		List<Project> tempProjectList = new ArrayList<>();
			if (employee.getProjectlist() != null && !employee.getProjectlist().isEmpty()) {
				for (Project proObj : employee.getProjectlist()) {
					if (proObj != null) {
						tempProjectList.add(proObj);
					}
				}
			}
			tempProjectList.add(project);
			employee.setProjectlist(tempProjectList);
		}
    	return employeeList;
    }
    
    /**
     * @description: This method will get the request from the user to perform delete operation
     * @param request
     * @return listproject.jsp
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ModelAndView deleteProject(HttpServletRequest request) {
        String id = request.getParameter("id");
        projectService.deleteProject(id);
        model.addObject("projectList", projectService.getProjectByStatus("active"));
        model.addObject("status", "active");
        return new ModelAndView("redirect:/");
    }
    
    /**
     * @description: This method will get the request from the user to perform restore operation
     * @param request
     * @return listproject.jsp
     */
    @RequestMapping(value = "/restore", method = RequestMethod.GET)
    public ModelAndView restoreProject(HttpServletRequest request) {
        String id = request.getParameter("id");
        projectService.restoreProject(id);
        model.addObject("projectList", projectService.getProjectByStatus("inactive"));
        model.addObject("status", "inactive");
        return new ModelAndView("redirect:/");
    }
    
    /**
     * @description: This method will get the request from the user to perform edit operation
     * @param request
     * @return project-form.jsp
     */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView editProject(HttpServletRequest request) {
    	String id = request.getParameter("id");
        Project project = projectService.getProjectById(id);
        ModelAndView model = new ModelAndView("project-form");
        model.addObject("project", project);
        List<Employee> employeeList = projectService.getEmployeeByStatus("active");
        if (employeeList != null && !employeeList.isEmpty()) {
        	for(Employee employee : employeeList) {
        		if (employee.getProjectlist() != null && !employee.getProjectlist().isEmpty() && 
        				employee.getProjectlist().stream().filter(o -> (o.getId() == project.getId())).findFirst().isPresent()) {
        			employee.setAssigned(true);
        		}
        	}
        }
        model.addObject("employeeList", employeeList);
        model.addObject("status", "active");
        return model;
    }
    
    /**
     * @description: This method will get the request from the user to perform list operation
     * @param model
     * @param request
     * @return listproject.jsp
     */
    @RequestMapping(value = "/")
    public ModelAndView listProject(ModelAndView model, HttpServletRequest request){
    	String status = request.getParameter("status");
        String projectId = request.getParameter("projectId");
        if (projectId != null && !projectId.isEmpty()) {
        	model.addObject("projectList", projectService.getProjectByProjectId(projectId, status)); 
        	model.addObject("searchValue", projectId);
        } else {
        	model.addObject("projectList", projectService.getProjectByStatus(status)); 
        }
        model.addObject("status", status);
        model.setViewName("listproject");
        return model;
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
    			Employee employee = projectService.getEmployeeById(employeeArray[index]);
				if (employee != null) {
					employeeList.add(employee);
				}
		    }
		}
		return employeeList;
    }
}