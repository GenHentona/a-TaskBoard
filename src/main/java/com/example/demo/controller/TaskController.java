package com.example.demo.controller;

import java.util.List;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.AssignmentTasks;
import com.example.demo.entity.Task;
import com.example.demo.entity.TaskBoard;
import com.example.demo.entity.User;
import com.example.demo.repository.AssignmentTasksRepository;
import com.example.demo.repository.TaskBoardRepository;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.UserRepository;

@Controller
public class TaskController {
	@Autowired
	HttpSession session;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	TaskBoardRepository taskBoardRepository;
	
	@Autowired
	TaskRepository taskRepository;
	
	@Autowired
	AssignmentTasksRepository assignmentTasksRepository;
	
	
	//タスク作成ボタンからタスク作成ページへ
	@GetMapping(value="/newTask")
	public ModelAndView newTask(ModelAndView mv) {
		mv.setViewName("new-task");
		return mv;
	}
	
	//タスク作成ページからタスク情報を受け取りDBにInsertし、そのタスクボードページへ遷移
	@PostMapping(value="/newTask")
	public ModelAndView newTask2(
			@RequestParam("title") String title,
			@RequestParam("contents") String contents,
			@RequestParam("priority") String priority,
			@RequestParam(name="storyPoint", defaultValue="0") String storyPoint,
			@RequestParam("completionCriteria") String completionCriteria,
			ModelAndView mv) {
		//空欄があればエラー
		if (title == null || title.length()==0 || 
			contents == null || contents.length()==0 ||
			priority == null || priority.length()==0 || 
			storyPoint == null || storyPoint.length()==0 ||
			completionCriteria == null || completionCriteria.length()==0) {
			mv.addObject("message", "空欄があります。");
			mv.setViewName("new-task");
			return mv;
		}
		int status = 1;
		int tbId = ((TaskBoard)session.getAttribute("taskBoardInfo")).getId();
		
		int taskId = taskRepository.saveAndFlush(new Task(title, contents, priority, storyPoint, 
				completionCriteria, status, tbId)).getId();
		assignmentTasksRepository.saveAndFlush(
				new AssignmentTasks(taskId, ((User) session.getAttribute("userInfo")).getId()));
		
		List<Task> status1 = taskRepository.findByStatusAndBoardIdAndIsDeleted(1, tbId, 0);
		List<Task> status2 = taskRepository.findByStatusAndBoardIdAndIsDeleted(2, tbId, 0);
		List<Task> status3 = taskRepository.findByStatusAndBoardIdAndIsDeleted(3, tbId, 0);
		
		mv.addObject("status1", status1);
		mv.addObject("status2", status2);
		mv.addObject("status3", status3);
		
		mv.setViewName("taskBoard");//re
		return mv;
	}
	
	//タスク編集ボタンからタスク編集ページへ
	@GetMapping(value="/editTask")
	public ModelAndView editTask(
			@RequestParam("taskId") String taskId,
			ModelAndView mv) {
		mv.addObject("taskId", taskId);
		mv.setViewName("edit-task");
		return mv;
	}
	
	//タスク編集ページから編集情報を受け取りDBをUpdateして、タスクボードページへ
	@PostMapping(value="/editTask")
	public ModelAndView editTask2(
			@RequestParam("title") String title,
			@RequestParam("contents") String contents,
			@RequestParam("priority") String priority,
			@RequestParam("storyPoint") String storyPoint,
			@RequestParam("completionCriteria") String completionCriteria,
			@RequestParam("taskId") int taskId,
			ModelAndView mv) {
		//Task編集
		Task task = taskRepository.getOne(taskId);
		task.setTitle(title);
		task.setContents(contents);
		task.setPriority(priority);
		task.setStoryPoint(storyPoint);
		task.setCompletionCriteria(completionCriteria);
		task.setUpdatedAt();
		task.setUpdatedBy(((User) session.getAttribute("userInfo")).getId());
		taskRepository.saveAndFlush(task);
		
		int tbId = ((TaskBoard)session.getAttribute("taskBoardInfo")).getId();
		List<Task> status1 = taskRepository.findByStatusAndBoardIdAndIsDeleted(1, tbId, 0);
		List<Task> status2 = taskRepository.findByStatusAndBoardIdAndIsDeleted(2, tbId, 0);
		List<Task> status3 = taskRepository.findByStatusAndBoardIdAndIsDeleted(3, tbId, 0);
		mv.addObject("status1", status1);
		mv.addObject("status2", status2);
		mv.addObject("status3", status3);
		
		mv.setViewName("taskBoard");//re
		return mv;
	}
	
	//タスクステータス変更ボタンから変更するtaskIdとstatusを受け取りDBをUpdateし、タスクボードページへ戻る
	@GetMapping(value="/editStatus")
	public ModelAndView editStatus(
			@RequestParam("taskId") int taskId,
			@RequestParam("statusTest") String statusT,
			ModelAndView mv) {
		//Status変更
		Task task = taskRepository.getOne(taskId);
		task.setStatus(Integer.parseInt(statusT));
		taskRepository.saveAndFlush(task);
		
		int tbId = ((TaskBoard)session.getAttribute("taskBoardInfo")).getId();
		List<Task> status1 = taskRepository.findByStatusAndBoardIdAndIsDeleted(1, tbId, 0);
		List<Task> status2 = taskRepository.findByStatusAndBoardIdAndIsDeleted(2, tbId, 0);
		List<Task> status3 = taskRepository.findByStatusAndBoardIdAndIsDeleted(3, tbId, 0);
		mv.addObject("status1", status1);
		mv.addObject("status2", status2);
		mv.addObject("status3", status3);
		
		mv.setViewName("taskBoard");//re
		return mv;
	}
	
	//タスク削除ボタンから削除するtaskIdを受け取りDBをUpdateし、TBへ
	@GetMapping(value="/deleteTask")
	public ModelAndView deleteTask(
			@RequestParam("taskId") int taskId,
			ModelAndView mv) {
		Task task = taskRepository.getOne(taskId);
		task.setIsDeleted(1);
		taskRepository.saveAndFlush(task);
		
		int tbId = ((TaskBoard)session.getAttribute("taskBoardInfo")).getId();
		List<Task> status1 = taskRepository.findByStatusAndBoardIdAndIsDeleted(1, tbId, 0);
		List<Task> status2 = taskRepository.findByStatusAndBoardIdAndIsDeleted(2, tbId, 0);
		List<Task> status3 = taskRepository.findByStatusAndBoardIdAndIsDeleted(3, tbId, 0);
		mv.addObject("status1", status1);
		mv.addObject("status2", status2);
		mv.addObject("status3", status3);
		
		mv.setViewName("taskBoard");//re
		return mv;
	}
}
