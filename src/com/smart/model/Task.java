package com.smart.model;

import java.util.Date;

public class Task {
	private String taskId, taskDescription,taskName,createdBy,assigned,status;
	private Date created,completionDate;
	private String priority;
	private String comment;
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public Task(){
		
	}
	public Task(String taskId, String taskDescription, String taskName,
			String createdBy, String assigned, String status, Date created,
			Date completionDate,String priority) {
		super();
		this.taskId = taskId;
		this.taskDescription = taskDescription;
		this.taskName = taskName;
		this.createdBy = createdBy;
		this.assigned = assigned;
		this.status = status;
		this.created = created;
		this.completionDate = completionDate;
		this.priority=priority;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getTaskDescription() {
		return taskDescription;
	}
	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getAssigned() {
		return assigned;
	}
	public void setAssigned(String assigned) {
		this.assigned = assigned;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getCompletionDate() {
		return completionDate;
	}
	public void setCompletionDate(Date completionDate) {
		this.completionDate = completionDate;
	}
	
}
