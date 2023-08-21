package com.example.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Meeting {

	private String boardid;
	private String meeting_title;
	private String meeting_content;
	private List<Member> user;
	private String date;
	
	private List<Message> messages;
		
	public List<Member> getUser() {
        if (user == null) {
            user = new ArrayList<>();
        }
        return user;
    }	
}
