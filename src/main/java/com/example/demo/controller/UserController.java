package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.BelongsToTB;
import com.example.demo.entity.User;
import com.example.demo.repository.BelongsToTBRepository;
import com.example.demo.repository.UserRepository;

@Controller
public class UserController {
	
	@Autowired
	HttpSession session;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	BelongsToTBRepository belongsToTBRepository;
	
	
	//show loginPage
	@RequestMapping("/")
	public String login() {
		session.invalidate();
		return "top";
	}
	
	//do login
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public ModelAndView doLogin(
			@RequestParam("loginId") String loginId,
			@RequestParam("password") String password,
			ModelAndView mv) {
		//空だとエラー
		if (loginId == null || loginId.length()==0 || password == null || password.length()==0) {
			mv.addObject("message", "ユーザ情報を入力してください");
			mv.setViewName("top");
			return mv;
		}
		//IDが存在しない場合エラー
		if (userRepository.checkID(loginId) != true) {
			mv.addObject("message", "このIDは存在しません");
			mv.setViewName("top");
			return mv;
		}
		
		
		//ログインIDとパスが一致したらセッションスコープにログイン情報を保管し、マイページへ遷移
		String checkPass = userRepository.findByLoginId(loginId).getPassword();
		if (checkPass.equals(password)) {
			session.setAttribute("userName", userRepository.findByLoginId(loginId).getName());
			session.setAttribute("userId", userRepository.findByLoginId(loginId).getId());
			session.setAttribute("loginId", loginId);
			List<BelongsToTB> bList = belongsToTBRepository.findByUserId(
					userRepository.findByLoginId(loginId).getId());
			if (bList != null) {//所属するタスクボードがあれば
				mv.addObject("group", bList);
			}else {
				mv.addObject("message", "表示するタスクボードはありません");
			}
			mv.setViewName("mypage");
			return mv;
		}else { //IDとパスワードが一致しない場合エラー
			mv.addObject("message", "正しいユーザ情報を入力してください");
			mv.setViewName("top");
			return mv;
		}
	}
	
	//新規登録URLから新規登録ページへ
	@GetMapping(value="/newUser")
	public ModelAndView newUser(ModelAndView mv) {
		mv.setViewName("new-user");
		return mv;
	}
	
	//新規登録ページから新規ユーザ情報を受け取り、DBにInsert、ログインページへ
	@PostMapping(value="/newUser")
	public ModelAndView newUser2(
			@RequestParam("userName") String userName,
			@RequestParam("loginId") String loginId,
			@RequestParam("password") String password,
			ModelAndView mv) {
		//空欄があればエラー
		if (loginId == null || loginId.length()==0 || userName == null || userName.length()==0 || password == null || password.length()==0) {
			mv.addObject("message", "ユーザ情報を入力してください");
			mv.setViewName("new-user");
			return mv;
		}
		//ログインIDが被らないようにログインIDがすでに存在していればエラー
		if (userRepository.checkID(loginId) == true) {
			mv.addObject("message", "このIDは既に存在しています");
			mv.setViewName("new-user");
			return mv;
		}
		
		userRepository.saveAndFlush(new User(userName,loginId,password));
		mv.setViewName("top");
		return mv;
	}
	
	//do logout
	@RequestMapping(value="/logout")
	public String logout() {
		session.invalidate();
		return login();
	}
	
	//ユーザ情報変更ボタンからユーザ情報変更ページへ
	@GetMapping(value="/editUser")
	public ModelAndView editUser(
			ModelAndView mv) {
		mv.setViewName("edit-user");
		return mv;
	}
	//ユーザ情報変更ページから新しいユーザ情報を受け取り、DBをUpdateして、ログインページへ
	@PostMapping(value="/editUser")
	public ModelAndView editUser2(
			@RequestParam("userName") String userName,
			@RequestParam("loginId") String loginId,
			@RequestParam("password") String password,
			ModelAndView mv) {
		//空欄があればエラー
		if (loginId == null || loginId.length()==0 || userName == null || userName.length()==0 || password == null || password.length()==0) {
			mv.addObject("message", "ユーザ情報を入力してください");
			mv.setViewName("edit-user");
			return mv;
		}
		//ログインIDが被らないようにログインIDがすでに存在していればエラー
		if (userRepository.checkID(loginId) == true) {
			mv.addObject("message", "このIDは既に存在しています");
			mv.setViewName("edit-user");
			return mv;
		}
		int userId = (int) session.getAttribute("userId");
		User a = userRepository.findById(userId);
		a.setName(userName);
		a.setPassword(password);
		a.setLoginId(loginId);
		a.setCreatedBy(userId);
		a.setUpdatedBy(userId);
		a.setUpdatedAt();
		userRepository.saveAndFlush(a);
		mv.addObject("message", "ユーザ情報の変更が完了しました。ログインしてください。");
		session.invalidate();
		mv.setViewName("top");
		return mv;
	}
		
}
