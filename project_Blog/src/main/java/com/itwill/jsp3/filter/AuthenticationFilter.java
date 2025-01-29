package com.itwill.jsp3.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet Filter implementation class AuthenticationFilter
 */
//@Webservlet 지우고 web.xml로..
public class AuthenticationFilter extends HttpFilter {
   
	private static final long serialVersionUID = 1L; //
	private final Logger log = LoggerFactory.getLogger(AuthenticationFilter.class);

	/**
     * @see HttpFilter#HttpFilter()
     */
    public AuthenticationFilter() {}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// 필터 객체를 소멸시킬 때(heap에서 삭제하기 전에) WAS가 호출하는 메서드.
		// 필터가 사용하던 리소스들을 해제.
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		log.debug("doFilter()");
		// 인증이 필요한 요청 주소들(새글, 상세보기, ...)에 대해서 
		// 로그인 여부(세션에 signedInUser 속성이 있는지)를 확인하고
		// (i) 로그인되어있으면, 컨트롤러(서블릿)로 요청을 전달(chain.doFilter()를 호출).
		// (ii) 로그인되어있지 않으면, 컨트롤러로 요청을 전달하지 않고(chain.doFilter()를 호출 X), 로그인 페이지로 이동.
		// -> 로그인을 담당하는 컨트롤러에서 로그인 성공 후에 최초 요청 페이지로 이동할 수 있도록 설정 필요.
		HttpServletRequest req = (HttpServletRequest)request; //ServletRequest타입 -> HttpServletRequest타입으로 변환 (Controller에서 request가 HttpServletRequest타입이라서)
		HttpServletResponse resp = (HttpServletResponse)response;
		
		// URL(Uniform Resource Location); 리소스가 있는 위치
		String reqUrl = req.getRequestURL().toString();
		log.debug("request URL = {}", reqUrl);
		
		// URI(Uniform Resource Identifier); 리소스가 있는 아이디
		String reqUri = req.getRequestURI();
		log.debug("request URI = {}", reqUri);
		
		String contextPath = req.getContextPath();
		log.debug("context path = {}", contextPath);
		
		String qs = req.getQueryString();
		log.debug("query string = {}", qs);
		
		//로그인 이후에 이동할 페이지(ex.페이지를 target이라는 이름으로 만들려고..)
		String target = null;
		if(qs != null) {//질의 문자열이 있는 경우
			target = URLEncoder.encode(reqUrl + "?" + qs, "UTF-8");
		} else {//질의 문자열이 없는 경우
			target = URLEncoder.encode(reqUrl, "UTF-8");
		}
		log.debug("target = {}", target);
		
		//세션에 signedInUser 속성이 있는지 확인
		HttpSession session = req.getSession(); 
		Object signedInUser = session.getAttribute("signedInUser");
		if (signedInUser != null) { //로그인 상태
			log.debug("로그인 사용자: {}", signedInUser);
			
			// pass the request along the filter chain(필터 체인을 통해서 그다음 필터 또는 서블릿으로 요청을 전달.)
			chain.doFilter(request, response);
		} else { //로그아웃 상태
			log.debug("로그아웃 상태 --> 로그인 페이지로 이동 --> 로그인 성공 후 target으로 이동");
			
			String url = req.getContextPath() + "/user/signin?target=" + target;
			resp.sendRedirect(url);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// WAS가 (생성자를 호출하여) 필터 객체를 생성한 직후에 호출하는 메서드.
		// 필터에 필요한 초기화 작업들.
	}

}

//ServletRequest(부모) , HttpServletRequest(자식)
//redirect도 응답이라서 redirect후에 doFilter 불가.
//로그인 여부에 따라 doFilter() 호출할지, redirect할지 정하기.
//질의문자열을 구분하기 위한 ?는 그냥 물음표. 그 뒤의 request parameter의 값에서의 물음표는 UTF-8로 인코딩되어야함.
//url 넣을 때, 브라우저(ex)js)는 자기 현재 위치를 알고 있으니까 그에 맞게 설정하고, 서버는 req.getContextPath()를 사용하여 설정해줘야함.