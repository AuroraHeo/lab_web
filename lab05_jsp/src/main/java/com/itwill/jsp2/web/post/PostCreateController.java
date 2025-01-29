package com.itwill.jsp2.web.post;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itwill.jsp2.domain.Post;
import com.itwill.jsp2.service.PostService;

/**
 * Servlet implementation class PostCreateController
 */
@WebServlet(name = "/postCreateController", urlPatterns = { "/post/create" })
public class PostCreateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(PostCreateController.class);
	private final PostService postService = PostService.INSTANCE;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PostCreateController() {}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
    		throws ServletException, IOException {
		log.debug("doGet()");
		
		request.getRequestDispatcher("/WEB-INF/views/post/create.jsp")
				.forward(request, response);
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
    		throws ServletException, IOException {
		log.debug("doPost()");		
		
		// 클라이언트가 보내준 양식 데이터에 포함된 요청 파라미터 값들(title, content, author)을 읽음.
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String author = request.getParameter("author");
		Post post = Post.builder()
				.title(title).content(content).author(author).build();
		
		// 서비스 계층의 메서드를 호출 -> DB에 저장.
		postService.create(post);
		
		// 포스트 목록 페이지로 이동(redirect)
		String url = request.getContextPath() + "/post/list"; //-> /jsp2(contextroot)/post/list
		//request.getContextPath()를 넣은 이유는 contextPath가 나중에 수정할 때 바뀔 수 있기때문에 
		log.debug("redirect to {}", url);
		response.sendRedirect(url);
		// PRG(Post-Redirect-Get) 패턴.
	}

}