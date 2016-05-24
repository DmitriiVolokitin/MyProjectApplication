package com.MyProject.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.util.Assert;

import com.MyProject.domain.commons.ContractorOrganizationEntity;
import com.MyProject.domain.project.ProjectEntity;
import com.MyProject.commons.dao.GenericJpaDao;

public class ContractorOrganizationJpaDao extends GenericJpaDao<ContractorOrganizationEntity, Long> implements ContractorOrganizationDao {

	public ContractorOrganizationJpaDao() {
		super(ContractorOrganizationEntity.class);
	}

	@SuppressWarnings("unchecked")
	public List<ContractorOrganizationEntity> findByProject(ProjectEntity projectEntity) {

		Query query = getEntityManager().createQuery("FROM " + getPersistentClass().getSimpleName()
				+ " f where f.project = :projectEntity").setParameter("projectEntity", projectEntity);

		try {
			return (List<ContractorOrganizationEntity>) query.getResultList();
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
	
	public boolean checkAvailableByProject(ProjectEntity projectEntity, String contractorOrganizationName) {

		Assert.notNull(projectEntity);
		Assert.notNull(contractorOrganizationName);

		Query query = getEntityManager().createQuery("select count(*) from " + getPersistentClass().getSimpleName()
				+ " u where (u.project = :projectEntity) and (u.name = :contractorOrganizationName)");
		
		query.setParameter("projectEntity", projectEntity);
		query.setParameter("contractorOrganizationName", contractorOrganizationName);
		
		Long count = (Long) query.getSingleResult();

		return count < 1;
	}

}
