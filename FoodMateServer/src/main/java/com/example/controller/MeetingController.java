package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Meeting;
import com.example.model.Member;
import com.example.model.Message;
import com.example.service.MeetingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("")
public class MeetingController {
	
	@Autowired
    private final MeetingService meetingService;
	
	@GetMapping("/getMeetingByNickname")
	public List<Meeting> getMeetingByNickname(@RequestParam String nickname) {
		return meetingService.getMeetingByNickname(nickname);
	}
	
	@PostMapping("/insertMeeting")
	public String insertMeeting(@RequestParam String boardid, @RequestBody Meeting meeting) {
       return meetingService.insertMeeting(boardid, meeting);	
	}
	
	@GetMapping("/getOneMeeting")
	public Meeting getOneMeeting(@RequestParam String boardid) {
	       return meetingService.getOneMeeting(boardid);
	}
	 
	@GetMapping("/deleteMeeting")
    public String deleteMeeting(@RequestParam String boardid)  {
           return meetingService.deleteMeeting(boardid);
    }
	
	@PostMapping("/updateMeeting")
	public String updateMeeting(@RequestParam String boardid, @RequestBody Meeting meeting) {
		return meetingService.updateMeeting(boardid, meeting);
	}
	
	@PostMapping("/addMember")
    public String addMember(@RequestParam String boardid, @RequestBody Member member) {
        return meetingService.addMember(boardid, member);
    }

    @PostMapping("/removeMember")
    public String removeMember(@RequestParam String boardid, @RequestBody Member member) {
        return meetingService.removeMember(boardid, member);
    }

    @PostMapping("/addMessage")
    public String addMessage(@RequestParam String boardid, @RequestBody Message message) {
        return meetingService.addMessage(boardid, message);
    }
}
