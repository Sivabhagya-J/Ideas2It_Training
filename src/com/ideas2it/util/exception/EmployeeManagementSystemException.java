package com.ideas2it.util.exception;

public class EmployeeManagementSystemException extends Exception {
	
	private static final long serialVersionUID = 1L;

	/**
	 * @author      : Sivabhagya Jawahar
	 * @description : method that handles custom exception
	 * @param       : message
	 */
	public EmployeeManagementSystemException(String message) {
		super(message);
	}
}