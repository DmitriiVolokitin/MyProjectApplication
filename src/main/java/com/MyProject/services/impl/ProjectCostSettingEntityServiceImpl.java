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

import com.MyProject.domain.project.ProjectCostSettingEntity;
import com.MyProject.domain.project.ProjectEntity;
import com.MyProject.services.ProjectCostSettingService;
import com.MyProject.dao.FormatTimeTrackingDao;
import com.MyProject.dao.ProjectCostSettingDao;

@javax.faces.bean.ManagedBean
@Scope(value="session")
public class ProjectCostSettingEntityServiceImpl implements ProjectCostSettingService, Serializable {

	private static final long serialVersionUID = 8264924564440250171L;

	private ProjectCostSettingDao projectCostSettingDao;
	private FormatTimeTrackingDao formatTimeTrackingDao;
	
	private List<ProjectCostSettingEntity> projectCostSettings = new ArrayList<ProjectCostSettingEntity>();
	
	private ProjectCostSettingEntity selectedProjectCostSetting = new ProjectCostSettingEntity();
	private ProjectCostSettingEntity projectCostSettingADD = new ProjectCostSettingEntity();
	
	@PostConstruct
	public void init() {
		System.out.println("New bean ProjectCostSettingEntityServiceImpl");
	}
	
	public void createProjectCostSettingOnStartNewDataBase(ProjectEntity projectEntity) {

		List<ProjectCostSettingEntity> newProjectCostSetting =new ArrayList<ProjectCostSettingEntity>();
		ProjectCostSettingEntity newEntity;

		newEntity = new ProjectCostSettingEntity();
		newEntity.setProject(projectEntity);
		newEntity.setFormatTimeTracking(formatTimeTrackingDao.findByProjectAndName(projectEntity, "Full time"));
		newEntity.setCost(1.0);
		newEntity.setStartDate(new Date());
		newEntity.setFinishDate(new Date());
		newProjectCostSetting.add(newEntity);
		
		newEntity = new ProjectCostSettingEntity();
		newEntity.setProject(projectEntity);
		newEntity.setFormatTimeTracking(formatTimeTrackingDao.findByProjectAndName(projectEntity, "By time"));
		newEntity.setCost(2.0);
		newEntity.setStartDate(new Date());
		newEntity.setFinishDate(new Date());
		newProjectCostSetting.add(newEntity);
		
		newEntity = new ProjectCostSettingEntity();
		newEntity.setProject(projectEntity);
		newEntity.setFormatTimeTracking(formatTimeTrackingDao.findByProjectAndName(projectEntity, "By specification"));
		newEntity.setCost(1.5);
		newEntity.setStartDate(new Date());
		newEntity.setFinishDate(new Date());
		newProjectCostSetting.add(newEntity);
		
		try {
			for(ProjectCostSettingEntity entityList : newProjectCostSetting) {
				projectCostSettingDao.save(entityList);
			}
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

	}
	
	public void clear() {
		setProjectCostSettingADD(new ProjectCostSettingEntity());
	}
	
	public void createProjectCostSetting(ProjectEntity projectEntity) {

		try {
			projectCostSettingADD.setProject(projectEntity);
			projectCostSettingDao.save(projectCostSettingADD);
			projectCostSettingADD = new ProjectCostSettingEntity();
			setProjectCostSettings(projectCostSettingDao.findByProject(projectEntity));
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

	}
	
	public void update(ProjectCostSettingEntity projectCostSettingEntity) {
		
		try {
			projectCostSettingDao.update(projectCostSettingEntity);
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

	}

	public List<ProjectCostSettingEntity> loadAll() {
		try {
			return projectCostSettingDao.findAll();
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

		return null;
	}
	
	public List<ProjectCostSettingEntity> findByProject(ProjectEntity projectEntity) {
		try {
			return projectCostSettingDao.findByProject(projectEntity);
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

		return null;
	}

	public void delete(ProjectCostSettingEntity projectCostSetting, ProjectEntity projectEntity) {

		try {
			projectCostSettingDao.delete(projectCostSetting);
			selectedProjectCostSetting = new ProjectCostSettingEntity();
			setProjectCostSettings(projectCostSettingDao.findByProject(projectEntity));
			FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "File removed!", null));
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

	}

	public ProjectCostSettingEntity findById(Long id) {
		try {
			return projectCostSettingDao.findById(id);

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
		FacesMessage msg = new FacesMessage("Task File Selected", ((ProjectCostSettingEntity) event.getObject()).getId().toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowUnselect(UnselectEvent event) {
		FacesMessage msg = new FacesMessage("Task File Unselect", ((ProjectCostSettingEntity) event.getObject()).getId().toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public List<ProjectCostSettingEntity> getProjectCostSettings() {
		return projectCostSettings;
	}

	public void setProjectCostSettings(List<ProjectCostSettingEntity> projectCostSettings) {
		this.projectCostSettings = projectCostSettings;
	}
	
	public void setProjectCostSettings(ProjectEntity projectEntity) {
		this.projectCostSettings = findByProject(projectEntity);
	}

	public ProjectCostSettingDao getProjectCostSettingDao() {
		return projectCostSettingDao;
	}

	public void setProjectCostSettingDao(ProjectCostSettingDao projectCostSettingDao) {
		this.projectCostSettingDao = projectCostSettingDao;
	}

	public ProjectCostSettingEntity getSelectedProjectCostSetting() {
		return selectedProjectCostSetting;
	}

	public void setSelectedProjectCostSetting(ProjectCostSettingEntity selectedProjectCostSetting) {
		this.selectedProjectCostSetting = selectedProjectCostSetting;
	}

	public ProjectCostSettingEntity getProjectCostSettingADD() {
		return projectCostSettingADD;
	}

	public void setProjectCostSettingADD(ProjectCostSettingEntity projectCostSettingADD) {
		this.projectCostSettingADD = projectCostSettingADD;
	}

	public FormatTimeTrackingDao getFormatTimeTrackingDao() {
		return formatTimeTrackingDao;
	}

	public void setFormatTimeTrackingDao(FormatTimeTrackingDao formatTimeTrackingDao) {
		this.formatTimeTrackingDao = formatTimeTrackingDao;
	}


}
