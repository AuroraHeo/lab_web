package com.itwill.spring2.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URLEncoder;

/**
 * Servlet Filter implementation class AuthenticationFilter
 */
@Slf4j
public class AuthenticationFilter extends HttpFilter {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpFilter#HttpFilter()
     */
    public AuthenticationFilter() {}

	/**
	 * @see Filter#destroy()
	 */
    @Override
	public void destroy() {
		// 필터 객체가 소멸될때(WAS가 종료될때 사용했던 bean을 해제) 해야할 일(예:리소스 해제)들을 작성.
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
    @Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// 로그인한 사용자(권한이 있는) 사용자(세션에 로그인정보가 있는 사람)는 필터 체인을 진행.
		// 로그인하지 않은 사용자(권한이 없는 사용자, 세션에 로그인정보가 없는 사람)는 로그인 페이지로 이동(redirect)
    	
    	HttpServletRequest httpReq = (HttpServletRequest) request; //필요한 메서드를 사용하기 위해 슈퍼타입을 하위타입으로 casting
    	HttpServletResponse httpResp = (HttpServletResponse) response; //위와 동일
    	
    	// 세션을 가져온다.
    	HttpSession session = httpReq.getSession();
    	// 세션에서 로그인 사용자 속성(signedInUser attribute)값을 읽음.
    	Object signedInUser = session.getAttribute("signedInUser");
    	
    	if(signedInUser != null && !signedInUser.equals("")) { //세션에 로그인정보가 있는 경우
    		log.debug("로그인 상태: username={}", signedInUser);
    		
    		// 필터 체인의 다음 단계(다음 필터 또는 서블릿)으로 request를 전달.
    		chain.doFilter(request, response);
    		return;
    	}
    	
    	log.debug("로그아웃 상태 ---> 로그인 페이지로 이동");
    	//로그인 이후에 최초 요청 주소로 이동(redirect)하기 위해서 
    	String url = httpReq.getRequestURL().toString();
    	log.debug("[request url] = {}", url);
    	
    	String qs = httpReq.getQueryString();
    	log.debug("[query string] = {}", qs);
    	
    	String target = null; //로그인 성공 후 이동할 주소
    	if(qs == null) {
    		target = URLEncoder.encode(url, "UTF-8"); //url, 인코딩타입
    	} else {
    		target = URLEncoder.encode(url + "?" + qs , "UTF-8");
    	}
    	log.debug("[target] = {}", target);
    	
    	String signInPage = httpReq.getContextPath() + "/user/signin?target=" + target;
    	
    	httpResp.sendRedirect(signInPage);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
    @Override
	public void init(FilterConfig fConfig) throws ServletException {
		// WAS가 필터를 생성한 이후에 필터에서 초기화 작업이 필요한 코드들을 작성.
	}

}
// 들어온 요청을 계속 진행할거면(두번째 필터로 or dispatcher servlet으로) dofilter() 호출, 진행시키지 않고 리다이렉트로 할거면 dofilter() 호출 x
// client ===req==> filter =====[dofilter()]=====> dispatcher servlet ====> controller