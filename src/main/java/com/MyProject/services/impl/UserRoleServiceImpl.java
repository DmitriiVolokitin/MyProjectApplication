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

import com.MyProject.domain.task.TaskEntity;
import com.MyProject.domain.user.UserRoleEntity;
import com.MyProject.services.UserRoleService;
import com.MyProject.dao.UserRoleDao;

@javax.faces.bean.ManagedBean
@Scope(value="session")
public class UserRoleServiceImpl implements UserRoleService, Serializable {

	private static final long serialVersionUID = -5925046043985476130L;

	private List<UserRoleEntity> userRoles = new ArrayList<UserRoleEntity>();
	private UserRoleDao userRoleDao;
	private UserRoleEntity selectedUserRole = new UserRoleEntity();
	private UserRoleEntity userRoleADD = new UserRoleEntity();
	
	@PostConstruct
	public void init() {
		
		System.out.println("New bean UserRoleServiceImpl");
		
		if (userRoleDao.countEntity() <= 0) {
			createUserRoleOnStartNewDataBase();
		}

		setUserRoles(userRoleDao.findAll());
		
	}
	
	public void clear() {
		setUserRoleADD(new UserRoleEntity());
	}
	
	public void createUserRoleOnStartNewDataBase() {

		List<UserRoleEntity> newUserRole =new ArrayList<UserRoleEntity>();
		UserRoleEntity newEntity;

		newEntity = new UserRoleEntity();
		newEntity.setName("Project Manager");
		newUserRole.add(newEntity);

		newEntity = new UserRoleEntity();
		newEntity.setName("Developer");
		newUserRole.add(newEntity);

		newEntity = new UserRoleEntity();
		newEntity.setName("Consultant");
		newUserRole.add(newEntity);

		newEntity = new UserRoleEntity();
		newEntity.setName("Tester");
		newUserRole.add(newEntity);

		newEntity = new UserRoleEntity();
		newEntity.setName("Acceptance Task");
		newUserRole.add(newEntity);
		
		newEntity = new UserRoleEntity();
		newEntity.setName("Base");
		newUserRole.add(newEntity);

		try {
			for(UserRoleEntity entityList : newUserRole) {
				userRoleDao.save(entityList);
			}
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

	}
	
	public void createUserRole(TaskEntity taskID) {

		try {
			userRoleDao.save(userRoleADD);
			userRoleADD = new UserRoleEntity();
			setUserRoles(userRoleDao.findByTaskID(taskID));
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

	}
	
	public void update(UserRoleEntity userRoleEntity) {
		
		try {
			userRoleDao.update(userRoleEntity);
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

	}

	public List<UserRoleEntity> loadAll() {
		try {
			return userRoleDao.findAll();
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

		return null;
	}
	
	public List<UserRoleEntity> findByTaskID(TaskEntity taskID) {
		try {
			return userRoleDao.findByTaskID(taskID);
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

		return null;
	}

	public void delete(UserRoleEntity userRole, TaskEntity taskID) {

		try {
			userRoleDao.delete(userRole);
			selectedUserRole = new UserRoleEntity();
			setUserRoles(userRoleDao.findByTaskID(taskID));
			FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "File removed!", null));
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

	}

	public UserRoleEntity findById(Long id) {
		try {
			return userRoleDao.findById(id);

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
		FacesMessage msg = new FacesMessage("Task File Selected", ((UserRoleEntity) event.getObject()).getId().toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowUnselect(UnselectEvent event) {
		FacesMessage msg = new FacesMessage("Task File Unselect", ((UserRoleEntity) event.getObject()).getId().toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public List<UserRoleEntity> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<UserRoleEntity> userRoles) {
		this.userRoles = userRoles;
	}

	public UserRoleDao getUserRoleDao() {
		return userRoleDao;
	}

	public void setUserRoleDao(UserRoleDao userRoleDao) {
		this.userRoleDao = userRoleDao;
	}

	public UserRoleEntity getSelectedUserRole() {
		return selectedUserRole;
	}

	public void setSelectedUserRole(UserRoleEntity selectedUserRole) {
		this.selectedUserRole = selectedUserRole;
	}

	public UserRoleEntity getUserRoleADD() {
		return userRoleADD;
	}

	public void setUserRoleADD(UserRoleEntity userRoleADD) {
		this.userRoleADD = userRoleADD;
	}

}
