
package com.MyProject.domain.project;

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


@Entity
@Table(name="project_files")
public class ProjectFileEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="project_file_ID", unique = true, nullable = false)
	private Long id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@JoinColumn(name="project_ID")
	private ProjectEntity project;
	
	@Column(name="project_file_Name", length = 255, nullable = false)
	private String name;
	
	@Column(name="project_file_Type", length = 150, nullable = false)
	private String type;
	
	@Column(name="project_file_Data", columnDefinition="longblob")
	private byte[] fileData;
	
	@Column(name="project_file_Comment", length = 255, nullable = true)
	private String comment;
	
	@Column(name="project_file_Commons", nullable = true)
	private boolean commons = true;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public byte[] getFileData() {
		return fileData;
	}

	public void setFileData(byte[] fileData) {
		this.fileData = fileData;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public ProjectEntity getProject() {
		return project;
	}

	public void setProject(ProjectEntity project) {
		this.project = project;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public boolean isCommons() {
		return commons;
	}

	public void setCommons(boolean commons) {
		this.commons = commons;
	}

	@Override
	public boolean equals(Object other) {
		return (other instanceof ProjectFileEntity) && (id != null)
				? id.equals(((ProjectFileEntity) other).id)
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