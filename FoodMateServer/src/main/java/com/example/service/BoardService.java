package com.example.service;

import java.util.List;
import com.example.model.Board;

public interface BoardService {

   public String insertBoard(Board board) throws Exception;
   
   public List<Board> getAllBoard() throws Exception;

    public Board getBoardDetail(String boardid) throws Exception;
    
    public String updateBoard(String boardid, Board board) throws Exception;

    public String deleteBoard(String boardid) throws Exception;
    
	public List<Board> getMyBoard(String userNicname) throws Exception;

}


	

