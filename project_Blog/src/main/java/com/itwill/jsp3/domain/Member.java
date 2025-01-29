package com.itwill.jsp3.domain;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.itwill.jsp3.domain.Member;

public class Member {
	private Integer id;
	private String username; //로그인 아이디
	private String password;
	private String email;
	private Integer points;
	private LocalDateTime createdTime; // created_time
	private LocalDateTime modifiedTime; // modified_time
	
	public Member() {}

	public Member(Integer id, String username, String password, String email, Integer points, LocalDateTime createdTime,
			LocalDateTime modifiedTime) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.points = points;
		this.createdTime = createdTime;
		this.modifiedTime = modifiedTime;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public Integer getPoints() {
		return points;
	}
	
	public void setPoints(Integer points) {
		this.points = points;
	}

	public LocalDateTime getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(LocalDateTime createdTime) {
		this.createdTime = createdTime;
	}

	public LocalDateTime getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(LocalDateTime modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	public Integer getId() {
		return id;
	}
	
	@Override
	public String toString() {
		return "Member [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email
				+ ", points=" + points + ", createdTime=" + createdTime + ", modifiedTime=" + modifiedTime + "]";
	}

	//빌드 디자인 패턴
	public static MemberBuilder builder() {
		return new MemberBuilder();
	}
	
	public static class MemberBuilder {
		private Integer id;
		private String username; //로그인 아이디
		private String password;
		private String email;
		private Integer points;
		private LocalDateTime createdTime; // created_time
		private LocalDateTime modifiedTime; // modified_time
		
		public MemberBuilder() {}
		
		public MemberBuilder id(Integer id) {
			this.id = id;
			return this;
		}
		
		public MemberBuilder username(String username) {
			this.username = username;
			return this;
		}
		
		public MemberBuilder password(String password) {
			this.password = password;
			return this;
		}
		
		public MemberBuilder email(String email) {
			this.email = email;
			return this;
		}
		
		public MemberBuilder points(Integer points) {
			this.points = points;
			return this;
		}
		
		public MemberBuilder createdTime(LocalDateTime createdTime) {
			this.createdTime = createdTime;
			return this;
		}
		
		public MemberBuilder createdTime(Timestamp createdTime) {
			this.createdTime = createdTime.toLocalDateTime();
			return this;
		}
		
		public MemberBuilder modifiedTime(LocalDateTime modifiedTime) {
			this.modifiedTime = modifiedTime;
			return this;
		}
		
		public MemberBuilder modifiedTime(Timestamp modifiedTime) {
			this.modifiedTime = modifiedTime.toLocalDateTime();
			return this;
		}
		
		public Member build() {
			return new Member(id, username, password, email, points, createdTime, modifiedTime);
		}
	}
}
