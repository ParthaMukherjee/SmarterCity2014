package com.smart;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.smart.model.Essential;
import com.smart.service.EssentialService;
@ManagedBean(name="e")
@RequestScoped
public class EssentialBean {
	
	EssentialService es = new EssentialService();
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	private String type;
	private String pin;
	private String phone;
	private String email;
	
	public String doRegistration(){
		Essential e = new Essential();
		e.setName(name);
		e.setEmail(email);
		e.setPhone(phone);
		e.setPin(pin);
		e.setType(type);
		es.createEssential(e);
		return "smartadmin";
	}
}
