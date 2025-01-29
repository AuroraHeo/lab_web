package com.itwill.jsp1.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import java.io.IOException;

/**
 * Servlet Filter implementation class FirstFilter
 */
//@WebFilter("/FirstFilter")을 지움((ii)애너테이션 설정하려고)

//필터 요청 주소 매핑 설정:
//(i) web.xml(deployment descriptor)파일에서 <filter>, <filter-mapping>태그로 설정.
//(ii)@WebFilter 애너테이션으로 설정.
//(주의) 한 개의 필터 클래스는 web.xml과 애너테이션을 중복으로 설정하면 안됨.
public class FirstFilter extends HttpFilter {
       
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L; //

	/**
     * @see HttpFilter#HttpFilter()
     */
    public FirstFilter() {
    	System.out.println("===== FirstFilter() 생성자 호출"); //
    }

	/**
	 * @see Filter#destroy()
	 */
    @Override //
	public void destroy() {
		// 'WAS가 종료될 때' 생성된 필터 객체를 소멸시키기 위해서 '호출'하는 메서드.
    	// 필터가 사용하고 있었던 리소스들을 해제.
    	System.out.println("===== FirstFilter::destroy() 호출");
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
    @Override 
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		System.out.println("====> FirstFilter chain.doFilter() 호출 전...");

		// pass the request along the filter chain(요청을 필터 체인으로 전달): 요청 주소에 매핑된 다른 필터 또는 서블릿 메서드 호출.
		chain.doFilter(request, response);
		
		System.out.println("<==== FirstFilter chain.doFilter() 호출 후...");
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
    @Override //
	public void init(FilterConfig fConfig) throws ServletException {
		//필터가 생성된 후 필터의 초기화 작업을 수행하기 위해서 호출되는 메서드.
    	System.out.println("===== FirstFilter::init() 호출");
	}

}

//[Filter] <--implement-- [HttpFilter] <--implement-- [MyFilter(우리가 구현해야될거..)]
//요청하면 필터 사슬을 거친 후 서블릿으로......서블릿이 응답을 만들면 응답도 필터 사슬을 거쳐서 클라이언트로 감