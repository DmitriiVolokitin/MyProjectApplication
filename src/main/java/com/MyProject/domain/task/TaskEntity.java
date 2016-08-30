package com.MyProject.domain.task;

import java.io.Serializable;
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

import com.MyProject.domain.project.ProjectEntity;
import com.MyProject.domain.project.ProjectMilestoneEntity;
import com.MyProject.domain.user.UserEntity;

@Entity
@Table(name="tasks")
public class TaskEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="task_ID", unique = true, nullable = false)
	private Long id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@JoinColumn(name="project_ID")
	private ProjectEntity project;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@JoinColumn(name="userInitiator_ID")
	private UserEntity userInitiator;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@JoinColumn(name="userResponsible_ID")
	private UserEntity userResponsible;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@JoinColumn(name="usercurrentExecutor_ID")
	private UserEntity usercurrentExecutor;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@JoinColumn(name="taskPriority_ID")
	private TaskPriorityEntity taskPriority;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@JoinColumn(name="taskType_ID")
	private TaskTypeEntity taskType;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@JoinColumn(name="taskStatus_ID")
	private TaskStatusEntity taskStatus;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@JoinColumn(name="milestone_ID")
	private ProjectMilestoneEntity milestone;
	
	
	@Column(name="task_Date", nullable = false)
	private Date date;
	
	@Column(name="task_Dead_Line", nullable = true)
	private Date deadLine;

	@Column(name="task_short_Content", length = 255, nullable = false)
	private String shortContent;

	@Column(name="task_full_Content", length = 255, nullable = true)
	private String fullContent;
	
	@Column(name="task_plan_Time", nullable = true)
	private double planTime = 0.00;
	
	@Column(name="task_full_actual_Time", nullable = true)
	private double actualTime = 0.00;
	
	@Column(name="task_full_Execution_Percentage", nullable = true)
	private double executionPercentage;
	
	@OneToMany(mappedBy="task")
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	private Set<TaskHistoryEntity> taskHistoryTask;
	
	@OneToMany(mappedBy="task")
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	private Set<TaskStatusHistoryEntity> taskStatusHistoryTask;
	
	@OneToMany(mappedBy="task")
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	private Set<TaskFileEntity> taskFileTask;
	
	@OneToMany(mappedBy="task")
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	private Set<TaskTimeTrackingEntity> taskTimeTrackingTask;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ProjectEntity getProject() {
		return project;
	}

	public void setProject(ProjectEntity project) {
		this.project = project;
	}

	public String getShortContent() {
		return shortContent;
	}

	public void setShortContent(String shortContent) {
		this.shortContent = shortContent;
	}

	public String getFullContent() {
		return fullContent;
	}

	public void setFullContent(String fullContent) {
		this.fullContent = fullContent;
	}

	public double getExecutionPercentage() {
		return executionPercentage;
	}

	public void setExecutionPercentage(double executionPercentage) {
		this.executionPercentage = executionPercentage;
	}

	public UserEntity getUserInitiator() {
		return userInitiator;
	}

	public void setUserInitiator(UserEntity userInitiator) {
		this.userInitiator = userInitiator;
	}

	public UserEntity getUserResponsible() {
		return userResponsible;
	}

	public void setUserResponsible(UserEntity userResponsible) {
		this.userResponsible = userResponsible;
	}

	public UserEntity getUsercurrentExecutor() {
		return usercurrentExecutor;
	}

	public void setUsercurrentExecutor(UserEntity usercurrentExecutor) {
		this.usercurrentExecutor = usercurrentExecutor;
	}

	public TaskTypeEntity getTaskType() {
		return taskType;
	}

	public void setTaskType(TaskTypeEntity taskType) {
		this.taskType = taskType;
	}

	public TaskStatusEntity getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(TaskStatusEntity taskStatus) {
		this.taskStatus = taskStatus;
	}

	public ProjectMilestoneEntity getMilestone() {
		return milestone;
	}

	public void setMilestone(ProjectMilestoneEntity milestone) {
		this.milestone = milestone;
	}

	public TaskPriorityEntity getTaskPriority() {
		return taskPriority;
	}

	public void setTaskPriority(TaskPriorityEntity taskPriority) {
		this.taskPriority = taskPriority;
	}

	public double getPlanTime() {
		return planTime;
	}

	public void setPlanTime(double planTime) {
		this.planTime = planTime;
	}

	public double getActualTime() {
		return actualTime;
	}

	public void setActualTime(double actualTime) {
		this.actualTime = actualTime;
	}

	public Set<TaskHistoryEntity> getTaskHistoryTask() {
		return taskHistoryTask;
	}

	public void setTaskHistoryTask(Set<TaskHistoryEntity> taskHistoryTask) {
		this.taskHistoryTask = taskHistoryTask;
	}

	public Set<TaskStatusHistoryEntity> getTaskStatusHistoryTask() {
		return taskStatusHistoryTask;
	}

	public void setTaskStatusHistoryTask(Set<TaskStatusHistoryEntity> taskStatusHistoryTask) {
		this.taskStatusHistoryTask = taskStatusHistoryTask;
	}

	public Set<TaskFileEntity> getTaskFileTask() {
		return taskFileTask;
	}

	public void setTaskFileTask(Set<TaskFileEntity> taskFileTask) {
		this.taskFileTask = taskFileTask;
	}

	public Set<TaskTimeTrackingEntity> getTaskTimeTrackingTask() {
		return taskTimeTrackingTask;
	}

	public void setTaskTimeTrackingTask(Set<TaskTimeTrackingEntity> taskTimeTrackingTask) {
		this.taskTimeTrackingTask = taskTimeTrackingTask;
	}

	public Date getDeadLine() {
		return deadLine;
	}

	public void setDeadLine(Date deadLine) {
		this.deadLine = deadLine;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public boolean equals(Object other) {
		return (other instanceof TaskEntity) && (id != null)
				? id.equals(((TaskEntity) other).id)
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
		return String.format("%s", shortContent);
	}

}
