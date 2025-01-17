package com.lec.board.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/board")
public class BoardController {

	@GetMapping("/list")
	public String list() {
		return "<h1 align=\"center\">20250108 최종테스트 9th!!!!! - Board List....</h1>";
	}
	
	@GetMapping("/getBoard")
	public String getBoard() {
		return "<h1>Get Board ....</h1>";
	}
	
	@GetMapping("/jenkins")
	public String testJenkins() {
		return "<h1>Jenkins CI/CD Test ....</h1>";
	}	
}
