package com.itwill.spring2.dto;

import com.itwill.spring2.domain.Comment;

import lombok.Data;

@Data
public class CommentCreateDto {
	private Integer postId;
	private String username;
	private String ctext;
	//필드 3가지로 댓글 작성 가능
	
	//DTO -> Entity 변환 메서드
	public Comment toEntity() { //파라미터에 이미 필드 3개로 채워진 상태.. 객체가 만들어진 다음에 호출될꺼라서 static으로 선언 안함.
		return Comment.builder().postId(postId).username(username).ctext(ctext).build(); //dto가 가지고 있는 postId,username,ctext로 Comment를 생성
	}
}

//request가 들어올때 dto가 생김
//[client]----request----> [controller] ---dto---> [service] ----entity ----> [dao] -----> [DB]