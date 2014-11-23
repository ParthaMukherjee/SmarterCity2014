package com.smart;

import java.util.Date;
import java.util.UUID;

import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.smart.model.Task;
import com.smart.service.TaskService;
@ManagedBean(name="complain")
@RequestScoped
public class ComplainBean {
	
	private String priority,assign,description,name;
	
	
	TaskService tService=new TaskService();
	
	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getAssign() {
		return assign;
	}

	public void setAssign(String assign) {
		this.assign = assign;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String doComplain(){
		System.out.println("Test");
		LoginBean logBean =(LoginBean)getBean("login");
		
		Task task= new Task(UUID.randomUUID().toString().substring(0, 8), this.description, this.name, logBean.getUser(), this.assign, "Open", new Date(), null, this.priority);
		tService.createTask(task);
		return "smartloginsuccess";
	}
	
	public static Object getBean(String beanName){
	    Object bean = null;
	    FacesContext fc = FacesContext.getCurrentInstance();
	    if(fc!=null){
	    	ELContext elContext =fc.getELContext();
	         bean = elContext.getELResolver().getValue(elContext, null, beanName);
	    }

	    return bean;
	}
	
	public String showComplainUI(){
		return "smartcomplain2";
	}
	
	
}
