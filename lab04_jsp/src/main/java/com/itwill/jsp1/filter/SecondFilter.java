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
 * Servlet Filter implementation class SecondFilter
 */
//@WebFilter("/SecondFilter")을 지움.
public class SecondFilter extends HttpFilter {
       
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L; //

	/**
     * @see HttpFilter#HttpFilter()
     */
    public SecondFilter() {
    	//
    	System.out.println("========== SecondFilter() 생성자 호출");
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		//
		System.out.println("========== SecondFilter()::destroy() 호출");
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		//
		System.out.println("==========> SecondFilter chain.doFilter() 호출 전.");

		// pass the request along the filter chain(필터 체인을 따라서 요청을 전달.)
		chain.doFilter(request, response); //doFilter을 호출하지 않으면, 첫번째 필터를 통과하지 못하고 바로 response됨
		
		//
		System.out.println("<========== SecondFilter chain.doFilter() 호출 후.");
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		//
		System.out.println("========== SecondFilter::init() 호출");
	}

}

//[client] ---request---> [first filter] ---request---> [second filter] ---request---> [servlet]
// 		   <--response--				<---response---					<---response---