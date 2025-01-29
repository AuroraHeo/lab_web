package com.itwill.jsp1;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class ForwardServlet
 */
@WebServlet(name = "forwardServlet", urlPatterns = { "/ex3" }) //애너테이션
public class ForwardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ForwardServlet() {
        System.out.println("ForwardServlet() 생성자 호출");//
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("ForwardServlet::doGet() 호출");//
		
		// 요청이 오면 WAS는 web.xml 또는 @WebServlet 애너테이션에서 설정된
		// URL 매핑에 따라서 요청을 처리할 수 있는 서블릿 객체의 메서드(doGet, doPost)를 호출.
		// 서블릿은 응답으로 보낼 HTML을 작성
		// but 서블릿에서 HTML을 작성하는 것이 너무 번거롭기 때문에
		// 서블릿은 JSP로 요청을 전달(forward)하고, JSP가 HTML을 작성하게 하는 것이 더 쉬움.
		// WAS는 JSP가 작성한 HTML을 클라이언트에게 응답보냄.
		
		// "forward"방식의 웹 페이지 이동:
		// - '같은 WAS'의 '같은 웹 애플리케이션 안'에서만 페이지를 이동하는 방식.
		// - 최초 요청 주소가 바뀌지 않음. (examle.jsp로 바뀌었지만 ex3으로 유지됨)
		// - request, response 객체가 유지됨.(WAS가 준거를 그담의 두번째 페이지에도 그대로 줌)
		// - 다른 WAS 또는 다른 웹 애플리케이션의 페이지로는 포워드할 수 없음.
		request.getRequestDispatcher("example.jsp")//응답을 보내줄 jsp 찾고, jsp에게 request와 response를 전달함.
			.forward(request, response);
	}

}
