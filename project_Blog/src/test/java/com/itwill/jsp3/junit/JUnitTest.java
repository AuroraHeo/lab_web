package com.itwill.jsp3.junit;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JUnitTest {
	private static final Logger log = LoggerFactory.getLogger(JUnitTest.class);
	
	@Test
	public void test1() {
		log.debug("JUnit 테스트1");
	}
	
	@Test
	public void test2() {
		log.debug("Junit 테스트2");
	}
}
