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
import com.example.demo.entity.User;
import com.example.demo.repository.AssignmentTasksRepository;
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
	
	@Autowired
	AssignmentTasksRepository assignmentTasksRepository;
	
	
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
		int boardId = taskBoardRepository.saveAndFlush(new TaskBoard(tbName)).getId();
		belongsToTBRepository.saveAndFlush(new BelongsToTB(boardId, ((User) session.getAttribute("userInfo")).getId()));
		session.setAttribute("taskBoardInfo", taskBoardRepository.getOne(boardId));
		mv.setViewName("taskBoard");
		return mv;
	}
	
	//taskBoard to mypage
	@RequestMapping("/mypage")
	public ModelAndView mypage(ModelAndView mv) {
		session.removeAttribute("taskBoardInfo");
		
		List<BelongsToTB> bList = belongsToTBRepository.findByKeyUserIdAndIsDeleted(
				((User) session.getAttribute("userInfo")).getId(), 0);
		if (bList.size() != 0) {//所属するタスクボードがあれば、
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
		int tbId = ((TaskBoard)session.getAttribute("taskBoardInfo")).getId();
		List<BelongsToTB> belongs = belongsToTBRepository.findByKeyBoardIdAndIsDeleted(tbId, 0);
		mv.addObject("group", belongs);
		mv.setViewName("edit-taskBoard");
		return mv;
	}
	
	//TB管理ページの名前変更ボタンから新しい名前を受け取りDBをUpdateしてTBページへ
	@PostMapping("/editTBname")
	public ModelAndView editTBname(
			@RequestParam("tbName") String tbName,
			ModelAndView mv) {
		int tbId = ((TaskBoard)session.getAttribute("taskBoardInfo")).getId();
		//空だとエラー
		if (tbName == null || tbName.length()==0) {
			mv.addObject("message1", "タスクボード名が未入力です。");
			List<BelongsToTB> bList = belongsToTBRepository.findByKeyBoardIdAndIsDeleted(tbId, 0);
			mv.addObject("group", bList);
			mv.setViewName("edit-taskBoard");
			return mv;
		}
	    TaskBoard taskBoard = taskBoardRepository.getOne(tbId);
	    taskBoard.setName(tbName);
	    taskBoard.setUpdatedAt();
	    taskBoard.setUpdatedBy(((User) session.getAttribute("userInfo")).getId());
	    taskBoardRepository.saveAndFlush(taskBoard);
		session.removeAttribute("taskBoardInfo");
		session.setAttribute("taskBoardInfo", taskBoardRepository.getOne(tbId));
		
		List<Task> status1 = taskRepository.findByStatusAndBoardIdAndIsDeleted(1, tbId, 0);
		List<Task> status2 = taskRepository.findByStatusAndBoardIdAndIsDeleted(2, tbId, 0);
		List<Task> status3 = taskRepository.findByStatusAndBoardIdAndIsDeleted(3, tbId, 0);
		mv.addObject("status1", status1);
		mv.addObject("status2", status2);
		mv.addObject("status3", status3);
		
		mv.setViewName("taskBoard");//redirect
		return mv;
	}
	
	//TB管理ページのメンバー追加ボタンから新しいユーザIDを受け取りDBをUpdateしてTBページへ
	@PostMapping("/addMember")
	public ModelAndView addMember(
			@RequestParam("loginId") String loginId,
			ModelAndView mv) {
		int tbId = ((TaskBoard)session.getAttribute("taskBoardInfo")).getId();
		//空だとエラー
		if (loginId == null || loginId.length()==0) {
			mv.addObject("message2", "追加するユーザのログインIDを入力してください。");
			List<BelongsToTB> bList = belongsToTBRepository.findByKeyBoardIdAndIsDeleted(tbId, 0);
			mv.addObject("group", bList);
			mv.setViewName("edit-taskBoard");
			return mv;
		}
		//IDが存在しない場合エラー
		if (userRepository.findByLoginId(loginId).equals(null)) {
			mv.addObject("message2", "このIDは存在しません");
			List<BelongsToTB> bList = belongsToTBRepository.findByKeyBoardIdAndIsDeleted(tbId, 0);
			mv.addObject("group", bList);
			mv.setViewName("edit-taskBoard");
			return mv;
		}
		//ユーザID取得
		int userId = userRepository.findByLoginId(loginId).getId();
		
		//割り当て
		belongsToTBRepository.saveAndFlush(new BelongsToTB(tbId, userId));
	
		List<Task> status1 = taskRepository.findByStatusAndBoardIdAndIsDeleted(1, tbId, 0);
		List<Task> status2 = taskRepository.findByStatusAndBoardIdAndIsDeleted(2, tbId, 0);
		List<Task> status3 = taskRepository.findByStatusAndBoardIdAndIsDeleted(3, tbId, 0);
		mv.addObject("status1", status1);
		mv.addObject("status2", status2);
		mv.addObject("status3", status3);
		
		
		mv.setViewName("taskBoard");//re
		return mv;
	}
	
	//TB管理ページのメンバー一覧の削除ボタンからユーザIDを受け取りDBをUpdateしてTBページへ
	@RequestMapping("/deleteMember/{userId}")//tbid
	public ModelAndView deleteMember(
			@PathVariable("userId") int userId,
			ModelAndView mv) {
		//TBID（主キーの方）取得
		int tbId = ((TaskBoard)session.getAttribute("taskBoardInfo")).getId();
		
		//削除（割り当て解除）、更新者など
		BelongsToTB belongs = belongsToTBRepository.findByKeyBoardIdAndKeyUserId(tbId, userId);
		belongs.setIsDeleted(1);
		belongsToTBRepository.saveAndFlush(belongs);
		
		List<Task> status1 = taskRepository.findByStatusAndBoardIdAndIsDeleted(1, tbId, 0);
		List<Task> status2 = taskRepository.findByStatusAndBoardIdAndIsDeleted(2, tbId, 0);
		List<Task> status3 = taskRepository.findByStatusAndBoardIdAndIsDeleted(3, tbId, 0);
		mv.addObject("status1", status1);
		mv.addObject("status2", status2);
		mv.addObject("status3", status3);
		
		mv.setViewName("taskBoard");//re
		return mv;
	}
	
	//マイページのTB一覧のTB名からそのTBへ
	@RequestMapping("/taskBoard/{boardId}")
	public ModelAndView gotoBoard(
			@PathVariable("boardId") int tbId,
			ModelAndView mv) {
		session.setAttribute("taskBoardInfo", taskBoardRepository.getOne(tbId));
		
		
		List<Task> status1 = taskRepository.findByStatusAndBoardIdAndIsDeleted(1, tbId, 0);
		List<Task> status2 = taskRepository.findByStatusAndBoardIdAndIsDeleted(2, tbId, 0);
		List<Task> status3 = taskRepository.findByStatusAndBoardIdAndIsDeleted(3, tbId, 0);
		mv.addObject("status1", status1);
		mv.addObject("status2", status2);
		mv.addObject("status3", status3);
		
		
		mv.setViewName("taskBoard");//re
		return mv;
	}
	
	//マイページのTB一覧の削除URLから削除するTBのIDを受け取り退会し、マイページへ
	@RequestMapping("/withdraw/{tbId}")
	public ModelAndView withdraw(
			@PathVariable("tbId") int tbId,
			ModelAndView mv) {
		//割り当て解除
		BelongsToTB a = belongsToTBRepository.findByKeyBoardIdAndKeyUserId(tbId, ((User) session.getAttribute("userInfo")).getId());
		a.setIsDeleted(1);
		belongsToTBRepository.saveAndFlush(a);
		
		List<BelongsToTB> bList = belongsToTBRepository.findByKeyUserIdAndIsDeleted(
				((User) session.getAttribute("userInfo")).getId(), 0);
		if (bList.size() != 0) {//所属するタスクボードがあれば、
			mv.addObject("group", bList);
		}else {
			mv.addObject("message", "表示するタスクボードはありません");
		}
		
		mv.setViewName("mypage");//re
		return mv;
	}
	
	//タスクボード編集ページのタスクボード削除ボタンからタスクボードIDを受け取り、
	//isDeletedをオン（１にupdateする）にし、マイページへ遷移。
	@GetMapping("/deleteTB")
	public ModelAndView deleteTB(ModelAndView mv) {
		int tbId = ((TaskBoard)session.getAttribute("taskBoardInfo")).getId();
		TaskBoard taskBoard = taskBoardRepository.getOne(tbId);
		taskBoard.setIsDeleted(1);
		taskBoardRepository.saveAndFlush(taskBoard);
		
		List<BelongsToTB> bList = belongsToTBRepository.findByKeyUserIdAndIsDeleted(
				((User) session.getAttribute("userInfo")).getId(), 0);
		if (bList.size() != 0) {//所属するタスクボードがあれば、
			mv.addObject("group", bList);
		}else {
			mv.addObject("message", "表示するタスクボードはありません");
		}
		
		mv.setViewName("mypage");//re
		return mv;
	}
	
	
	
}
