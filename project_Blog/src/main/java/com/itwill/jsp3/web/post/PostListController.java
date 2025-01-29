package com.itwill.jsp3.web.post;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itwill.jsp3.domain.Post;
import com.itwill.jsp3.service.PostService;

/**
 * Servlet implementation class PostListController
 */
@WebServlet(name = "postListController", urlPatterns = { "/post/list" })
public class PostListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(PostListController.class);
	private final PostService postService = PostService.INSTANCE;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PostListController() {}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	log.debug("doGet()");
    	
    	int offset = 0;
    	int limit = 5;
    	
    	String offsetParam = request.getParameter("offset");
    	if(offsetParam != null) {
    		offset = Integer.parseInt(offsetParam);
    	}
    	
    	List<Post> list = postService.read(offset, limit);
		log.debug("# of list = {}", list.size());
		
		//서비스 계층의 메서드를 호출해서 현재 페이지 및 총 페이지 수 계산
		int totalPages = postService.getTotalPages(limit);
		int currentPage = postService.getCurrentPage(offset, limit);
		
		log.debug("currentPage={}, totalPages={}, offset={}", currentPage, totalPages, offset);
		
		//서비스 계층의 메서드를 호출해서 뷰(테이블)를 그릴 수 있는 데이터를 읽어옴.
		request.setAttribute("posts", list);
		request.setAttribute("offset", offset);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("totalPages", totalPages);
		request.setAttribute("limit", limit);
		
		//뷰로 이동(forward).
		request.getRequestDispatcher("/WEB-INF/views/post/list.jsp").forward(request, response);
	}


}
