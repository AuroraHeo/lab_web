package com.itwill.jsp2.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itwill.jsp2.domain.Member;
import com.itwill.jsp2.repository.MemberDao;

public enum MemberService {
	INSTANCE;
	
	private static final Logger log = LoggerFactory.getLogger(MemberService.class);
	private final MemberDao memberDao = MemberDao.INSTANCE;
	
	// 회원 가입 서비스
	public int signUp(Member member) {
		log.debug("signUp(member={})", member);

		int result = memberDao.insert(member);
		log.debug("insert result = {}", result);
		
		return result;
	}
	
	// 로그인 서비스
	public Member signIn(String username, String password) {
		log.debug("signIn(username={}, password={})", username, password);
		
		Member member = memberDao.select(username, password);
		log.debug("sign-In result = {}", member);
		
		return member;
	}
}
