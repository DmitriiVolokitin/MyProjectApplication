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

import com.MyProject.domain.commons.ProjectUserRoleEntity;
import com.MyProject.domain.project.ProjectEntity;
import com.MyProject.domain.user.UserEntity;
import com.MyProject.domain.user.UserRoleEntity;
import com.MyProject.services.ProjectUserRoleService;
import com.MyProject.dao.ProjectUserRoleDao;
import com.MyProject.dao.UserDao;
import com.MyProject.dao.UserRoleDao;

@javax.faces.bean.ManagedBean
@Scope(value="session")
public class ProjectUserRoleServiceImpl implements ProjectUserRoleService, Serializable {

	private static final long serialVersionUID = 4970009585487143230L;

	private ProjectServiceImpl projectServiceImpl;
	private ProjectUserRoleDao projectUserRoleDao;
	private UserDao userDao;
	private UserRoleDao userRoleDao;
	
	private ProjectUserRoleEntity selectedProjectUserRoleEntity;

	private List<ProjectEntity> projects = new ArrayList<ProjectEntity>();
	private List<ProjectEntity> projectsByProjectManager = new ArrayList<ProjectEntity>();
	private ProjectEntity selectedProject = new ProjectEntity();
	private ProjectEntity projectADD = new ProjectEntity();

	private List<UserEntity> users = new ArrayList<UserEntity>();
	private List<UserEntity> searchUserEntities = new ArrayList<UserEntity>();
	private UserEntity selectedUser = new UserEntity();
	private UserEntity selectedUserADD = new UserEntity();
	private UserEntity userADD = new UserEntity();

	private List<UserRoleEntity> userRoles = new ArrayList<UserRoleEntity>();
	private List<UserRoleEntity> searchUserRoleEntities = new ArrayList<UserRoleEntity>();
	private UserRoleEntity selectedUserRole = new UserRoleEntity();
	private UserRoleEntity selectedUserRoleADD = new UserRoleEntity();
	private UserRoleEntity userRoleADD = new UserRoleEntity();

	private ProjectEntity newProject = new ProjectEntity();
	private UserRoleEntity newUserRole = new UserRoleEntity();

	private String searchText;

	@PostConstruct
	public void init() {
		System.out.println("New bean ProjectUserRoleServiceImpl");
	}
//
//		setProjects(projectUserRoleDao.findProject(userEntity, true));
//
//		if (getProjects().isEmpty()) {
//			setNewProject(projectServiceImpl.createProjectForNewUser());
//
//			setUserRoles(projectUserRoleDao.findUserRole(getNewProject(), userEntity, true));
//
//			if (getUserRoles().isEmpty()) {
//				setNewUserRole(userRoleDao.findByName("Project Manager"));
//			}
//
//			createProjectUserRole(getNewProject(), userEntity, getNewUserRole());
//		}
//		setProjects(projectUserRoleDao.findProject(userEntity, true));
//		projectServiceImpl.setProjects(getProjects());
//
//		ProjectEntity projectOpenByDefault = null;
//
//		for (ProjectEntity projectEntityListElement : projectServiceImpl.getProjects()) {
//			if (projectEntityListElement.getOpenByDefault()) {
//				projectOpenByDefault = projectEntityListElement;
//			}
//		}
//
//		if (projectOpenByDefault != null) {
//			projectServiceImpl.setSelectedCurrentProject(projectOpenByDefault);
//			projectServiceImpl.getTaskServiceImpl().initByProject(projectOpenByDefault);
//		}
//
//		setSearchUserRoleEntities(userRoleDao.findAll());
//
//	}

	public void clear() {
		setUserADD(new UserEntity());
	}

	public void clearUser() {

		setUserADD(new UserEntity());
		setSearchText("");
		setSearchUserEntities(new ArrayList<UserEntity>());;

	}

	public void clearUserRole() {

		setUserRoleADD(new UserRoleEntity());

	}

	public void clearProject() {

		setProjectADD(new ProjectEntity());

	}
	
	public void setSelectedProjectAndUpdate(ProjectEntity selectedProject) {
		setSelectedProject(selectedProject);
		setUserByProject(selectedProject);
		
	}

	public void createProjectUserRole() {
		// TODO Auto-generated method stub

	}

	public void createProjectUser(ProjectEntity projectEntity, UserEntity userEntity) {

		ProjectUserRoleEntity projectUserRoleEntity = new ProjectUserRoleEntity();
		projectUserRoleEntity.setProject(projectEntity);
		projectUserRoleEntity.setUser(userEntity);
		projectUserRoleEntity.setUserRole(userRoleDao.findByName("Base"));

		projectUserRoleDao.save(projectUserRoleEntity);

		setUserByProject(projectEntity);

	}

