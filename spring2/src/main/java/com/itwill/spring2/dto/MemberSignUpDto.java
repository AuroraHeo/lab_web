package com.itwill.spring2.dto;

import com.itwill.spring2.domain.Member;

import lombok.Data;

@Data
public class MemberSignUpDto {
	private String username;
	private String password;
	private String email;
	
	//DTO -> Entity로 변환해주는 메서드
	public Member toEntity() {
		return Member.builder().username(username).password(password).email(email).build();
	}
}

//signup.jsp의 요청 파라미터의 name과 같게 필드명을 선언.