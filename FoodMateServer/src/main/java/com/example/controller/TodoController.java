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
import com.example.model.Todo;
import com.example.service.TodoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("")
public class TodoController {
	
	@Autowired
	   private TodoService todoService;
	
	   @PostMapping("/insertTodo")
	    public String insertBoard(@RequestBody Todo todo) throws Exception {
	           return todoService.insertTodo(todo);
	    }

	   @GetMapping("/todoList")
	    public List<Todo> getAllBoard()throws Exception {
	      List<Todo> todolist = todoService.getAlltodo();
	         return todolist;
	   }
	   
	   @GetMapping("/myTodo")
	    public ResponseEntity<List<Todo>> getMyTodo(@RequestParam("userNicname") String userNicname) {
	        try {
	            List<Todo> myTodoList = todoService.getMyTodo(userNicname);
	            return ResponseEntity.ok(myTodoList);
	        } catch (Exception e) {
	            e.printStackTrace();
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	        }
	    }

	   @GetMapping("/deleteTodo")
	    public String deleteBoard(@RequestParam String todoid) throws Exception {
	           return todoService.deleteTodo(todoid);
	           
	    }
}
