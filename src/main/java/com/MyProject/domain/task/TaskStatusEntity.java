
package com.MyProject.domain.task;

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

import com.MyProject.domain.project.ProjectEntity;

@Entity
@Table(name="task_status")
public class TaskStatusEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="task_status_ID", unique = true, nullable = false)
	private Long id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@JoinColumn(name="project_ID")
	private ProjectEntity project;
	
	@Column(name="task_status_Name", length = 255, nullable = false)
	private String name;
	
	@Column(name="task_status_Constant", nullable = false)
	private Boolean constant = false;
	
	@OneToMany(mappedBy="taskStatus")
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	private Set<TaskEntity> taskStatus;
	
	@OneToMany(mappedBy="status")
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	private Set<TaskStatusHistoryEntity> taskStatusHistoryStatus;
	
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

	public Set<TaskEntity> getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(Set<TaskEntity> taskStatus) {
		this.taskStatus = taskStatus;
	}

	public Set<TaskStatusHistoryEntity> getTaskStatusHistoryStatus() {
		return taskStatusHistoryStatus;
	}

	public void setTaskStatusHistoryStatus(Set<TaskStatusHistoryEntity> taskStatusHistoryStatus) {
		this.taskStatusHistoryStatus = taskStatusHistoryStatus;
	}

	@Override
	public boolean equals(Object other) {
		return (other instanceof TaskStatusEntity) && (id != null)
				? id.equals(((TaskStatusEntity) other).id)
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