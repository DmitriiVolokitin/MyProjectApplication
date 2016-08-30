
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
@Table(name="task_priorities")
public class TaskPriorityEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="task_priority_ID", unique = true, nullable = false)
	private Long id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@JoinColumn(name="project_ID")
	private ProjectEntity project;
	
	@Column(name="task_priority_Name", length = 255, nullable = false)
	private String name;
	
	@Column(name="task_priority_Constant", nullable = false)
	private Boolean constant = false;
	
	@OneToMany(mappedBy="taskPriority")
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	private Set<TaskEntity> taskPriority;
	
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

	public Set<TaskEntity> getTaskPriority() {
		return taskPriority;
	}

	public void setTaskPriority(Set<TaskEntity> taskPriority) {
		this.taskPriority = taskPriority;
	}

	@Override
	public boolean equals(Object other) {
		return (other instanceof TaskPriorityEntity) && (id != null)
				? id.equals(((TaskPriorityEntity) other).id)
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