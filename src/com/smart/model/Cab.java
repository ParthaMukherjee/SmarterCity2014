package com.smart.model;

public class Cab {
 String name,website,address,charge,phone;

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public Cab(String name, String website, String address, String charge,
		String phone) {
	super();
	this.name = name;
	this.website = website;
	this.address = address;
	this.charge = charge;
	this.phone = phone;
}

public String getWebsite() {
	return website;
}

public void setWebsite(String website) {
	this.website = website;
}

public String getAddress() {
	return address;
}

public void setAddress(String address) {
	this.address = address;
}

public String getCharge() {
	return charge;
}

public void setCharge(String charge) {
	this.charge = charge;
}

public String getPhone() {
	return phone;
}

public void setPhone(String phone) {
	this.phone = phone;
}
}
