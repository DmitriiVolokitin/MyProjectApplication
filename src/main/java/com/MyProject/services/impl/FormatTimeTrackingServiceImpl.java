package com.MyProject.services.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.component.inputtext.InputText;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.springframework.context.annotation.Scope;

import com.MyProject.domain.commons.FormatTimeTrackingEntity;
import com.MyProject.domain.project.ProjectEntity;
import com.MyProject.services.FormatTimeTrackingService;
import com.MyProject.dao.FormatTimeTrackingDao;

@javax.faces.bean.ManagedBean
@Scope(value="session")
public class FormatTimeTrackingServiceImpl implements FormatTimeTrackingService, Serializable {

	private static final long serialVersionUID = -1487407542666289735L;

	private FormatTimeTrackingDao formatTimeTrackingDao;

	private List<FormatTimeTrackingEntity> formatTimeTrackings = new ArrayList<FormatTimeTrackingEntity>();

	private FormatTimeTrackingEntity selectedFormatTimeTracking = new FormatTimeTrackingEntity();
	private FormatTimeTrackingEntity formatTimeTrackingADD = new FormatTimeTrackingEntity();

	@PostConstruct
	public void init() {
		System.out.println("New bean FormatTimeTrackingServiceImpl");
	}

	public void clear() {

		setFormatTimeTrackingADD(new FormatTimeTrackingEntity());

	}

	public void createTaskFormatTimeTrackingByProjectOnStartNewDataBase(ProjectEntity projectEntity) {

		List<FormatTimeTrackingEntity> newFormatTimeTrackings =new ArrayList<FormatTimeTrackingEntity>();
		FormatTimeTrackingEntity newEntity;

		newEntity = new FormatTimeTrackingEntity();
		newEntity.setName("Full time");
		newEntity.setProject(projectEntity);
		newEntity.setConstant(true);
		newFormatTimeTrackings.add(newEntity);

		newEntity = new FormatTimeTrackingEntity();
		newEntity.setName("By time");
		newEntity.setProject(projectEntity);
		newEntity.setConstant(true);
		newFormatTimeTrackings.add(newEntity);

		newEntity = new FormatTimeTrackingEntity();
		newEntity.setName("By specification");
		newEntity.setProject(projectEntity);
		newEntity.setConstant(true);
		newFormatTimeTrackings.add(newEntity);

		try {
			for(FormatTimeTrackingEntity entityList : newFormatTimeTrackings) {
				formatTimeTrackingDao.save(entityList);
			}
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

	}

	public void createFormatTimeTracking(ProjectEntity projectEntity, FormatTimeTrackingEntity formatTimeTrackingEntity) {

		try {
			formatTimeTrackingEntity.setProject(projectEntity);
			formatTimeTrackingDao.save(formatTimeTrackingEntity);
			formatTimeTrackingADD = new FormatTimeTrackingEntity();
			setFormatTimeTrackings(formatTimeTrackingDao.findByProject(projectEntity));
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

	}

	public void update(ProjectEntity projectEntity, FormatTimeTrackingEntity formatTimeTrackingEntity) {

		try {
			formatTimeTrackingDao.update(formatTimeTrackingEntity);
			setFormatTimeTrackings(formatTimeTrackingDao.findByProject(projectEntity));
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

	}

	public List<FormatTimeTrackingEntity> loadAll() {

		try {
			return formatTimeTrackingDao.findAll();
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

		return null;
	}

	public void delete(ProjectEntity projectEntity, FormatTimeTrackingEntity formatTimeTracking) {

		try {
			formatTimeTrackingDao.delete(formatTimeTracking);
			selectedFormatTimeTracking = new FormatTimeTrackingEntity();
			setFormatTimeTrackings(formatTimeTrackingDao.findByProject(projectEntity));
			FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "File removed!", null));
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

	}

	public FormatTimeTrackingEntity findById(Long id) {

		try {
			return formatTimeTrackingDao.findById(id);

		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

		return null;
	}

	public List<FormatTimeTrackingEntity> findByProject(ProjectEntity projectEntity) {
		try {
			return formatTimeTrackingDao.findByProject(projectEntity);
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

		return null;
	}

	public boolean checkAvailableByProject(AjaxBehaviorEvent event, ProjectEntity projectEntity) {

		InputText nameFormatTimeTracking = (InputText) event.getSource();
		String value = (String) nameFormatTimeTracking.getValue();

		boolean available = formatTimeTrackingDao.checkAvailableByProject(projectEntity, value);

		if (!available) {
			FacesMessage message = constructErrorMessage(null, String.format(getMessageBundle().getString("ExistsMsg")));
			getFacesContext().addMessage(event.getComponent().getClientId(), message);
		} else {
			FacesMessage message = constructInfoMessage(null, String.format(getMessageBundle().getString("AvailableMsg")));
			getFacesContext().addMessage(event.getComponent().getClientId(), message);
		}

		return available;

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
		FacesMessage msg = new FacesMessage("Selected", ((FormatTimeTrackingEntity) event.getObject()).getId().toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowUnselect(UnselectEvent event) {
		FacesMessage msg = new FacesMessage("Unselected", ((FormatTimeTrackingEntity) event.getObject()).getId().toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public FormatTimeTrackingDao getFormatTimeTrackingDao() {
		return formatTimeTrackingDao;
	}

	public void setFormatTimeTrackingDao(FormatTimeTrackingDao formatTimeTrackingDao) {
		this.formatTimeTrackingDao = formatTimeTrackingDao;
	}

	public FormatTimeTrackingEntity getSelectedFormatTimeTracking() {
		return selectedFormatTimeTracking;
	}

	public void setSelectedFormatTimeTracking(FormatTimeTrackingEntity selectedFormatTimeTracking) {
		this.selectedFormatTimeTracking = selectedFormatTimeTracking;
	}

	public FormatTimeTrackingEntity getFormatTimeTrackingADD() {
		return formatTimeTrackingADD;
	}

	public void setFormatTimeTrackingADD(FormatTimeTrackingEntity formatTimeTrackingADD) {
		this.formatTimeTrackingADD = formatTimeTrackingADD;
	}

	public List<FormatTimeTrackingEntity> getFormatTimeTrackings() {
		return formatTimeTrackings;
	}

	public void setFormatTimeTrackings(List<FormatTimeTrackingEntity> formatTimeTrackings) {
		this.formatTimeTrackings = formatTimeTrackings;
	}

	public void setFormatTimeTrackings(ProjectEntity projectEntity) {
		this.formatTimeTrackings = findByProject(projectEntity);
	}

}
