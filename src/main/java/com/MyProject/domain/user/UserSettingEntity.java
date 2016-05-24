
package com.MyProject.domain.user;

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

import com.MyProject.domain.commons.FormatTimeTrackingEntity;
import com.MyProject.domain.project.ProjectEntity;

@Entity
@Table(name="user_settings")
public class UserSettingEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="user_settings_ID", unique = true, nullable = false)
	private Long id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@JoinColumn(name="user_ID")
	private UserEntity user;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@JoinColumn(name="projectByDefault_ID")
	private ProjectEntity projectByDefault;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@JoinColumn(name="formatTimeTrackingByDefault_ID")
	private FormatTimeTrackingEntity formatTimeTrackingByDefault;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public ProjectEntity getProjectByDefault() {
		return projectByDefault;
	}

	public void setProjectByDefault(ProjectEntity projectByDefault) {
		this.projectByDefault = projectByDefault;
	}

	public FormatTimeTrackingEntity getFormatTimeTrackingByDefault() {
		return formatTimeTrackingByDefault;
	}

	public void setFormatTimeTrackingByDefault(FormatTimeTrackingEntity formatTimeTrackingByDefault) {
		this.formatTimeTrackingByDefault = formatTimeTrackingByDefault;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	@Override
	public boolean equals(Object other) {
		return (other instanceof UserSettingEntity) && (id != null)
				? id.equals(((UserSettingEntity) other).id)
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
		return String.format("%d", id);
	}
	
}