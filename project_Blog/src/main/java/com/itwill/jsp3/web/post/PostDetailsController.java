package com.itwill.jsp3.web.post;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itwill.jsp3.domain.Post;
import com.itwill.jsp3.service.PostService;

/**
 * Servlet implementation class PostDetailsController
 */
@WebServlet(name = "postDetailsController", urlPatterns = { "/post/details" })
public class PostDetailsController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(PostDetailsController.class);
    private final PostService postService = PostService.INSTANCE;

    public PostDetailsController() {}

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 요청 파라미터 id(글 번호)를 읽음.
        int id = Integer.parseInt(request.getParameter("id"));
        log.debug("doGet(id={})", id);

        HttpSession session = request.getSession();
        Set<Integer> viewedPosts = (Set<Integer>) session.getAttribute("viewedPosts");
        if (viewedPosts == null) {
            viewedPosts = new HashSet<>();
        }

        // 현재 로그인한 사용자 가져오기
        String currentUser = (String) session.getAttribute("signedInUser");

        // 게시글 작성자 가져오기
        Post post = postService.read(id);  // 이 부분은 게시글 작성자의 ID를 가져오기 위한 가정입니다.
        String postAuthor = post.getAuthor();

        if (!postAuthor.equals(currentUser)) {
            // 본인의 글이 아닐 때에만 조회수 증가
            if (!viewedPosts.contains(id)) {
                postService.increaseViewCount(id);
                viewedPosts.add(id);
                session.setAttribute("viewedPosts", viewedPosts);
                log.debug("조회수 증가 완료: postId = {}", id);
            } else {
                log.debug("이미 조회한 글: postId = {}", id);
            }
        } else {
            log.debug("본인의 글: postId = {}", id);
        }


        // 서비스 계층의 메서드를 호출해서 해당 아이디의 글 상세정보를 가져옴.
        post = postService.read(id);

        // 글 상세정보를 view에 전달
        request.setAttribute("post", post);

        // 뷰로 이동(forward)
        request.getRequestDispatcher("/WEB-INF/views/post/details.jsp").forward(request, response);
    }
}
