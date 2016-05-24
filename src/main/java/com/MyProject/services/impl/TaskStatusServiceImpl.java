package com.MyProject.services.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.springframework.context.annotation.Scope;

import com.MyProject.domain.project.ProjectEntity;
import com.MyProject.domain.task.TaskStatusEntity;
import com.MyProject.services.TaskStatusService;
import com.MyProject.dao.TaskStatusDao;

@javax.faces.bean.ManagedBean
@Scope(value="session")
public class TaskStatusServiceImpl implements TaskStatusService, Serializable {

	private static final long serialVersionUID = -8581798655895223983L;

	private TaskStatusDao taskStatusDao;
	private TaskStatusEntity selectedTaskStatus = new TaskStatusEntity();
	private List<TaskStatusEntity> taskStatuses = new ArrayList<TaskStatusEntity>();
	private TaskStatusEntity taskStatusADD = new TaskStatusEntity();

	@PostConstruct
	public void init() {
		System.out.println("New bean TaskStatusServiceImpl");
	}

	public void clear() {

		setTaskStatusADD(new TaskStatusEntity());
		
	}

	public void createTaskStatusByProjectOnStartNewDataBase(ProjectEntity projectEntity) {

		List<TaskStatusEntity> newStatus =new ArrayList<TaskStatusEntity>();
		TaskStatusEntity newEntity;

		newEntity = new TaskStatusEntity();
		newEntity.setName("Done");
		newEntity.setProject(projectEntity);
		newEntity.setConstant(true);
		newStatus.add(newEntity);

		newEntity = new TaskStatusEntity();
		newEntity.setName("Requires analysis");
		newEntity.setProject(projectEntity);
		newEntity.setConstant(true);
		newStatus.add(newEntity);

		newEntity = new TaskStatusEntity();
		newEntity.setName("Requires clarification");
		newEntity.setProject(projectEntity);
		newEntity.setConstant(true);
		newStatus.add(newEntity);

		newEntity = new TaskStatusEntity();
		newEntity.setName("Requires acceptance");
		newEntity.setProject(projectEntity);
		newEntity.setConstant(true);
		newStatus.add(newEntity);

		newEntity = new TaskStatusEntity();
		newEntity.setName("Requires rework");
		newEntity.setProject(projectEntity);
		newEntity.setConstant(true);
		newStatus.add(newEntity);

		newEntity = new TaskStatusEntity();
		newEntity.setName("Requires additional information");
		newEntity.setProject(projectEntity);
		newEntity.setConstant(true);
		newStatus.add(newEntity);

		try {
			for(TaskStatusEntity entityList : newStatus) {
				taskStatusDao.save(entityList);
			}
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

	}

	public void createTaskStatus(ProjectEntity projectEntity, TaskStatusEntity taskStatusEntity) {

		try {
			taskStatusEntity.setProject(projectEntity);
			taskStatusDao.save(taskStatusEntity);
			setTaskStatuses(taskStatusDao.findByProject(projectEntity));
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

	}

	public List<TaskStatusEntity> loadAll() {
		try {
			return taskStatusDao.findAll();
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

		return null;
	}
	
	public List<TaskStatusEntity> findByProject(ProjectEntity projectEntity) {
		try {
			return taskStatusDao.findByProject(projectEntity);
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

		return null;
	}

	public void delete(ProjectEntity projectEntity, TaskStatusEntity taskStatusEntity) {

		try {
			taskStatusDao.delete(taskStatusEntity);
			setSelectedTaskStatus(new TaskStatusEntity());
			setTaskStatuses(taskStatusDao.findByProject(projectEntity));
			FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Task removed!", null));
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

	}

	public void update(ProjectEntity projectEntity, TaskStatusEntity taskStatusEntity) {

		try {
			taskStatusDao.update(taskStatusEntity);
			setTaskStatuses(taskStatusDao.findByProject(projectEntity));
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

	}

	public TaskStatusEntity findById(Long id) {
		try {
			return taskStatusDao.findById(id);

		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}
		return null;
	}
	
	public boolean checkAvailableByProject(ProjectEntity projectEntity, String statusName) {
		
		return taskStatusDao.checkAvailableByProject(projectEntity, statusName);
		
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
		(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Task Status onRowSelect!", null));
	}

	public void onRowUnselect(UnselectEvent event) {
		FacesContext.getCurrentInstance().addMessage
		(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Task Status onRowUnselect!", null));
	}

	public TaskStatusEntity getSelectedTaskStatus() {
		return selectedTaskStatus;
	}

	public void setSelectedTaskStatus(TaskStatusEntity selectedTaskStatus) {
		this.selectedTaskStatus = selectedTaskStatus;
	}

	public TaskStatusDao getTaskStatusDao() {
		return taskStatusDao;
	}

	public void setTaskStatusDao(TaskStatusDao taskStatusDao) {
		this.taskStatusDao = taskStatusDao;
	}

	public List<TaskStatusEntity> getTaskStatuses() {
		return taskStatuses;
	}

	public void setTaskStatuses(List<TaskStatusEntity> taskStatuses) {
		this.taskStatuses = taskStatuses;
	}
	
	public void setTaskStatuses(ProjectEntity projectEntity) {
		this.taskStatuses = findByProject(projectEntity);
	}

	public TaskStatusEntity getTaskStatusADD() {
		return taskStatusADD;
	}

	public void setTaskStatusADD(TaskStatusEntity taskStatusADD) {
		this.taskStatusADD = taskStatusADD;
	}

}
