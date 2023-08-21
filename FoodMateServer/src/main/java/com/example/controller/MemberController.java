package com.example.controller;

import java.util.Base64;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Member;
import com.example.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("")
public class MemberController {

    private final MemberService memberService;
    
    public byte[] decodeBase64(String input) {
        return Base64.getDecoder().decode(input);
    }

    @PostMapping("/insertMember")
    public String insertMember(
            @RequestParam("id") String id,
            @RequestParam("pw") String pw,
            @RequestParam("nickname") String nickname,
            @RequestParam("encodedImage") String encodedImage) throws Exception {
        Member member = new Member();
        member.setId(id);
        member.setPw(pw);
        member.setNickname(nickname);
        member.setDecodedImage(encodedImage);
        System.out.println("Received data - id: " + id);
        System.out.println("Received data - pw: " + pw);
        System.out.println("Received data - nickname: " + nickname);
        System.out.println("Received data - Image: " + encodedImage);
        return memberService.insertMember(member);
    }

    @GetMapping("/getMemberDetail")
    public Member getMemberDetail(@RequestParam String id) throws Exception {
        return memberService.getMemberDetail(id);
    }
    
    @PostMapping("/updateMember")
    public String updateMember(
          @RequestParam("id") String id,
            @RequestParam("pw") String pw,
            @RequestParam("nickname") String nickname,
            @RequestParam("encodedImage") String encodedImage) throws Exception {
       Member member = new Member();
        member.setId(id);
        member.setPw(pw);
        member.setNickname(nickname);
        member.setDecodedImage(encodedImage);
        System.out.println("update data - id: " + id);
        System.out.println("update data - pw: " + pw);
        System.out.println("update data - nickname: " + nickname);
        System.out.println("update data - Image: " + encodedImage);
        return memberService.updateMember(member);
    }

//    @PostMapping("/updateMember")
//    public String updateMember(@RequestBody Member member) throws Exception {
//        System.out.println("update data - id: "+member.getId());
//        System.out.println("update data - pw: "+member.getPw());
//        System.out.println("update data - nickname: "+member.getNickname());
//        return memberService.updateMember(member);
//    }

    @GetMapping("/deleteMember")
    public String deleteMember(@RequestParam("id") String id) throws Exception {
        System.out.println("Delete data - id: " + id);
        return memberService.deleteMember(id);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody Member member) throws Exception {
        LoginResponse response = new LoginResponse();

        System.out.println("Incoming id: " + member.getId());
        System.out.println("Incoming pw: " + member.getPw().toString()); // 수정: getPw 대신 getPassword로 변경

        boolean loginSuccessful = memberService.verifyLogin(member.getId(), member.getPw()); // 수정: getPw 대신 getPassword로 변경

        if (loginSuccessful) {
            response.setStatus("success");
            response.setMessage("Login successful");
            response.setSessionId(member.getId());
            response.setSessionPw(member.getPw().toString()); // 수정: getPw 대신 getPassword로 변경

            // 추가: 닉네임 값 가져오기
            String nickname = memberService.getNickname(member.getId());
            response.setSessionNickname(nickname);
            System.out.println("Incoming nickname: " + nickname);
            
         // 추가: 이미지 데이터 가져오기
            byte[] imageBytes = memberService.getImageData(member.getId());
            if (imageBytes != null) {
                String encodedImage = Base64.getEncoder().encodeToString(imageBytes);
                response.setSessionImage(encodedImage);
                System.out.println("Incoming Image: " +encodedImage);
            } else {
                response.setSessionImage(""); // 이미지가 없을 경우 빈 문자열로 설정
            }
        } else {
            response.setStatus("fail");
            response.setMessage("Login failed");
            response.setSessionId("");
            response.setSessionPw("");
            response.setSessionNickname("");
        }

        return response;
    }
    
    
}