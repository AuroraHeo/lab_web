package com.itwill.spring1.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.itwill.spring1.dto.User;

import lombok.extern.slf4j.Slf4j;

/* 
 * @Controller vs @RestController : '클래스'에 사용하는 애너테이션
 * 
 * @Controller 애너테이션이 설정된 클래스의 컨트롤러 메서드들의 리턴값은
 * 	- 기본적으로는 '뷰의 이름'을 의미.
 * 	- @ResponseBody가 설정된 메서드의 경우에는, '응답으로 전송되는 데이터'를 의미
 * 
 * @RestController 애너테이션이 설정된 클래스의 모든 컨트롤러 메서드들의 리턴 값은
 * 	- '응답으로 직접 전송되는 데이터'이기 때문에
 * 	  RestController의 메서드에서는 @ResponseBody 애너테이션을 사용하지 않음.
 *  - 단순 문자열을 client로 응답보내기 때문에 응답속도가 빨라서 사용...
 */

@Slf4j
@RestController //뷰의 이름을 리턴할 수 없고, 모든 메서드의 리턴값은 전부다 문자열!( @ResponseBody 역할)
public class ExampleRestController {
	
	@GetMapping("/rest3")
	public String rest3() {
		log.debug("rest3()");
		
		return "안녕하세요, REST!";
	}
	
	@GetMapping("/rest4")
	public List<User> rest4() {
		log.debug("rest4()");
		
		User user1 = User.builder().age(10).username("홍길동").build();
		User user2 = User.builder().age(18).username("허유진").build();
		
		List<User> userlist = new ArrayList<>();
		userlist.add(user1);
		userlist.add(user2);
		
		return userlist; //JSON문자열로 리턴됨. ex.WAS가 client에게 JSON데이터만 보내고, client의 브라우저의 jS가 화면을 그려줌.
	}
	
	
}
