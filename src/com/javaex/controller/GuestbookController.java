package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.GuestbookDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.GuestbookVo;


@WebServlet("/gbc")
public class GuestbookController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    //생성자(디폴트)
	
	//메소드 gs


	//메소드 일반
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//post 방식일때 한글 깨짐 방지
		request.setCharacterEncoding("UTF-8");
		
		//action 파라미터를 가져옴
		String action = request.getParameter("action");
		System.out.println(action);
		
		if("addListForm".equals(action)) { //방명록 폼
			System.out.println("guestbookController->addListForm");
			
			//데이터 가져오기
			GuestbookDao gbDao = new GuestbookDao();
			List<GuestbookVo> gbList = gbDao.getGuestList();
			
			//request에 데이터 추가
			request.setAttribute("gbList", gbList);
			
			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/addList.jsp");
			
			
			
		} else if("add".equals(action)) { //방명록 추가
			System.out.println("guestbookController->add");
			
			//파라미터 가져오기
			String name = request.getParameter("name");
			String password = request.getParameter("pass");
			String content = request.getParameter("content");
			content = content.replace("\r\n","<br>"); //textarea 줄바꿈을 저장하는 코드
			
			//guestInsert로 DB에 추가
			GuestbookDao gbDao = new GuestbookDao();
			int count  = gbDao.guestInsert(new GuestbookVo(name, password, content));
			System.out.println(count);
			
			//리다이렉트 list
			WebUtil.redirect(request, response, "./gbc?action=addListForm");
			
			
			
		} else if("deleteForm".equals(action)) { //방명록 삭제폼
			System.out.println("guestbookController->deleteForm");
			
			//파라미터 가져오기
			int delNo = Integer.parseInt(request.getParameter("del_no"));
			
			GuestbookVo gbVo = new GuestbookVo();
			gbVo.setNo(delNo);
			
			request.setAttribute("gbVo", gbVo);
			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/deleteForm.jsp");
			
			
			
		} else if("delete".equals(action)) { //방명록 삭제
			System.out.println("guestbookController->delete");
			
			//파라미터 가져오기
			int delNo = Integer.parseInt(request.getParameter("del_no"));
			String delPw = request.getParameter("del_pw");
			
			
			GuestbookDao gbDao = new GuestbookDao();
			
			gbDao.guestDelete(delNo, delPw);
			
			//리다이렉트 list
			WebUtil.redirect(request, response, "./gbc?action=addListForm");
			
			
			
			
		} else {
			System.out.println("action 파라미터 없음");
			WebUtil.redirect(request, response, "./gbc?action=addListForm");
		}
	
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
