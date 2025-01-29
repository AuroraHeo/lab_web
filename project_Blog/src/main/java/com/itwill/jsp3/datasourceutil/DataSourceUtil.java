package com.itwill.jsp3.datasourceutil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public enum DataSourceUtil {
	INSTANCE; //singleton 객체
	
	//필드
	private HikariConfig config; //HikariConfig클래스 : JDBC connection pool 라이브러리에서 사용하는 설정 클래스. 데이터베이스 연결 풀을 설정할 때 주로 사용
	private HikariDataSource ds;
	
	//생성자
	DataSourceUtil(){
		// HikariCP(라이브러리?)의 환경 설정(configuration)하기: JDBC URL, 사용자 이름, 비밀번호, 최소 유휴 커넥션 수, 최대 커넥션 풀 크기, 유휴 타임아웃)을 설정하는 HikariConfig 객체 생성.
		config = new HikariConfig();
		
		// // 커넥션 풀(데이터 소스)의 기본적인 환경 설정.
		config.setDriverClassName("oracle.jdbc.OracleDriver");
		config.setJdbcUrl("jdbc:oracle:thin:@localhost:1521:xe");
		config.setUsername("blogproject");
		config.setPassword("blogproject");
		
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

//HikariConfig: 데이터베이스 연결 풀을 설정하는 데 사용되는 클래스
//HikariDataSource: HikariConfig를 바탕으로 실제 데이터베이스 연결 풀을 생성하고 관리하는 클래스
//DataSource는 데이터베이스와 같은 데이터 소스에 연결을 관리하는 표준 인터페이스입니다. 쉽게 말해서, DataSource는 데이터베이스 연결을 생성하고 관리
//config.setDriverClassName: HikariConfig 객체에 JDBC 드라이버의 클래스 이름을 설정하는 코드입니다. 이 설정을 통해 HikariCP가 어떤 JDBC 드라이버를 사용할지를 지정
//getDataSource() 메서드를 통해 데이터 소스 객체를 반환

//public static void close(Connection conn, Statement stmt, ResultSet rs): static으로 선언한 이유는 객체 생성 없이도 사용할 수 있게 하기 위함