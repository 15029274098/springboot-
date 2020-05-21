/**
 * 功能说明:
 * 功能作者:
 * 创建日期:
 * 版权归属:每特教育|蚂蚁课堂所有 www.itmayiedu.com
 */
package com.itmayeidu.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 功能说明: <br>
 * 创建作者:每特教育-余胜军<br>
 * 创建时间:2018年9月3日 下午9:46:26<br>
 * 教育机构:每特教育|蚂蚁课堂<br>
 * 版权说明:上海每特教育科技有限公司版权所有<br>
 * 官方网站:www.itmayiedu.com|www.meitedu.com<br>
 * 联系方式:qq644064779<br>
 * 注意:本内容有每特教育学员共同研发,请尊重原创版权
 */
@RestController
public class OrderControler {

	// RestTemplate 是有SpringBoot Web组件提供 默认整合ribbon负载均衡器
	// rest方式底层是采用httpclient技术
	@Autowired
	private RestTemplate restTemplate;

	/**
	 * 在SpringCloud 中有两种方式调用 rest、fegin（SpringCloud）
	 * 
	 * @return
	 */

	// 订单服务调用会员服务
	@RequestMapping("/getOrder")
	public String getOrder() {
		// 有两种方式，一种是采用服务别名方式调用，另一种是直接调用 使用别名去注册中心上获取对应的服务调用地址
		String url = "http://app-itmayiedu-member/getMember";
		String result = restTemplate.getForObject(url, String.class);
		System.out.println("订单服务调用会员服务result:" + result);
		return result;
	}

}
