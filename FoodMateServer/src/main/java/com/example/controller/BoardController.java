package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Board;
import com.example.service.BoardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("")
public class BoardController {
   
   @Autowired
   private BoardService boardService;
   
   @PostMapping("/insertBoard")
    public String insertBoard(@RequestBody  Board board) throws Exception {
           return boardService.insertBoard(board);
    }
   
   @GetMapping("/boardList")
    public List<Board> getAllBoard()throws Exception {
      List<Board> boardlist = boardService.getAllBoard();
         return boardlist;
   }

    @GetMapping("/getBoardDetail")
    public Board getBoardDetail(@RequestParam String boardid) throws Exception {
           return boardService.getBoardDetail(boardid);
    }

//    @PostMapping("/updateBoard")
//    public String updateBoard(@RequestParam String boardid, String userNicname, String title, String content, String barName, String barImg, String memberCount, String meetdate, String regdate ) throws Exception {
//       Board board = new Board();
//       board.setUserNicname(userNicname);
//      board.setTitle(title);
//      board.setContent(content);
//      board.setBarName(barName);
//      board.setBarImg(barImg);
//      board.setMemberCount(memberCount);
//      board.setMeetdate(meetdate);
//      board.setRegdate(regdate);
//           return boardService.updateBoard(boardid, board);
//    }
    
    @PostMapping("/updateBoard")
    public String updateBoard(@RequestParam String boardid, @RequestBody Board board ) throws Exception {
     
          return boardService.updateBoard(boardid, board);
   }


    @GetMapping("/deleteBoard")
    public String deleteBoard(@RequestParam String boardid) throws Exception {
           return boardService.deleteBoard(boardid);
           
    }
    
    @GetMapping("/myBoard")
    public ResponseEntity<List<Board>> getMyBoard(@RequestParam("userNicname") String userNicname) {
        try {
            List<Board> myBoardList = boardService.getMyBoard(userNicname);
            return ResponseEntity.ok(myBoardList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
    