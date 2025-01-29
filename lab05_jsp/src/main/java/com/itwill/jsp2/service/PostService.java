package com.itwill.jsp2.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itwill.jsp2.domain.Post;
import com.itwill.jsp2.repository.MemberDao;
import com.itwill.jsp2.repository.PostDao;

// 웹 MVC 아키텍쳐에서 서비스/비즈니스 계층(service/ business layer)을 담당하는 객체.
// 영속성 계층의 기능들을 사용해서 비즈니스 로직(서비스)을 구현하는 객체.
public enum PostService {
	INSTANCE; //싱글톤 객체.
	
	private static final Logger log = LoggerFactory.getLogger(PostService.class);
	private final PostDao postDao = PostDao.INSTANCE; //서비스가 postDao객체를 사용하려고 필드로 선언.
	private final MemberDao memberDao = MemberDao.INSTANCE; //서비스가 memberDao객체를 사용하려고 필드로 선언.
	
	public List<Post> read() {
		log.debug("read()");
		
		return postDao.select();
	}

	public int create(Post post) {
		log.info("create({})", post);
		
		// 영속성 계층의 메서드를 호출 -> DB posts 테이블 insert 수행.
		int result = postDao.insert(post);
		log.debug("insert result = {}", result);
		
		// 포스트 작성자에게 10포인트 지급 - members 테이블의 포인트 업데이트 수행.
		result = memberDao.update(post.getAuthor(), 10);
		log.debug("update members result = {}", result);
		
		return result;
	}

	public Post read(int id) {
		log.debug("read(id={})", id);
		
		//영속성 계층의 메서드를 호출해서 DB 테이블에서 select한 결과를 리턴.
//		Post post = postDao.select(id);
//		return post;
		return postDao.select(id);
	}
	
	public int delete(int id) {
		log.debug("delete(id={})", id);
		
		int result = postDao.delete(id);
		log.debug("{}개 행이 삭제됨.", result);
		
		return result;
	}
	
	public int update(Post post) {
		log.debug("update(post={})", post);
		
		int result = postDao.update(post);
		log.debug("{}개 행이 업데이트됨.", result);
		
		return result;
	}
	
	public List<Post> read(String category, String keyword){
		log.debug("read(category={}, keyword={})", category, keyword);
		
		return postDao.select(category, keyword);
	}
	
}
