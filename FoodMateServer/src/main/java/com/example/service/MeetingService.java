package com.example.service;

import java.util.List;

import com.example.model.Meeting;
import com.example.model.Member;
import com.example.model.Message;



public interface MeetingService {

	
	public List<Meeting> getMeetingByNickname(String nickname);

	public String insertMeeting(String boardid, Meeting meeting);

	public Meeting getOneMeeting(String boardid);
	
	public String deleteMeeting(String boardid);
	
	public String updateMeeting(String boardid, Meeting meeting);
	
	public String addMember(String boardid, Member member);
	
	public String removeMember(String boardid, Member member);
	
	public String addMessage(String boardid, Message message);

	
}
