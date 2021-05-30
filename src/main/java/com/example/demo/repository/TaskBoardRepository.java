package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.TaskBoard;

@Repository
public interface TaskBoardRepository extends JpaRepository<TaskBoard, Integer>{
	
	TaskBoard findByName(String tbName);

}
