package com.itwill.jsp2.domain;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Post {
	private Integer id;
	private String title;
	private String Content;
	private String author;
	private LocalDateTime createdTime;
	private LocalDateTime modifiedTime;
	
	public Post() {}

	public Post(Integer id, String title, String content, String author, LocalDateTime createdTime,
			LocalDateTime modifiedTime) {
		this.id = id;
		this.title = title;
		this.Content = content;
		this.author = author;
		this.createdTime = createdTime;
		this.modifiedTime = modifiedTime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
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
	//setId는 안만듦

	@Override
	public String toString() {
		return "Post [id=" + id + ", title=" + title + ", Content=" + Content + ", author=" + author + ", createdTime="
				+ createdTime + ", modifiedTime=" + modifiedTime + "]";
	}
	
	
	//Builder 디자인 패턴
	//Builder 클래스를 static으로 선언하면, 외부 클래스의 인스턴스 없이도 Builder를 바로 생성하고 사용할 수 있음.
	public static PostBuilder builder() {
		return new PostBuilder();
	}

	public static class PostBuilder {
		//필드는 외부로부터 직접적인 접근을 막기 위해 캡슐화
		private Integer id;
		private String title;
		private String content;
		private String author;
		private LocalDateTime createdTime;
		private LocalDateTime modifiedTime;

		public PostBuilder() {
		}

		public PostBuilder id(Integer id) {
			this.id = id;
			return this;
		}

		public PostBuilder title(String title) {
			this.title = title;
			return this;
		}

		public PostBuilder content(String content) {
			this.content = content;
			return this;
		}

		public PostBuilder author(String author) {
			this.author = author;
			return this;
		}

		public PostBuilder createdTime(LocalDateTime createdTime) {
			this.createdTime = createdTime;
			return this;
		}
		
		//유연성을 위해 위 createdTime메서드를 오버로딩한 것
		//데이터베이스와의 상호작용 시, 날짜와 시간은 종종 Timestamp 형식으로 저장되기 때문에
		//Timestamp를 파라미터로 받아들이면 데이터베이스에서 직접 읽어온 데이터를 쉽게 변환하고 사용할 수 있음.
		public PostBuilder createdTime(Timestamp createdTime) { 
			this.createdTime = createdTime.toLocalDateTime(); //toLocalDateTime() : Timstamp타입을 LocalDateTime타입으로 변환
			return this;
		}

		public PostBuilder modifiedTime(LocalDateTime modifiedTime) {
			this.modifiedTime = modifiedTime;
			return this;
		}

		public PostBuilder modifiedTime(Timestamp modifiedTime) {//위와 같은 이유로 위 modifiedTime메서드를 오버로딩한 것
			this.modifiedTime = modifiedTime.toLocalDateTime();
			return this;
		}

		public Post build() {
			return new Post(id, title, content, author, createdTime, modifiedTime);
		}
	}
}
