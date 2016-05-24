package com.MyProject.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.util.Assert;

import com.MyProject.domain.commons.CustomerOrganizationEntity;
import com.MyProject.domain.project.ProjectEntity;
import com.MyProject.commons.dao.GenericJpaDao;

public class CustomerOrganizationJpaDao extends GenericJpaDao<CustomerOrganizationEntity, Long> implements CustomerOrganizationDao {
	
	public CustomerOrganizationJpaDao() {
		super(CustomerOrganizationEntity.class);
	}

	@SuppressWarnings("unchecked")
	public List<CustomerOrganizationEntity> findByProject(ProjectEntity projectEntity) {
		
		Assert.notNull(projectEntity);
		
		Query query = getEntityManager().createQuery("FROM " + getPersistentClass().getSimpleName()
				+ " f where f.project = :projectEntity").setParameter("projectEntity", projectEntity);
		
		try {
			return (List<CustomerOrganizationEntity>) query.getResultList();
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
	
	public boolean checkAvailableByProject(ProjectEntity projectEntity, String customerOrganizationName) {

		Assert.notNull(projectEntity);
		Assert.notNull(customerOrganizationName);

		Query query = getEntityManager().createQuery("select count(*) from " + getPersistentClass().getSimpleName()
				+ " u where (u.project = :projectEntity) and (u.name = :customerOrganizationName)");
		
		query.setParameter("projectEntity", projectEntity);
		query.setParameter("customerOrganizationName", customerOrganizationName);
		
		Long count = (Long) query.getSingleResult();

		return count < 1;
	}

}
