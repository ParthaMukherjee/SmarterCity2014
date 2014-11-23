package com.smart.service;


import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

import com.smart.model.Task;
 
@ManagedBean(name="dtLazyView")
@ViewScoped
public class LazyView implements Serializable,ActionListener {
     
    private LazyDataModel<Task> lazyModel;
     
    private Task  selectedTask;
     
    @ManagedProperty("#{taskService}")
    private TaskService service=new TaskService();
     
    @PostConstruct
    public void init() {
        lazyModel = new LazyTaskDataModel(service.createTasks(200));
    }
 
    public LazyDataModel<Task> getLazyModel() {
        return lazyModel;
    }
 
    public Task getSelectedTask() {
        return selectedTask;
    }
 
    public void setSelectedTask(Task selectedTask) {
        this.selectedTask = selectedTask;
    }
     
    public void setService(TaskService service) {
        this.service = service;
    }
    
    public String save(){
    	service.updateTask(getSelectedTask());
    	return "smartadmin";
    }
    
    
    public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("Task Selected", ((Task) event.getObject()).getTaskId());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

	@Override
	public void processAction(ActionEvent arg0) throws AbortProcessingException {
		System.out.println("Hi");
		
	}
}