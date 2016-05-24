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
import com.MyProject.domain.project.ProjectMilestoneEntity;
import com.MyProject.services.ProjectMilestoneService;
import com.MyProject.dao.ProjectDao;
import com.MyProject.dao.ProjectMilestoneDao;

@javax.faces.bean.ManagedBean
@Scope(value="session")
public class ProjectMilestoneServiceImpl implements ProjectMilestoneService, Serializable {

	private static final long serialVersionUID = -2575875881649652560L;
	
	private ProjectDao projectDao;
	private ProjectMilestoneDao projectMilestoneDao;
	
	private List<ProjectMilestoneEntity> projectMilestones = new ArrayList<ProjectMilestoneEntity>();
	
	private ProjectMilestoneEntity selectedProjectMilestone = new ProjectMilestoneEntity();
	private ProjectMilestoneEntity projectMilestoneADD = new ProjectMilestoneEntity();
	
	@PostConstruct
	public void init() {
		System.out.println("New bean ProjectMilestoneServiceImpl");
	}
	
	public void clear() {
		setProjectMilestoneADD(new ProjectMilestoneEntity());
	}
	
	public void createProjectMilestoneOnStartNewDataBase(ProjectEntity projectEntity) {

		List<ProjectMilestoneEntity> newProjectMilestone =new ArrayList<ProjectMilestoneEntity>();
		ProjectMilestoneEntity newEntity;
		
		newEntity = new ProjectMilestoneEntity();
		newEntity.setName("Initiation");
		newEntity.setProject(projectEntity);
		newEntity.setStartDate(new Date());
		newEntity.setFinishDate(new Date());
		newProjectMilestone.add(newEntity);

		try {
			for(ProjectMilestoneEntity entityList : newProjectMilestone) {
				projectMilestoneDao.save(entityList);
			}
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

	}
	
	public void createProjectMilestone(ProjectEntity projectEntity, ProjectMilestoneEntity projectMilestoneEntity) {

		try {
			projectMilestoneEntity.setProject(projectEntity);
			projectMilestoneDao.save(projectMilestoneEntity);
			projectMilestoneADD = new ProjectMilestoneEntity();
			setProjectMilestones(projectMilestoneDao.findByProject(projectEntity));
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

	}
	
	public void update(ProjectMilestoneEntity projectMilestoneEntity) {
		
		try {
			projectMilestoneDao.update(projectMilestoneEntity);
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

	}

	public List<ProjectMilestoneEntity> loadAll() {
		try {
			return projectMilestoneDao.findAll();
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

		return null;
	}
	
	public List<ProjectMilestoneEntity> findByProject(ProjectEntity projectEntity) {
		try {
			return projectMilestoneDao.findByProject(projectEntity);
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

		return null;
	}

	public void delete(ProjectMilestoneEntity projectMilestone, ProjectEntity projectEntity) {

		try {
			projectMilestoneDao.delete(projectMilestone);
			selectedProjectMilestone = new ProjectMilestoneEntity();
			setProjectMilestones(projectMilestoneDao.findByProject(projectEntity));
			FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "File removed!", null));
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

	}

	public ProjectMilestoneEntity findById(Long id) {
		try {
			return projectMilestoneDao.findById(id);

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
		FacesMessage msg = new FacesMessage("Task File Selected", ((ProjectMilestoneEntity) event.getObject()).getId().toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowUnselect(UnselectEvent event) {
		FacesMessage msg = new FacesMessage("Task File Unselect", ((ProjectMilestoneEntity) event.getObject()).getId().toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public List<ProjectMilestoneEntity> getProjectMilestones() {
		return projectMilestones;
	}

	public void setProjectMilestones(List<ProjectMilestoneEntity> projectMilestones) {
		this.projectMilestones = projectMilestones;
	}
	
	public void setProjectMilestones(ProjectEntity projectEntity) {
		this.projectMilestones = findByProject(projectEntity);
	}

	public ProjectMilestoneDao getProjectMilestoneDao() {
		return projectMilestoneDao;
	}

	public void setProjectMilestoneDao(ProjectMilestoneDao projectMilestoneDao) {
		this.projectMilestoneDao = projectMilestoneDao;
	}

	public ProjectMilestoneEntity getProjectMilestoneADD() {
		return projectMilestoneADD;
	}

	public void setProjectMilestoneADD(ProjectMilestoneEntity projectMilestoneADD) {
		this.projectMilestoneADD = projectMilestoneADD;
	}

	public ProjectMilestoneEntity getSelectedProjectMilestone() {
		return selectedProjectMilestone;
	}

	public void setSelectedProjectMilestone(ProjectMilestoneEntity selectedProjectMilestone) {
		this.selectedProjectMilestone = selectedProjectMilestone;
	}

	public ProjectDao getProjectDao() {
		return projectDao;
	}

	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}

}
