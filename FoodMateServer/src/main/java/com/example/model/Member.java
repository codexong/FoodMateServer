package com.example.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Data
@ToString
public class Member {

    private String id;
    private String pw;
    private String nickname;
    private String decodedImage;

}
