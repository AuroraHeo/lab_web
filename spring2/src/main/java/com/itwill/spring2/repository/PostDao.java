package com.itwill.spring2.repository;

import java.util.List;

import com.itwill.spring2.domain.Post;
import com.itwill.spring2.dto.PostSearchDto;

public interface PostDao {

	List<Post> selectOrderByIdDesc(); // 추상메서드의 {}(몸통부)를 만들지 않아도 Mybatis가 구현클래스를 만들어주니까 이 메서드를 만들어달라고 선언.
	Post selectById(Integer id); //MyBatis가 자동으로 구현하게끔
	int insertPost(Post post);
	int updatePost(Post post);
	int deletePost(Integer id);
	List<Post> search(PostSearchDto dto);
}
//post-mapper.xml와 연관
//<mapper namespace="com.itwill.spring2.repository.PostDao">
//	<!-- 영속성 프레임워크 MyBatis에서 실행할 SQL 문장들을 선언하는 파일 -->
//</mapper>