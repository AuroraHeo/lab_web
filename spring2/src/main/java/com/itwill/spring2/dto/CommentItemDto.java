package com.itwill.spring2.dto;

import java.sql.Timestamp;

import com.itwill.spring2.domain.Comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentItemDto {
	private Integer id;
	private String username;
	private String ctext;
	private Timestamp modifiedTime;
	
	// 엔터티(domain, model) 객체를 DTO 객체로 변환하는 편의 메서드.(comment타입의 객체 -> 필드 4개만 갖는 CommentItemDto타입 객체로 변환)
	public static CommentItemDto fromEntity(Comment comment) {
		if(comment != null) {
		return CommentItemDto.builder()
				.id(comment.getId())
				.username(comment.getUsername())
				.ctext(comment.getCtext())
				.modifiedTime(Timestamp.valueOf(comment.getModifiedTime()))
				.build();
		} else {
			return null;
		}
	}
}


//Timestamp.valueOf(LocalDateTime ~) : LocalDateTime타입을 Timestamp타입으로 변환