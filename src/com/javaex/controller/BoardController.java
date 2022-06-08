package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.BoardDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;

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
			
			//세션에서 no 가져옴
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			int no = authUser.getNo();
			
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			content = content.replace("\r\n","<br>"); //textarea 줄바꿈을 저장하는 코드
			
			//boardWrite로 DB에 추가
			BoardDao boardDao = new BoardDao();
			boardDao.boardWrite(new BoardVo(title, content, no));
			
			//list로 리다이렉트
			WebUtil.redirect(request, response, "./board?action=list");
		
			
			
		} else if("read".equals(action)) { //글 보기
			System.out.println("boardController->read");
			
			int no = Integer.parseInt(request.getParameter("no"));
			
			//board 찾기
			BoardDao boardDao = new BoardDao();
			BoardVo boardVo = boardDao.getBoard(no);
			
			//조회수 늘어나는 로직
			boardVo.setHit(boardVo.getHit()+1);
			boardDao.boardUpdateHit(boardVo);
			
			//request에 데이터 추가
			request.setAttribute("boardVo", boardVo);
			
			WebUtil.forward(request, response, "/WEB-INF/views/board/read.jsp");
			
			
			
		} else if("modifyForm".equals(action)) { //글 수정폼
			System.out.println("boardController->modifyForm");
			
			int no = Integer.parseInt(request.getParameter("no"));
			
			//board 찾기
			BoardDao boardDao = new BoardDao();
			BoardVo boardVo = boardDao.getBoard(no);
			boardVo.setContent(boardVo.getContent().replace("<br>", "\r\n"));
			
			//request에 데이터 추가
			request.setAttribute("boardVo", boardVo);
			
			
			WebUtil.forward(request, response, "/WEB-INF/views/board/modifyForm.jsp");
			
			
			
		} else if("modify".equals(action)) { //글 수정
			System.out.println("boardController->modify");
			
			int no = Integer.parseInt(request.getParameter("no"));
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			content = content.replace("\r\n","<br>");
			
			BoardVo boardVo = new BoardVo();
			boardVo.setNo(no);
			boardVo.setTitle(title);
			boardVo.setContent(content);
			
			BoardDao boardDao = new BoardDao();
			boardDao.boardUpdate(boardVo);
			
			//list로 리다이렉트
			WebUtil.redirect(request, response, "./board?action=list");
		
		
		
		} else if("delete".equals(action)) { //글 삭제
			System.out.println("boardController->delete");
			
			//파라미터 가져오기
			int no = Integer.parseInt(request.getParameter("no"));
			
			//board 삭제
			BoardDao boardDao = new BoardDao();
			boardDao.boardDelete(no);
			
			//list로 리다이렉트
			WebUtil.redirect(request, response, "./board?action=list");
			
		} else {
			System.out.println("action 파라미터 없음");
			WebUtil.redirect(request, response, "./board?action=list");
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
