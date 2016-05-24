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
import com.MyProject.domain.user.UserEntity;
import com.MyProject.services.TaskService;
import com.MyProject.dao.TaskDao;

@javax.faces.bean.ManagedBean
@Scope(value="session")
public class TaskServiceImpl implements TaskService, Serializable {

	private static final long serialVersionUID = 2055385126343419811L;

	private TaskDao taskDao;
	
	private List<TaskEntity> tasks = new ArrayList<TaskEntity>();
	private List<TaskEntity> filteredTask = new ArrayList<TaskEntity>();

	private TaskEntity selectedTask = new TaskEntity();
	private TaskEntity taskADD = new TaskEntity();
	
	@PostConstruct
	public void init() {
		System.out.println("New bean TaskServiceImplTaskServiceImplTaskServiceImplTaskServiceImplTaskServiceImplTaskServiceImpl");
	}

	public List<TaskEntity> findByProject(ProjectEntity projectEntity) {
		return taskDao.findByProject(projectEntity);
	}

	public void clear() {
		setTaskADD(new TaskEntity());
	}
	
	public void beforeCreateTask(UserEntity currentUser) {
		clear();
		taskADD.setUsercurrentExecutor(currentUser);
		taskADD.setUserResponsible(currentUser);
	}
	
	public void createTask(ProjectEntity projectEntity) {

		try {
			taskADD.setDate(new Date());
			taskADD.setProject(projectEntity);
			taskDao.save(taskADD);
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}
		
	}

	public List<TaskEntity> loadAll() {
		try {
			return taskDao.findAll();
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

		return null;
	}

	public void delete(TaskEntity task) {

		try {
			taskDao.delete(task);
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

	}

	public TaskEntity findById(Long id) {
		
		try {
			return taskDao.findById(id);
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}
		return null;
	}

	public void update(TaskEntity taskEntity, ProjectEntity projectEntity) {

		try {
			taskDao.update(taskEntity);
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

	}

	public TaskDao getTaskDao() {
		return taskDao;
	}

	public void setTaskDao(TaskDao taskDao) {
		this.taskDao = taskDao;
	}

	public TaskEntity getSelectedTask() {
		return selectedTask;
	}

	public void setSelectedTask(TaskEntity selectedTask) {
		this.selectedTask = selectedTask;
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
		(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Task onRowSelect!", null));
	}

	public void onRowUnselect(UnselectEvent event) {
		FacesContext.getCurrentInstance().addMessage
		(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Task onRowUnselect!", null));
	}

	public TaskEntity getTaskADD() {
		return taskADD;
	}

	public void setTaskADD(TaskEntity taskADD) {
		this.taskADD = taskADD;
	}

	public List<TaskEntity> getTasks() {
		return tasks;
	}

	public void setTasks(List<TaskEntity> tasks) {
		this.tasks = tasks;
	}

	public List<TaskEntity> getFilteredTask() {
		return filteredTask;
	}

	public void setFilteredTask(List<TaskEntity> filteredTask) {
		this.filteredTask = filteredTask;
	}

}
