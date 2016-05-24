package com.MyProject.services.impl;

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
import com.MyProject.domain.task.TaskPriorityEntity;
import com.MyProject.services.TaskPriorityService;
import com.MyProject.dao.TaskPriorityDao;

@javax.faces.bean.ManagedBean
@Scope(value="session")
public class TaskPriorityServiceImpl implements TaskPriorityService {

	private TaskPriorityDao taskPriorityDao;
	private TaskPriorityEntity selectedTaskPriority = new TaskPriorityEntity();
	private List<TaskPriorityEntity> taskPriorities = new ArrayList<TaskPriorityEntity>();
	private TaskPriorityEntity taskPriorityADD = new TaskPriorityEntity();
	
	@PostConstruct
	public void init() {
		System.out.println("New bean TaskPriorityServiceImpl");
	}
	
	public void clear() {

		setTaskPriorityADD(new TaskPriorityEntity());
	
	}
	
	public void setPriorities(ProjectEntity projectEntity) {
		setTaskPriorities(taskPriorityDao.findByProject(projectEntity));
	}
	
	public void createTaskPriorityByProjectOnStartNewDataBase(ProjectEntity projectEntity) {

		List<TaskPriorityEntity> newPriority =new ArrayList<TaskPriorityEntity>();
		TaskPriorityEntity newEntity;
		
		newEntity = new TaskPriorityEntity();
		newEntity.setName("Immediately");
		newEntity.setProject(projectEntity);
		newEntity.setConstant(true);
		newPriority.add(newEntity);

		newEntity = new TaskPriorityEntity();
		newEntity.setName("High");
		newEntity.setProject(projectEntity);
		newEntity.setConstant(true);
		newPriority.add(newEntity);
		
		newEntity = new TaskPriorityEntity();
		newEntity.setName("Medium");
		newEntity.setProject(projectEntity);
		newEntity.setConstant(true);
		newPriority.add(newEntity);

		newEntity = new TaskPriorityEntity();
		newEntity.setName("Low");
		newEntity.setProject(projectEntity);
		newEntity.setConstant(true);
		newPriority.add(newEntity);

		try {
			for(TaskPriorityEntity entityList : newPriority) {
				taskPriorityDao.save(entityList);
			}
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

	}
	
	public void createTaskPriority(ProjectEntity projectEntity, TaskPriorityEntity taskPriorityEntity) {

		try {
			taskPriorityEntity.setProject(projectEntity);
			taskPriorityDao.save(taskPriorityEntity);
			setTaskPriorities(taskPriorityDao.findByProject(projectEntity));
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

	}
	
	public List<TaskPriorityEntity> loadAll() {
		
		try {
			return taskPriorityDao.findAll();
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

		return null;
	}

	public void delete(ProjectEntity projectEntity, TaskPriorityEntity taskPriorityEntity) {

		try {
			taskPriorityDao.delete(taskPriorityEntity);
			setSelectedTaskPriority(new TaskPriorityEntity());
			setTaskPriorities(taskPriorityDao.findByProject(projectEntity));
			FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Task removed!", null));
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}
		
	}
	
	public void update(ProjectEntity projectEntity, TaskPriorityEntity taskPriorityEntity) {
		
		try {
			taskPriorityDao.update(taskPriorityEntity);
			setTaskPriorities(taskPriorityDao.findByProject(projectEntity));
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

	}
	
	public TaskPriorityEntity findById(Long id) {
		
		try {
			return taskPriorityDao.findById(id);
			
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}
		
		return null;
	}
	
	public List<TaskPriorityEntity> findByProject(ProjectEntity projectEntity) {
		try {
			return taskPriorityDao.findByProject(projectEntity);
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

		return null;
	}
	
	public boolean checkAvailableByProject(ProjectEntity projectEntity, String priorityName) {
		
		return taskPriorityDao.checkAvailableByProject(projectEntity, priorityName);
		
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
		(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Task Priority onRowSelect!", null));
    }
 
    public void onRowUnselect(UnselectEvent event) {
    	FacesContext.getCurrentInstance().addMessage
		(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Task Priority onRowUnselect!", null));
    }

	public TaskPriorityEntity getSelectedTaskPriority() {
		return selectedTaskPriority;
	}

	public void setSelectedTaskPriority(TaskPriorityEntity selectedTaskPriority) {
		this.selectedTaskPriority = selectedTaskPriority;
	}

	public TaskPriorityDao getTaskPriorityDao() {
		return taskPriorityDao;
	}

	public void setTaskPriorityDao(TaskPriorityDao taskPriorityDao) {
		this.taskPriorityDao = taskPriorityDao;
	}

	public List<TaskPriorityEntity> getTaskPriorities() {
		return taskPriorities;
	}

	public void setTaskPriorities(List<TaskPriorityEntity> taskPriorities) {
		this.taskPriorities = taskPriorities;
	}
	
	public void setTaskPriorities(ProjectEntity projectEntity) {
		this.taskPriorities = findByProject(projectEntity);
	}

	public TaskPriorityEntity getTaskPriorityADD() {
		return taskPriorityADD;
	}

	public void setTaskPriorityADD(TaskPriorityEntity taskPriorityADD) {
		this.taskPriorityADD = taskPriorityADD;
	}
 	
}
