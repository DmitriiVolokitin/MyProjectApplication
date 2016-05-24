package com.MyProject.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.util.Assert;

import com.MyProject.domain.project.ProjectEntity;
import com.MyProject.domain.project.ProjectMilestoneEntity;
import com.MyProject.commons.dao.GenericJpaDao;

public class ProjectMilestoneJpaDao extends GenericJpaDao<ProjectMilestoneEntity, Long> implements ProjectMilestoneDao {
	
	public ProjectMilestoneJpaDao() {
		super(ProjectMilestoneEntity.class);
	}

	@SuppressWarnings("unchecked")
	public List<ProjectMilestoneEntity> findByProject(ProjectEntity projectEntity) {
		
		Query query = getEntityManager().createQuery("FROM " + getPersistentClass().getSimpleName()
				+ " f where f.project = :projectEntity").setParameter("projectEntity", projectEntity);
		
		try {
			return (List<ProjectMilestoneEntity>) query.getResultList();
		} catch(NoResultException e) {
			//do nothing
		}
		
		return null;
	}
	
	public boolean checkAvailable(String milestoneName) {
		Assert.notNull(milestoneName);
		
		Query query = getEntityManager()
			.createQuery("select count(*) from " + getPersistentClass().getSimpleName() 
					+ " u where u.project_milestone_Name = :milestoneName").setParameter("milestoneName", milestoneName);
		
		Long count = (Long) query.getSingleResult();
		
		return count < 1;
	}
	
	public boolean checkAvailableByProject(ProjectEntity projectEntity, String nameMilestone) {

		Assert.notNull(projectEntity);
		Assert.notNull(nameMilestone);

		Query query = getEntityManager().createQuery("select count(*) from " + getPersistentClass().getSimpleName()
				+ " u where (u.project = :projectEntity) and (u.name = :nameMilestone)");
		
		query.setParameter("projectEntity", projectEntity);
		query.setParameter("nameMilestone", nameMilestone);
		
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

		Query query = getEntityManager()
				.createQuery("select count(*) from " + getPersistentClass().getSimpleName() 
						+ " u where u.project = :projectEntity").setParameter("projectEntity", projectEntity);

		return (Long) query.getSingleResult();

	}

}
