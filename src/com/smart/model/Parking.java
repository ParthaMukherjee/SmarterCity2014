package com.smart.model;

public class Parking {
	
	private String avaiable,status,street,landmark,capacity;

	public String getAvaiable() {
		return avaiable;
	}

	public Parking(String avaiable, String status, String street,
			String landmark, String capacity) {
		super();
		this.avaiable = avaiable;
		this.status = status;
		this.street = street;
		this.landmark = landmark;
		this.capacity = capacity;
	}

	public void setAvaiable(String avaiable) {
		this.avaiable = avaiable;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getLandmark() {
		return landmark;
	}

	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}

	public String getCapacity() {
		return capacity;
	}

	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}

}
