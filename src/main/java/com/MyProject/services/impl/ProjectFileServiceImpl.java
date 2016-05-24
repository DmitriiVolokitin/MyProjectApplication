package com.MyProject.services.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.context.annotation.Scope;

import com.MyProject.domain.project.ProjectEntity;
import com.MyProject.domain.project.ProjectFileEntity;
import com.MyProject.services.ProjectFileService;
import com.MyProject.dao.ProjectFileDao;

@javax.faces.bean.ManagedBean
@Scope(value="session")
public class ProjectFileServiceImpl implements ProjectFileService, Serializable {

	private static final long serialVersionUID = 1414008814729096236L;

	private ProjectFileDao projectFileDao;

	private List<ProjectFileEntity> files = new ArrayList<ProjectFileEntity>();

	private ProjectFileEntity selectedProjectFile = new ProjectFileEntity();
	private ProjectFileEntity fileADD = new ProjectFileEntity();
	private UploadedFile file;

	@PostConstruct
	public void init() {
		System.out.println("New bean ProjectFileServiceImpl");
		setFiles(projectFileDao.findAll());
	}

	public void clear() {
		setfileADD(new ProjectFileEntity());
	}

	public StreamedContent getFileToDownload(ProjectFileEntity file){

		InputStream stream = new ByteArrayInputStream(file.getFileData());
		StreamedContent fileToDownload = new DefaultStreamedContent(stream, file.getType(), file.getName());

		return fileToDownload;

	}

	public void handleFileUpload(FileUploadEvent event) {  
        setFile(event.getFile());
        if(getFile() != null) {
        	fileADD.setName(file.getFileName());
			fileADD.setType(file.getContentType());
			fileADD.setFileData(file.getContents());
        }
    }

	public void createProjectFile(ProjectEntity projectEntity) {

		try {
			fileADD.setProject(projectEntity);
			projectFileDao.save(fileADD);
			setFile(null);
			fileADD = new ProjectFileEntity();
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

	}

	public void update(ProjectFileEntity projectFileEntity) {

		try {
			projectFileDao.update(projectFileEntity);
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

	}

	public List<ProjectFileEntity> loadAll() {
		try {
			return projectFileDao.findAll();
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

		return null;
	}

	public void delete(ProjectFileEntity projectFile, ProjectEntity projectEntity) {

		try {
			projectFileDao.delete(projectFile);
			selectedProjectFile = new ProjectFileEntity();
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

	}

	public ProjectFileEntity findById(Long id) {
		try {
			return projectFileDao.findById(id);

		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}
		return null;
	}
	
	public List<ProjectFileEntity> findByProject(ProjectEntity projectEntity, boolean commons) {
		try {
			return projectFileDao.findByProject(projectEntity, commons);
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
		FacesMessage msg = new FacesMessage("Task File Selected", ((ProjectFileEntity) event.getObject()).getId().toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowUnselect(UnselectEvent event) {
		FacesMessage msg = new FacesMessage("Task File Unselect", ((ProjectFileEntity) event.getObject()).getId().toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public ProjectFileDao getProjectFileDao() {
		return projectFileDao;
	}

	public void setProjectFileDao(ProjectFileDao projectFileDao) {
		this.projectFileDao = projectFileDao;
	}

	public ProjectFileEntity getSelectedProjectFile() {
		
		if (selectedProjectFile==null){
			return new ProjectFileEntity();
		}
		
		return selectedProjectFile;
		
	}

	public void setSelectedProjectFile(ProjectFileEntity selectedProjectFile) {
		this.selectedProjectFile = selectedProjectFile;
	}

	public ProjectFileEntity getfileADD() {
		return fileADD;
	}

	public void setfileADD(ProjectFileEntity fileADD) {
		this.fileADD = fileADD;
	}

	public List<ProjectFileEntity> getFiles() {
		return files;
	}

	public void setFiles(List<ProjectFileEntity> files) {
		this.files = files;
	}
	
	public void setFiles(ProjectEntity projectEntity, boolean commons) {
		this.files = findByProject(projectEntity, commons);
	}

	public ProjectFileEntity getFileADD() {
		return fileADD;
	}

	public void setFileADD(ProjectFileEntity fileADD) {
		this.fileADD = fileADD;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

}
