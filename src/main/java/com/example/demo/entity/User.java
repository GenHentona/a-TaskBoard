package com.example.demo.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class User {
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

	@Column(name="login_id")
	private String loginId;
	
	@Column(name="password")
	private String password;
	
	@Column(name="name")
	private String name;
	
	//新規作成
	public User(String name, String loginId, String password) {
		this.createdAt = new Timestamp(System.currentTimeMillis());
		this.createdBy = 0;
		this.updatedAt = new Timestamp(System.currentTimeMillis());
		this.updatedBy = 0;
		this.loginId = loginId;
		this.password = password;
		this.name = name;
		
	}
	
	
	
	public User(Integer id, int isDeleted, Timestamp createdAt, int createdBy, Timestamp updatedAt, int updatedBy,
			String loginId, String password, String name) {
		this.id = id;
		this.isDeleted = isDeleted;
		this.createdAt = createdAt;
		this.createdBy = createdBy;
		this.updatedAt = updatedAt;
		this.updatedBy = updatedBy;
		this.loginId = loginId;
		this.password = password;
		this.name = name;
	}

	
	public User() {
		
	}
	
	

	public Timestamp getCreatedAt() {
		return createdAt;
	}



	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}



	public Timestamp getUpdatedAt() {
		return updatedAt;
	}



	public void setUpdatedAt() {
		this.updatedAt = new Timestamp(System.currentTimeMillis());
	}



	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}



	public int getIsDeleted() {
		return isDeleted;
	}

	public void setIs_deleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}


	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public int getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(int updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	
	
}
