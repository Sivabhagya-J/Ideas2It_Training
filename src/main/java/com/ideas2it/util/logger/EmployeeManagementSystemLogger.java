package main.java.com.ideas2it.util.logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @description: Class provides a common logger which makes use of Apache Log4j for logging 
 * @author     : Sivabhagya Jawahar
 */

public class EmployeeManagementSystemLogger {
	private static Logger logger = LogManager.getRootLogger();
	
	public static void debug(Object message) {
		logger.debug(message);
	}
	
	public static void info(Object message) {
		logger.info(message);
	}
	
	public static void warn(Object message) {
		logger.warn(message);
	}
	
	public static void error(Object message) {
		logger.error(message);
	}
	
	public static void fatal(Object message) {
		logger.fatal(message);
	}

	public static void error(Object message, Throwable cause) {
		logger.error(message, cause);
	}
}

