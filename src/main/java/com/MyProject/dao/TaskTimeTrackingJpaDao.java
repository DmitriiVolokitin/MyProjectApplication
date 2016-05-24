package com.MyProject.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.MyProject.domain.project.ProjectEntity;
import com.MyProject.domain.task.TaskEntity;
import com.MyProject.domain.task.TaskTimeTrackingEntity;
import com.MyProject.domain.user.UserEntity;
import com.MyProject.commons.dao.GenericJpaDao;

public class TaskTimeTrackingJpaDao extends GenericJpaDao<TaskTimeTrackingEntity, Long> implements TaskTimeTrackingDao {

	public TaskTimeTrackingJpaDao() {
		super(TaskTimeTrackingEntity.class);
	}

	@SuppressWarnings("unchecked")
	public List<TaskTimeTrackingEntity> findByTask(TaskEntity taskEntity) {

		Query query = getEntityManager().createQuery("FROM " + getPersistentClass().getSimpleName()
				+ " f where f.task = :taskEntity ORDER BY f.date desc").setParameter("taskEntity", taskEntity);

		try {
			return (List<TaskTimeTrackingEntity>) query.getResultList();
		} catch(NoResultException e) {
			//do nothing
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public List<TaskTimeTrackingEntity> findByProject(ProjectEntity projectEntity) {

		Query query = getEntityManager().createQuery("FROM " + getPersistentClass().getSimpleName()
				+ " f where f.task.project = :projectEntity").setParameter("projectEntity", projectEntity);

		try {
			return (List<TaskTimeTrackingEntity>) query.getResultList();
		} catch(NoResultException e) {
			//do nothing
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> getTimeByProject(ProjectEntity projectEntity, Date dateStart, Date dateFinish) {

		Query query = getEntityManager().createQuery(
				"SELECT f.userAuthor, f.accepted, sum(f.actualTime) as actualTime"
						+ " FROM " + getPersistentClass().getSimpleName()
						+ " f where f.task.project = :projectEntity and (f.date >=:dateStart and f.date <=:dateFinish)"
						+ " GROUP BY f.userAuthor, f.accepted");

		query.setParameter("projectEntity", projectEntity);
		query.setParameter("dateStart", dateStart);
		query.setParameter("dateFinish", dateFinish);

		try {
			return (List<Object[]>) query.getResultList();
		} catch(NoResultException e) {
			//do nothing
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> getTimeByProjectAndUsers(ProjectEntity projectEntity, Date dateStart, Date dateFinish, ArrayList<UserEntity> users) {

		Query query = getEntityManager().createQuery(
				"SELECT f.userAuthor, f.accepted, sum(f.actualTime) as actualTime"
				+ " FROM " + getPersistentClass().getSimpleName()
				+ " f where f.task.project = :projectEntity"
				+ " and (f.date >=:dateStart and f.date <=:dateFinish)"
				+ " and (f.userAuthor in (:users))"
				+ " GROUP BY f.userAuthor, f.accepted");

		query.setParameter("projectEntity", projectEntity);
		query.setParameter("dateStart", dateStart);
		query.setParameter("dateFinish", dateFinish);
		query.setParameter("users", users);

		try {
			return (List<Object[]>) query.getResultList();
		} catch(NoResultException e) {
			//do nothing
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> getCostByProject(ProjectEntity projectEntity, Date dateStart, Date dateFinish) {

		String MyQuery = "" 
				+ "SELECT"
				+ " format_time_tracking.format_time_tracking_Name,"
				+ " task_time_tracking_accepted,"
				+ " SUM(nullif(task_time_tracking_actual_Time,0) * nullif(project_cost_settings_cost,0)) AS summCostByFormatTime"
				+ " FROM"                                                
				+ " myprojectapplication.task_time_tracking"
				+ " LEFT JOIN"
				+ " myprojectapplication.tasks ON"
				+ " task_time_tracking.task_ID = tasks.task_ID"
				+ " LEFT JOIN"
				+ " myprojectapplication.format_time_tracking ON"
				+ " (task_time_tracking.formatTimeTracking_ID = format_time_tracking.format_time_tracking_ID)"
				+ " LEFT JOIN"
				+ " myprojectapplication.project_cost_settings ON"
				+ " (task_time_tracking.formatTimeTracking_ID = project_cost_settings.formatTimeTracking_ID)"
				+ " and (date(task_time_tracking.task_time_tracking_Date) >= date(project_cost_settings.project_cost_settings_start_Date))"
				+ " and (date(task_time_tracking.task_time_tracking_Date) <= date(project_cost_settings.project_cost_settings_finish_Date))"
				+ " and (project_cost_settings.project_ID = :projectEntity)"
				+ " WHERE"
				+ " tasks.project_ID = :projectEntity"
				+ " and task_time_tracking.task_time_tracking_Date >=:dateStart"
				+ " and task_time_tracking.task_time_tracking_Date <=:dateFinish"
				+ " GROUP BY"
				+ " task_time_tracking.task_time_tracking_accepted,"
				+ " task_time_tracking.formatTimeTracking_ID";

		Query query = getEntityManager().createNativeQuery(MyQuery);

		query.setParameter("projectEntity", projectEntity.getId());
		query.setParameter("dateStart", dateStart);
		query.setParameter("dateFinish", dateFinish);

		try {
			return (List<Object[]>) query.getResultList();
		} catch(NoResultException e) {
			//do nothing
		}

		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getCostByProjectAndUsers(ProjectEntity projectEntity, Date dateStart, Date dateFinish, ArrayList<UserEntity> users) {

		String MyQuery = "" 
				+ "SELECT"
				+ " format_time_tracking.format_time_tracking_Name,"
				+ " task_time_tracking_accepted,"
				+ " nullif((SUM(nullif(task_time_tracking_actual_Time,0) * nullif(project_cost_settings_cost,0))),0) AS summCostByFormatTime"
				+ " FROM"                                                
				+ " myprojectapplication.task_time_tracking"
				+ " LEFT JOIN"
				+ " myprojectapplication.tasks ON"
				+ " task_time_tracking.task_ID = tasks.task_ID"
				+ " LEFT JOIN"
				+ " myprojectapplication.format_time_tracking ON"
				+ " (task_time_tracking.formatTimeTracking_ID = format_time_tracking.format_time_tracking_ID)"
				+ " LEFT JOIN"
				+ " myprojectapplication.project_cost_settings ON"
				+ " (task_time_tracking.formatTimeTracking_ID = project_cost_settings.formatTimeTracking_ID)"
				+ " and (date(task_time_tracking.task_time_tracking_Date) >= date(project_cost_settings.project_cost_settings_start_Date))"
				+ " and (date(task_time_tracking.task_time_tracking_Date) <= date(project_cost_settings.project_cost_settings_finish_Date))"
				+ " and (project_cost_settings.project_ID = :projectEntity)"
				+ " WHERE"
				+ " tasks.project_ID = :projectEntity"
				+ " and task_time_tracking.task_time_tracking_Date >=:dateStart"
				+ " and task_time_tracking.task_time_tracking_Date <=:dateFinish"
				+ " and task_time_tracking.userAuthor_ID in (:users)"
				+ " GROUP BY"
				+ " task_time_tracking.task_time_tracking_accepted,"
				+ " task_time_tracking.formatTimeTracking_ID";

		Query query = getEntityManager().createNativeQuery(MyQuery);

		query.setParameter("projectEntity", projectEntity.getId());
		query.setParameter("dateStart", dateStart);
		query.setParameter("dateFinish", dateFinish);
		
		List<Long> listCurrentReportUser = new ArrayList<Long>();
		for(UserEntity listUserEntity : users){
			listCurrentReportUser.add(listUserEntity.getId());
		}
		query.setParameter("users", listCurrentReportUser);

		try {
			return (List<Object[]>) query.getResultList();
		} catch(NoResultException e) {
			//do nothing
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> countTimeByProject(ProjectEntity projectEntity, Date dateStart, Date dateFinish) {

		Query query = getEntityManager().createQuery(
				"SELECT f.accepted, sum(f.actualTime) as actualTime"
						+ " FROM " + getPersistentClass().getSimpleName()
						+ " f where f.task.project = :projectEntity and (f.date >=:dateStart and f.date <=:dateFinish)"
						+ " GROUP BY f.accepted"
						+ " ORDER BY f.accepted");

		query.setParameter("projectEntity", projectEntity);
		query.setParameter("dateStart", dateStart);
		query.setParameter("dateFinish", dateFinish);

		try {
			return (List<Object[]>) query.getResultList();
		} catch(NoResultException e) {
			//do nothing
		}

		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> countTimeByProjectAndUsers(ProjectEntity projectEntity, Date dateStart, Date dateFinish, ArrayList<UserEntity> users) {

		Query query = getEntityManager().createQuery(
				"SELECT f.accepted, sum(f.actualTime) as actualTime"
				+ " FROM " + getPersistentClass().getSimpleName()
				+ " f where f.task.project = :projectEntity "
				+ " and (f.date >=:dateStart and f.date <=:dateFinish)"
				+ " and (f.userAuthor in (:users))"
				+ " GROUP BY f.accepted"
				+ " ORDER BY f.accepted");

		query.setParameter("projectEntity", projectEntity);
		query.setParameter("dateStart", dateStart);
		query.setParameter("dateFinish", dateFinish);
		query.setParameter("users", users);

		try {
			return (List<Object[]>) query.getResultList();
		} catch(NoResultException e) {
			//do nothing
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> countCostByProject(ProjectEntity projectEntity, Date dateStart, Date dateFinish) {

		String MyQuery = "" 
				+ "SELECT"
				+ " task_time_tracking_accepted,"
				+ " SUM(nullif(task_time_tracking_actual_Time,0) * nullif(project_cost_settings_cost,0)) AS summCostByFormatTime"
				+ " FROM"                                                
				+ " myprojectapplication.task_time_tracking"
				+ " LEFT JOIN"
				+ " myprojectapplication.tasks ON"
				+ " task_time_tracking.task_ID = tasks.task_ID"
				+ " LEFT JOIN"
				+ " myprojectapplication.project_cost_settings ON"
				+ " (task_time_tracking.formatTimeTracking_ID = project_cost_settings.formatTimeTracking_ID)"
				+ " and (date(task_time_tracking.task_time_tracking_Date) >= date(project_cost_settings.project_cost_settings_start_Date))"
				+ " and (date(task_time_tracking.task_time_tracking_Date) <= date(project_cost_settings.project_cost_settings_finish_Date))"
				+ " and (project_cost_settings.project_ID = :projectEntity)"
				+ " WHERE"
				+ " tasks.project_ID = :projectEntity"
				+ " and task_time_tracking.task_time_tracking_Date >=:dateStart"
				+ " and task_time_tracking.task_time_tracking_Date <=:dateFinish"
				+ " GROUP BY"
				+ " task_time_tracking.task_time_tracking_accepted";

		Query query = getEntityManager().createNativeQuery(MyQuery);

		query.setParameter("projectEntity", projectEntity);
		query.setParameter("dateStart", dateStart);
		query.setParameter("dateFinish", dateFinish);

		try {
			return (List<Object[]>) query.getResultList();
		} catch(NoResultException e) {
			//do nothing
		}

		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> countCostByProjectAndUsers(ProjectEntity projectEntity, Date dateStart, Date dateFinish, ArrayList<UserEntity> users) {

		String MyQuery = "" 
				+ "SELECT"
				+ " task_time_tracking_accepted,"
				+ " SUM(nullif(task_time_tracking_actual_Time,0) * nullif(project_cost_settings_cost,0)) AS summCostByFormatTime"
				+ " FROM"                                                
				+ " myprojectapplication.task_time_tracking"
				+ " LEFT JOIN"
				+ " myprojectapplication.tasks ON"
				+ " task_time_tracking.task_ID = tasks.task_ID"
				+ " LEFT JOIN"
				+ " myprojectapplication.project_cost_settings ON"
				+ " (task_time_tracking.formatTimeTracking_ID = project_cost_settings.formatTimeTracking_ID)"
				+ " and (date(task_time_tracking.task_time_tracking_Date) >= date(project_cost_settings.project_cost_settings_start_Date))"
				+ " and (date(task_time_tracking.task_time_tracking_Date) <= date(project_cost_settings.project_cost_settings_finish_Date))"
				+ " and (project_cost_settings.project_ID = :projectEntity)"
				+ " WHERE"
				+ " tasks.project_ID = :projectEntity"
				+ " and task_time_tracking.task_time_tracking_Date >=:dateStart"
				+ " and task_time_tracking.task_time_tracking_Date <=:dateFinish"
				+ " and task_time_tracking.userAuthor_ID in (:users)"
				+ " GROUP BY"
				+ " task_time_tracking.task_time_tracking_accepted";

		Query query = getEntityManager().createNativeQuery(MyQuery);

		query.setParameter("projectEntity", projectEntity);
		query.setParameter("dateStart", dateStart);
		query.setParameter("dateFinish", dateFinish);
		
		List<Long> listCurrentReportUser = new ArrayList<Long>();
		for(UserEntity listUserEntity : users){
			listCurrentReportUser.add(listUserEntity.getId());
		}
		query.setParameter("users", listCurrentReportUser);

		try {
			return (List<Object[]>) query.getResultList();
		} catch(NoResultException e) {
			//do nothing
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> countFormatTimeTrackingByProject(ProjectEntity projectEntity, Date dateStart, Date dateFinish) {

		Query query = getEntityManager().createQuery(
				"SELECT f.formatTimeTracking, count(f.formatTimeTracking) as countFormatTimeTracking"
						+ " FROM " + getPersistentClass().getSimpleName()
						+ " f where f.task.project = :projectEntity and (f.date >=:dateStart and f.date <=:dateFinish)"
						+ " GROUP BY f.formatTimeTracking"
						+ " ORDER BY f.formatTimeTracking");

		query.setParameter("projectEntity", projectEntity);
		query.setParameter("dateStart", dateStart);
		query.setParameter("dateFinish", dateFinish);

		try {
			return (List<Object[]>) query.getResultList();
		} catch(NoResultException e) {
			//do nothing
		}

		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> countFormatTimeTrackingByProjectAndUsers(ProjectEntity projectEntity, Date dateStart, Date dateFinish, ArrayList<UserEntity> users) {

		Query query = getEntityManager().createQuery(
				"SELECT f.formatTimeTracking, count(f.formatTimeTracking) as countFormatTimeTracking"
						+ " FROM " + getPersistentClass().getSimpleName()
						+ " f where f.task.project = :projectEntity"
						+ " and (f.date >=:dateStart and f.date <=:dateFinish)"
						+ " and (f.userAuthor in (:users))"
						+ " GROUP BY f.formatTimeTracking"
						+ " ORDER BY f.formatTimeTracking");

		query.setParameter("projectEntity", projectEntity);
		query.setParameter("dateStart", dateStart);
		query.setParameter("dateFinish", dateFinish);
		query.setParameter("users", users);

		try {
			return (List<Object[]>) query.getResultList();
		} catch(NoResultException e) {
			//do nothing
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> countCostFormatTimeTrackingByProjectAccepted(ProjectEntity projectEntity, Date dateStart, Date dateFinish) {

		String MyQuery = "" 
				+ "SELECT"
				+ " format_time_tracking.format_time_tracking_Name,"
				+ " SUM(nullif(task_time_tracking_actual_Time,0) * nullif(project_cost_settings_cost,0)) AS summCostByFormatTime"
				+ " FROM"                                                
				+ " myprojectapplication.task_time_tracking"
				+ " LEFT JOIN"
				+ " myprojectapplication.tasks ON"
				+ " task_time_tracking.task_ID = tasks.task_ID"
				+ " LEFT JOIN"
				+ " myprojectapplication.format_time_tracking ON"
				+ " (task_time_tracking.formatTimeTracking_ID = format_time_tracking.format_time_tracking_ID)"
				+ " LEFT JOIN"
				+ " myprojectapplication.project_cost_settings ON"
				+ " (task_time_tracking.formatTimeTracking_ID = project_cost_settings.formatTimeTracking_ID)"
				+ " and (date(task_time_tracking.task_time_tracking_Date) >= date(project_cost_settings.project_cost_settings_start_Date))"
				+ " and (date(task_time_tracking.task_time_tracking_Date) <= date(project_cost_settings.project_cost_settings_finish_Date))"
				+ " and (project_cost_settings.project_ID = :projectEntity)"
				+ " WHERE"
				+ " tasks.project_ID = :projectEntity"
				+ " and task_time_tracking.task_time_tracking_Date >=:dateStart"
				+ " and task_time_tracking.task_time_tracking_Date <=:dateFinish"
				+ " and task_time_tracking_accepted"
				+ " GROUP BY"
				+ " task_time_tracking.formatTimeTracking_ID";

		Query query = getEntityManager().createNativeQuery(MyQuery);

		query.setParameter("projectEntity", projectEntity);
		query.setParameter("dateStart", dateStart);
		query.setParameter("dateFinish", dateFinish);

		try {
			return (List<Object[]>) query.getResultList();
		} catch(NoResultException e) {
			//do nothing
		}

		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> countCostFormatTimeTrackingByProjectAcceptedAndUsers(ProjectEntity projectEntity, Date dateStart, Date dateFinish, ArrayList<UserEntity> users) {

		String MyQuery = "" 
				+ "SELECT"
				+ " format_time_tracking.format_time_tracking_Name,"
				+ " SUM(nullif(task_time_tracking_actual_Time,0) * nullif(project_cost_settings_cost,0)) AS summCostByFormatTime"
				+ " FROM"                                                
				+ " myprojectapplication.task_time_tracking"
				+ " LEFT JOIN"
				+ " myprojectapplication.tasks ON"
				+ " task_time_tracking.task_ID = tasks.task_ID"
				+ " LEFT JOIN"
				+ " myprojectapplication.format_time_tracking ON"
				+ " (task_time_tracking.formatTimeTracking_ID = format_time_tracking.format_time_tracking_ID)"
				+ " LEFT JOIN"
				+ " myprojectapplication.project_cost_settings ON"
				+ " (task_time_tracking.formatTimeTracking_ID = project_cost_settings.formatTimeTracking_ID)"
				+ " and (date(task_time_tracking.task_time_tracking_Date) >= date(project_cost_settings.project_cost_settings_start_Date))"
				+ " and (date(task_time_tracking.task_time_tracking_Date) <= date(project_cost_settings.project_cost_settings_finish_Date))"
				+ " and (project_cost_settings.project_ID = :projectEntity)"
				+ " WHERE"
				+ " tasks.project_ID = :projectEntity"
				+ " and task_time_tracking.task_time_tracking_Date >=:dateStart"
				+ " and task_time_tracking.task_time_tracking_Date <=:dateFinish"
				+ " and task_time_tracking_accepted"
				+ " and task_time_tracking.userAuthor_ID in (:users)"
				+ " GROUP BY"
				+ " task_time_tracking.formatTimeTracking_ID";

		Query query = getEntityManager().createNativeQuery(MyQuery);

		query.setParameter("projectEntity", projectEntity);
		query.setParameter("dateStart", dateStart);
		query.setParameter("dateFinish", dateFinish);
		
		List<Long> listCurrentReportUser = new ArrayList<Long>();
		for(UserEntity listUserEntity : users){
			listCurrentReportUser.add(listUserEntity.getId());
		}
		query.setParameter("users", listCurrentReportUser);

		try {
			return (List<Object[]>) query.getResultList();
		} catch(NoResultException e) {
			//do nothing
		}

		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> countTimeFormatTimeTrackingByProjectAcceptedAndUsers(ProjectEntity projectEntity, Date dateStart, Date dateFinish, ArrayList<UserEntity> users) {

		String MyQuery = "" 
				+ "SELECT"
				+ " format_time_tracking.format_time_tracking_Name,"
				+ " SUM(nullif(task_time_tracking_actual_Time,0)) AS summTimeByFormatTime"
				+ " FROM"                                                
				+ " myprojectapplication.task_time_tracking"
				+ " LEFT JOIN"
				+ " myprojectapplication.tasks ON"
				+ " task_time_tracking.task_ID = tasks.task_ID"
				+ " LEFT JOIN"
				+ " myprojectapplication.format_time_tracking ON"
				+ " (task_time_tracking.formatTimeTracking_ID = format_time_tracking.format_time_tracking_ID)"
				+ " LEFT JOIN"
				+ " myprojectapplication.project_cost_settings ON"
				+ " (task_time_tracking.formatTimeTracking_ID = project_cost_settings.formatTimeTracking_ID)"
				+ " and (date(task_time_tracking.task_time_tracking_Date) >= date(project_cost_settings.project_cost_settings_start_Date))"
				+ " and (date(task_time_tracking.task_time_tracking_Date) <= date(project_cost_settings.project_cost_settings_finish_Date))"
				+ " and (project_cost_settings.project_ID = :projectEntity)"
				+ " WHERE"
				+ " tasks.project_ID = :projectEntity"
				+ " and task_time_tracking.task_time_tracking_Date >=:dateStart"
				+ " and task_time_tracking.task_time_tracking_Date <=:dateFinish"
				+ " and task_time_tracking_accepted"
				+ " and task_time_tracking.userAuthor_ID in (:users)"
				+ " GROUP BY"
				+ " task_time_tracking.formatTimeTracking_ID";

		Query query = getEntityManager().createNativeQuery(MyQuery);

		query.setParameter("projectEntity", projectEntity);
		query.setParameter("dateStart", dateStart);
		query.setParameter("dateFinish", dateFinish);
		
		List<Long> listCurrentReportUser = new ArrayList<Long>();
		for(UserEntity listUserEntity : users){
			listCurrentReportUser.add(listUserEntity.getId());
		}
		query.setParameter("users", listCurrentReportUser);

		try {
			return (List<Object[]>) query.getResultList();
		} catch(NoResultException e) {
			//do nothing
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> countCostFormatTimeTrackingByProjectNotAccepted(ProjectEntity projectEntity, Date dateStart, Date dateFinish) {

		String MyQuery = "" 
				+ "SELECT"
				+ " format_time_tracking.format_time_tracking_Name,"
				+ " SUM(nullif(task_time_tracking_actual_Time,0) * nullif(project_cost_settings_cost,0)) AS summCostByFormatTime"
				+ " FROM"                                                
				+ " myprojectapplication.task_time_tracking"
				+ " LEFT JOIN"
				+ " myprojectapplication.tasks ON"
				+ " task_time_tracking.task_ID = tasks.task_ID"
				+ " LEFT JOIN"
				+ " myprojectapplication.format_time_tracking ON"
				+ " (task_time_tracking.formatTimeTracking_ID = format_time_tracking.format_time_tracking_ID)"
				+ " LEFT JOIN"
				+ " myprojectapplication.project_cost_settings ON"
				+ " (task_time_tracking.formatTimeTracking_ID = project_cost_settings.formatTimeTracking_ID)"
				+ " and (date(task_time_tracking.task_time_tracking_Date) >= date(project_cost_settings.project_cost_settings_start_Date))"
				+ " and (date(task_time_tracking.task_time_tracking_Date) <= date(project_cost_settings.project_cost_settings_finish_Date))"
				+ " and (project_cost_settings.project_ID = :projectEntity)"
				+ " WHERE"
				+ " tasks.project_ID = :projectEntity"
				+ " and task_time_tracking.task_time_tracking_Date >=:dateStart"
				+ " and task_time_tracking.task_time_tracking_Date <=:dateFinish"
				+ " and (not task_time_tracking_accepted)"
				+ " GROUP BY"
				+ " task_time_tracking.formatTimeTracking_ID";

		Query query = getEntityManager().createNativeQuery(MyQuery);

		query.setParameter("projectEntity", projectEntity);
		query.setParameter("dateStart", dateStart);
		query.setParameter("dateFinish", dateFinish);

		try {
			return (List<Object[]>) query.getResultList();
		} catch(NoResultException e) {
			//do nothing
		}

		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> countCostFormatTimeTrackingByProjectNotAcceptedAndUsers(ProjectEntity projectEntity, Date dateStart, Date dateFinish, ArrayList<UserEntity> users) {

		String MyQuery = "" 
				+ "SELECT"
				+ " format_time_tracking.format_time_tracking_Name,"
				+ " SUM(nullif(task_time_tracking_actual_Time,0) * nullif(project_cost_settings_cost,0)) AS summCostByFormatTime"
				+ " FROM"                                                
				+ " myprojectapplication.task_time_tracking"
				+ " LEFT JOIN"
				+ " myprojectapplication.tasks ON"
				+ " task_time_tracking.task_ID = tasks.task_ID"
				+ " LEFT JOIN"
				+ " myprojectapplication.format_time_tracking ON"
				+ " (task_time_tracking.formatTimeTracking_ID = format_time_tracking.format_time_tracking_ID)"
				+ " LEFT JOIN"
				+ " myprojectapplication.project_cost_settings ON"
				+ " (task_time_tracking.formatTimeTracking_ID = project_cost_settings.formatTimeTracking_ID)"
				+ " and (date(task_time_tracking.task_time_tracking_Date) >= date(project_cost_settings.project_cost_settings_start_Date))"
				+ " and (date(task_time_tracking.task_time_tracking_Date) <= date(project_cost_settings.project_cost_settings_finish_Date))"
				+ " and (project_cost_settings.project_ID = :projectEntity)"
				+ " WHERE"
				+ " tasks.project_ID = :projectEntity"
				+ " and task_time_tracking.task_time_tracking_Date >=:dateStart"
				+ " and task_time_tracking.task_time_tracking_Date <=:dateFinish"
				+ " and (not task_time_tracking_accepted)"
				+ " and task_time_tracking.userAuthor_ID in (:users)"
				+ " GROUP BY"
				+ " task_time_tracking.formatTimeTracking_ID";

		Query query = getEntityManager().createNativeQuery(MyQuery);

		query.setParameter("projectEntity", projectEntity);
		query.setParameter("dateStart", dateStart);
		query.setParameter("dateFinish", dateFinish);
		
		List<Long> listCurrentReportUser = new ArrayList<Long>();
		for(UserEntity listUserEntity : users){
			listCurrentReportUser.add(listUserEntity.getId());
		}
		query.setParameter("users", listCurrentReportUser);

		try {
			return (List<Object[]>) query.getResultList();
		} catch(NoResultException e) {
			//do nothing
		}

		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> countTimeFormatTimeTrackingByProjectNotAcceptedAndUsers(ProjectEntity projectEntity, Date dateStart, Date dateFinish, ArrayList<UserEntity> users) {

		String MyQuery = "" 
				+ "SELECT"
				+ " format_time_tracking.format_time_tracking_Name,"
				+ " SUM(nullif(task_time_tracking_actual_Time,0)) AS summTimeByFormatTime"
				+ " FROM"                                                
				+ " myprojectapplication.task_time_tracking"
				+ " LEFT JOIN"
				+ " myprojectapplication.tasks ON"
				+ " task_time_tracking.task_ID = tasks.task_ID"
				+ " LEFT JOIN"
				+ " myprojectapplication.format_time_tracking ON"
				+ " (task_time_tracking.formatTimeTracking_ID = format_time_tracking.format_time_tracking_ID)"
				+ " LEFT JOIN"
				+ " myprojectapplication.project_cost_settings ON"
				+ " (task_time_tracking.formatTimeTracking_ID = project_cost_settings.formatTimeTracking_ID)"
				+ " and (date(task_time_tracking.task_time_tracking_Date) >= date(project_cost_settings.project_cost_settings_start_Date))"
				+ " and (date(task_time_tracking.task_time_tracking_Date) <= date(project_cost_settings.project_cost_settings_finish_Date))"
				+ " and (project_cost_settings.project_ID = :projectEntity)"
				+ " WHERE"
				+ " tasks.project_ID = :projectEntity"
				+ " and task_time_tracking.task_time_tracking_Date >=:dateStart"
				+ " and task_time_tracking.task_time_tracking_Date <=:dateFinish"
				+ " and (not task_time_tracking_accepted)"
				+ " and task_time_tracking.userAuthor_ID in (:users)"
				+ " GROUP BY"
				+ " task_time_tracking.formatTimeTracking_ID";

		Query query = getEntityManager().createNativeQuery(MyQuery);

		query.setParameter("projectEntity", projectEntity);
		query.setParameter("dateStart", dateStart);
		query.setParameter("dateFinish", dateFinish);
		
		List<Long> listCurrentReportUser = new ArrayList<Long>();
		for(UserEntity listUserEntity : users){
			listCurrentReportUser.add(listUserEntity.getId());
		}
		query.setParameter("users", listCurrentReportUser);

		try {
			return (List<Object[]>) query.getResultList();
		} catch(NoResultException e) {
			//do nothing
		}

		return null;
	}

}
