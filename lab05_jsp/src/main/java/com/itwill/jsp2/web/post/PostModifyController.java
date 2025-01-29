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

//수정할 게시글 내용을 보여주는 역할
/**
 * Servlet implementation class PostModifyController
 */
@WebServlet(name = "postModifyController", urlPatterns = { "/post/modify" })
public class PostModifyController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(PostModifyController.class);
	private final PostService postService = PostService.INSTANCE;
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PostModifyController() {}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	//질의 문자열의 요청 파라미터 id의 값을 읽음.
    	int id = Integer.parseInt(request.getParameter("id")); // details.jsp에서  <c:param name="id"...
    	log.debug("doGet(id={})", id);
    	
    	//서비스 계층의 메서드를 호출해서 수정하기 전의 포스트 상세보기 내용을 읽어옴.
    	Post post = postService.read(id);
    	
    	//포스트 객체를 요청의 attribute에 추가.
    	request.setAttribute("post", post);
    	
    	//뷰로 이동.
    	request.getRequestDispatcher("/WEB-INF/views/post/modify.jsp").forward(request, response);
	}

}
