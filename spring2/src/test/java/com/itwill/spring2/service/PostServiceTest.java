package com.itwill.spring2.service;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.itwill.spring2.domain.Post;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/application-context.xml"})
public class PostServiceTest {
	// 단위 테스트에서,
	// 단위 테스트 객체(PostServiceTest)를 생성하고 테스트 메서드를 호출하는 테스트 메인 쓰레드는
	// '단위 테스트 클래스'(class PostServiceTest)의 '기본' 생성자만 호출)
	// -> 아규먼트를 갖는 생성자를 호출할 수 없기 때문에 '애너테이션을 사용한 의존성 주입만 가능'.
	
	@Autowired private PostService postService;
	
	@Test
	public void testRead() {
		Assertions.assertNotNull(postService);
		List<Post> list = postService.read(); //postService가 null 아니면 postService.read()가 호출되어야하니까 확인차..
		list.forEach(x -> log.debug("{}", x));
	}
	
}
