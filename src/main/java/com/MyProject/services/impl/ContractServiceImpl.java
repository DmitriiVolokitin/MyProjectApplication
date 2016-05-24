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

import com.MyProject.domain.commons.ContractEntity;
import com.MyProject.domain.project.ProjectEntity;
import com.MyProject.services.ContractService;
import com.MyProject.dao.ContractDao;

@javax.faces.bean.ManagedBean
@Scope(value="session")
public class ContractServiceImpl implements ContractService, Serializable {

	private static final long serialVersionUID = 8264924564440250171L;

	private ContractDao contractDao;
	
	private List<ContractEntity> contracts = new ArrayList<ContractEntity>();
	
	private ContractEntity selectedContract = new ContractEntity();
	private ContractEntity contractADD = new ContractEntity();
	
	@PostConstruct
	public void init() {
		System.out.println("New bean ContractServiceImpl");
	}
	
	public void createContractOnStartNewDataBase(ProjectEntity projectEntity) {

		ContractEntity newEntity;

		newEntity = new ContractEntity();
		newEntity.setName("New Contract");
		newEntity.setProject(projectEntity);
		
		newEntity.setStartDate(new Date());
		newEntity.setFinishDate(new Date());

		try {
			contractDao.save(newEntity);
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

	}
	
	public void clear() {
		setContractADD(new ContractEntity());
	}
	
	public void createProjectContract(ProjectEntity projectEntity) {

		try {
			contractADD.setProject(projectEntity);
			contractDao.save(contractADD);
			contractADD = new ContractEntity();
			setContracts(contractDao.findByProject(projectEntity));
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

	}
	
	public void update(ContractEntity contractEntity) {
		
		try {
			contractDao.update(contractEntity);
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

	}

	public List<ContractEntity> loadAll() {
		try {
			return contractDao.findAll();
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

		return null;
	}
	
	public List<ContractEntity> findByProject(ProjectEntity projectEntity) {
		try {
			return contractDao.findByProject(projectEntity);
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

		return null;
	}

	public void delete(ContractEntity contract, ProjectEntity projectEntity) {

		try {
			contractDao.delete(contract);
			selectedContract = new ContractEntity();
			setContracts(contractDao.findByProject(projectEntity));
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

	}

	public ContractEntity findById(Long id) {
		try {
			return contractDao.findById(id);

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
		FacesMessage msg = new FacesMessage("Task File Selected", ((ContractEntity) event.getObject()).getId().toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowUnselect(UnselectEvent event) {
		FacesMessage msg = new FacesMessage("Task File Unselect", ((ContractEntity) event.getObject()).getId().toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public List<ContractEntity> getContracts() {
		return contracts;
	}

	public void setContracts(List<ContractEntity> contracts) {
		this.contracts = contracts;
	}
	
	public void setContracts(ProjectEntity projectEntity) {
		this.contracts = findByProject(projectEntity);
	}

	public ContractDao getContractDao() {
		return contractDao;
	}

	public void setContractDao(ContractDao contractDao) {
		this.contractDao = contractDao;
	}

	public ContractEntity getSelectedContract() {
		return selectedContract;
	}

	public void setSelectedContract(ContractEntity selectedContract) {
		this.selectedContract = selectedContract;
	}

	public ContractEntity getContractADD() {
		return contractADD;
	}

	public void setContractADD(ContractEntity contractADD) {
		this.contractADD = contractADD;
	}


}
