package com.itwill.jsp1;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class RedirectServlet
 */
@WebServlet(name = "redirectServlet", urlPatterns = { "/ex4" }) //애너테이션
public class RedirectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RedirectServlet() {
        System.out.println("RedirectServlet() 생성자 호출");//
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override//
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("RedirectServlet::doGet() 호출");//
		
		//"redirect"  방식의 페이지 이동:
		//클라이언트가 최초 요청(request) 
		//--> redirect(요청을 다시 보내세요) 응답(response)
		//--> 클라이언트가 재요청
		//--> 재요청을 받은 WAS가 응답.
		//브라우저 주소창의 URL이 최초 요청 주소가 아니라 최종 요청 주소로 '바뀜'.
		//최초 요청의 request, response 객체가 이동하는 페이지로 전달되지 않음.
		//같은 WAS뿐만 아니라 다른 웹 서버 혹은 다른 웹 애플리케이션으로 redirect 가능.
		////response.sendRedirect("https://www.naver.com"); 가능
		
		
		response.sendRedirect("ex3"); //개발자도구에서 네트워크를 보면 302Found : 너(브라우저) 주소바꿔서(ex4에서 ex3으로 바꿔서) 다시 요청 보내
		//response.sendRedirect("https://www.naver.com"); //외부 서버 주소도 넣을 수 있음
    }

}

//<redirect(리다이렉트)>
//client ---------1. request /ex4----------> server
//		<---2. response(redirect(ex3))------
// 		----------3. request /ex3---------->
//		<----------4. response--------------


//redirect하면 최종적으로 주소도 ex4에서 ex3으로 바뀜(ex4->ex3:같은 웹 애플리케이션에서 이동). 다른 웹 애플리케이션으로도 이동 가능. 다른 외부 웹 서버로도 이동 가능(response.sendRedirect("https://www.naver.com");도 가능)
//포워드는 주소가 안바뀜. '같은' 웹 애플리케이션에서만 이동 가능.