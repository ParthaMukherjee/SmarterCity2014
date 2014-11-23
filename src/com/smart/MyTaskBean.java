package com.smart;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.smart.model.Cab;
import com.smart.model.Task;
import com.smart.service.TaskService;
import com.smart.utility.Utility;

@ManagedBean(name="mytaskbean")
@ViewScoped
public class MyTaskBean {
	
	List<Task> tasks = new ArrayList<Task>();
	TaskService ts = new TaskService();

	public List<Task> getTasks() {
		LoginBean logBean =(LoginBean)Utility.getBean("login");
		return ts.getMyTask(logBean.getUser());
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}
	

		
		

}
