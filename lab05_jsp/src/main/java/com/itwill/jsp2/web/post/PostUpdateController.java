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

//게시글 수정 작업을 실제로 처리하는 역할
/**
 * Servlet implementation class PostUpdateController
 */
@WebServlet(name = "postUpdateController", urlPatterns = { "/post/update" })
public class PostUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(PostCreateController.class);
	private final PostService postService = PostService.INSTANCE;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PostUpdateController() {}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
    // post_modify.js에서 modifyForm.method = 'post'; // 요청 '방식'을 POST로 설정했기 때문에 doPost만 오버라이드
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 양식 데이터(id, title, content) 값을 읽어옴.
    	int id = Integer.parseInt(request.getParameter("id")); //modify.jsp에서 요청파라미터의 이름을 확인해서 넣기
    	String title = request.getParameter("title"); //modify.jsp에서 요청파라미터의 이름을 확인해서 넣기
    	String content = request.getParameter("content"); //modify.jsp에서 요청파라미터의 이름을 확인해서 넣기
    	Post post = Post.builder().id(id).title(title).content(content).build();
    	log.debug("doPost(post={})", post);
    	
    	// 서비스 계층의 메서드를 호출해서 DB 테이블을 업데이트.
    	postService.update(post);
    	
    	// 목록 페이지로 이동(redirect)
    	String url = request.getContextPath() + "/post/list";
    	// 만약 상세보기 페이지로 이동하고 싶으면: "/post/details?id=" + id
    	
    	log.debug("redirect to {}", url);
    	response.sendRedirect(url);
	}

}
