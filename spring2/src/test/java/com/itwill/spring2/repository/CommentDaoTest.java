package com.itwill.spring2.repository;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.itwill.spring2.domain.Comment;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/application-context.xml"}) //dao주입받기 위해 compononet 스캔하려고
public class CommentDaoTest {
	@Autowired
	CommentDao commentDao; //dao 주입받기
	
//	@Test
	public void testCommentDao() {
		Assertions.assertNotNull(commentDao);
		log.debug("commenttDao={}", commentDao);
	}
	
//	@Test
	public void testSelectByPostId() {
		Assertions.assertNotNull(commentDao);
		
		List<Comment> comments = commentDao.selectByPostId(1);
		log.debug("# of comments = {}", comments.size());
		comments.forEach(x -> log.debug("{}", x));
	}
	
	@Test
	public void testselectById() {
		//댓글 아이디가 테이블에 있는 경우
		Comment c1 = commentDao.selectById(1);  //1
		Assertions.assertNotNull(c1);
		log.debug("c1 = {}", c1);
		
		//댓글 아이디가 테이블에 없는 경우
		Comment c2 = commentDao.selectById(1000); //0
		Assertions.assertNull(c2);
		log.debug("c2 = {}", c2);
	}
	
//	@Test
	public void testInsertComment() {
		Comment comment = Comment.builder()
				.postId(1)
				.ctext("comment insert test")
				.username("admin").build();
		
		int result = commentDao.insertComment(comment);
		Assertions.assertEquals(1, result);
	}
	
//	@Test
	public void testDeleteById() {
		int result = commentDao.deleteById(21);
		Assertions.assertEquals(1, result);
	}
	
//	@Test
	public void testDeleteByPostId() {
		int result = commentDao.deleteByPostId(1);
		Assertions.assertEquals(4, result);
	}
	
//	@Test
	public void testUpdateComment() {
		Comment comment = Comment.builder()
				.id(1)
				.ctext("comment update test")
				.build();
		int result = commentDao.updateComment(comment);
		Assertions.assertEquals(1, result);
		
	}
	
//	@Test
	public void testSelectCommentCount() {
		Integer result = commentDao.selectCommentCount(1);
		Assertions.assertEquals(5, result); // 5는 댓글의 갯수
	}
	
}

// @Test public void testSelectCommentCount()
//25-01-08 12:15:07.561 DEBUG [repository.CommentDao.selectCommentCount(135)] ==>  Preparing: select count(*) from comments where post_id = ?
//25-01-08 12:15:07.887 DEBUG [repository.CommentDao.selectCommentCount(135)] ==> Parameters: 1(Integer)
//25-01-08 12:15:08.034 DEBUG [repository.CommentDao.selectCommentCount(135)] <==      Total: 1 (resultSet의 행의 갯수(댓글의 갯수가 아님))!!!!!!!!!
