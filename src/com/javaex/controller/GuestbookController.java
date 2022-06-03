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


@WebServlet("/gb")
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
			
			
			
		}
	
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
