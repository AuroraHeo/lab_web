package com.itwill.spring2.repository;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.itwill.spring2.domain.Post;

import lombok.extern.slf4j.Slf4j;

@Slf4j //로그 출력을 위해
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/application-context.xml"})
public class PostDaoTest {
	
	@Autowired// MyBatis 프레임워크에서 자동으로 생성된 PostDao 인터페이스 구현 객체를 주입.
	PostDao postDao; //개발자가 추상메서드의 몸통부를 구현하지 않아도 Mybatis가 인터페이스를 구현하는 클래스의 객체를 생성하여 postDao에 넣어줌. 
	
//	@Test
	public void testPostDao() {
		Assertions.assertNotNull(postDao);
		log.debug("postDao={}", postDao);
	}
	
//	@Test
	public void testSelectOrderByIdDesc() {
		List<Post> list = postDao.selectOrderByIdDesc();
		list.forEach(x -> log.debug("{}", x));
	}
		
//	@Test
	public void testSelectById() {
		//테이블에 id가 존재하는 경우
		Post post1 = postDao.selectById(1);
		Assertions.assertNotNull(post1);
		log.debug("post1={}", post1);
		
		//테이블에 id가 없는 경우
		Post post2 = postDao.selectById(2);
		Assertions.assertNull(post2);
		log.debug("post2={}", post2);
	}
	
//	@Test
	public void testInsertPost() {
		Post post = Post.builder()
				.title("안녕하세요.")
				.content("월요일입니다.")
				.author("guest")
				.build();
		int result = postDao.insertPost(post);
		Assertions.assertEquals(1, result); // result가 1임을 주장!(1개 행이 insert)
		
	}
	
//	@Test
	public void testUpdatePost() {
		Post post = Post.builder().id(81).title("Hello.").content("It's Monday!").build();
		
		int result = postDao.updatePost(post);
		Assertions.assertEquals(1, result);
	}
	
	@Test
	public void testDeletePost() {
		int result = postDao.deletePost(81);
		Assertions.assertEquals(1, result); // 첫번째 unittest만 1이 리턴되고, 두번째 unittiest부터는 0이 리턴됨
	}
}
