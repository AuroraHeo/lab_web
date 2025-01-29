package com.itwill.jsp2.web.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itwill.jsp2.domain.Member;
import com.itwill.jsp2.service.MemberService;

/**
 * Servlet implementation class UserSignInController
 */
@WebServlet(name = "userSignInController", urlPatterns = { "/user/signin" })
public class UserSignInController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final Logger log = LoggerFactory.getLogger(UserSignInController.class);
	private final MemberService memberService = MemberService.INSTANCE;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserSignInController() {}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("doGet()");
    	
    	request.getRequestDispatcher("/WEB-INF/views/user/signin.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("doPost()");
		
		// 양식 데이터(로그인할 때 username, password)를 읽음.
		String username = request.getParameter("username"); //대소문자 구분하기 때문에 signin.jsp의 name속성의 이름과 같아야함
		String password = request.getParameter("password");
		String target = request.getParameter("target");
		log.debug("doPost(username={}, password={}, target = {})", username, password, target);
		
		// 서비스 계층의 메서드를 호출해서 로그인 성공/실패 여부를 판단.
		Member member = memberService.signIn(username, password);
		if(member != null) { // username과 password가 일치하는 사용자 -> 로그인 성공
			// 세션에 로그인 정보(비번말고 username만)를 저장 -> target페이지로 이동.
			
			HttpSession session = request.getSession(); //request에서 session을 찾아서 선언;
			session.setAttribute("signedInUser", member.getUsername()); //비번말고 username만 저장
			
			//target페이지로 이동(redirect)
			if(target != null && !target.equals("")) {
				response.sendRedirect(target); // target페이지로 이동
			} else { //home페이지에서 로그인하는 경우
				String url = request.getContextPath() + "/"; //홈페이지
				response.sendRedirect(url); //홈페이지로 이동
			}
			
		} else { // username과 password가 일치하는 사용자가 없든 경우 -> 로그인 실패
			// 다시 로그인 페이지로 이동(redirect).
			String url = request.getContextPath() + "/user/signin?result=f&target="
					+ URLEncoder.encode(target, "UTF-8");
			log.debug("로그인 실패: redirect to {}", url);
			response.sendRedirect(url);
		}
	}
}
//jsp에서는 내장객체로 session이 이미 있기 때문에 바로 사용 가능.
// request가 session을 가지고 있음