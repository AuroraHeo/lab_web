package com.itwill.jsp3.ojdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import oracle.jdbc.OracleDriver;

public class OjdbcTest {
	private static final Logger log = LoggerFactory.getLogger(OjdbcTest.class);
	
	@Test
	public void testSelect() throws SQLException {
		DriverManager.registerDriver(new OracleDriver());
		log.debug("오라클 드라이버 등록 성공");
		
		final String url = "jdbc:oracle:thin:@localhost:1521:xe";
		final String user = "blogproject";
		final String password = "blogproject";
		Connection conn = DriverManager.getConnection(url, user, password);
		log.debug("conn = " + conn);
		
		final String sql = "select * from posts";
		PreparedStatement stmt = conn.prepareStatement(sql);
		Assertions.assertNotNull(stmt);
		
		ResultSet rs = stmt.executeQuery();
		Assertions.assertNotNull(rs);
		
		while(rs.next()) {
			int id = rs.getInt("id");
			String title = rs.getString("title");
			String content = rs.getString("content");
			String imageUrl = rs.getString("image_url");
			String author = rs.getString("author");
			int viewCount = rs.getInt("view_count");
			LocalDateTime createdTime = rs.getTimestamp("created_time").toLocalDateTime();
			LocalDateTime modifiedTime = rs.getTimestamp("modified_time").toLocalDateTime();
			
			log.debug("{} | {} | {} | {} | {} | {} | {} | {}", id, title, content, imageUrl, author, viewCount, createdTime, modifiedTime);
			
			rs.close();
			stmt.close();
			conn.close();
			log.debug("오라클 접속 해제");
		}
	}
}
