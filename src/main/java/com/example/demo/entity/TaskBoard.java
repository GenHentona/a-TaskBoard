package com.example.demo.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="boards")
public class TaskBoard {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name="is_deleted")
	private int isDeleted = 0;
	
	@Column(name="created_at")
	private Timestamp createdAt;
	
	@Column(name="created_by")
	private int createdBy = 0;
	
	@Column(name="updated_at")
	private Timestamp updatedAt;
	
	@Column(name="updated_by")
	private int updatedBy = 0;
	
	@Column(name="name")
	private String name;
	
	public TaskBoard() {
		
	}
	
	public TaskBoard(String name) {
		this.name = name;
		this.createdAt = new Timestamp(System.currentTimeMillis());
		this.createdBy = 0;
		this.updatedAt = new Timestamp(System.currentTimeMillis());
		this.updatedBy = 0;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(int isDeleted) {
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
}
