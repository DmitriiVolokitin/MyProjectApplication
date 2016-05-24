
package com.MyProject.domain.commons;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

import com.MyProject.domain.project.ProjectEntity;

@Entity
@Table(name="customer_organizations")
public class CustomerOrganizationEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="customer_organization_ID", unique = true, nullable = false)
	private Long id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@JoinColumn(name="project_ID")
	private ProjectEntity project;
	
	@Column(name="customer_organization_Name", length = 255, nullable = false)
	private String name;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ProjectEntity getProject() {
		return project;
	}

	public void setProject(ProjectEntity project) {
		this.project = project;
	}

	@Override
	public boolean equals(Object other) {
		return (other instanceof CustomerOrganizationEntity) && (id != null)
				? id.equals(((CustomerOrganizationEntity) other).id)
						: (other == this);
	}

	@Override
	public int hashCode() {
		return (id != null)
				? (this.getClass().hashCode() + id.hashCode())
						: super.hashCode();
	}

	@Override
	public String toString() {
		return String.format("%s", name);
	}
	
}