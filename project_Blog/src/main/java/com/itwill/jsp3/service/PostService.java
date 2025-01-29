package com.itwill.jsp3.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itwill.jsp3.domain.Post;
import com.itwill.jsp3.repository.MemberDao;
import com.itwill.jsp3.repository.PostDao;

public enum PostService {
    INSTANCE;
    
    private static final Logger log = LoggerFactory.getLogger(PostService.class);
    private final PostDao postDao = PostDao.INSTANCE;
    private final MemberDao memberDao = MemberDao.INSTANCE;
    
    public List<Post> read() {
        log.debug("read()");
        return postDao.select();
    }

    public int create(Post post) {
        log.info("create({})", post);
        
        // 영속성 계층의 메서드를 호출 -> DB posts 테이블 insert 수행.
        int result = postDao.insert(post);
        log.debug("insert result = {}", result);
        
        // 포스트 작성자에게 100포인트 지급 - members 테이블의 포인트 업데이트 수행.
        result = memberDao.update(post.getAuthor(), 100);
        log.debug("update members result = {}", result);
        
        return result;
    }

    public Post read(int id) {
        log.debug("read(id={})", id);
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
    
    public List<Post> read(String category, String keyword) {
        log.debug("read(category={}, keyword={})", category, keyword);
        return postDao.select(category, keyword);
    }
    
    // 조회수 1 증가 메서드
    public void increaseViewCount(int id) {
        log.debug("increaseViewCount(id={})", id);
        postDao.increaseViewCount(id);
        log.debug("조회수 증가 완료");
    }

    // offset 5개 게시글
    public List<Post> read(int offset, int limit) {
        log.debug("read(offset={}, limit={})", offset, limit);
        List<Post> posts = postDao.read(offset, limit);
        if (posts == null) {
            posts = new ArrayList<>(); // null일 경우 빈 리스트를 반환
        }
        return posts;
    }

    // 최근 게시글 5개 가져오기
    public List<Post> getRecentPosts(int limit) {
        log.debug("getRecentPosts(limit={})", limit);
        return postDao.selectRecentPosts(limit);
    }

    // 조회수 높은 게시글 3개 가져오기
    public List<Post> getPopularPosts(int limit) {
        log.debug("getPopularPosts(limit={})", limit);
        return postDao.selectPopularPosts(limit);
    }
    
    // 글 수정하기에서 이미지 삭제 
    public void removeImage(int postId) {
        log.debug("removeImage(postId={})", postId);
        postDao.removeImage(postId);
    }
   
    // 총 게시글 수를 가져오는 메서드 
    public int getTotalPostCount() { 
    	return postDao.countPosts(); 
    }
    
    // 총 페이지 수를 계산하는 메서드 
    public int getTotalPages(int postsPerPage) { 
    	int totalPosts = getTotalPostCount(); 
    	return (int) Math.ceil((double) totalPosts / postsPerPage); 
    }
    
    // 현재 페이지를 계산하는 메서드 
    public int getCurrentPage(int offset, int postsPerPage) { 
    	return (offset / postsPerPage) + 1; 
    }

}

