package com.example.service;

import java.util.List;

import com.example.model.Todo;

public interface TodoService {
	
	public String insertTodo(Todo todo) throws Exception;
	public List<Todo> getAlltodo() throws Exception;
	public List<Todo> getMyTodo(String userNicname) throws Exception;
	public String deleteTodo(String todoid) throws Exception;
	

}
