package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.BoardDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.BoardVo;

@WebServlet("/board")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	//생성자(디폴트)
	
	
	//메소드
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		System.out.println(action);
		
		
		if("list".equals(action)) { //게시판 폼
			System.out.println("boardController->list");
			
			//boardList 데이터 가져오기
			BoardDao boardDao = new BoardDao();
			List<BoardVo> boardList = boardDao.getBoardList();
			
			//request에 데이터 추가
			request.setAttribute("boardList", boardList);
			
			WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");
			
			
			
		} else if("writeForm".equals(action)) { //글쓰기 폼
			System.out.println("boardController->writeForm");
			
			//writeForm으로 리다이렉트
			WebUtil.forward(request, response, "/WEB-INF/views/board/writeForm.jsp");
			
			
			
		} else if("delete".equals(action)) { //글 삭제
			System.out.println("boardController->delete");
			
			//파라미터 가져오기
			int no = Integer.parseInt(request.getParameter("no"));
			
			//board 삭제
			BoardDao boardDao = new BoardDao();
			boardDao.boardDelete(no);
			
			WebUtil.redirect(request, response, "./board?action=list");
			
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