	public void deleteProjectUser(ProjectEntity projectEntity, UserEntity userEntity) {

		for (ProjectUserRoleEntity projectUserRoleEntity : projectUserRoleDao.findEntityByProjectAndUser(projectEntity, userEntity, false)) {

			projectUserRoleDao.delete(projectUserRoleEntity);

		}

		setUserByProject(projectEntity);

	}

	public void createProjectUserRole(ProjectEntity projectEntity, UserEntity userEntity, UserRoleEntity userRoleEntity) {

		ProjectUserRoleEntity projectUserRoleEntity = new ProjectUserRoleEntity();
		projectUserRoleEntity.setProject(projectEntity);
		projectUserRoleEntity.setUser(userEntity);
		projectUserRoleEntity.setUserRole(userRoleEntity);

		projectUserRoleDao.save(projectUserRoleEntity);

		setUserRoleByProjectAndUser(projectEntity, userEntity);

	}
	
	public void createProjectUserRole(ProjectEntity projectEntity, UserEntity userEntity) {
		
		ProjectUserRoleEntity projectUserRoleEntity = new ProjectUserRoleEntity();
		
		projectUserRoleEntity.setProject(projectEntity);
		projectUserRoleEntity.setUser(userEntity);
		projectUserRoleEntity.setUserRole(userRoleDao.findByName("Base"));

		projectUserRoleDao.save(projectUserRoleEntity);

		setUserRoleByProjectAndUser(projectEntity, userEntity);

	}
	
	public void deleteProjectUserRole(ProjectEntity projectEntity, UserEntity userEntity, UserRoleEntity userRoleEntity) {

		for (ProjectUserRoleEntity projectUserRoleEntity : projectUserRoleDao.findEntityByProjectUserAndUserRole(projectEntity, userEntity, userRoleEntity, false)) {

			projectUserRoleDao.delete(projectUserRoleEntity);

		}

		setUserRoleByProjectAndUser(projectEntity, userEntity);

	}

	public List<ProjectUserRoleEntity> loadAll() {

		return projectUserRoleDao.findAll();

	}

	public List<ProjectEntity> findProject(UserEntity userEntity) {

		return projectUserRoleDao.findProject(userEntity, true);

	}

	public void setProjectByUser(UserEntity userEntity) {

		setProjects(projectUserRoleDao.findProject(userEntity, true));

	}

	public List<UserEntity> findUser(ProjectEntity projectEntity) {

		return projectUserRoleDao.findUser(projectEntity, true);

	}

	public void setUserByProject(ProjectEntity projectEntity) {

		setUsers(projectUserRoleDao.findUser(projectEntity, true));
		if (!(getUsers().isEmpty())) {
			setSelectedUser(getUsers().get(0));
			setUserRoleByProjectAndUser(getSelectedProject(), getSelectedUser());
		}
		else{
			setUserRoles(new ArrayList<UserRoleEntity>());
		}
		
		
	}

	public List<UserRoleEntity> findUserRole(ProjectEntity projectEntity, UserEntity userEntity) {

		return projectUserRoleDao.findUserRole(projectEntity, userEntity, true);

	}

	public void setUserRoleByProjectAndUser(ProjectEntity projectEntity, UserEntity userEntity) {

		setUserRoles(projectUserRoleDao.findUserRole(projectEntity, userEntity, true));
		
		if (!(getUserRoles().isEmpty())) {
			setSelectedUserRole(getUserRoles().get(0));
		}

	}
	
	public void setUsersAndUserRoliesByProject(ProjectEntity projectEntity) {
		
		setUsers(projectUserRoleDao.findUser(projectEntity, true));
		
		if (!(getUsers().isEmpty())) {
			
			setSelectedUser(getUsers().get(0));
			
			setUserRoles(projectUserRoleDao.findUserRole(projectEntity, getSelectedUser(), true));
			
			if (!(getUserRoles().isEmpty())) {
				setSelectedUserRole(getUserRoles().get(0));
			}
			
		}

	}

	public void delete(ProjectUserRoleEntity projectUserRoleEntity) {

		projectUserRoleDao.delete(projectUserRoleEntity);

	}

	public void update(ProjectUserRoleEntity projectUserRoleEntity) {

		projectUserRoleDao.update(projectUserRoleEntity);

	}

	public void searchUsersByUserNameOrEmail(String searchText) {

		setSearchUserEntities(userDao.searchUsersByUserNameOrEmail(searchText));

	}

