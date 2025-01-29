package com.itwill.spring2.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.itwill.spring2.domain.Member;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/application-context.xml"})
public class MemberDaoTest {
	// 애너테이션을 사용한 의존성 주입
	@Autowired private MemberDao memberDao;
	
//	@Test
	public void testSelectByUsername() {
		//username이 테이블에 있는 경우:
		Member admin = memberDao.selectByUsername("admin");
		Assertions.assertNotNull(admin);
		log.debug("admin={}", admin);
		
		//username이 테이블에 없는 경우:
		Member qwer = memberDao.selectByUsername("qwer");
		Assertions.assertNull(qwer); // 테스트 결과, qwer=null // 중복되지 않아서 회원가입 가능한 username라는 의미.
		log.debug("qwer={}", qwer);
	}
	
	@Test
	public void testSelectByEmail() {
		//email이 테이블에 있는 경우:
		Member guest = memberDao.selectByEmail("guest@naver.com");
		Assertions.assertNotNull(guest);
		log.debug("guest={}", guest);
	
		
		//email이 테이블에 없는 경우:
		Member qwer = memberDao.selectByEmail("qwer@itwill.com");
		Assertions.assertNull(qwer);
		log.debug("qwer={}", qwer);
	}
}
