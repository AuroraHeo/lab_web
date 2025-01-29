package com.itwill.jsp2.domain;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PostTest {
	private static final Logger log = LoggerFactory.getLogger(PostTest.class);
	
	@Test
	public void testPostBuilder() {
		Post post = Post.builder()
				.id(1)
				.title("test")
				.content("테스트 빌더 디자인 패턴")
				.author("admin")
				.createdTime(LocalDateTime.now())
				.modifiedTime(LocalDateTime.now())
				.build();
		
		//단위 테스트의 성공 케이스를 작성.
		Assertions.assertNotNull(post); // 빌더된 post객체가 null이 아니면 단위 테스트 성공~
		
		//Assertions.assertEquals(expected, actual); //실제 값(actual)이 기대값(expected)과 같으면 단위 테스트 성공~
		Assertions.assertEquals(1, post.getId()); //post의 id값(실제 값)이 1(성공을 예상하는 값)과 같다고 주장~ 같으면 단위 테스트 성공~
		Assertions.assertEquals("test", post.getTitle()); //만약 아규먼트가 "Test", post.getTitle()이면 실패!
		
		log.debug("post = {}", post);
	}
}