package com.MyProject.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.util.Assert;

import com.MyProject.domain.project.ProjectCostSettingEntity;
import com.MyProject.domain.project.ProjectEntity;
import com.MyProject.commons.dao.GenericJpaDao;

public class ProjectCostSettingJpaDao extends GenericJpaDao<ProjectCostSettingEntity, Long> implements ProjectCostSettingDao {

	public ProjectCostSettingJpaDao() {
		super(ProjectCostSettingEntity.class);
	}

	@SuppressWarnings("unchecked")
	public List<ProjectCostSettingEntity> findByProject(ProjectEntity projectEntity) {

		Assert.notNull(projectEntity);

		Query query = getEntityManager().createQuery("FROM " + getPersistentClass().getSimpleName()
				+ " f where f.project = :projectEntity").setParameter("projectEntity", projectEntity);

		try {
			return (List<ProjectCostSettingEntity>) query.getResultList();
		} catch(NoResultException e) {
			//do nothing
		}

		return null;
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
