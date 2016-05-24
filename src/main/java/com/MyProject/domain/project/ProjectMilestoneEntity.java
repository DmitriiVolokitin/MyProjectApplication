
package com.MyProject.domain.project;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.MyProject.domain.project.ProjectMilestoneEntity;
import com.MyProject.domain.task.TaskEntity;


@Entity
@Table(name="project_milestonies")
public class ProjectMilestoneEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="project_milestone_ID", unique = true, nullable = false)
	private Long id;

	@ManyToOne(fetch=FetchType.EAGER)
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@JoinColumn(name="project_ID")
	private ProjectEntity project;

	@Column(name="project_milestone_Name", length = 255, nullable = false)
	private String name;

	@Column(name="project_milestone_start_Data", nullable = false)
	private Date startDate;

	@Column(name="project_milestone_finish_Data", nullable = false)
	private Date finishDate;
	
	@OneToMany(mappedBy="milestone")
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	private Set<TaskEntity> taskMilestone;

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

	public Set<TaskEntity> getTaskMilestone() {
		return taskMilestone;
	}

	public void setTaskMilestone(Set<TaskEntity> taskMilestone) {
		this.taskMilestone = taskMilestone;
	}

	@Override
	public boolean equals(Object other) {
		return (other instanceof ProjectMilestoneEntity) && (id != null)
				? id.equals(((ProjectMilestoneEntity) other).id)
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