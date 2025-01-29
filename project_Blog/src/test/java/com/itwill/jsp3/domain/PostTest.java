package com.itwill.jsp3.domain;

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
				.title("test해봄")
				.content("빌드 디자인 패턴 테스트!")
				.imageUrl("이미지 url 넣기")
				.author("테스트 당사자")
				.viewCount(0)
				.createdTime(LocalDateTime.now())
				.modifiedTime(LocalDateTime.now())
				.build();
		
		Assertions.assertNotNull(post);
		
		Assertions.assertEquals(1, post.getId());
		Assertions.assertEquals("test해봄", post.getTitle());
		
		log.debug("post = {}", post);
	}
}
