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

import com.MyProject.domain.project.ProjectEntity;
import com.MyProject.domain.task.TaskEntity;
import com.MyProject.domain.task.TaskHistoryEntity;
import com.MyProject.domain.user.UserEntity;
import com.MyProject.services.TaskHistoryService;
import com.MyProject.dao.TaskHistoryDao;

@javax.faces.bean.ManagedBean
@Scope(value="session")
public class TaskHistoryServiceImpl implements TaskHistoryService, Serializable {

	private static final long serialVersionUID = -5120179667709475229L;

	private TaskHistoryDao taskHistoryDao;
	private TaskHistoryEntity selectedTaskHistory = new TaskHistoryEntity();
	private List<TaskHistoryEntity> taskHistories = new ArrayList<TaskHistoryEntity>();
	private TaskHistoryEntity taskHistoryADD = new TaskHistoryEntity();
	
	@PostConstruct
	public void init() {
		System.out.println("New bean TaskHistoryServiceImpl");
	}
	
	public void clear() {
		setTaskHistoryADD(new TaskHistoryEntity());
	}
	
	public void beforeCreateTaskHistory(UserEntity currentUser) {

		clear();
		taskHistoryADD.setChangeDate(new Date());
		taskHistoryADD.setChangeAuthor(currentUser);

	}
	
	public void setHistories(TaskEntity taskID) {
		setTaskHistories(taskHistoryDao.findByTaskID(taskID));
		if (!(getTaskHistories().isEmpty())) {
			setSelectedTaskHistory(getTaskHistories().get(0));
		}
		else{
			setSelectedTaskHistory(new TaskHistoryEntity());
		}
	}

	public void createTaskHistory(TaskServiceImpl taskServiceImpl, ProjectEntity projectEntity) {

		try {
			
			TaskEntity taskID = taskServiceImpl.getSelectedTask();
			
			taskServiceImpl.update(taskID, projectEntity);
			
			taskHistoryADD.setTask(taskID);
			taskHistoryDao.save(taskHistoryADD);
			
			taskHistoryADD = new TaskHistoryEntity();
			setTaskHistories(taskHistoryDao.findByTaskID(taskID));
			
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

	}
	
	public void createTaskHistory(TaskEntity taskID) {

		try {
			
			taskHistoryADD.setTask(taskID);
			taskHistoryDao.save(taskHistoryADD);
			setTaskHistories(taskHistoryDao.findByTaskID(taskID));
			taskHistoryADD = new TaskHistoryEntity();
			
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

	}
	
	public List<TaskHistoryEntity> loadAll() {
		try {
			return taskHistoryDao.findAll();
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

		return null;
	}

	public void delete(TaskHistoryEntity taskHistoryEntity, TaskEntity taskID) {

		try {
			taskHistoryDao.delete(taskHistoryEntity);
			setTaskHistories(taskHistoryDao.findByTaskID(taskID));
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}
		
	}
	
	public void update(TaskHistoryEntity taskHistoryEntity) {
		
		try {
			taskHistoryDao.update(taskHistoryEntity);
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

	}
	
	public TaskHistoryEntity findById(Long id) {
		try {
			return taskHistoryDao.findById(id);
			
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

	public TaskHistoryEntity getSelectedTaskHistory() {
		return selectedTaskHistory;
	}

	public void setSelectedTaskHistory(TaskHistoryEntity selectedTaskHistory) {
		this.selectedTaskHistory = selectedTaskHistory;
	}

	public TaskHistoryDao getTaskHistoryDao() {
		return taskHistoryDao;
	}

	public void setTaskHistoryDao(TaskHistoryDao taskHistoryDao) {
		this.taskHistoryDao = taskHistoryDao;
	}

	public List<TaskHistoryEntity> getTaskHistories() {
		return taskHistories;
	}

	public void setTaskHistories(List<TaskHistoryEntity> taskHistories) {
		this.taskHistories = taskHistories;
	}

	public TaskHistoryEntity getTaskHistoryADD() {
		return taskHistoryADD;
	}

	public void setTaskHistoryADD(TaskHistoryEntity taskHistoryADD) {
		this.taskHistoryADD = taskHistoryADD;
	}
 	
}
