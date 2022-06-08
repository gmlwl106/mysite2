package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.BoardVo;

public class BoardDao {

	// 0. import java.sql.*;
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "webdb";
	private String pw = "webdb";
	
	private void getConnection() {
		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName(driver);

			// 2. Connection 얻어오기
			conn = DriverManager.getConnection(url, id, pw);
			// System.out.println("접속성공");

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}
	
	private void close() {
		// 5. 자원정리
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}
	
	
	//Board list 출력
	public List<BoardVo> getBoardList() {
		
		List<BoardVo> boardList = new ArrayList<BoardVo>();
		getConnection();
		
		try {

			// 3. SQL문 준비 / 바인딩 / 실행 --> 완성된 sql문을 가져와서 작성할것
			String query = "";
			query += " select  b.no ";
			query += "         ,b.title ";
			query += "         ,b.content ";
			query += "         ,b.hit ";
			query += "         ,to_char(b.reg_date,'YY-MM-DD HH24:MI') \"reg_date\" ";
			query += "         ,b.user_no ";
			query += "         ,u.name ";
			query += " from board b, users u ";
			query += " where b.user_no = u.no ";
			query += " order by no desc ";


			pstmt = conn.prepareStatement(query); // 쿼리로 만들기

			rs = pstmt.executeQuery();

			// 4.결과처리
			while (rs.next()) {
				int no = rs.getInt("no");
				String title = rs.getString("title");
				String content = rs.getString("content");
				int hit = rs.getInt("hit");
				String date = rs.getString("reg_date");
				int userNo = rs.getInt("user_no");
				String name = rs.getString("name");

				boardList.add(new BoardVo(no, title, content, hit, date, userNo, name));
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();
		return boardList;
	}
	
	
	//Board 글 쓰기
	public void boardWrite(BoardVo boardVo) {
		getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			String query = ""; // 쿼리문 문자열만들기, ? 주의
			query += " insert into board ";
			query += " values (seq_board_no.nextval, ?, ?, 0, sysdate, ?) ";

			pstmt = conn.prepareStatement(query); // 쿼리로 만들기

			pstmt.setString(1, boardVo.getTitle());
			pstmt.setString(2, boardVo.getContent());
			pstmt.setInt(3, boardVo.getUserNo());
			

			int count = pstmt.executeUpdate(); // 쿼리문 실행

			// 4.결과처리
			System.out.println("[" + count + "건 추가되었습니다.]");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();
	}
	
	
	//Board 찾기
	public BoardVo getBoard(int no) {
		BoardVo boardVo = null;
		getConnection();
		
		try {

			// 3. SQL문 준비 / 바인딩 / 실행 --> 완성된 sql문을 가져와서 작성할것
			String query = "";
			query += " select  b.no ";
			query += "         ,b.title ";
			query += "         ,b.content ";
			query += "         ,b.hit ";
			query += "         ,to_char(b.reg_date,'YY-MM-DD HH24:MI') \"reg_date\" ";
			query += "         ,b.user_no ";
			query += "         ,u.name ";
			query += " from board b, users u ";
			query += " where b.user_no = u.no ";
			query += " and b.no = ? ";


			pstmt = conn.prepareStatement(query); // 쿼리로 만들기
			pstmt.setInt(1, no);

			rs = pstmt.executeQuery();

			// 4.결과처리
			while (rs.next()) {
				String title = rs.getString("title");
				String content = rs.getString("content");
				int hit = rs.getInt("hit");
				String date = rs.getString("reg_date");
				int userNo = rs.getInt("user_no");
				String name = rs.getString("name");

				boardVo = new BoardVo(no, title, content, hit, date, userNo, name);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();
		return boardVo;
	}
	
	
	//Board 글 삭제
	public void boardDelete(int no) {
		getConnection();
		
		try {

			// 3. SQL문 준비 / 바인딩 / 실행 --> 완성된 sql문을 가져와서 작성할것
			String query = "";
			query += " delete from board ";
			query += " where no = ? ";

			pstmt = conn.prepareStatement(query); // 쿼리로 만들기
			pstmt.setInt(1, no);

			int count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println("["+count+"건 삭제 되었습니다.]");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();
	}

	
	
	//Board 글 수정
	public void boardUpdate(BoardVo boardVo) {
		getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			String query = ""; // 쿼리문 문자열만들기, ? 주의
			query += " update board ";
			query += " set title = ?, ";
			query += "     content = ? ";
			query += " where no = ? ";

			pstmt = conn.prepareStatement(query); // 쿼리로 만들기

			pstmt.setString(1, boardVo.getTitle());
			pstmt.setString(2, boardVo.getContent());
			pstmt.setInt(3, boardVo.getNo());
			

			int count = pstmt.executeUpdate(); // 쿼리문 실행

			// 4.결과처리
			System.out.println("[" + count + "건 수정 되었습니다.]");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();
	}

	//Board 조회수 수정
	public void boardUpdateHit(BoardVo boardVo) {
		getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			String query = ""; // 쿼리문 문자열만들기, ? 주의
			query += " update board ";
			query += " set hit = ? ";
			query += " where no = ? ";

			pstmt = conn.prepareStatement(query); // 쿼리로 만들기

			pstmt.setInt(1, boardVo.getHit());
			pstmt.setInt(2, boardVo.getNo());
			

			int count = pstmt.executeUpdate(); // 쿼리문 실행

			// 4.결과처리
			System.out.println("[" + count + "건 수정 되었습니다.]");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();
	}

	
}
