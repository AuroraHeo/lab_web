package com.itwill.jsp2.dbcp;

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
		//HikariCP의 환경 설정을 할 수 있는 객체를 생성.
		HikariConfig config = new HikariConfig();
		
		//데이터베이스에 접속(커넥션, connection)할 수 있는 환경 설정.
		config.setDriverClassName("oracle.jdbc.OracleDriver"); //OjdbcTest.java에서 import되었던 oracle.jdbc.OracleDriver
		config.setJdbcUrl("jdbc:oracle:thin:@localhost:1521:xe"); //OjdbcTest.java에서 final String url
		config.setUsername("jspstudy");
		config.setPassword("jspstudy");
//참고
//		config.setIdleTimeout(1000); //connection이 몇초(1000ms=1초)동안 놀고 있으면 반환시키는..
//		config.setMinimumIdle(3);//놀고 있는 conncection의 최소 갯수(ex.3개)를 설정.
//		config.setMaximumPoolSize(20);//pool의 connection 최대 갯수(ex.20개)를 설정.
		
		//DataSource(데이터 소스(커넥션 풀과 유사...)) 객체 생성
		//-> 데이터베이스 서버와 물리적인 연결(커넥션)이 맺어짐.
		HikariDataSource ds = new HikariDataSource(config);
		log.debug("ds = {}", ds);
		
		//데이터 소스(커넥션 풀)에서 이미 생성된 커넥션 객체를 빌려옴(getConnection()). (물리적인 연결을 맺는게 아님)
		Connection conn = ds.getConnection();
		Assertions.assertNotNull(conn);
		log.debug("connection = {}", conn);
		
		//Statement 생성, sql 실행(executeQuery or executeUpdate), 결과 처리
		
		//사용했던 커넥션 객체를 데이터 소스(커넥션 풀)에 '반환'. (물리적인 연결을 끊는게 아님)
		conn.close();
		log.debug("커넥션 반환 성공");
	}
}
