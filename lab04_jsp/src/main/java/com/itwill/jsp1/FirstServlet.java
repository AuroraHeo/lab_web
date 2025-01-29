package com.itwill.jsp1;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

// WAS(Web Application Server): 
// - 웹 요청(request)과 응답(response)을 처리하는 프로그램. ex) Tomcat
// - WAS는 시작될 때, web.xml파일을 읽어서 웹 서비스 준비(초기화)를 진행함.
// web.xml 파일: 배포 설명자(deployment descriptor).
// 클라이언트에서 요청이 왔을 때 WAS는 web.xml에 작성된 서블릿 설정을 보고,
// 요청 주소에 매핑된 서블릿 클래스의 doGet() 또는 doPost() 메서드를 호출함.
// Servlet: Server + Applet. 서버에서 실행되는 작은 자바 프로그램.
// 서블릿 컨테이너(servlet container)
// - 서블릿 객체를 생성/관리, 필요할 때 서블릿 객체의 메서드를 호출하는 프로그램.
// 서블릿 설정: 서블릿 클래스와 요청 주소를 매핑 설정.
// (i) web.xml 파일에서 <servlet>, <servlet-mapping>으로 설정.
// (ii) 각각의 서블릿 클래스에서 @WebServlet 애너테이션으로 설정.
// (주의)하나의 서블릿 클래스는 web.xml 또는 애너테이션 중 한가지 방법으로만 설정해야 됨.

//서블릿 동작 원리:
// (1) 요청 --> 서블릿 객체 생성 --> doGet()/doPost() 호출
// (2) 요청 --> doGet()/doPost() 호출
/**
 * Servlet implementation class FirstServlet
 */

public class FirstServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FirstServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    //GET 방식의 요청일 때 WAS가 호출하는 메서드
    //파라미터 request: 클라이언트에게 서버로 보낸 요청의 정보 등을 저장하고 있는 객체
    //파라미터 response: 서버가 클라이언트로 보낼 응답의 데이터, 기능 등을 갖는 객체
    @Override //
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("FirstServlet::doGet() 호출");//
		
		response.setContentType("text/html; charset=UTF-8");//
		
		//'응답으로 보낼' HTML을 '문자열'로 작성. servlet 동작 원리를 공부하기 위해 해본...
		PrintWriter writer = response.getWriter();
		writer.append("<!doctype html>")
			.append("<html>")
			.append("	<head>")
			.append("		<meta charset='UTF-8' />")
			.append("		<title>Servlet</title>")
			.append("	</head>")
			.append("	<body>")
			.append("		<h1>첫번째 서블릿</h1>")
			.append("		<a href='/jsp1/'>목차</a>")
			.append("	</body>")
			.append("</html>");
	}
    
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    //POST 방식의 요청일 때 WAS가 호출하는 메서드.
    @Override //
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
