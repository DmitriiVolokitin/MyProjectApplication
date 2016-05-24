package com.MyProject.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.MyProject.domain.project.ProjectEntity;
import com.MyProject.domain.task.TaskEntity;
import com.MyProject.domain.task.TaskTimeTrackingEntity;
import com.MyProject.domain.user.UserEntity;
import com.MyProject.commons.dao.GenericDao;

public interface TaskTimeTrackingDao extends GenericDao<TaskTimeTrackingEntity, Long> {
	
	List<TaskTimeTrackingEntity> findByTask(TaskEntity taskEntity);
	
	List<TaskTimeTrackingEntity> findByProject(ProjectEntity projectEntity);
	
	List<Object[]> getTimeByProject(ProjectEntity projectEntity, Date dateStart, Date dateFinish);
	
	List<Object[]> getTimeByProjectAndUsers(ProjectEntity projectEntity, Date dateStart, Date dateFinish, ArrayList<UserEntity> users);
	
	List<Object[]> getCostByProject(ProjectEntity projectEntity, Date dateStart, Date dateFinish);
	
	List<Object[]> getCostByProjectAndUsers(ProjectEntity projectEntity, Date dateStart, Date dateFinish, ArrayList<UserEntity> users);
	
	List<Object[]> countTimeByProject(ProjectEntity projectEntity, Date dateStart, Date dateFinish);
	
	List<Object[]> countTimeByProjectAndUsers(ProjectEntity projectEntity, Date dateStart, Date dateFinish, ArrayList<UserEntity> users);
	
	List<Object[]> countCostByProject(ProjectEntity projectEntity, Date dateStart, Date dateFinish);
	
	List<Object[]> countCostByProjectAndUsers(ProjectEntity projectEntity, Date dateStart, Date dateFinish, ArrayList<UserEntity> users);
	
	List<Object[]> countFormatTimeTrackingByProject(ProjectEntity projectEntity, Date dateStart, Date dateFinish);
	
	List<Object[]> countFormatTimeTrackingByProjectAndUsers(ProjectEntity projectEntity, Date dateStart, Date dateFinish, ArrayList<UserEntity> users);
	
	List<Object[]> countCostFormatTimeTrackingByProjectAccepted(ProjectEntity projectEntity, Date dateStart, Date dateFinish);
	
	List<Object[]> countCostFormatTimeTrackingByProjectAcceptedAndUsers(ProjectEntity projectEntity, Date dateStart, Date dateFinish, ArrayList<UserEntity> users);
	
	List<Object[]> countTimeFormatTimeTrackingByProjectAcceptedAndUsers(ProjectEntity projectEntity, Date dateStart, Date dateFinish, ArrayList<UserEntity> users);
	
	List<Object[]> countCostFormatTimeTrackingByProjectNotAccepted(ProjectEntity projectEntity, Date dateStart, Date dateFinish);
	
	List<Object[]> countCostFormatTimeTrackingByProjectNotAcceptedAndUsers(ProjectEntity projectEntity, Date dateStart, Date dateFinish, ArrayList<UserEntity> users);
	
	List<Object[]> countTimeFormatTimeTrackingByProjectNotAcceptedAndUsers(ProjectEntity projectEntity, Date dateStart, Date dateFinish, ArrayList<UserEntity> users);
	
}
