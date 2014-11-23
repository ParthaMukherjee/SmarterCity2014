package com.smart;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.smart.model.User;

@ManagedBean(name="login")
@SessionScoped
public class LoginBean {
	
	String user;
	String passcode;
	String welcome;
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getWelcome() {
		return welcome;
	}
	public void setWelcome(String welcome) {
		this.welcome = welcome;
	}
	public String getPasscode() {
		return passcode;
	}
	public void setPasscode(String passcode) {
		this.passcode = passcode;
	}

	public String doLogin(){
		UserService us=new UserService();
		User retUser=us.getUser(getUser());
			if(retUser!=null && getPasscode().equals(retUser.getPasscode())){
				setWelcome("Welcome : "+retUser.getName()+"!");
				if("admin".equalsIgnoreCase(getUser())){
					return "smartadmin?faces-redirect=true";
				}else{
					return "smartloginsuccess?faces-redirect=true";
				}
				
			}else{
				return "smartloginfailed";
			}
	}
}
