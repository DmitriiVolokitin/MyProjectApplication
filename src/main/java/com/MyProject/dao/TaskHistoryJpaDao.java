package com.MyProject.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.MyProject.domain.project.ProjectEntity;
import com.MyProject.domain.task.TaskEntity;
import com.MyProject.domain.task.TaskHistoryEntity;
import com.MyProject.commons.dao.GenericJpaDao;

public class TaskHistoryJpaDao extends GenericJpaDao<TaskHistoryEntity, Long> implements TaskHistoryDao {

	public TaskHistoryJpaDao() {
		super(TaskHistoryEntity.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<TaskHistoryEntity> findByTaskID(TaskEntity taskID) {
		
		Query query = getEntityManager().createQuery("FROM " + getPersistentClass().getSimpleName()
				+ " f where f.task = :taskID ORDER BY f.changeDate desc").setParameter("taskID", taskID);
		
		try {
			return (List<TaskHistoryEntity>) query.getResultList();
		} catch(NoResultException e) {
			//do nothing
		}
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> countTaskActivityByProject(ProjectEntity projectEntity, Date startDate, Date finishDate) {
		
		Query query = getEntityManager().createQuery(
				"SELECT DATE(f.changeDate), count(f.changeEvent) as countChangeEvent"
						+ " FROM " + getPersistentClass().getSimpleName()
						+ " f where (f.task.project = :projectEntity) and (DATE(f.changeDate) >=DATE(:startDate) and DATE(f.changeDate)<=DATE(:finishDate))"
						+ " GROUP BY DATE(f.changeDate)"
						+ " ORDER BY DATE(f.changeDate) desc");

		query.setParameter("projectEntity", projectEntity);
		query.setParameter("startDate", startDate);
		query.setParameter("finishDate", finishDate);
		
		try {
			return (List<Object[]>) query.getResultList();
		} catch(NoResultException e) {
			//do nothing
		}
		
		return null;
	}
	
}
