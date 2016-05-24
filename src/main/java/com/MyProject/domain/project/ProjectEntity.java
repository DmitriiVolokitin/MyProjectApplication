
package com.MyProject.domain.project;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

import com.MyProject.domain.commons.ContractEntity;
import com.MyProject.domain.commons.ContractorOrganizationEntity;
import com.MyProject.domain.commons.CustomerOrganizationEntity;
import com.MyProject.domain.commons.FormatTimeTrackingEntity;
import com.MyProject.domain.commons.ProjectUserRoleEntity;
import com.MyProject.domain.task.TaskEntity;
import com.MyProject.domain.task.TaskPriorityEntity;
import com.MyProject.domain.task.TaskStatusEntity;
import com.MyProject.domain.task.TaskTypeEntity;
import com.MyProject.domain.user.UserSettingEntity;

@Entity
@Table(name="projects")
public class ProjectEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="project_ID", unique = true, nullable = false)
	private Long id;
	
	@Column(name="project_Name", length = 255, nullable = false)
	private String name;
	
	@Column(name="project_start_Date", nullable = false)
	private Date startDate;
	
	@Column(name="project_finish_Date",nullable = false)
	private Date finishDate;
	
	@OneToMany(mappedBy="project")
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	private Set<TaskEntity> taskProject;
	
	@OneToMany(mappedBy="project")
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	private Set<ProjectUserRoleEntity>projectUserRoleProject;
	
	@OneToMany(mappedBy="project")
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	private Set<ProjectFileEntity> projectFileProject;
	
	@OneToMany(mappedBy="project")
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	private Set<ContractorOrganizationEntity> contractorOrganizationProject;
	
	@OneToMany(mappedBy="project")
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	private Set<ProjectCostSettingEntity> projectCostSettingProject;
	
	@OneToMany(mappedBy="project")
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	private Set<FormatTimeTrackingEntity> formatTimeTrackingProject;
	
	@OneToMany(mappedBy="project")
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	private Set<CustomerOrganizationEntity> customerOrganizationEntityProject;
	
	@OneToMany(mappedBy="project")
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	private Set<TaskTypeEntity> taskTypeEntityProject;
	
	@OneToMany(mappedBy="project")
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	private Set<TaskStatusEntity> taskStatusProject;
	
	@OneToMany(mappedBy="project")
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	private Set<TaskPriorityEntity> taskPriorityProject;
	
	@OneToMany(mappedBy="project")
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	private Set<ContractEntity> contractProject;
	
	@OneToMany(mappedBy="project")
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	private Set<ProjectMilestoneEntity> ProjectMilestoneProject;
	
	@OneToMany(mappedBy="projectByDefault")
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	private Set<UserSettingEntity> userSettingProjectByDefault;


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
	
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public Set<TaskEntity> getTaskProject() {
		return taskProject;
	}

	public void setTaskProject(Set<TaskEntity> taskProject) {
		this.taskProject = taskProject;
	}

	public Set<ProjectUserRoleEntity> getProjectUserRoleProject() {
		return projectUserRoleProject;
	}

	public void setProjectUserRoleProject(Set<ProjectUserRoleEntity> projectUserRoleProject) {
		this.projectUserRoleProject = projectUserRoleProject;
	}

	public Set<ProjectFileEntity> getProjectFileProject() {
		return projectFileProject;
	}

	public void setProjectFileProject(Set<ProjectFileEntity> projectFileProject) {
		this.projectFileProject = projectFileProject;
	}

	public Set<ContractorOrganizationEntity> getContractorOrganizationProject() {
		return contractorOrganizationProject;
	}

	public void setContractorOrganizationProject(Set<ContractorOrganizationEntity> contractorOrganizationProject) {
		this.contractorOrganizationProject = contractorOrganizationProject;
	}

	public Set<ProjectCostSettingEntity> getProjectCostSettingProject() {
		return projectCostSettingProject;
	}

	public void setProjectCostSettingProject(Set<ProjectCostSettingEntity> projectCostSettingProject) {
		this.projectCostSettingProject = projectCostSettingProject;
	}

	public Set<FormatTimeTrackingEntity> getFormatTimeTrackingProject() {
		return formatTimeTrackingProject;
	}

	public void setFormatTimeTrackingProject(Set<FormatTimeTrackingEntity> formatTimeTrackingProject) {
		this.formatTimeTrackingProject = formatTimeTrackingProject;
	}

	public Set<CustomerOrganizationEntity> getCustomerOrganizationEntityProject() {
		return customerOrganizationEntityProject;
	}

	public void setCustomerOrganizationEntityProject(Set<CustomerOrganizationEntity> customerOrganizationEntityProject) {
		this.customerOrganizationEntityProject = customerOrganizationEntityProject;
	}

	public Set<TaskTypeEntity> getTaskTypeEntityProject() {
		return taskTypeEntityProject;
	}

	public void setTaskTypeEntityProject(Set<TaskTypeEntity> taskTypeEntityProject) {
		this.taskTypeEntityProject = taskTypeEntityProject;
	}

	public Set<TaskStatusEntity> getTaskStatusProject() {
		return taskStatusProject;
	}

	public void setTaskStatusProject(Set<TaskStatusEntity> taskStatusProject) {
		this.taskStatusProject = taskStatusProject;
	}

	public Set<TaskPriorityEntity> getTaskPriorityProject() {
		return taskPriorityProject;
	}

	public void setTaskPriorityProject(Set<TaskPriorityEntity> taskPriorityProject) {
		this.taskPriorityProject = taskPriorityProject;
	}

	public Set<ContractEntity> getContractProject() {
		return contractProject;
	}

	public void setContractProject(Set<ContractEntity> contractProject) {
		this.contractProject = contractProject;
	}

	public Set<ProjectMilestoneEntity> getProjectMilestoneProject() {
		return ProjectMilestoneProject;
	}

	public void setProjectMilestoneProject(Set<ProjectMilestoneEntity> projectMilestoneProject) {
		ProjectMilestoneProject = projectMilestoneProject;
	}

	public Set<UserSettingEntity> getUserSettingProjectByDefault() {
		return userSettingProjectByDefault;
	}

	public void setUserSettingProjectByDefault(Set<UserSettingEntity> userSettingProjectByDefault) {
		this.userSettingProjectByDefault = userSettingProjectByDefault;
	}

	@Override
	public boolean equals(Object other) {
		return (other instanceof ProjectEntity) && (id != null)
				? id.equals(((ProjectEntity) other).id)
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
		
		if(this.name==null){
			return String.format("%s", name);
		}
		else{
			DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
			return String.format("%s (from %s to %s)", this.name,  formatter.format(startDate), formatter.format(finishDate));
		}
		
	}
	
}