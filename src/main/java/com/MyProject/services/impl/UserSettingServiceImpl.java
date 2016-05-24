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

import com.MyProject.domain.commons.FormatTimeTrackingEntity;
import com.MyProject.domain.project.ProjectEntity;
import com.MyProject.domain.user.UserEntity;
import com.MyProject.domain.user.UserSettingEntity;
import com.MyProject.services.UserSettingService;
import com.MyProject.dao.UserSettingDao;

@javax.faces.bean.ManagedBean
@Scope(value="session")
public class UserSettingServiceImpl implements UserSettingService, Serializable {

	private static final long serialVersionUID = 3764295637314579071L;

	private List<UserSettingEntity> userSettings = new ArrayList<UserSettingEntity>();
	private UserSettingDao userSettingDao;
	private UserSettingEntity selectedUserSetting = new UserSettingEntity();
	private UserSettingEntity userSettingADD = new UserSettingEntity();
	private UserSettingEntity userSetting = new UserSettingEntity();

	@PostConstruct
	public void init() {
		System.out.println("New bean UserSettingServiceImpl");
	}

	public void clear() {
		setUserSettingADD(new UserSettingEntity());
	}

	public void createUserSettingOnStartNewDataBase(ProjectEntity projectEntity, UserEntity userEntity, FormatTimeTrackingEntity formatTimeTrackingEntity) {

		UserSettingEntity newEntity;

		newEntity = new UserSettingEntity();
		newEntity.setUser(userEntity);
		newEntity.setProjectByDefault(projectEntity);
		newEntity.setFormatTimeTrackingByDefault(formatTimeTrackingEntity);

		try {
			userSettingDao.save(newEntity);
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

	}

	public void createUserSetting(UserEntity userEntity, ProjectEntity projectEntity) {

		try {
			userSettingDao.save(userSettingADD);
			userSettingADD = new UserSettingEntity();
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

	}

	public void update(UserSettingEntity UserSettingEntity) {

		try {
			userSettingDao.update(UserSettingEntity);
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

	}

	public UserSettingEntity findByUser(UserEntity userEntity) {
		
		try {
			return userSettingDao.findByUser(userEntity);
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

		return null;
	}
	
	public UserSettingEntity findByUserAndProject(UserEntity userEntity, ProjectEntity projectEntity) {
		
		try {
			return userSettingDao.findByUserAndProject(userEntity, projectEntity);
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

		return null;
	}

	public void delete(UserSettingEntity UserSettingEntity) {

		try {
			userSettingDao.delete(UserSettingEntity);
			selectedUserSetting = new UserSettingEntity();
			FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "File removed!", null));
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

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
		FacesMessage msg = new FacesMessage("Task File Selected", ((UserSettingEntity) event.getObject()).getId().toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowUnselect(UnselectEvent event) {
		FacesMessage msg = new FacesMessage("Task File Unselect", ((UserSettingEntity) event.getObject()).getId().toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public List<UserSettingEntity> getUserSettings() {
		return userSettings;
	}

	public void setUserSettings(List<UserSettingEntity> userSettings) {
		this.userSettings = userSettings;
	}

	public UserSettingDao getUserSettingDao() {
		return userSettingDao;
	}

	public void setUserSettingDao(UserSettingDao userSettingDao) {
		this.userSettingDao = userSettingDao;
	}

	public UserSettingEntity getSelectedUserSetting() {
		return selectedUserSetting;
	}

	public void setSelectedUserSetting(UserSettingEntity selectedUserSetting) {
		this.selectedUserSetting = selectedUserSetting;
	}

	public UserSettingEntity getUserSettingADD() {
		return userSettingADD;
	}

	public void setUserSettingADD(UserSettingEntity userSettingADD) {
		this.userSettingADD = userSettingADD;
	}

	public UserSettingEntity getUserSetting() {
		return userSetting;
	}

	public void setUserSetting(UserSettingEntity userSetting) {
		this.userSetting = userSetting;
	}

}
