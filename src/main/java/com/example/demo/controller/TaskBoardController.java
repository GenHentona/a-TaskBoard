package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.BelongsToTB;
import com.example.demo.entity.Task;
import com.example.demo.entity.TaskBoard;
import com.example.demo.repository.BelongsToTBRepository;
import com.example.demo.repository.TaskBoardRepository;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.UserRepository;

@Controller
public class TaskBoardController {
	@Autowired
	HttpSession session;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	TaskBoardRepository taskBoardRepository;
	
	@Autowired
	BelongsToTBRepository belongsToTBRepository;
	
	@Autowired
	TaskRepository taskRepository;
	
	//タスクボード作成ボタンからタスクボード作成ページへ
	@GetMapping(value="/newTaskBoard")
	public ModelAndView newTB(ModelAndView mv) {
		mv.setViewName("new-taskBoard");
		return mv;
	}
	//タスクボード作成ページからタスクボード名（メンバーのログインIDも後に追加）を受け取りDBにInsertし、そのTBページへ遷移
	@PostMapping(value="/newTaskBoard")
	public ModelAndView newTB2(
			@RequestParam("tbName") String tbName,
			ModelAndView mv) {
		//空だとエラー
		if (tbName == null || tbName.length()==0) {
			mv.addObject("message", "タスクボード名が未入力です。");
			mv.setViewName("new-taskBoard");
			return mv;
		}
		taskBoardRepository.saveAndFlush(new TaskBoard(tbName));
		session.setAttribute("tbName", tbName);
		session.setAttribute("tbId", taskBoardRepository.findByName(tbName).getId());
		mv.setViewName("taskBoard");
		return mv;
	}
	
	//taskBoard to mypage
	@RequestMapping("/mypage")
	public ModelAndView mypage(ModelAndView mv) {
		session.removeAttribute("tbId");
		session.removeAttribute("tbName");
		
		String loginId = (String) session.getAttribute("loginId");
		List<BelongsToTB> bList = belongsToTBRepository.findByUserId(
				userRepository.findByLoginId(loginId).getId());
		if (bList != null) {//所属するタスクボードがあれば
			mv.addObject("group", bList);
		}else {
			mv.addObject("message", "表示するタスクボードはありません");
		}
		
		mv.setViewName("mypage");
		return mv;
	}
	
	//タスクボードページのタスクボード管理ボタンからタスクボード管理ページへ
	@PostMapping("/editTB")
	public ModelAndView editTB(ModelAndView mv) {
		//TBID（主キーの方）取得
		int tbId = (int) session.getAttribute("tbId");
		List<BelongsToTB> bList = belongsToTBRepository.findByBoardId(tbId);
		mv.addObject("group", bList);
		mv.setViewName("edit-taskBoard");
		return mv;
	}
	
	//TB管理ページの名前変更ボタンから新しい名前を受け取りDBをUpdateしてTBページへ
	@PostMapping("/editTBname")
	public ModelAndView editTBname(
			@RequestParam("tbName") String tbName,
			ModelAndView mv) {
		//空だとエラー
		if (tbName == null || tbName.length()==0) {
			mv.addObject("message", "タスクボード名が未入力です。");
			mv.setViewName("edit-taskBoard");
			return mv;
		}
	    int tbId = (int) session.getAttribute("tbId");
	    int b = (int) session.getAttribute("userId");
	    TaskBoard a = taskBoardRepository.findById(tbId);
	    a.setName(tbName);
	    a.setUpdatedAt();
	    a.setUpdatedBy(b);
	    taskBoardRepository.saveAndFlush(a);
		session.removeAttribute("tbName");
		session.setAttribute("tbName", tbName);
		
		List<Task> status1 = taskRepository.findByStatusAndBoardId(1, tbId);
		List<Task> status2 = taskRepository.findByStatusAndBoardId(2, tbId);
		List<Task> status3 = taskRepository.findByStatusAndBoardId(3, tbId);
		mv.addObject("status1", status1);
		mv.addObject("status2", status2);
		mv.addObject("status3", status3);
		
		
		mv.setViewName("taskBoard");
		return mv;
	}
	
