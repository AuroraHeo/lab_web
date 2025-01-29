package com.itwill.jsp1.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class CookieController
 */
@WebServlet(name = "cookieController", urlPatterns = { "/cookie" })
public class CookieController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CookieController() {}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//
    	System.out.println("CookieController::doGet() 호출");
    	
    	// 1.쿠키 객체 생성.
    	Cookie cookie = new Cookie("hello", "안녕하세요");
    	
    	// 2.쿠키 설정 - 쿠키 만료 시간, 도메인, 경로 설정.
    	
    	// 3.쿠키 객체를 응답(response)에 포함시킴(클라이언트의 요청에 대해서 응답을 보낼때 쿠키를 add해서 보내줌)
    	response.addCookie(cookie);
    	
    	int count = 1; //클라이언트가 서버를 방문한 횟수.
		
    	// 클라이언트가 보낸 쿠키들을 확인하는 방법
    	Cookie[] cookies = request.getCookies();
    	for(Cookie c : cookies) {
    		System.out.println(c.getName() + "=" + c.getValue());
    		
    		if(c.getName().equals("cnt")) { //쿠키의 이름이 cnt이면
    			count = Integer.parseInt(c.getValue()); //쿠키의 값을 count에 저장.
    		}
    	}
    	
    	//count 변수(방문 횟수)를 뷰에게 전달하기 위해서, 요청 객체에 속성(attribute)을 추가.
    	request.setAttribute("visitCount", count); 
    	
    	count++; // 방문 횟수를 1 증가.
    	// 변경된 방문횟수를 저장하는 쿠키를 생성.
    	Cookie visitCookie = new Cookie("cnt", count + ""); //new Cookie("cnt", String.valueOf(count));
    	// 쿠키를 응답에 포함.
    	response.addCookie(visitCookie);
    	
		// 4.뷰로 요청을 전달(forward).
    	request.getRequestDispatcher("/WEB-INF/views/cookie.jsp").forward(request, response);
    	
	}

}