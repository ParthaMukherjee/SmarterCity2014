package com.smart.model;

public class Hospital {
	
	public String getOpen() {
		return open;
	}

	public void setOpen(String open) {
		this.open = open;
	}

	private String name,address,rating,open;
	private String emergency;
	

	public String getEmergency() {
		return emergency;
	}

	public void setEmergency(String emergency) {
		this.emergency = emergency;
	}

	public Hospital(String name, String address, String rating, String open,
			String emergency) {
		super();
		this.name = name;
		this.address = address;
		this.setRating(rating);
		this.open = open;
		this.emergency = emergency;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

}
