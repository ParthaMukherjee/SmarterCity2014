package com.smart;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.smart.model.User;

@ManagedBean(name="register")
@RequestScoped
public class Registration {
	
	UserService us = new UserService();
	String user;
	String passcode;
	String name;
	String pin;
	String email;
	String dob;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPasscode() {
		return passcode;
	}
	public void setPasscode(String passcode) {
		this.passcode = passcode;
	}

	public String doRegistration(){
		User user = new User();
		user.setName(this.getName());
		user.setPasscode(this.getPasscode());
		user.setUser(this.getUser());
		user.setEmail(this.getEmail());
		user.setPin(this.getPin());
		us.createUser(user);
		
		return "registrationsuccess?faces-redirect=true";
	}
}
