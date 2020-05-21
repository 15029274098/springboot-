/**
 * 功能说明:
 * 功能作者:
 * 创建日期:
 * 版权归属:每特教育|蚂蚁课堂所有 www.itmayiedu.com
 */
package com.itmayeidu.api.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * 功能说明: <br>
 * 创建作者:每特教育-余胜军<br>
 * 创建时间:2018年9月3日 下午9:53:12<br>
 * 教育机构:每特教育|蚂蚁课堂<br>
 * 版权说明:上海每特教育科技有限公司版权所有<br>
 * 官方网站:www.itmayiedu.com|www.meitedu.com<br>
 * 联系方式:qq644064779<br>
 * 注意:本内容有每特教育学员共同研发,请尊重原创版权
 */
@SpringBootApplication
@EnableEurekaClient
public class AppOrder {
	public static void main(String[] args) {
		SpringApplication.run(AppOrder.class, args);

		// 如果使用rest方式以别名方式进行调用依赖ribbon负载均衡器 @LoadBalanced
		// @LoadBalanced就能让这个RestTemplate在请求时拥有客户端负载均衡的能力
	}

	// 解决RestTemplate 找不到原因 应该把restTemplate注册SpringBoot容器中 @bean
	@Bean
	@LoadBalanced
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
