package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.AssignmentTasks;
import com.example.demo.entity.key.AssignmentKey;

public interface AssignmentTasksRepository extends JpaRepository<AssignmentTasks, AssignmentKey>{
	
	public List<AssignmentTasks> findByKeyUserId(Integer userId);
	
	public AssignmentTasks findByKeyTaskId(Integer taskId);
	
}
