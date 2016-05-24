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
import com.MyProject.services.ProjectService;
import com.MyProject.dao.ProjectDao;
import com.MyProject.dao.UserDao;

@javax.faces.bean.ManagedBean
@Scope(value="session")
public class ProjectServiceImpl implements ProjectService, Serializable {

	private static final long serialVersionUID = -5364209236000783283L;

	private ProjectDao projectDao;
	private UserDao userDao;

	private List<ProjectEntity> projects = new ArrayList<ProjectEntity>();
	private List<UserEntity> searchUserEntities = new ArrayList<UserEntity>();
	private String searchText = new String();

	private UserEntity selectedUser = new UserEntity();
	private UserEntity selectedUserADD = new UserEntity();
	private ProjectEntity selectedProject = new ProjectEntity();
	private ProjectEntity selectedCurrentProject = new ProjectEntity();
	private ProjectEntity projectADD = new ProjectEntity();

	@PostConstruct
	public void init() {
		System.out.println("New bean ProjectServiceImplProjectServiceImplProjectServiceImplProjectServiceImpl");
	}

	public void searchUsersByUserNameOrEmail(String searchText) {

		setSearchUserEntities(userDao.searchUsersByUserNameOrEmail(searchText));

	}

	public void clear() {

		setProjectADD(new ProjectEntity());
		setSelectedUser(new UserEntity());

	}

	public void clearUsers() {

		setSearchUserEntities(new ArrayList<UserEntity>());
		setSearchText("");

	}

	public void createProjectOnStartNewDataBase() {

		ProjectEntity newEntity;

		newEntity = new ProjectEntity();
		newEntity.setName("New project");
		newEntity.setStartDate(new Date());
		newEntity.setFinishDate(new Date());

		try {
			projectDao.save(newEntity);
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

	}

	public ProjectEntity createProjectForNewUser(UserEntity userEntity) {

		ProjectEntity newEntity = new ProjectEntity();
		newEntity.setName("New project for " + userEntity.getFirstName() + " " + userEntity.getLastName());
		newEntity.setStartDate(new Date());
		newEntity.setFinishDate(new Date());

		try {
			projectDao.save(newEntity);
			return newEntity;	
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

		return null;

	}

	public void createProject(TaskEntity taskEntity) {

		try {
			projectDao.save(projectADD);
			projectADD = new ProjectEntity();
			setProjects(projectDao.findByTask(taskEntity));
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

	}

	public void update(ProjectEntity projectEntity) {

		try {
			projectDao.update(projectEntity);
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

	}

	public List<ProjectEntity> loadAll() {
		try {
			return projectDao.findAll();
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

		return null;
	}

	public List<ProjectEntity> findByTaskID(TaskEntity taskEntity) {
		try {
			return projectDao.findByTask(taskEntity);
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

		return null;
	}

	public void delete(ProjectEntity projectEntity) {

		try {
			projectDao.delete(projectEntity);
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

	}

	public ProjectEntity findById(Long id) {
		try {
			return projectDao.findById(id);

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
		FacesMessage msg = new FacesMessage("Task File Selected", ((ProjectEntity) event.getObject()).getId().toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowUnselect(UnselectEvent event) {
		FacesMessage msg = new FacesMessage("Task File Unselect", ((ProjectEntity) event.getObject()).getId().toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public List<ProjectEntity> getProjects() {
		return projects;
	}

	public void setProjects(List<ProjectEntity> projects) {
		this.projects = projects;
	}

	public ProjectDao getProjectDao() {
		return projectDao;
	}

	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}

	public ProjectEntity getSelectedProject() {
		return selectedProject;
	}

	public void setSelectedProject(ProjectEntity selectedProject) {
		this.selectedProject = selectedProject;
	}

	public ProjectEntity getProjectADD() {
		return projectADD;
	}

	public void setProjectADD(ProjectEntity projectADD) {
		this.projectADD = projectADD;
	}

	public ProjectEntity getSelectedCurrentProject() {
		return selectedCurrentProject;
	}

	public void setSelectedCurrentProject(ProjectEntity selectedCurrentProject) {
		this.selectedCurrentProject = selectedCurrentProject;
	}

	public UserEntity getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(UserEntity selectedUser) {
		this.selectedUser = selectedUser;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public UserEntity getSelectedUserADD() {
		return selectedUserADD;
	}

	public void setSelectedUserADD(UserEntity selectedUserADD) {
		this.selectedUserADD = selectedUserADD;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public List<UserEntity> getSearchUserEntities() {
		return searchUserEntities;
	}

	public void setSearchUserEntities(List<UserEntity> searchUserEntities) {
		this.searchUserEntities = searchUserEntities;
	}

}
