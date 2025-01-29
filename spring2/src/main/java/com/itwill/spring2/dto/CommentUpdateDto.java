package com.itwill.spring2.dto;

import com.itwill.spring2.domain.Comment;

import lombok.Data;

@Data
public class CommentUpdateDto {
	private Integer id;
	private String ctext;
	//필드 2가지로 댓글 업데이트 가능
	
	//DTO -> Entity 변환하는 메서드
	public Comment toEntity() {
		return Comment.builder().id(id).ctext(ctext).build();
	}
}
