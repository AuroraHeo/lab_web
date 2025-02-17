package com.itwill.spring2.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {
	private Integer id;
	private String username;
	private String password;
	private String email;
	private Integer points;
	private LocalDateTime createdTime; //created_time
	private LocalDateTime modifiedTime; //modified_time
}
