package com.mayikt.api.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class MessageController {

	@RequestMapping("/sendMessage")
	public String sendMessage() {
		return "消息服务:发送消息";
	}

	public static void main(String[] args) {
		SpringApplication.run(MessageController.class, args);
	}

}
