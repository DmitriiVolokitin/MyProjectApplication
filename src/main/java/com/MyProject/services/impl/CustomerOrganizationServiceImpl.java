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

import com.MyProject.domain.commons.CustomerOrganizationEntity;
import com.MyProject.domain.project.ProjectEntity;
import com.MyProject.services.CustomerOrganizationService;
import com.MyProject.dao.CustomerOrganizationDao;

@javax.faces.bean.ManagedBean
@Scope(value="session")
public class CustomerOrganizationServiceImpl implements CustomerOrganizationService, Serializable {

	private static final long serialVersionUID = 161944124778559281L;

	private CustomerOrganizationDao customerOrganizationDao;
	
	private List<CustomerOrganizationEntity> customerOrganizations = new ArrayList<CustomerOrganizationEntity>();
	
	private CustomerOrganizationEntity selectedCustomerOrganization = new CustomerOrganizationEntity();
	private CustomerOrganizationEntity customerOrganizationADD = new CustomerOrganizationEntity();
	
	@PostConstruct
	public void init() {
		System.out.println("New bean CustomerOrganizationServiceImpl");
	}
	
	public void createCustomerOrganizationOnStartNewDataBase(ProjectEntity projectEntity) {

		CustomerOrganizationEntity newEntity;

		newEntity = new CustomerOrganizationEntity();
		newEntity.setName("New Customer Organization");
		newEntity.setProject(projectEntity);

		try {
			customerOrganizationDao.save(newEntity);
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

	}
	
	public void clear() {
		setCustomerOrganizationADD(new CustomerOrganizationEntity());
	}
	
	public void update(CustomerOrganizationEntity contractorOrganizationEntity, ProjectEntity projectEntity) {
		
		try {
			customerOrganizationDao.update(contractorOrganizationEntity);
			setCustomerOrganizations(customerOrganizationDao.findByProject(projectEntity));
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

	}

	public void createProjectCustomerOrganization(ProjectEntity projectEntity) {

		try {
			customerOrganizationADD.setProject(projectEntity);
			customerOrganizationDao.save(customerOrganizationADD);
			customerOrganizationADD = new CustomerOrganizationEntity();
			setCustomerOrganizations(customerOrganizationDao.findByProject(projectEntity));
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

	}

	public List<CustomerOrganizationEntity> loadAll() {
		try {
			return customerOrganizationDao.findAll();
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

		return null;
	}
	
	public List<CustomerOrganizationEntity> findByProject(ProjectEntity projectEntity) {
		try {
			return customerOrganizationDao.findByProject(projectEntity);
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

		return null;
	}

	public void delete(CustomerOrganizationEntity file, ProjectEntity projectEntity) {

		try {
			customerOrganizationDao.delete(file);
			selectedCustomerOrganization = new CustomerOrganizationEntity();
			setCustomerOrganizations(customerOrganizationDao.findByProject(projectEntity));
			FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "File removed!", null));
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

	}

	public CustomerOrganizationEntity findById(Long id) {
		try {
			return customerOrganizationDao.findById(id);

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
		FacesMessage msg = new FacesMessage("Task File Selected", ((CustomerOrganizationEntity) event.getObject()).getId().toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowUnselect(UnselectEvent event) {
		FacesMessage msg = new FacesMessage("Task File Unselect", ((CustomerOrganizationEntity) event.getObject()).getId().toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public CustomerOrganizationDao getCustomerOrganizationDao() {
		return customerOrganizationDao;
	}

	public void setCustomerOrganizationDao(CustomerOrganizationDao customerOrganizationDao) {
		this.customerOrganizationDao = customerOrganizationDao;
	}

	public CustomerOrganizationEntity getSelectedCustomerOrganization() {
		return selectedCustomerOrganization;
	}

	public void setSelectedCustomerOrganization(CustomerOrganizationEntity selectedCustomerOrganization) {
		this.selectedCustomerOrganization = selectedCustomerOrganization;
	}

	public CustomerOrganizationEntity getCustomerOrganizationADD() {
		return customerOrganizationADD;
	}

	public void setCustomerOrganizationADD(CustomerOrganizationEntity customerOrganizationADD) {
		this.customerOrganizationADD = customerOrganizationADD;
	}

	public List<CustomerOrganizationEntity> getCustomerOrganizations() {
		return customerOrganizations;
	}

	public void setCustomerOrganizations(List<CustomerOrganizationEntity> customerOrganizations) {
		this.customerOrganizations = customerOrganizations;
	}
	
	public void setCustomerOrganizations(ProjectEntity projectEntity) {
		this.customerOrganizations = findByProject(projectEntity);
	}
	
}
