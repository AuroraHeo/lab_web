package com.itwill.spring2.web;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itwill.spring2.domain.Member;
import com.itwill.spring2.dto.MemberSignInDto;
import com.itwill.spring2.dto.MemberSignUpDto;
import com.itwill.spring2.service.MemberService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//회원가입, 로그인, 로그아웃 컨트롤러
@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class MemberController {

	//final 필드와 생성자를 사용한 의존성 주입.
	private final MemberService memberService;
	
	@GetMapping("/signup")
	public void signUp() {
		log.debug("GET signUp()");
	}
	
	@PostMapping("/signup") 
	public String signUp(MemberSignUpDto dto) {
		log.debug("[POST] signUp(dto={})", dto);
		
		memberService.create(dto);
		
		//회원가입 성공 이후에는 로그인 페이지로 리다이렉트(이동).
		return "redirect:/user/signin";
	}
	
	@GetMapping("/signin")
	public void signIn() {
		log.debug("GET signIn()");
	}
	
	@PostMapping("/signin")
	public String signIn(MemberSignInDto dto, 
			@RequestParam(name = "target", defaultValue = "") String target, 
			HttpSession session) throws UnsupportedEncodingException {
	    log.debug("[POST] signIn(dto={}, target={})", dto, target);

	    Member member = memberService.read(dto);
	    String targetPage = null;
	    if (member != null) {
	        // username과 password가 일치하는 사용자가 DB에 있는 경우 - 로그인 성공.
	        session.setAttribute("signedInUser", member.getUsername()); // 세션에 로그인 정보 저장
	        
	        //로그인 성공인 경우 이동할 페이지 설정.
	        //질의 문자열에 target이 없으면 홈페이지, target이 있으면 target 페이지로 이동.
	        targetPage = target.equals("") ? "/" : target;
	    } else {
	        // username과 password가 일치하는 사용자가 DB에 없는 경우 - 로그인 실패.
	    	// 로그인 실패인 경우 다시 로그인 페이지로 이동.
	        targetPage = "/user/signin?result=f&target=" + URLEncoder.encode(target, "UTF-8");
	    }
	    return "redirect:" + targetPage;
	}
	
	@GetMapping("/signout")
	public String signOut(HttpSession session) {
		log.debug("signOut()");
		
		//로그아웃 - 세션에 저장된 로그인 정보를 지움. 세션을 무효화(invalidate)
		session.removeAttribute("singedInUser");
		session.invalidate();
		
		//로그아웃 이후에 로그인 페이지로 이동(redirect)
		return "redirect:/user/signin";
	}
	
	//username 중복체크
	//중복되지 않은 username이면 "Y", 중복된 username이면 "N"을 전송(응답).
	@GetMapping("/checkusername")
	@ResponseBody
	public ResponseEntity<String> checkUsername(@RequestParam String username){
		log.debug("checkUsername(username={})", username);
		
		boolean result = memberService.checkUsername(username);
		
		if(result) {
			return ResponseEntity.ok("Y");
		} else {
			return ResponseEntity.ok("N");
		}
		
	}
	
	//email 중복체크
	//중복되지 않은 email이면 "Y", 중복된 email이면 "N"을 전송(응답).
	@GetMapping("/checkemail")
	@ResponseBody
	public ResponseEntity<String> checkEmail(@RequestParam String email){
	    log.debug("checkEmail(email={})", email);
	    
	    boolean result = memberService.checkEmail(email);
	    
	    if(result) {
	        return ResponseEntity.ok("Y");
	    } else {
	        return ResponseEntity.ok("N");
	    }
	    //return result ? ResponseEntity.ok("Y") : return ResponseEntity.ok("N");
	}

}
