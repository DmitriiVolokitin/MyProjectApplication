package com.MyProject.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.springframework.util.Assert;

import com.MyProject.domain.commons.FormatTimeTrackingEntity;
import com.MyProject.domain.project.ProjectEntity;
import com.MyProject.commons.dao.GenericJpaDao;

public class FormatTimeTrackingJpaDao extends GenericJpaDao<FormatTimeTrackingEntity, Long> implements FormatTimeTrackingDao {

	public FormatTimeTrackingJpaDao() {
		super(FormatTimeTrackingEntity.class);
	}
	
	public FormatTimeTrackingEntity findByName(String nameFormatTimeTracking) {

		Assert.notNull(nameFormatTimeTracking);

		Query query = getEntityManager().createQuery("FROM " + getPersistentClass().getSimpleName()
				+ " f where f.name = :nameFormatTimeTracking").setParameter("nameFormatTimeTracking", nameFormatTimeTracking);

		try {
			return (FormatTimeTrackingEntity) query.getSingleResult();
		} catch(NoResultException e) {
			//do nothing
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public List<FormatTimeTrackingEntity> findByProject(ProjectEntity projectEntity) {
		
		Assert.notNull(projectEntity);

		Query query = getEntityManager().createQuery("FROM " + getPersistentClass().getSimpleName()
				+ " f where f.project = :projectEntity").setParameter("projectEntity", projectEntity);

		try {
			return (List<FormatTimeTrackingEntity>) query.getResultList();
		} catch(NoResultException e) {
			//do nothing
		}

		return null;
	}
	
	public FormatTimeTrackingEntity findByProjectAndName(ProjectEntity projectEntity, String nameFormatTimeTracking) {

		Assert.notNull(projectEntity);
		Assert.notNull(nameFormatTimeTracking);
		
		Query query = getEntityManager().createQuery("FROM " + getPersistentClass().getSimpleName()
				+ " f where (f.project = :projectEntity) and (f.name = :nameFormatTimeTracking)");
		
		query.setParameter("projectEntity", projectEntity);
		query.setParameter("nameFormatTimeTracking", nameFormatTimeTracking);
		
		try {
			return (FormatTimeTrackingEntity) query.getSingleResult();
		} catch(NoResultException e) {
			//do nothing
		}

		return null;
	}

	public boolean checkAvailable(String nameFormatTimeTracking) {

		Assert.notNull(nameFormatTimeTracking);

		Query query = getEntityManager()
				.createQuery("select count(*) from " + getPersistentClass().getSimpleName() 
						+ " u where u.format_time_tracking_Name = :formatTimeTrackingName").setParameter("formatTimeTrackingName", nameFormatTimeTracking);

		Long count = (Long) query.getSingleResult();

		return count < 1;
	}
	
	public boolean checkAvailableByProject(ProjectEntity projectEntity, String nameFormatTimeTracking) {

		Assert.notNull(projectEntity);
		Assert.notNull(nameFormatTimeTracking);

		Query query = getEntityManager().createQuery("select count(*) from " + getPersistentClass().getSimpleName()
				+ " u where (u.project = :projectEntity) and (u.name = :nameFormatTimeTracking)");
		
		query.setParameter("projectEntity", projectEntity);
		query.setParameter("nameFormatTimeTracking", nameFormatTimeTracking);
		
		Long count = (Long) query.getSingleResult();

		return count < 1;
	}

	public Long countEntity() {

		Query query = getEntityManager()
				.createQuery("select count(*) from " + getPersistentClass().getSimpleName() 
						+ " u");

		return (Long) query.getSingleResult();

	}

	public Long countEntityByProject(ProjectEntity projectEntity) {
		
		Assert.notNull(projectEntity);

		Query query = getEntityManager()
				.createQuery("select count(*) from " + getPersistentClass().getSimpleName() 
						+ " u where u.project = :projectEntity").setParameter("projectEntity", projectEntity);

		return (Long) query.getSingleResult();

	}

}
