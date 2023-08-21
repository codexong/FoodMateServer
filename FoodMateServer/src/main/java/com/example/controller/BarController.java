package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Bar;
import com.example.service.BarService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("")
public class BarController {

   @Autowired
   private BarService barService;
   
   @GetMapping("/getBarDetail")
    public Bar getBarDetail(@RequestParam String id) throws Exception {
           return barService.getBarDetail(id);
    }
   // 모든 리뷰 리스트
   @GetMapping("/bars")
    public List<Bar> getAllBars()throws Exception {
      List<Bar> test = barService.getAllBars();
         return test;
   }
}