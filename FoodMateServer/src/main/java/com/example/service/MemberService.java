package com.example.service;

import com.example.model.Member;

public interface MemberService {

//    List<Member> getMember() throws ExecutionException, InterruptedException;

   public String insertMember(Member member) throws Exception;

    public Member getMemberDetail(String id) throws Exception;

    public String updateMember(Member member) throws Exception;

    public String deleteMember(String id) throws Exception;

   boolean verifyLogin(String id, String pw);

   public String getNickname(String id) throws Exception;

   public byte[] getImageData(String id);
    
}