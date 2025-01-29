package com.itwill.spring2.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// MVC 아키텍쳐에서 Model역할을 담당하는 객체.
// 데이터베이스에서 posts테이블의 모델.
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {
	private Integer id;
	private String title;
	private String content;
	private String author;
	private LocalDateTime createdTime; //created_time컬럼
	private LocalDateTime modifiedTime; //modified_time컬럼
	
}


//mybatis-config.xml에서
//<typeAliases>
//<typeAlias type="com.itwill.spring2.domain.Post" alias="Post"/>
//</typeAliases>
