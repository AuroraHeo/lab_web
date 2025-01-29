package com.itwill.spring2.repository;

import com.itwill.spring2.domain.Member;

public interface MemberDao {
	Member selectByUsername(String username);
	Member selectByEmail(String email);
	int insertMember(Member member);
	Member selectByUsernameAndPassword(Member member);
}
