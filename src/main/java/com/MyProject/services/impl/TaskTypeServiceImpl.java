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
import com.MyProject.domain.task.TaskTypeEntity;
import com.MyProject.services.TaskTypeService;
import com.MyProject.dao.TaskTypeDao;

@javax.faces.bean.ManagedBean
@Scope(value="session")
public class TaskTypeServiceImpl implements TaskTypeService, Serializable {

	private static final long serialVersionUID = -8270770641678622659L;

	private TaskTypeDao taskTypeDao;
	private TaskTypeEntity selectedTaskType = new TaskTypeEntity();
	private List<TaskTypeEntity> taskTypes = new ArrayList<TaskTypeEntity>();
	private TaskTypeEntity taskTypeADD = new TaskTypeEntity();

	@PostConstruct
	public void init() {
		System.out.println("New bean TaskTypeServiceImpl");
	}

	public void clear() {

		setTaskTypeADD(new TaskTypeEntity());
	}

	public void createTaskTypeByProjectOnStartNewDataBase(ProjectEntity projectEntity) {

		List<TaskTypeEntity> newTypes =new ArrayList<TaskTypeEntity>();
		TaskTypeEntity newEntity;

		newEntity = new TaskTypeEntity();
		newEntity.setName("New Task");
		newEntity.setProject(projectEntity);
		newEntity.setConstant(true);
		newTypes.add(newEntity);

		newEntity = new TaskTypeEntity();
		newEntity.setName("Rework");
		newEntity.setProject(projectEntity);
		newEntity.setConstant(true);
		newTypes.add(newEntity);

		newEntity = new TaskTypeEntity();
		newEntity.setName("Modification");
		newEntity.setProject(projectEntity);
		newEntity.setConstant(true);
		newTypes.add(newEntity);
		
		newEntity = new TaskTypeEntity();
		newEntity.setName("Consultation");
		newEntity.setProject(projectEntity);
		newEntity.setConstant(true);
		newTypes.add(newEntity);

		try {
			for(TaskTypeEntity entityList : newTypes) {
				taskTypeDao.save(entityList);
			}
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

	}

	public void createTaskType(ProjectEntity projectEntity, TaskTypeEntity taskTypeEntity) {

		try {
			taskTypeEntity.setProject(projectEntity);
			taskTypeDao.save(taskTypeEntity);
			setTaskTypes(taskTypeDao.findByProject(projectEntity));
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

	}

	public List<TaskTypeEntity> loadAll() {
		try {
			return taskTypeDao.findAll();
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

		return null;
	}

	public void delete(ProjectEntity projectEntity, TaskTypeEntity taskTypeEntity) {

		try {
			taskTypeDao.delete(taskTypeEntity);
			setSelectedTaskType(new TaskTypeEntity());
			setTaskTypes(taskTypeDao.findByProject(projectEntity));
			FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Task removed!", null));
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

	}

	public void update(ProjectEntity projectEntity, TaskTypeEntity taskTypeEntity) {

		try {
			taskTypeDao.update(taskTypeEntity);
			setTaskTypes(taskTypeDao.findByProject(projectEntity));
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

	}

	public TaskTypeEntity findById(Long id) {
		try {
			return taskTypeDao.findById(id);

		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}
		return null;
	}
	
	public List<TaskTypeEntity> findByProject(ProjectEntity projectEntity) {
		try {
			return taskTypeDao.findByProject(projectEntity);
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

		return null;
	}
	
	public boolean checkAvailableByProject(ProjectEntity projectEntity, String typeName) {
		
		return taskTypeDao.checkAvailableByProject(projectEntity, typeName);
		
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
		(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Task Type onRowSelect!", null));
	}

	public void onRowUnselect(UnselectEvent event) {
		FacesContext.getCurrentInstance().addMessage
		(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Task Type onRowUnselect!", null));
	}

	public TaskTypeEntity getSelectedTaskType() {
		return selectedTaskType;
	}

	public void setSelectedTaskType(TaskTypeEntity selectedTaskType) {
		this.selectedTaskType = selectedTaskType;
	}

	public TaskTypeDao getTaskTypeDao() {
		return taskTypeDao;
	}

	public void setTaskTypeDao(TaskTypeDao taskTypeDao) {
		this.taskTypeDao = taskTypeDao;
	}

	public List<TaskTypeEntity> getTaskTypes() {
		return taskTypes;
	}

	public void setTaskTypes(List<TaskTypeEntity> taskTypes) {
		this.taskTypes = taskTypes;
	}
	
	public void setTaskTypes(ProjectEntity projectEntity) {
		this.taskTypes = findByProject(projectEntity);
	}

	public TaskTypeEntity getTaskTypeADD() {
		return taskTypeADD;
	}

	public void setTaskTypeADD(TaskTypeEntity taskTypeADD) {
		this.taskTypeADD = taskTypeADD;
	}

}
