/**
 * 功能说明:
 * 功能作者:
 * 创建日期:
 * 版权归属:每特教育|蚂蚁课堂所有 www.itmayiedu.com
 */
package com.itmayiedu.api.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能说明: <br>
 * 创建作者:每特教育-余胜军<br>
 * 创建时间:2018年9月3日 下午9:37:55<br>
 * 教育机构:每特教育|蚂蚁课堂<br>
 * 版权说明:上海每特教育科技有限公司版权所有<br>
 * 官方网站:www.itmayiedu.com|www.meitedu.com<br>
 * 联系方式:qq644064779<br>
 * 注意:本内容由每特教育学员共同研发,请尊重原创版权
 */
@RestController
public class MemberApiController {
	@Value("${server.port}")
	private String serverPort;

	@RequestMapping("/getMember")
	public String getMember() {
		return "this is member，我是会员服务,springcloud2.0版本,学习分布式和微服务请上蚂蚁课堂!端口号:" + serverPort;
	}

}
