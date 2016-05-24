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

import com.MyProject.domain.commons.ContractorOrganizationEntity;
import com.MyProject.domain.project.ProjectEntity;
import com.MyProject.services.ContractorOrganizationService;
import com.MyProject.dao.ContractorOrganizationDao;

@javax.faces.bean.ManagedBean
@Scope(value="session")
public class ContractorOrganizationServiceImpl implements ContractorOrganizationService, Serializable {

	
	private static final long serialVersionUID = -5783536163075776504L;

	private ContractorOrganizationDao contractorOrganizationDao;
	
	private List<ContractorOrganizationEntity> contractorOrganizations = new ArrayList<ContractorOrganizationEntity>();
	
	private ContractorOrganizationEntity selectedContractorOrganization = new ContractorOrganizationEntity();
	private ContractorOrganizationEntity contractorOrganizationADD = new ContractorOrganizationEntity();
	
	@PostConstruct
	public void init() {
		System.out.println("New bean ContractorOrganizationServiceImplContractorOrganizationServiceImplContractorOrganizationServiceImplContractorOrganizationServiceImplContractorOrganizationServiceImpl");
	}
	
	public void createContractorOrganizationOnStartNewDataBase(ProjectEntity projectEntity) {

		ContractorOrganizationEntity newEntity;

		newEntity = new ContractorOrganizationEntity();
		newEntity.setName("New Contractor Organization");
		newEntity.setProject(projectEntity);

		try {
			contractorOrganizationDao.save(newEntity);
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

	}
	
	public void clear() {
		setContractorOrganizationADD(new ContractorOrganizationEntity());
	}
	
	public void update(ContractorOrganizationEntity contractorOrganizationEntity, ProjectEntity projectEntity) {
		
		try {
			contractorOrganizationDao.update(contractorOrganizationEntity);
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

	}

	public void createProjectContractorOrganization(ProjectEntity projectEntity) {

		try {
			contractorOrganizationADD.setProject(projectEntity);
			contractorOrganizationDao.save(contractorOrganizationADD);
			contractorOrganizationADD = new ContractorOrganizationEntity();
			setContractorOrganizations(contractorOrganizationDao.findByProject(projectEntity));
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

	}

	public List<ContractorOrganizationEntity> loadAll() {
		try {
			return contractorOrganizationDao.findAll();
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

		return null;
	}
	
	public List<ContractorOrganizationEntity> findByProject(ProjectEntity projectEntity) {
		try {
			return contractorOrganizationDao.findByProject(projectEntity);
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

		return null;
	}

	public void delete(ContractorOrganizationEntity file, ProjectEntity projectEntity) {

		try {
			contractorOrganizationDao.delete(file);
			selectedContractorOrganization = new ContractorOrganizationEntity();
			setContractorOrganizations(contractorOrganizationDao.findByProject(projectEntity));
			FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "File removed!", null));
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

	}

	public ContractorOrganizationEntity findById(Long id) {
		try {
			return contractorOrganizationDao.findById(id);

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
		FacesMessage msg = new FacesMessage("Task File Selected", ((ContractorOrganizationEntity) event.getObject()).getId().toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowUnselect(UnselectEvent event) {
		FacesMessage msg = new FacesMessage("Task File Unselect", ((ContractorOrganizationEntity) event.getObject()).getId().toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public List<ContractorOrganizationEntity> getContractorOrganizations() {
		return contractorOrganizations;
	}

	public void setContractorOrganizations(List<ContractorOrganizationEntity> contractorOrganizations) {
		this.contractorOrganizations = contractorOrganizations;
	}
	
	public void setContractorOrganizations(ProjectEntity projectEntity) {
		this.contractorOrganizations = findByProject(projectEntity);
	}

	public ContractorOrganizationDao getContractorOrganizationDao() {
		return contractorOrganizationDao;
	}

	public void setContractorOrganizationDao(ContractorOrganizationDao contractorOrganizationDao) {
		this.contractorOrganizationDao = contractorOrganizationDao;
	}

	public ContractorOrganizationEntity getSelectedContractorOrganization() {
		return selectedContractorOrganization;
	}

	public void setSelectedContractorOrganization(ContractorOrganizationEntity selectedContractorOrganization) {
		this.selectedContractorOrganization = selectedContractorOrganization;
	}

	public ContractorOrganizationEntity getContractorOrganizationADD() {
		return contractorOrganizationADD;
	}

	public void setContractorOrganizationADD(ContractorOrganizationEntity contractorOrganizationADD) {
		this.contractorOrganizationADD = contractorOrganizationADD;
	}

	
}
