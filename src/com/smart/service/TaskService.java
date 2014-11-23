package com.smart.service;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;





import com.smar.dataaccess.TaskDAO;
import com.smart.model.Task;
 
@ManagedBean(name = "taskService")
@ApplicationScoped
public class TaskService {
     
    private final static String[] priorities;
    
    private TaskDAO taskDAO =new TaskDAO();
     
    private final static String[] brands;
     
    static {
    	priorities = new String[3];
    	priorities[0] = "High";
    	priorities[1] = "Medium";
    	priorities[2] = "Low";
       
         
        brands = new String[10];
        brands[0] = "BMW";
        brands[1] = "Mercedes";
        brands[2] = "Volvo";
        brands[3] = "Audi";
        brands[4] = "Renault";
        brands[5] = "Fiat";
        brands[6] = "Volkswagen";
        brands[7] = "Honda";
        brands[8] = "Jaguar";
        brands[9] = "Ford";
    }
     
    public List<Task> createTasks(int size) {
        List<Task> list = new ArrayList<Task>();
        for(int i = 0 ; i < size ; i++) {
            list.add(new Task(getRandomId(),getRandomDescription(),getRandomName(),getRandomCBy(),getRandomAssigned(),getRandomStatus(),getRandomCreated(),getRandomCompletionDate(),getRandomPriority()));
        }
         
        return list;
    }
    
    public List<Task> getAllTasks() {
    	List<Task> tasks=null;
    	try {
    		tasks= taskDAO.findAllTasks();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return tasks;
    }
    
    public List<Task> getMyTask(String name){
    	List<Task> tasks=null;
    	try {
    		tasks= taskDAO.getMyTask(name);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return tasks;
    }
     
    private String getRandomPriority() {
		// TODO Auto-generated method stub
		return   priorities[(int) (Math.random() * 3)];
	}

	private String getRandomAssigned() {
		// TODO Auto-generated method stub
		return "PWD";
	}

	private String getRandomCBy() {
		// TODO Auto-generated method stub
		return "Nabin Mandal";
	}

	private Date getRandomCompletionDate() {
		// TODO Auto-generated method stub
		return new Date();
	}

	private Date getRandomCreated() {
		// TODO Auto-generated method stub
		return new Date();
	}

	private String getRandomStatus() {
		// TODO Auto-generated method stub
		return "In Progress";
	}

	private String getRandomName() {
		// TODO Auto-generated method stub
		return "Road Repair";
	}

	private String getRandomDescription() {
		// TODO Auto-generated method stub
		return "122 M.G Road Repair Required due to heavy rail faill";
	}

	private String getRandomId() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
     
   public void createTask(Task task){
	   
	   try {
		taskDAO.createTask(task);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   System.out.println("Success");
   }
   
public void updateTask(Task task){
	   
	   try {
		taskDAO.updateTask(task);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   System.out.println("Success");
   }
}