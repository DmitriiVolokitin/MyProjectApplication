package com.MyProject.services.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.springframework.context.annotation.Scope;

import com.MyProject.domain.task.TaskEntity;
import com.MyProject.domain.task.TaskStatusHistoryEntity;
import com.MyProject.domain.user.UserEntity;
import com.MyProject.services.TaskStatusHistoryService;
import com.MyProject.dao.TaskStatusHistoryDao;

@javax.faces.bean.ManagedBean
@Scope(value="session")
public class TaskStatusHistoryServiceImpl implements TaskStatusHistoryService, Serializable {

	private static final long serialVersionUID = 6326366238821061913L;
	
	private TaskStatusHistoryDao taskStatusHistoryDao;
	private TaskStatusHistoryEntity selectedTaskStatusHistory = new TaskStatusHistoryEntity();
	private List<TaskStatusHistoryEntity> statusHistories = new ArrayList<TaskStatusHistoryEntity>();
	private TaskStatusHistoryEntity statusHistoryADD = new TaskStatusHistoryEntity();
	
	@PostConstruct
	public void init() {
		System.out.println("New bean TaskStatusHistoryServiceImpl");
	}
	
	public void clear() {
		setStatusHistoryADD(new TaskStatusHistoryEntity());
	}
	
	public void beforeCreateTaskStatusHistory(UserEntity currentUser) {

		clear();
		statusHistoryADD.setDate(new Date());
		statusHistoryADD.setUserAuthor(currentUser);

	}
	
	public void createTaskStatusHistory(TaskEntity taskID) {

		try {
			
			statusHistoryADD.setTask(taskID);
			taskStatusHistoryDao.save(statusHistoryADD);
			setStatusHistories(taskStatusHistoryDao.findByTaskID(taskID));
			
			statusHistoryADD = new TaskStatusHistoryEntity();
			
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

	}
	
	public List<TaskStatusHistoryEntity> loadAll() {
		try {
			return taskStatusHistoryDao.findAll();
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

		return null;
	}

	public void delete(TaskStatusHistoryEntity taskStatusHistoryEntity, TaskEntity taskID) {

		try {
			taskStatusHistoryDao.delete(taskStatusHistoryEntity);
			setStatusHistories(taskStatusHistoryDao.findByTaskID(taskID));
			FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Task removed!", null));
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}
		
	}
	
	public void update(TaskStatusHistoryEntity taskStatusHistoryEntity) {
		
		try {
			taskStatusHistoryDao.update(taskStatusHistoryEntity);
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

	}
	
	public TaskStatusHistoryEntity findById(Long id) {
		try {
			return taskStatusHistoryDao.findById(id);
			
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}
		return null;
	}


	protected FacesMessage constructErrorMessage(String message, String detail){
		return new FacesMessage(FacesMessage.SEVERITY_ERROR, message, detail);
	}

	protected FacesMessage constructInfoMessage(String message, String detail) {
		return new FacesMessage(FacesMessage.SEVERITY_INFO, message, detail);
	}

	protected FacesMessage constructFatalMessage(String message, String detail) {
		return new FacesMessage(FacesMessage.SEVERITY_FATAL, message, detail);
	}

	protected FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}

	protected ResourceBundle getMessageBundle() {
		return ResourceBundle.getBundle("message-labels");
	}
	
	public void onRowSelect(SelectEvent event) {
		FacesContext.getCurrentInstance().addMessage
		(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Task History onRowSelect!", null));
    }
 
    public void onRowUnselect(UnselectEvent event) {
    	FacesContext.getCurrentInstance().addMessage
		(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Task History onRowUnselect!", null));
    }

	public TaskStatusHistoryEntity getStatusHistoryADD() {
		return statusHistoryADD;
	}

	public void setStatusHistoryADD(TaskStatusHistoryEntity statusHistoryADD) {
		this.statusHistoryADD = statusHistoryADD;
	}

	public List<TaskStatusHistoryEntity> getStatusHistories() {
		return statusHistories;
	}
	
	public void setStatusHistories(List<TaskStatusHistoryEntity> statusHistories) {
		this.statusHistories = statusHistories;
	}
	
	public void setStatusHistories(TaskEntity taskID) {
		setStatusHistories(taskStatusHistoryDao.findByTaskID(taskID));
		if (!(getStatusHistories().isEmpty())) {
			setSelectedTaskStatusHistory(getStatusHistories().get(0));
		}
		else{
			setSelectedTaskStatusHistory(new TaskStatusHistoryEntity());
		}
	}

	public TaskStatusHistoryEntity getHistoryADD() {
		return statusHistoryADD;
	}

	public TaskStatusHistoryDao getTaskStatusHistoryDao() {
		return taskStatusHistoryDao;
	}

	public void setTaskStatusHistoryDao(TaskStatusHistoryDao taskStatusHistoryDao) {
		this.taskStatusHistoryDao = taskStatusHistoryDao;
	}

	public TaskStatusHistoryEntity getSelectedTaskStatusHistory() {
		return selectedTaskStatusHistory;
	}

	public void setSelectedTaskStatusHistory(TaskStatusHistoryEntity selectedTaskStatusHistory) {
		this.selectedTaskStatusHistory = selectedTaskStatusHistory;
	}

}
