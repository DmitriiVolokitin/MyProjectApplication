package com.MyProject.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.util.Assert;

import com.MyProject.domain.project.ProjectEntity;
import com.MyProject.domain.task.TaskEntity;
import com.MyProject.commons.dao.GenericJpaDao;

public class ProjectJpaDao extends GenericJpaDao<ProjectEntity, Long> implements ProjectDao {
	
	public ProjectJpaDao() {
		super(ProjectEntity.class);
	}

	@SuppressWarnings("unchecked")
	public List<ProjectEntity> findByTask(TaskEntity taskEntity) {
		
		Query query = getEntityManager().createQuery("FROM " + getPersistentClass().getSimpleName()
				+ " f where f.task = :taskEntity").setParameter("taskEntity", taskEntity);
		
		try {
			return (List<ProjectEntity>) query.getResultList();
		} catch(NoResultException e) {
			//do nothing
		}
		
		return null;
	}
	
	public boolean checkAvailable(String ProjectName) {
		Assert.notNull(ProjectName);
		
		Query query = getEntityManager()
			.createQuery("select count(*) from " + getPersistentClass().getSimpleName() 
					+ " u where u.project_Name = :ProjectName").setParameter("ProjectName", ProjectName);
		
		Long count = (Long) query.getSingleResult();
		
		return count < 1;
	}
	
	public Long countEntity() {
		
		Query query = getEntityManager()
			.createQuery("select count(*) from " + getPersistentClass().getSimpleName() 
					+ " u");
		
		return (Long) query.getSingleResult();
		
	}

}
