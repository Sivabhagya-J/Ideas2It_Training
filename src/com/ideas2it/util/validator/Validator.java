package com.ideas2it.util.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description: Class to validate the employee details  
 * @author     : Sivabhagya Jawahar
 */
public class Validator {

    /**
     * @description: Method to check whether the employee date of join is valid or not  
     * 
     * @return     : String - Validated employee date of join date
     */
    public static String checkValidDate(String dateofJoin) {
    	String datePattern = "^(1[0-2]|0[1-9])/(3[01]|[12][0-9]|0[1-9])/[0-9]{4}$";
    	Pattern pattern = Pattern.compile(datePattern);
    	Matcher matcher = pattern.matcher(dateofJoin);
    	boolean result = matcher.matches();
    	if (result == false) {
    		System.out.println("You Entered Invalid Date. Enter valid date (MM/DD/YYYY):\n");
    		dateofJoin = null;
    	}
    	return dateofJoin;
    }

    /**
     * @description: Method to check whether the employee phone number is valid or not  
     * 
     * @return     : Long - Validated employee phone number
     */
    public static long checkValidPhone(long phone) {
    	String mobilePattern = "^\\d{10}$";
    	String phoneNumber = Long.toString(phone);
    	Pattern pattern = Pattern.compile(mobilePattern);
    	Matcher matcher = pattern.matcher(phoneNumber);
    	boolean result = matcher.matches();
    	if (result == false) {
    		System.out.println("You Entered Invalid Phone Number. Enter valid phone number:\n");
    		phone = 0;
    	}
    	return phone;
    }

    /**
     * @description: Method to check whether the employee pincode is valid or not  
     * 
     * @return     : int - Validated employee pincode
     */
    public static int checkValidPincode(int pincode) {
    	String pincodePattern = "^[1-9]{1}[0-9]{5}$";
    	String pincodeNumber = Integer.toString(pincode);
    	Pattern pattern = Pattern.compile(pincodePattern);
    	Matcher matcher = pattern.matcher(pincodeNumber);
    	boolean result = matcher.matches();
    	if (result == false) {
    		System.out.println("You Entered Invalid Pincode. Enter valid pincode:\\n");
    		pincode = 0;   
    	}	    
        return pincode;
    }
}
