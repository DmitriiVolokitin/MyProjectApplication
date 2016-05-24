package com.MyProject.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.MyProject.domain.project.ProjectEntity;
import com.MyProject.domain.task.TaskEntity;
import com.MyProject.domain.user.UserEntity;
import com.MyProject.commons.dao.GenericJpaDao;

public class TaskJpaDao extends GenericJpaDao<TaskEntity, Long> implements TaskDao {

	public TaskJpaDao() {
		super(TaskEntity.class);
	}

	@SuppressWarnings("unchecked")
	public List<TaskEntity> findByProject(ProjectEntity projectEntity) {
		
		Query query = getEntityManager().createQuery("FROM " + getPersistentClass().getSimpleName()
				+ " f where f.project = :projectEntity").setParameter("projectEntity", projectEntity);
		
		try {
			return (List<TaskEntity>) query.getResultList();
		} catch(NoResultException e) {
			//do nothing
		}
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> countTaskDeadLineByProject(ProjectEntity projectEntity, Date dateStart, Date dateFinish) {
		
		Query query = getEntityManager().createQuery(
				"SELECT f.id, f.shortContent, f.executionPercentage"
				+ " FROM " + getPersistentClass().getSimpleName()
				+ " f where (f.project = :projectEntity) and (DATE(f.deadLine) >=DATE(:dateStart) and DATE(f.deadLine)<=DATE(:dateFinish))"
				+ " ORDER BY f.id desc");

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
	public List<Object[]> countTaskDeadLineByProjectAndUsers(ProjectEntity projectEntity, Date dateStart, Date dateFinish, ArrayList<UserEntity> users) {
		
		Query query = getEntityManager().createQuery(
				"SELECT f.id, f.shortContent, f.executionPercentage"
				+ " FROM " + getPersistentClass().getSimpleName()
				+ " f where (f.project = :projectEntity)"
				+ " and (DATE(f.deadLine) >=DATE(:dateStart) and DATE(f.deadLine)<=DATE(:dateFinish))"
				+ " and ((f.userInitiator in (:users)) or (f.userResponsible in (:users)) or (f.usercurrentExecutor in (:users)))"
				+ " ORDER BY f.id desc");

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
	public List<Object[]> countTaskTypeByProject(ProjectEntity projectEntity, Date dateStart, Date dateFinish) {
		
		Query query = getEntityManager().createQuery(
				"SELECT f.taskType, count(f.taskType) as CountTaskType"
				+ " FROM " + getPersistentClass().getSimpleName()
				+ " f where f.project = :projectEntity"
				+ " and (DATE(f.date) >=DATE(:dateStart) and DATE(f.date) <=DATE(:dateFinish))"
				+ " GROUP BY f.taskType"
				+ " ORDER BY f.taskType");

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
	public List<Object[]> countTaskTypeByProjectAndUsers(ProjectEntity projectEntity, Date dateStart, Date dateFinish, ArrayList<UserEntity> users) {
		
		Query query = getEntityManager().createQuery(
				"SELECT f.taskType, count(f.taskType) as CountTaskType"
				+ " FROM " + getPersistentClass().getSimpleName()
				+ " f where f.project = :projectEntity"
				+ " and (DATE(f.date) >=DATE(:dateStart) and DATE(f.date) <=DATE(:dateFinish))"
				+ " and ((f.userInitiator in (:users)) or (f.userResponsible in (:users)) or (f.usercurrentExecutor in (:users)))"
				+ " GROUP BY f.taskType"
				+ " ORDER BY f.taskType");

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
	public List<Object[]> countTaskDeadlineTypeByProject(ProjectEntity projectEntity, Date dateStart, Date dateFinish) {
		
		Query query = getEntityManager().createQuery(
				"SELECT f.taskType, count(f.taskType) as CountTaskType"
				+ " FROM " + getPersistentClass().getSimpleName()
				+ " f where f.project = :projectEntity"
				+ " and (DATE(f.deadLine) >=DATE(:dateStart) and DATE(f.deadLine) <=DATE(:dateFinish))"
				+ " GROUP BY f.taskType"
				+ " ORDER BY f.taskType");

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
	public List<Object[]> countTaskDeadlineTypeByProjectAndUsers(ProjectEntity projectEntity, Date dateStart, Date dateFinish, ArrayList<UserEntity> users) {
		
		Query query = getEntityManager().createQuery(
				"SELECT f.taskType, count(f.taskType) as CountTaskType"
				+ " FROM " + getPersistentClass().getSimpleName()
				+ " f where f.project = :projectEntity"
				+ " and (DATE(f.deadLine) >=DATE(:dateStart) and DATE(f.deadLine) <=DATE(:dateFinish))"
				+ " and ((f.userInitiator in (:users)) or (f.userResponsible in (:users)) or (f.usercurrentExecutor in (:users)))"
				+ " GROUP BY f.taskType"
				+ " ORDER BY f.taskType");

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
	public List<Object[]> countTaskStatusByProject(ProjectEntity projectEntity, Date dateStart, Date dateFinish) {
		
		Query query = getEntityManager().createQuery(
				"SELECT f.taskStatus, count(f.taskStatus) as CountTaskStatus"
				+ " FROM " + getPersistentClass().getSimpleName()
				+ " f where f.project = :projectEntity"
				+ " and (DATE(f.date) >=DATE(:dateStart) and DATE(f.date) <=DATE(:dateFinish))"
				+ " GROUP BY f.taskStatus"
				+ " ORDER BY f.taskStatus");

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
	public List<Object[]> countTaskStatusByProjectAndUsers(ProjectEntity projectEntity, Date dateStart, Date dateFinish, ArrayList<UserEntity> users) {
		
		Query query = getEntityManager().createQuery(
				"SELECT f.taskStatus, count(f.taskStatus) as CountTaskStatus"
				+ " FROM " + getPersistentClass().getSimpleName()
				+ " f where f.project = :projectEntity"
				+ " and (DATE(f.date) >=DATE(:dateStart) and DATE(f.date) <=DATE(:dateFinish))"
				+ " and ((f.userInitiator in (:users)) or (f.userResponsible in (:users)) or (f.usercurrentExecutor in (:users)))"
				+ " GROUP BY f.taskStatus"
				+ " ORDER BY f.taskStatus");

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
	public List<Object[]> countTaskDeadlineStatusByProject(ProjectEntity projectEntity, Date dateStart, Date dateFinish) {
		
		Query query = getEntityManager().createQuery(
				"SELECT f.taskStatus, count(f.taskStatus) as CountTaskStatus"
				+ " FROM " + getPersistentClass().getSimpleName()
				+ " f where f.project = :projectEntity"
				+ " and (DATE(f.deadLine) >=DATE(:dateStart) and DATE(f.deadLine) <=DATE(:dateFinish))"
				+ " GROUP BY f.taskStatus"
				+ " ORDER BY f.taskStatus");

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
	public List<Object[]> countTaskDeadlineStatusByProjectAndUsers(ProjectEntity projectEntity, Date dateStart, Date dateFinish, ArrayList<UserEntity> users) {
		
		Query query = getEntityManager().createQuery(
				"SELECT f.taskStatus, count(f.taskStatus) as CountTaskStatus"
				+ " FROM " + getPersistentClass().getSimpleName()
				+ " f where f.project = :projectEntity"
				+ " and (DATE(f.deadLine) >=DATE(:dateStart) and DATE(f.deadLine) <=DATE(:dateFinish))"
				+ " and ((f.userInitiator in (:users)) or (f.userResponsible in (:users)) or (f.usercurrentExecutor in (:users)))"
				+ " GROUP BY f.taskStatus"
				+ " ORDER BY f.taskStatus");

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
	public List<Object[]> countTaskPriorityByProject(ProjectEntity projectEntity, Date dateStart, Date dateFinish) {
		
		Query query = getEntityManager().createQuery(
				"SELECT f.taskPriority, count(f.taskPriority) as CountTaskPriority"
				+ " FROM " + getPersistentClass().getSimpleName()
				+ " f where f.project = :projectEntity"
				+ " and (DATE(f.date) >=DATE(:dateStart) and DATE(f.date) <=DATE(:dateFinish))"
				+ " GROUP BY f.taskPriority"
				+ " ORDER BY f.taskPriority");

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
	public List<Object[]> countTaskPriorityByProjectAndUsers(ProjectEntity projectEntity, Date dateStart, Date dateFinish, ArrayList<UserEntity> users) {
		
		Query query = getEntityManager().createQuery(
				"SELECT f.taskPriority, count(f.taskPriority) as CountTaskPriority"
				+ " FROM " + getPersistentClass().getSimpleName()
				+ " f where f.project = :projectEntity"
				+ " and (DATE(f.date) >=DATE(:dateStart) and DATE(f.date) <=DATE(:dateFinish))"
				+ " and ((f.userInitiator in (:users)) or (f.userResponsible in (:users)) or (f.usercurrentExecutor in (:users)))"
				+ " GROUP BY f.taskPriority"
				+ " ORDER BY f.taskPriority");

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
	public List<Object[]> countTaskDeadlinePriorityByProject(ProjectEntity projectEntity, Date dateStart, Date dateFinish) {
		
		Query query = getEntityManager().createQuery(
				"SELECT f.taskPriority, count(f.taskPriority) as CountTaskPriority"
				+ " FROM " + getPersistentClass().getSimpleName()
				+ " f where f.project = :projectEntity"
				+ " and (DATE(f.deadLine) >=DATE(:dateStart) and DATE(f.deadLine) <=DATE(:dateFinish))"
				+ " GROUP BY f.taskPriority"
				+ " ORDER BY f.taskPriority");

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
	public List<Object[]> countTaskDeadlinePriorityByProjectAndUsers(ProjectEntity projectEntity, Date dateStart, Date dateFinish, ArrayList<UserEntity> users) {
		
		Query query = getEntityManager().createQuery(
				"SELECT f.taskPriority, count(f.taskPriority) as CountTaskPriority"
				+ " FROM " + getPersistentClass().getSimpleName()
				+ " f where f.project = :projectEntity"
				+ " and (DATE(f.deadLine) >=DATE(:dateStart) and DATE(f.deadLine) <=DATE(:dateFinish))"
				+ " and ((f.userInitiator in (:users)) or (f.userResponsible in (:users)) or (f.usercurrentExecutor in (:users)))"
				+ " GROUP BY f.taskPriority"
				+ " ORDER BY f.taskPriority");

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
	
}
