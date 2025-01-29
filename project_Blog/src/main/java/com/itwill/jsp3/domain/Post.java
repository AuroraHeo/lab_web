package com.itwill.jsp3.domain;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Post {
	private Integer id;
	private String title;
	private String content;
	private String imageUrl; //image_url
	private String author;
	private Integer viewCount = 0; // view_count
	private LocalDateTime createdTime; // created_time
	private LocalDateTime modifiedTime; // modified_time
	
	public Post() {}

	public Post(Integer id, String title, String content, String imageUrl, String author, Integer viewCount, LocalDateTime createdTime, LocalDateTime modifiedTime) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.imageUrl = imageUrl;
		this.author = author;
		this.viewCount = viewCount;
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
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImageUrl() {
		return imageUrl;
	}
	
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public Integer getViewCount() {
		return viewCount;
	}

	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
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
	
	// 변환된 날짜 형식을 반환하는 메서드 추가 
	public String getFormattedCreatedTime() { 
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분 ss초"); 
		return this.createdTime.format(formatter); 
	} 
	
	public String getFormattedModifiedTime() { 
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분 ss초"); 
		return this.modifiedTime.format(formatter); 
	}

	@Override
	public String toString() {
		return "Post [id=" + id + ", title=" + title + ", content=" + content + ", imageUrl=" + imageUrl + ", author=" + author + ", viewCount=" + viewCount + ", createdTime=" + createdTime
				+ ", modifiedTime=" + modifiedTime + "]";
	}
	
	//빌드 디자인 패턴
	public static PostBuilder builder() {
		return new PostBuilder();
	}
	
	public static class PostBuilder{
		private Integer id;
		private String title;
		private String content;
		private String imageUrl; //image_url
		private String author;
		private Integer viewCount; // view_count
		private LocalDateTime createdTime; // created_time
		private LocalDateTime modifiedTime; // modified_time
		
		public PostBuilder() {}

		public PostBuilder id(Integer id) {
			this.id = id;
			return this;
		}
		
		public PostBuilder title(String title){
			this.title = title;
			return this;
		}
		
		public PostBuilder content(String content) {
			this.content = content;
			return this;
		}
		
		public PostBuilder imageUrl(String imageUrl) {
			this.imageUrl = imageUrl;
			return this;
		}
		
		public PostBuilder author(String author) {
			this.author = author;
			return this;
		}
		
		public PostBuilder viewCount(Integer viewCount) {
			this.viewCount = viewCount;
			return this;
		}

		public PostBuilder createdTime(LocalDateTime createdTime) {
			this.createdTime = createdTime;
			return this;
		}
		
		public PostBuilder createdTime(Timestamp createdTime) {
			this.createdTime = createdTime.toLocalDateTime();
			return this;
		}
		
		public PostBuilder modifiedTime(LocalDateTime modifiedTime) {
			this.modifiedTime = modifiedTime;
			return this;
		}
		
		public PostBuilder modifiedTime(Timestamp modifiedTime) {
			this.modifiedTime = modifiedTime.toLocalDateTime();
			return this;
		}
		
		public Post build() {
			return new Post(id, title, content, imageUrl, author, viewCount, createdTime, modifiedTime);
		}
	}
}
