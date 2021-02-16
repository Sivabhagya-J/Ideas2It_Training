package main.java.com.ideas2it.employee.model;

/**
 * @description: Class to get and set employee personal information  
 *
 * @author     : Sivabhagya Jawahar
 */

public class Address {
	private int addressId;
    private String doorNo;
    private String streetName;
    private String city;
    private String state;
    private int pincode;
    private Employee employee;
	
    /** Default Constructor */
    public Address() {
    }
    
    /** Parameterized constructor */
    public Address(String doorNo, String streetName, String city, String state, int pincode) {
        this.doorNo = doorNo;
        this.streetName = streetName;
        this.city = city;
        this.state = state;
        this.pincode = pincode;
    }  
    
    public int getAddressId() {
		return addressId;
	}

	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}
	
    public String getDoorNo() {
    	return doorNo;
    }

    public void setDoorNo(String doorNo) {
    	this.doorNo = doorNo;
    }

    public String getStreetName() {
    	return streetName;
    }

    public void setStreetName(String streetName) {
    	this.streetName = streetName;
    }

    public String getCity() {
    	return city;
    }

    public void setCity(String city) {
    	this.city = city;
    }

    public String getState() {
    	return state;
    }

    public void setState(String state) {
    	this.state = state;
    }

    public int getPincode() {
    	return pincode;
    }

    public void setPincode(int pincode) {
    	this.pincode = pincode;
    }
    

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
    @Override
    public String toString() {
    	return " \n DoorNo : " + getDoorNo() + " \n StreetName : " + getStreetName() + " \n City : " + getCity() + " \n State : " + getState() + " \n Pincode :" + getPincode();
    }

}
