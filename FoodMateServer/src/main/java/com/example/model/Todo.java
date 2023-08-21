package com.example.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Data
@ToString
public class Todo {
	
	   private String title;
	   private String memo;
	   private String todoid;
	   private String userNicname;
	   
}
