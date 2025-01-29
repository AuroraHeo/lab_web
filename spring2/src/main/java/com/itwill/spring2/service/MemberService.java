package com.itwill.spring2.service;

import org.springframework.stereotype.Service;

import com.itwill.spring2.domain.Member;
import com.itwill.spring2.dto.MemberSignInDto;
import com.itwill.spring2.dto.MemberSignUpDto;
import com.itwill.spring2.repository.MemberDao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {

	//final 필드와 (필수 아규먼트를 갖는) 생성자를 사용한 의존성 주입
	private final MemberDao memberDao;
	
	//boolean checkUsername(String)
	//사용가능한(테이블에 없는) username이면 true, 그렇지 않으면 false를 리턴
	public boolean checkUsername(String username) {
		log.debug("checkUsername(username={})", username);
	
		Member member = memberDao.selectByUsername(username);
		if(member == null) { // username이 테이블에 없는 경우 -> 회원가입에서 사용할 수 있는 username
			return true;
		} else { //username이 테이블에 있는 경우 -> 회원가입에서 사용할 수 없는 username
			return false;
		}
	}
	
	//boolean checkemail(String)
	//사용가능한(테이블에 없는) email이면 true, 그렇지 않으면 false를 리턴
	public boolean checkEmail(String email) {
		log.debug("checkEmail(email= {})", email);
		Member member = memberDao.selectByEmail(email);
		
		return (member == null) ? true : false;
		//return (member == null);
	}
	
	// 회원 가입 메서드 추가 
	public int create(MemberSignUpDto dto) {
		log.debug("create(dto={})", dto);
		
		int result = memberDao.insertMember(dto.toEntity());
		
		return result;
	}
	
	public Member read(MemberSignInDto dto) {
		log.debug("read(dto={})", dto);
		
		Member member = memberDao.selectByUsernameAndPassword(dto.toEntity());
		
		return member;
	}

}
