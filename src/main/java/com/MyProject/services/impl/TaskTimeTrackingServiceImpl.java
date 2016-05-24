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

import com.MyProject.domain.task.TaskEntity;
import com.MyProject.domain.task.TaskTimeTrackingEntity;
import com.MyProject.domain.user.UserEntity;
import com.MyProject.services.TaskTimeTrackingService;
import com.MyProject.dao.TaskTimeTrackingDao;
import com.MyProject.dao.UserSettingDao;

@javax.faces.bean.ManagedBean
@Scope(value="session")
public class TaskTimeTrackingServiceImpl implements TaskTimeTrackingService, Serializable {

	private static final long serialVersionUID = -5022973820660505465L;

	private TaskTimeTrackingDao taskTimeTrackingDao;
	private UserSettingDao userSettingDao;

	private TaskTimeTrackingEntity selectedTaskTimeTracking = new TaskTimeTrackingEntity();
	private List<TaskTimeTrackingEntity> TimeTrackings = new ArrayList<TaskTimeTrackingEntity>();
	private TaskTimeTrackingEntity timeTrackingADD = new TaskTimeTrackingEntity();

	@PostConstruct
	public void init() {
		System.out.println("New bean TaskTimeTrackingServiceImpl");
	}

	public void clear() {

		setTimeTrackingADD(new TaskTimeTrackingEntity());

	}

	public void beforeCreateTaskTimeTracking(UserEntity currentUser) {

		clear();
		timeTrackingADD.setDate(new Date());
		timeTrackingADD.setUserAuthor(currentUser);
		timeTrackingADD.setFormatTimeTracking(userSettingDao.findByUser(currentUser).getFormatTimeTrackingByDefault());


	}

	public void changeTime(TaskTimeTrackingEntity taskTimeTrackingEntity){
		
		
		double startTimeNum = taskTimeTrackingEntity.getStartTime();
	    long iPartStartTime = (long) startTimeNum;
	    double fPartStartTime = startTimeNum - iPartStartTime;
	    
	    double finishTimeNum = taskTimeTrackingEntity.getFinishTime();
	    long iPartFinishTime = (long) finishTimeNum;
	    double fPartFinishTime = finishTimeNum - iPartFinishTime;
	    
	    if (finishTimeNum<=0.0){
	    	taskTimeTrackingEntity.setActualTime(-startTimeNum);
	    	return;
	    }
	    
	    finishTimeNum = iPartFinishTime - iPartStartTime;
	    
	    if (fPartFinishTime-fPartStartTime < 0) {
	    	fPartFinishTime = fPartFinishTime + 0.60-fPartStartTime;
	    	finishTimeNum--;
	    }
	    else {
	    	fPartFinishTime = fPartFinishTime-fPartStartTime;
	    }
	    
	    finishTimeNum = finishTimeNum + fPartFinishTime;
		
		taskTimeTrackingEntity.setActualTime(finishTimeNum);

	}
	
	public double addTime(double startTimeNum,  double finishTimeNum){
		
		double resultTimeNum = 0.0;
		
		if (finishTimeNum < 0.0){
	    	return resultTimeNum;
	    }
	    
	    if (startTimeNum < 0.0){
	    	return resultTimeNum;
	    }
		
	    long iPartStartTime = (long) startTimeNum;
	    double fPartStartTime = startTimeNum - iPartStartTime;
	    
	    long iPartFinishTime = (long) finishTimeNum;
	    double fPartFinishTime = finishTimeNum - iPartFinishTime;
	    
	    
	    resultTimeNum = iPartFinishTime + iPartStartTime;
	    
	    if (fPartFinishTime + fPartStartTime > 0.60) {
	    	fPartFinishTime = fPartFinishTime - 0.60 + fPartStartTime;
	    	resultTimeNum++;
	    }
	    else {
	    	fPartFinishTime = fPartFinishTime + fPartStartTime;
	    }
	    
	    resultTimeNum = resultTimeNum + fPartFinishTime;
		
	    return resultTimeNum;
		
	}

	public void setAccepted(TaskTimeTrackingEntity taskTimeTrackingEntity, UserEntity userEntity) {

		if(taskTimeTrackingEntity.getAccepted()) {
			taskTimeTrackingEntity.setDateAccepted(new Date());
			taskTimeTrackingEntity.setUserAccepted(userEntity);
		}
		else {
			taskTimeTrackingEntity.setDateAccepted(null);
			taskTimeTrackingEntity.setUserAccepted(null);
		}

	}

	public void createTaskTimeTracking(TaskEntity taskID) {

		try {
			timeTrackingADD.setTask(taskID);
			taskTimeTrackingDao.save(timeTrackingADD);
			timeTrackingADD = new TaskTimeTrackingEntity();
			setTimeTrackings(taskTimeTrackingDao.findByTask(taskID));
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

	}
	
	public List<TaskTimeTrackingEntity> loadAll() {

		try {
			return taskTimeTrackingDao.findAll();
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

		return null;
	}

	public void delete(TaskTimeTrackingEntity taskTimeTrackingEntity, TaskEntity taskID) {

		try {
			taskTimeTrackingDao.delete(taskTimeTrackingEntity);
			setTimeTrackings(taskTimeTrackingDao.findByTask(taskID));
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

	}

	public void update(TaskTimeTrackingEntity taskTimeTrackingEntity) {

		try {
			taskTimeTrackingDao.update(taskTimeTrackingEntity);
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}

	}

	public TaskTimeTrackingEntity findById(Long id) {

		try {
			return taskTimeTrackingDao.findById(id);
		} catch(Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
		}
		return null;
	}

	public List<TaskTimeTrackingEntity> getTimeTrackings() {
		return TimeTrackings;
	}

	public void setTimeTrackings(List<TaskTimeTrackingEntity> timeTrackings) {
		TimeTrackings = timeTrackings;
	}

	public void setTimeTrackings(TaskEntity taskID) {
		setTimeTrackings(taskTimeTrackingDao.findByTask(taskID));
		if (!(getTimeTrackings().isEmpty())) {
			setSelectedTaskTimeTracking(getTimeTrackings().get(0));
		}
		else{
			setSelectedTaskTimeTracking(new TaskTimeTrackingEntity());
		}
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
		FacesContext.getCurrentInstance().addMessage
		(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Task TimeTracking onRowSelect!", null));
	}

	public void onRowUnselect(UnselectEvent event) {
		FacesContext.getCurrentInstance().addMessage
		(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Task TimeTracking onRowUnselect!", null));
	}

	public TaskTimeTrackingEntity getSelectedTaskTimeTracking() {
		return selectedTaskTimeTracking;
	}

	public void setSelectedTaskTimeTracking(TaskTimeTrackingEntity selectedTaskTimeTracking) {
		this.selectedTaskTimeTracking = selectedTaskTimeTracking;
	}

	public TaskTimeTrackingDao getTaskTimeTrackingDao() {
		return taskTimeTrackingDao;
	}

	public void setTaskTimeTrackingDao(TaskTimeTrackingDao taskTimeTrackingDao) {
		this.taskTimeTrackingDao = taskTimeTrackingDao;
	}

	public TaskTimeTrackingEntity getTimeTrackingADD() {
		return timeTrackingADD;
	}

	public void setTimeTrackingADD(TaskTimeTrackingEntity timeTrackingADD) {
		this.timeTrackingADD = timeTrackingADD;
	}

	public UserSettingDao getUserSettingDao() {
		return userSettingDao;
	}

	public void setUserSettingDao(UserSettingDao userSettingDao) {
		this.userSettingDao = userSettingDao;
	}

}
