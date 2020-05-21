package com.mayikt.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class MsgController {

	@RequestMapping("/sndMsg")
	public String sndMsg() {
		try {
			Thread.sleep(5000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "我是消息服务平台";
	}

	public static void main(String[] args) {
		SpringApplication.run(MsgController.class, args);
	}

}
