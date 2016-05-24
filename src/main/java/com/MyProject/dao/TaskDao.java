package com.MyProject.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.MyProject.domain.project.ProjectEntity;
import com.MyProject.domain.task.TaskEntity;
import com.MyProject.domain.user.UserEntity;
import com.MyProject.commons.dao.GenericDao;

public interface TaskDao extends GenericDao<TaskEntity, Long> {
	
	List<TaskEntity> findByProject(ProjectEntity projectEntity);
	
	List<Object[]> countTaskDeadLineByProject(ProjectEntity projectEntity, Date dateStart, Date dateFinish);
	
	List<Object[]> countTaskDeadLineByProjectAndUsers(ProjectEntity projectEntity, Date dateStart, Date dateFinish, ArrayList<UserEntity> users);
	
	List<Object[]> countTaskTypeByProject(ProjectEntity projectEntity, Date dateStart, Date dateFinish);
	
	List<Object[]> countTaskTypeByProjectAndUsers(ProjectEntity projectEntity, Date dateStart, Date dateFinish, ArrayList<UserEntity> users);
	
	List<Object[]> countTaskDeadlineTypeByProject(ProjectEntity projectEntity, Date dateStart, Date dateFinish);
	
	List<Object[]> countTaskDeadlineTypeByProjectAndUsers(ProjectEntity projectEntity, Date dateStart, Date dateFinish, ArrayList<UserEntity> users);
	
	List<Object[]> countTaskStatusByProject(ProjectEntity projectEntity, Date dateStart, Date dateFinish);
	
	List<Object[]> countTaskStatusByProjectAndUsers(ProjectEntity projectEntity, Date dateStart, Date dateFinish, ArrayList<UserEntity> users);
	
	List<Object[]> countTaskDeadlineStatusByProject(ProjectEntity projectEntity, Date dateStart, Date dateFinish);
	
	List<Object[]> countTaskDeadlineStatusByProjectAndUsers(ProjectEntity projectEntity, Date dateStart, Date dateFinish, ArrayList<UserEntity> users);
	
	List<Object[]> countTaskPriorityByProject(ProjectEntity projectEntity, Date dateStart, Date dateFinish);
	
	List<Object[]> countTaskPriorityByProjectAndUsers(ProjectEntity projectEntity, Date dateStart, Date dateFinish, ArrayList<UserEntity> users);
	
	List<Object[]> countTaskDeadlinePriorityByProject(ProjectEntity projectEntity, Date dateStart, Date dateFinish);
	
	List<Object[]> countTaskDeadlinePriorityByProjectAndUsers(ProjectEntity projectEntity, Date dateStart, Date dateFinish, ArrayList<UserEntity> users);
	
}
