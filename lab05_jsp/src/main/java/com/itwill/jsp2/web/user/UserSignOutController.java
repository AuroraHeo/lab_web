package com.itwill.jsp2.web.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet implementation class UserSignOutController
 */
@WebServlet(name = "userSignOutController", urlPatterns = { "/user/signout" })
public class UserSignOutController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(UserSignOutController.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserSignOutController() {}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("doGet()");
		
		// 로그아웃:
		// (i) 세션에 저장된 로그인 관련 정보들(signedInUser)을 삭제.
		// (ii) 세션 객체를 무효화(invalidate)시킴 - 기존의 생성된 세션 객체를 heap에서 지움.
		// 의도에 따라 선택하면 되는데, 사실 (ii)만 하면 (i)은 자동으로 실행됨.
		
		HttpSession session = request.getSession(); //쿠키(JSESSIONID)의 값으로 session을 찾음.
		session.removeAttribute("signedInUser"); // (i) setAttribute로 설정했던 아규먼트 이름을 넣으면 됨
		session.invalidate(); // (ii)쿠키(JSESSIONID)의 값 지워버림?
		
		// 로그아웃 이후에 홈페이지로 이동(redirect)
		String url = request.getContextPath() + "/";
		log.debug("로그아웃: redirect to {}", url);
		response.sendRedirect(url);
		
		
	}

}
