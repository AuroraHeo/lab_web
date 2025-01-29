package com.itwill.spring2.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//DB comments 테이블의 엔터티(모델).
@Data 
@NoArgsConstructor // 스프링 컨테이너(디스패쳐 서블릿, 마이바티스)에 사용되는 생성자. 
@AllArgsConstructor // Builder 디자인 패턴에서 사용되는 생성자.
@Builder
public class Comment {
	private Integer id; // PK
	private Integer postId; // post_id 컬럼. FK // id 몇번 글에 댓글인지
	private String username; // 댓글 작성자
	private String ctext; // 댓글 내용
	private LocalDateTime createdTime; // created_time 컬럼. 댓글 작성 시간
	private LocalDateTime modifiedTime; //modified_time 컬럼. 댓글 최종 수정 시간
	

}
