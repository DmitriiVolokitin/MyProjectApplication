
package com.MyProject.domain.user;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

import com.MyProject.domain.commons.ProjectUserRoleEntity;

@Entity
@Table(name="user_roles")
public class UserRoleEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="user_role_ID", unique = true, nullable = false)
	private Long id;
	
	@Column(name="user_role_Name", length = 255, unique = true, nullable = false)
	private String name;
	
	@OneToMany(mappedBy="userRole")
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	private Set<ProjectUserRoleEntity>projectUserRoleUserRole;
	
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
	
	public Set<ProjectUserRoleEntity> getProjectUserRoleUserRole() {
		return projectUserRoleUserRole;
	}

	public void setProjectUserRoleUserRole(Set<ProjectUserRoleEntity> projectUserRoleUserRole) {
		this.projectUserRoleUserRole = projectUserRoleUserRole;
	}

	@Override
	public boolean equals(Object other) {
		return (other instanceof UserRoleEntity) && (id != null)
				? id.equals(((UserRoleEntity) other).id)
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