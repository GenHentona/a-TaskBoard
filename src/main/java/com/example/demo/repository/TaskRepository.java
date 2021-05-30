package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Task;

@Repository
public interface TaskRepository  extends JpaRepository<Task, Integer>{
	
	
	List<Task> findByStatusAndBoardIdAndIsDeleted(int status, int boardId, int dFlag);
}
