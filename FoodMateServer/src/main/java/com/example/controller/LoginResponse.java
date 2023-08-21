package com.example.controller;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private String status;
    private String message;
    private String sessionId;
    private String sessionPw;
    private String sessionNickname;
    private String sessionImage;
    
}