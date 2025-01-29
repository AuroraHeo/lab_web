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
 * Servlet implementation class PostSearchController
 */
@WebServlet(name = "postSearchController", urlPatterns = { "/post/search" })
public class PostSearchController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(PostSearchController.class);
	private final PostService postService = PostService.INSTANCE;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PostSearchController() {}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	//검색 양식 데이터(category, keyword)를 읽음.
    	String category = request.getParameter("category"); //list.jsp에서 <select class="form=control" name="category">
    	String keyword = request.getParameter("keyword"); //list.jsp에서 <input class="form=control" type="text" name="keyword" placeholder="검색어" required/>????
    	log.debug("doGet(category={}, keyword={})", category, keyword);
    	
    	List<Post> list = postService.read(category, keyword);
    	log.debug("# of search result = {}", list.size());
    	
    	// 검색 결과를 뷰(jsp)에게 전달.
    	request.setAttribute("posts", list);
    	
    	// 뷰로 이동(forward)
    	request.getRequestDispatcher("/WEB-INF/views/post/list.jsp").forward(request, response);
    	
	}

}