	//TB管理ページのメンバー追加ボタンから新しいユーザIDを受け取りDBをUpdateしてTBページへ
	@PostMapping("/addMember")
	public ModelAndView addMember(
			@RequestParam("loginId") String loginId,
			ModelAndView mv) {
		//空だとエラー
		if (loginId == null || loginId.length()==0) {
			mv.addObject("message", "追加するユーザのログインIDを入力してください。");
			mv.setViewName("edit-taskBoard");
			return mv;
		}
		//ユーザID取得
		int userId = userRepository.findByLoginId(loginId).getId();
		//TBID取得
		int tbId = (int) session.getAttribute("tbId");
		
		//割り当て
		belongsToTBRepository.saveAndFlush(new BelongsToTB(tbId, userId));
	
		List<Task> status1 = taskRepository.findByStatusAndBoardId(1, tbId);
		List<Task> status2 = taskRepository.findByStatusAndBoardId(2, tbId);
		List<Task> status3 = taskRepository.findByStatusAndBoardId(3, tbId);
		mv.addObject("status1", status1);
		mv.addObject("status2", status2);
		mv.addObject("status3", status3);
		
		
		mv.setViewName("taskBoard");
		return mv;
	}
	
	//TB管理ページのメンバー一覧の削除ボタンからユーザIDを受け取りDBをUpdateしてTBページへ
	@RequestMapping("/deleteMember/{userId}")
	public ModelAndView deleteMember(
			@PathVariable("userId") int userId,
			ModelAndView mv) {
		//TBID（主キーの方）取得
		int tbId = (int) session.getAttribute("tbId");
		
		//割り当て解除
		BelongsToTB a = belongsToTBRepository.findByBoardIdAndUserId(tbId, userId);
		a.setIsDeleted(1);
		belongsToTBRepository.saveAndFlush(a);
		
		List<Task> status1 = taskRepository.findByStatusAndBoardId(1, tbId);
		List<Task> status2 = taskRepository.findByStatusAndBoardId(2, tbId);
		List<Task> status3 = taskRepository.findByStatusAndBoardId(3, tbId);
		mv.addObject("status1", status1);
		mv.addObject("status2", status2);
		mv.addObject("status3", status3);
		
		
		
		
		mv.setViewName("taskBoard");
		return mv;
	}
	
	//マイページのTB一覧のTB名からそのTBへ
	@RequestMapping("/taskBoard/{boardId}")
	public ModelAndView gotoBoard(
			@PathVariable("boardId") int tbId,
			ModelAndView mv) {
		session.setAttribute("tbId", tbId);
		session.setAttribute("tbName", taskBoardRepository.findById(tbId).getName());
		
		
		List<Task> status1 = taskRepository.findByStatusAndBoardId(1, tbId);
		List<Task> status2 = taskRepository.findByStatusAndBoardId(2, tbId);
		List<Task> status3 = taskRepository.findByStatusAndBoardId(3, tbId);
		mv.addObject("status1", status1);
		mv.addObject("status2", status2);
		mv.addObject("status3", status3);
		
		
		mv.setViewName("taskBoard");
		return mv;
	}
	
	//マイページのTB一覧の削除URLから削除するTBのIDを受け取り退会し、マイページへ
	@RequestMapping("/withdraw/{tbId}")
	public ModelAndView withdraw(
			@PathVariable("tbId") int tbId,
			ModelAndView mv) {
		int userId = (int) session.getAttribute("userId");
		//割り当て解除
		BelongsToTB a = belongsToTBRepository.findByBoardIdAndUserId(tbId, userId);
		a.setIsDeleted(1);
		belongsToTBRepository.saveAndFlush(a);
		
		mv.setViewName("mypage");
		return mv;
	}
	
	
	
}
