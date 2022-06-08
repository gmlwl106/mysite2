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
			
			
			
		} else if("writeForm".equals(action)) { //게시판 리스트
			System.out.println("boardController->writeForm");
			
			//writeForm으로 리다이렉트
			WebUtil.forward(request, response, "/WEB-INF/views/board/writeForm.jsp");
			
			
			
		} else if("write".equals(action)) { //글 작성
			System.out.println("boardController->write");
			
			int userNo = Integer.parseInt(request.getParameter("user_no"));
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			content = content.replace("\r\n","<br>"); //textarea 줄바꿈을 저장하는 코드
			
			//boardWrite로 DB에 추가
			BoardDao boardDao = new BoardDao();
			boardDao.boardWrite(new BoardVo(title, content, userNo));
			
			//list로 리다이렉트
			WebUtil.redirect(request, response, "./board?action=list");
		
			
			
		} else if("read".equals(action)) { //글 보기
			System.out.println("boardController->read");
			
			int no = Integer.parseInt(request.getParameter("no"));
			
			//board 찾기
			BoardDao boardDao = new BoardDao();
			BoardVo boardVo = boardDao.getBoard(no);
			
			//조회수 늘어나는 로직 추가해야함
			
			//request에 데이터 추가
			request.setAttribute("boardVo", boardVo);
			
			WebUtil.forward(request, response, "/WEB-INF/views/board/read.jsp");
			
			
		
		
		
		}else if("delete".equals(action)) { //글 삭제
			System.out.println("boardController->delete");
			
			//파라미터 가져오기
			int no = Integer.parseInt(request.getParameter("no"));
			
			//board 삭제
			BoardDao boardDao = new BoardDao();
			boardDao.boardDelete(no);
			
			//list로 리다이렉트
			WebUtil.redirect(request, response, "./board?action=list");
			
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
