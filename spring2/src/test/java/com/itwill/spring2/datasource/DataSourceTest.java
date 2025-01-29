package com.itwill.spring2.datasource;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;

@Slf4j //로그 출력하기 위해서
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/application-context.xml"}) // locations :  String []임.....파일 위치를 문자열로!
public class DataSourceTest {
	
	/*
	 * 의존성 주입(DI: Dependency Injection) = 제어의 역전(IoC: Inversion of Control) : 객체 제어권(생성&관리권리) 주체 == 객체 사용하는 곳이였으나, 제어권을 spring container가 가져가서 객체의 생성과 관리를 담당하고 개발자는 객체를 직접 생성할 필요 없이 주입받아 사용.
	 * 전통적인 자바 개발 방법에서는 객체를 사용하는 곳(ex.PostDao)에서 객체를 생성(DataSource를 생성)하고, 그 (DataSource의) 기능을 이용함.
	 * 스프링 프레임워크에서는 스프링 컨테이너가 필요한 객체들을 미리 생성하고 관리하고 있다가
	 * 객체를 필요로 하는 곳에서는 변수 선언과 애너테이션으로 스프링 컨테이너가 관리하는 bean(자바객체)을 주입받아서 사용.
	 */

	@Autowired //스프링프레임워크가 생성한 객체(bean)를 여기에 자동으로 주입됨(의존성 주입) // 필드만 선언하고 @Autowired를 붙이면 됨(객체를 직접 생성할 필요 없음) ->DataSourceUtil.java를 만들 필요 없음..? //스프링 컨테이너에서 생성/관리하는 빈(객체)을 주입받음.
	private HikariDataSource ds;
	
	@Autowired
	private SqlSessionFactoryBean sqlSession;
	
	@Test
	public void testDataSource() throws SQLException {
		Assertions.assertNotNull(ds);
		log.debug("ds={}", ds);
		
		Connection conn = ds.getConnection();
		Assertions.assertNotNull(conn);
		log.debug("conn={}", conn);
		
		conn.close();
		log.debug("커넥션 객체를 풀에 반환");
	}
	
	@Test
	public void testSqlSession() {
		Assertions.assertNotNull(sqlSession);
		log.debug("sqlSession={}", sqlSession);
	}
	
}

//스프링 프레임 워크 : 웹 mvc를 제공하고 의존성 주입(DI: Dependency Injection) = 제어의 역전(IoC: Inversion of Control)을 사용하는 프레임워크
// spring container가 제어권을 가져서 객체의 생성과 관리를 담당하고 개발자는 객체를 직접 생성할 필요 없이 주입받아 사용.
// 의존성 주입을 사용하는 이유 : 프로그램의 유지보수가 쉬움(나중에 변경할 코드가 적어짐)