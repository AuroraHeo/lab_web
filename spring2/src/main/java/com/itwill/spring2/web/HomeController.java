package com.itwill.spring2.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller //디스패쳐 서블릿의 핸들러 매핑에 컨트롤러 빈으로 등록.
public class HomeController {
	
	@GetMapping("/")
	public String home() {
		log.debug("home()");
		
		return "home";// 뷰(jsp파일)의 이름.
	}

}

//1.servlet-context.xml에 <context:component-scan base-package="com.itwill.spring2.web" />
//2.@Controller 필수