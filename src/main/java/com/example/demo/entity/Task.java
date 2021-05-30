package com.example.demo.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tasks")
public class Task {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name="board_id")
	private Integer boardId;
	
	@Column(name="is_deleted")
	private Integer isDeleted;
	
	@Column(name="created_at")
	private Timestamp createdAt;
	
	@Column(name="created_by")
	private int createdBy = 0;
	
	@Column(name="updated_at")
	private Timestamp updatedAt;
	
	@Column(name="updated_by")
	private int updatedBy = 0;
	
	@Column(name="status")
	private Integer status;
	
	@Column(name="title")
	private String title;
	
	@Column(name="contents")
	private String contents;
	
	@Column(name="priority")
	private String priority;
	
	@Column(name="story_point")
	private String storyPoint;
	
	@Column(name="completion_criteria")
	private String completionCriteria;
	

	public Task(String title, String contents, 
				String priority, String storyPoint, 
				String completionCriteria,  int status, 
				int boardId) {
		this.boardId = boardId;
		this.isDeleted = 0;
		this.createdAt = new Timestamp(System.currentTimeMillis());
		this.createdBy = 0;
		this.updatedAt = new Timestamp(System.currentTimeMillis());
		this.updatedBy = 0;
		this.title = title;
		this.contents = contents;
		this.priority = priority;
		this.storyPoint = storyPoint;
		this.completionCriteria = completionCriteria;
		this.status = status;
	}



	public Task() {
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getBoardId() {
		return boardId;
	}
	public void setBoardId(Integer boardId) {
		this.boardId = boardId;
	}
	public Integer getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	public int getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}
	public Timestamp getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt() {
		this.updatedAt = new Timestamp(System.currentTimeMillis());
	}
	public int getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(int updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getStoryPoint() {
		return storyPoint;
	}
	public void setStoryPoint(String storyPoint) {
		this.storyPoint = storyPoint;
	}
	public String getCompletionCriteria() {
		return completionCriteria;
	}
	public void setCompletionCriteria(String completionCriteria) {
		this.completionCriteria = completionCriteria;
	}
}
