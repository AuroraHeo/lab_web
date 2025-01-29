package com.itwill.jsp2.datasourceutil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public enum DataSourceUtil {
	INSTANCE; //singleton 객체
	
	//필드
	private HikariConfig config;
	private HikariDataSource ds;
	
	//생성자 - enum 생성자는 항상 private (private는 생략 가능)
	DataSourceUtil() {
		// HikariCP(라이브러리?)의 환경 설정(configuration)하기: JDBC URL, 사용자 이름, 비밀번호, 최소 유휴 커넥션 수, 최대 커넥션 풀 크기, 유휴 타임아웃)을 설정하는 HikariConfig 객체 생성.
		config = new HikariConfig();
		
		// 커넥션 풀(데이터 소스)의 기본적인 환경 설정.
		config.setDriverClassName("oracle.jdbc.OracleDriver");
		config.setJdbcUrl("jdbc:oracle:thin:@localhost:1521:xe");
		config.setUsername("jspstudy");
		config.setPassword("jspstudy");
		
		// 데이터 소스 객체 생성.
		ds = new HikariDataSource(config); //config를 사용한 데이터소스객체 생성
	}
	
	public HikariDataSource getDataSource() {
		return ds;
	}
	
	// 편의(utility;유틸리티) 메서드.
	public static void close(Connection conn, Statement stmt, ResultSet rs) {
		try {
			if(rs != null) rs.close();
			if(stmt != null) stmt.close();
			if(conn != null) conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(Connection conn, Statement stmt) {
		close(conn, stmt, null);
	}
}
