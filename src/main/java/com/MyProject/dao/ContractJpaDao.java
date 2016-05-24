package com.MyProject.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.util.Assert;

import com.MyProject.domain.commons.ContractEntity;
import com.MyProject.domain.project.ProjectEntity;
import com.MyProject.commons.dao.GenericJpaDao;

public class ContractJpaDao extends GenericJpaDao<ContractEntity, Long> implements ContractDao {

	public ContractJpaDao() {
		super(ContractEntity.class);
	}

	@SuppressWarnings("unchecked")
	public List<ContractEntity> findByProject(ProjectEntity projectEntity) {
		
		Assert.notNull(projectEntity);

		Query query = getEntityManager().createQuery("FROM " + getPersistentClass().getSimpleName()
				+ " f where f.project = :projectEntity").setParameter("projectEntity", projectEntity);

		try {
			return (List<ContractEntity>) query.getResultList();
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
	
	public boolean checkAvailableByProject(ProjectEntity projectEntity, String contractName) {

		Assert.notNull(projectEntity);
		Assert.notNull(contractName);

		Query query = getEntityManager().createQuery("select count(*) from " + getPersistentClass().getSimpleName()
				+ " u where (u.project = :projectEntity) and (u.name = :contractName)");
		
		query.setParameter("projectEntity", projectEntity);
		query.setParameter("contractName", contractName);
		
		Long count = (Long) query.getSingleResult();

		return count < 1;
	}

}
