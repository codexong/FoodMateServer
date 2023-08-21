package com.example.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Data
@ToString
public class Board {
   
   private String boardid;
   private String userNicname;
   private String title;
   private String content;
   private String barName;
   private String barImg;
   private String memberCount;
   private String meetdate;
   private String regdate;
}