	public ProjectUserRoleEntity findById(Long id) {

		return projectUserRoleDao.findById(id);

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

		FacesMessage msg = new FacesMessage("Project User Role Selected", ((UserRoleEntity) event.getObject()).getId().toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);

	}

	public void onRowUnselect(UnselectEvent event) {

		FacesMessage msg = new FacesMessage("Project User Role Unselect", ((UserRoleEntity) event.getObject()).getId().toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);

	}

	public ProjectUserRoleDao getProjectUserRoleDao() {

		return projectUserRoleDao;

	}

	public void setProjectUserRoleDao(ProjectUserRoleDao projectUserRoleDao) {

		this.projectUserRoleDao = projectUserRoleDao;

	}

	public List<ProjectEntity> getProjects() {
		return projects;
	}

	public void setProjects(List<ProjectEntity> projects) {
		this.projects = projects;
	}

	public ProjectServiceImpl getProjectServiceImpl() {
		return projectServiceImpl;
	}

	public void setProjectServiceImpl(ProjectServiceImpl projectServiceImpl) {
		this.projectServiceImpl = projectServiceImpl;
	}

	public ProjectEntity getNewProject() {
		return newProject;
	}

	public void setNewProject(ProjectEntity newProject) {
		this.newProject = newProject;
	}

	public UserRoleEntity getNewUserRole() {
		return newUserRole;
	}

	public void setNewUserRole(UserRoleEntity newUserRole) {
		this.newUserRole = newUserRole;
	}

	public UserRoleDao getUserRoleDao() {
		return userRoleDao;
	}

	public void setUserRoleDao(UserRoleDao userRoleDao) {
		this.userRoleDao = userRoleDao;
	}

	public List<UserRoleEntity> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<UserRoleEntity> userRoles) {
		this.userRoles = userRoles;
	}

	public ProjectUserRoleEntity getSelectedProjectUserRoleEntity() {
		return selectedProjectUserRoleEntity;
	}

	public void setSelectedProjectUserRoleEntity(ProjectUserRoleEntity selectedProjectUserRoleEntity) {
		this.selectedProjectUserRoleEntity = selectedProjectUserRoleEntity;
	}

	public void setSelectedProject(ProjectEntity selectedProject) {
		this.selectedProject = selectedProject;
	}
	
	public UserEntity getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(UserEntity selectedUser) {
		this.selectedUser = selectedUser;
	}

	public List<UserEntity> getUsers() {
		return users;
	}

	public void setUsers(List<UserEntity> users) {
		this.users = users;
	}

	public ProjectEntity getSelectedProject() {
		return selectedProject;
	}

	public UserRoleEntity getSelectedUserRole() {
		return selectedUserRole;
	}

	public void setSelectedUserRole(UserRoleEntity selectedUserRole) {
		this.selectedUserRole = selectedUserRole;
	}

	public UserEntity getUserADD() {
		return userADD;
	}

	public void setUserADD(UserEntity userADD) {
		this.userADD = userADD;
	}

	public UserRoleEntity getUserRoleADD() {
		return userRoleADD;
	}

	public void setUserRoleADD(UserRoleEntity userRoleADD) {
		this.userRoleADD = userRoleADD;
	}

	public ProjectEntity getProjectADD() {
		return projectADD;
	}

	public void setProjectADD(ProjectEntity projectADD) {
		this.projectADD = projectADD;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public UserEntity getSelectedUserADD() {
		return selectedUserADD;
	}

	public void setSelectedUserADD(UserEntity selectedUserADD) {
		this.selectedUserADD = selectedUserADD;
	}

	public List<UserEntity> getSearchUserEntities() {
		return searchUserEntities;
	}

	public void setSearchUserEntities(List<UserEntity> searchUserEntities) {
		this.searchUserEntities = searchUserEntities;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public List<UserRoleEntity> getSearchUserRoleEntities() {
		return searchUserRoleEntities;
	}

	public void setSearchUserRoleEntities(List<UserRoleEntity> searchUserRoleEntities) {
		this.searchUserRoleEntities = searchUserRoleEntities;
	}

	public UserRoleEntity getSelectedUserRoleADD() {
		return selectedUserRoleADD;
	}

	public void setSelectedUserRoleADD(UserRoleEntity selectedUserRoleADD) {
		this.selectedUserRoleADD = selectedUserRoleADD;
	}

	public List<ProjectEntity> getProjectsByProjectManager() {
		return projectsByProjectManager;
	}

	public void setProjectsByProjectManager(List<ProjectEntity> projectsByProjectManager) {
		this.projectsByProjectManager = projectsByProjectManager;
	}

}
