package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.UserVo;

@WebServlet("/user")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//action을 가져옴
		String action = request.getParameter("action");
		System.out.println(action);
		
		
		if("joinForm".equals(action)) { //회원가입 폼
			System.out.println("UserController->joinForm");
			
			//회원가입 폼 포워드
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinForm.jsp");
			
			
			
		} else if("join".equals(action)) { //회원가입->insert
			System.out.println("UserContoller->join");
			
			String id = request.getParameter("id");
			String password = request.getParameter("password");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");
			
			UserVo userVo = new UserVo(id, password, name, gender);
			UserDao userDao = new UserDao();
			int count = userDao.insertUser(userVo);

			if(count > 0) {
				WebUtil.redirect(request, response, "./user?action=joinOk");
			}
			
			
			

		} else if("joinOk".equals(action)) { //회원가입 성공 폼
			System.out.println("UserContoller->joinOk");
			
			//회원가입 폼 포워드
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinOk.jsp");
			
			
			
			
		} else if("loginForm".equals(action)) { //로그인 폼
			System.out.println("UserContoller->loginForm");
			
			//회원가입 폼 포워드
			WebUtil.forward(request, response, "/WEB-INF/views/user/loginForm.jsp");
			
			
			
			
			
		} else if("login".equals(action)) { //로그인
			System.out.println("UserContoller->login");
			
			String id = request.getParameter("id");
			String password = request.getParameter("password");
			
			UserVo userVo = new UserVo(id, password);
			System.out.println(userVo);
			
			UserDao userDao = new UserDao();
			UserVo authUser = userDao.getUser(userVo);


			if(authUser == null) {
				System.out.println("로그인 실패");
				//메인 리다이렉트
				WebUtil.redirect(request, response, "/mysite2/main");
			} else {
				System.out.println("로그인 성공");
				
				//세션
				HttpSession session = request.getSession();
				session.setAttribute("authUser", authUser); //세션에 key로 저장
				
				//메인 리다이렉트
				WebUtil.redirect(request, response, "/mysite2/main");
			}
			
			
			
			
		} else if("logout".equals(action)) { //로그아웃
			System.out.println("UserContoller->logout");
			
			//세션값을 지운다
			HttpSession session = request.getSession();
			session.removeAttribute("authUser");
			session.invalidate();
			
			//메인 리다이렉트
			WebUtil.redirect(request, response, "/mysite2/main");
			
			
			
			
			
		} else if("modifyForm".equals(action)) { //회원정보 수정 폼
			System.out.println("UserContoller->modifyForm");
			
			//회원정보 수정 폼 포워드
			WebUtil.forward(request, response, "/WEB-INF/views/user/modifyForm.jsp");
			
			
			
			
		} else if("modify".equals(action)) {
			System.out.println("UserContoller->modify");
			
			String uid = request.getParameter("id");
			String password = request.getParameter("password");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");
			
			
			
			UserVo userVo = new UserVo(uid, password, name, gender);
			UserDao userDao = new UserDao();
			UserVo authUser = userDao.updateUser(userVo);
			
			//세션
			HttpSession session = request.getSession();
			session.setAttribute("authUser", authUser); //세션에 key로 저장
			
			//메인 리다이렉트
			WebUtil.redirect(request, response, "/mysite2/main");
			
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
