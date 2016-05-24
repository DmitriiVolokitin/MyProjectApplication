
package com.MyProject.domain.commons;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

import com.MyProject.domain.project.ProjectCostSettingEntity;
import com.MyProject.domain.project.ProjectEntity;
import com.MyProject.domain.task.TaskTimeTrackingEntity;
import com.MyProject.domain.user.UserSettingEntity;

@Entity
@Table(name="format_time_tracking")
public class FormatTimeTrackingEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="format_time_tracking_ID", unique = true, nullable = false)
	private Long id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@JoinColumn(name="project_ID")
	private ProjectEntity project;
	
	@Column(name="format_time_tracking_Name", length = 150, nullable = false)
	private String name;
	
	@Column(name="format_time_tracking_Constant", nullable = false)
	private Boolean constant = false;
	
	@OneToMany(mappedBy="formatTimeTracking")
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	private Set<ProjectCostSettingEntity> projectCostSettingFormatTimeTracking;
	
	@OneToMany(mappedBy="formatTimeTrackingByDefault")
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	private Set<UserSettingEntity> userSettingFormatTimeTrackingByDefault;
	
	@OneToMany(mappedBy="formatTimeTracking")
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	private Set<TaskTimeTrackingEntity> taskTimeTrackingFormatTimeTracking;
	
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

	public Boolean getConstant() {
		return constant;
	}

	public void setConstant(Boolean constant) {
		this.constant = constant;
	}
	
	public ProjectEntity getProject() {
		return project;
	}

	public void setProject(ProjectEntity project) {
		this.project = project;
	}

	public Set<ProjectCostSettingEntity> getProjectCostSettingFormatTimeTracking() {
		return projectCostSettingFormatTimeTracking;
	}

	public void setProjectCostSettingFormatTimeTracking(
			Set<ProjectCostSettingEntity> projectCostSettingFormatTimeTracking) {
		this.projectCostSettingFormatTimeTracking = projectCostSettingFormatTimeTracking;
	}

	public Set<UserSettingEntity> getUserSettingFormatTimeTrackingByDefault() {
		return userSettingFormatTimeTrackingByDefault;
	}

	public void setUserSettingFormatTimeTrackingByDefault(Set<UserSettingEntity> userSettingFormatTimeTrackingByDefault) {
		this.userSettingFormatTimeTrackingByDefault = userSettingFormatTimeTrackingByDefault;
	}

	public Set<TaskTimeTrackingEntity> getTaskTimeTrackingFormatTimeTracking() {
		return taskTimeTrackingFormatTimeTracking;
	}

	public void setTaskTimeTrackingFormatTimeTracking(Set<TaskTimeTrackingEntity> taskTimeTrackingFormatTimeTracking) {
		this.taskTimeTrackingFormatTimeTracking = taskTimeTrackingFormatTimeTracking;
	}

	@Override
	public boolean equals(Object other) {
		return (other instanceof FormatTimeTrackingEntity) && (id != null)
				? id.equals(((FormatTimeTrackingEntity) other).id)
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