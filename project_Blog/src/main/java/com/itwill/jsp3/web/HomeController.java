package com.itwill.jsp3.web;

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

@WebServlet(name = "homeController", urlPatterns = { "" })
public class HomeController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(HomeController.class);
    private final PostService postService = PostService.INSTANCE;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeController() {}
    
    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("doGet()");

        List<Post> recentPosts = postService.getRecentPosts(5);
        List<Post> popularPosts = postService.getPopularPosts(3);

        log.debug("Recent Posts: {}", recentPosts);
        log.debug("Popular Posts: {}", popularPosts);

        request.setAttribute("recentPosts", recentPosts);
        request.setAttribute("popularPosts", popularPosts);

        request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
    }

}
