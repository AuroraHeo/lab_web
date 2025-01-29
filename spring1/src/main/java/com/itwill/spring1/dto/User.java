package com.itwill.spring1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // 1. @getter, @setter, @toString(), @EqualsAndHashCode, @RequiredArgsConstructor 
	// final 필드가 없으면 RequiredArgsConstructor 대신 기본생성자 생성 
	// @RequiredArgsConstructor : final 필드를 초기화할 수 있는 아규먼트(들)을 갖는 생성자.
	// 필드에 final int x;를 선언하게 되면
	// public User(int x){
	//		this.x = x;	
	// } 생성자가 있어야하니까...
@NoArgsConstructor // 3. 기본생성자 생성
@AllArgsConstructor // 2. (모든 필드를 초기화할 수 있는)아규먼트가 2개인 생성자 생성(but 기본생성자 사라짐)
@Builder //4. builder 디자인 패턴 적용
public class User {
	private String username;
	private Integer age;
	
}


//@Getter private String username; //getter만 갖는 필드 선언
//@Setter private int age; // setter만 갖는 필드 선언
//https://projectlombok.org/features/ << lombok 애너테이션 참고해보기

