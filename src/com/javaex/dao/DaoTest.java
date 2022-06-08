package com.javaex.dao;

import com.javaex.vo.BoardVo;

public class DaoTest {

	public static void main(String[] args) {
		BoardDao boardDao = new BoardDao();
      //List<BoardVo> boardList = boardDao.getList();

      //System.out.println(boardList.toString());
      
       
      BoardVo boardVo = boardDao.getBoard(1);
      System.out.println(boardVo);
	}
}
