package com.itwill.jsp3.web.post;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itwill.jsp3.domain.Post;
import com.itwill.jsp3.service.PostService;

/**
 * Servlet implementation class PostCreateController
 */
@WebServlet(name = "postCreateController", urlPatterns = { "/post/create" })
@MultipartConfig( // 이미지 파일 크기 설정
        fileSizeThreshold = 1024 * 1024 * 1,  // 1 MB
        maxFileSize = 1024 * 1024 * 20,       // 20 MB
        maxRequestSize = 1024 * 1024 * 200    // 200 MB
    )
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("doGet()");
        request.getRequestDispatcher("/WEB-INF/views/post/create.jsp").forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("doPost()");
        
        request.setCharacterEncoding("UTF-8");

        String title = request.getParameter("title");
        String content = request.getParameter("content");
        
        // 세션에서 사용자 이름 가져오기
        String author = (String) request.getSession().getAttribute("signedInUser");
        if (author == null) {
            // 사용자 정보가 없으면 로그인 페이지로 리디렉션
            response.sendRedirect(request.getContextPath() + "/user/signin");
            return;
        }
    
        // 이미지 파일 처리
        Part filePart = request.getPart("file");
        String imageUrl = null;

        if (filePart != null && filePart.getSize() > 0) {
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String uploadPath = "C:\\upload";

            log.debug("Upload Path: " + uploadPath);
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                log.debug("파일 존재하지 않아요");
                uploadDir.mkdirs();
            }

            String filePath = uploadPath + File.separator + fileName;
            log.debug("File Path: " + filePath);
            filePart.write(filePath);

            // 파일이 실제로 저장되었는지 확인
            File uploadedFile = new File(filePath);
            if (uploadedFile.exists()) {
                log.debug("파일 저장 성공: " + filePath);
                // 이미지 URL 설정
                imageUrl = request.getContextPath() + "/upload/" + URLEncoder.encode(fileName, "UTF-8");
                log.debug("Image URL: " + imageUrl);
            } else {
                log.error("파일 저장 실패: " + filePath);
            }
        }

        // Post 객체 생성
        Post post = Post.builder()
                .title(title)
                .content(content)
                .author(author)
                .imageUrl(imageUrl)  // 이미지 URL은 선택적으로 설정됨
                .build();

        // PostService를 사용해 데이터베이스에 저장
        postService.create(post);

        // 새 글 작성 후 리스트 페이지로 리다이렉트
        response.sendRedirect(request.getContextPath() + "/post/list");
    }
}
