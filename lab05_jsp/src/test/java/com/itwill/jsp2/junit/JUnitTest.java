package com.itwill.jsp2.junit;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JUnitTest {
	private static final Logger log = LoggerFactory.getLogger(JUnitTest.class);
	
	@Test //Test 애너테이션 : 단위 테스트하기 위해서 ...
	public void test1() { //public void 이고, 아규먼트를 갖지 않게 메서드를 선언
		log.debug("JUnit 테스트입니다...");
	}
	
	@Test //Test 애너테이션
	public void test2() { //public void 이고, 아규먼트를 갖지 않게 메서드를 선언
		log.debug("JUnit test...");
	}
}
