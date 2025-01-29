package com.itwill.jsp3.dbcp;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DbcpTest {
	private static final Logger log = LoggerFactory.getLogger(DbcpTest.class);
	
	@Test
	public void testHikariCP() throws SQLException {
		HikariConfig config = new HikariConfig();
		
		config.setDriverClassName("oracle.jdbc.OracleDriver");
		config.setJdbcUrl("jdbc:oracle:thin:@localhost:1521:xe");
		config.setUsername("blogproject");
		config.setPassword("blogproject");
		
		HikariDataSource ds = new HikariDataSource(config);
		log.debug("ds = {}", ds);
		
		Connection conn = ds.getConnection();
		Assertions.assertNotNull(conn);
		log.debug("connection = {}", conn);
		
		conn.close();
		log.debug("커넥션 반환 성공");
	}
}
