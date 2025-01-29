package com.itwill.jsp2.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itwill.jsp2.domain.Member;

public class MemberDaoTest {
	private static final Logger log = LoggerFactory.getLogger(MemberDaoTest.class);
	private final MemberDao memberDao = MemberDao.INSTANCE;
	
	// MemberDao.insert() 메서드를 단위 테스트. 단위 테스트에서 사용하는 메서드는 리턴 타입을 반드시 void로 !!!! 아규먼트는 전달하지 않으니 넣으면 안됨!!!!
	//@Test
	public void testInsert() {
		Member member = Member.builder().username("admin").password("admin1234").email("admin@itwill.com").build();
		
		int result = memberDao.insert(member);
		
		Assertions.assertEquals(1, result); //(기대값, 실제값) : result가 1이라고 주장
	}// 유닛테스트에서 첫번째는 성공. 두번째 테스트하면 실패(회원가입 한번 해놓으면 같은 username, password, email의 제약조건이 unique이기 때문에)
	
	@Test
	public void testSelect() {
		Member m1 = memberDao.select("admin","admin1234");
		Assertions.assertNotNull(m1);
		
		Member m2 = memberDao.select("admin","1234");
		Assertions.assertNull(m2);
	}
}
