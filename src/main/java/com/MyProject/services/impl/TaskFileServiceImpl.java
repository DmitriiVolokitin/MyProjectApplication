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

import com.MyProject.domain.task.TaskEntity;
import com.MyProject.domain.task.TaskFileEntity;
import com.MyProject.services.TaskFileService;
import com.MyProject.dao.TaskFileDao;

@javax.faces.bean.ManagedBean
@Scope(value="session")
public class TaskFileServiceImpl implements TaskFileService, Serializable {

	private static final long serialVersionUID = 4825337912310849590L;

	private List<TaskFileEntity> files = new ArrayList<TaskFileEntity>();
	private TaskFileDao taskFileDao;
	private TaskFileEntity selectedTaskFile = new TaskFileEntity();
	private TaskFileEntity fileADD = new TaskFileEntity();
	private UploadedFile file;

	@PostConstruct
	public void init() {
		System.out.println("New bean TaskFileServiceImpl");
	}

	public void clear() {
		setFileADD(new TaskFileEntity());
	}

	public StreamedContent getFileToDownload(TaskFileEntity file){
		
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
	
	public void update(TaskFileEntity taskFileEntity) {

		try {
			taskFileDao.update(taskFileEntity);
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

	}

	public List<TaskFileEntity> getFiles() {
		return files;
	}

	public void setFiles(List<TaskFileEntity> files) {
		this.files = files;
	}

	public void setFiles(TaskEntity taskID) {
		setFiles(taskFileDao.findByTaskID(taskID));
		if (!(getFiles().isEmpty())) {
			setSelectedTaskFile(getFiles().get(0));
		}
		else{
			setSelectedTaskFile(new TaskFileEntity());
		}
	}

	public void createTaskFile(TaskEntity taskID) {

		try {
			if (getFile() != null){
				getFileADD().setTask(taskID);
				taskFileDao.save(getFileADD());
				setFileADD(new TaskFileEntity());
				setFile(null);
				setFiles(taskFileDao.findByTaskID(taskID));
			}
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

	}

	public List<TaskFileEntity> loadAll() {
		try {
			return taskFileDao.findAll();
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

		return null;
	}

	public List<TaskFileEntity> findByTaskID(TaskEntity taskID) {
		try {
			return taskFileDao.findByTaskID(taskID);
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

		return null;
	}

	public void delete(TaskFileEntity file, TaskEntity taskID) {

		try {
			taskFileDao.delete(file);
			selectedTaskFile = new TaskFileEntity();
			setFiles(taskFileDao.findByTaskID(taskID));
			FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "File removed!", null));
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

	}

	public TaskFileEntity findById(Long id) {
		try {
			return taskFileDao.findById(id);

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
		FacesMessage msg = new FacesMessage("Task File Selected", ((TaskFileEntity) event.getObject()).getId().toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowUnselect(UnselectEvent event) {
		FacesMessage msg = new FacesMessage("Task File Unselect", ((TaskFileEntity) event.getObject()).getId().toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public TaskFileDao getTaskFileDao() {
		return taskFileDao;
	}

	public void setTaskFileDao(TaskFileDao taskFileDao) {
		this.taskFileDao = taskFileDao;
	}

	public TaskFileEntity getSelectedTaskFile() {
		
		if(selectedTaskFile==null){
			return new TaskFileEntity();
		}
		
		return selectedTaskFile;
	}

	public void setSelectedTaskFile(TaskFileEntity selectedTaskFile) {
		this.selectedTaskFile = selectedTaskFile;
	}

	public TaskFileEntity getFileADD() {
		return fileADD;
	}

	public void setFileADD(TaskFileEntity fileADD) {
		this.fileADD = fileADD;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

}
