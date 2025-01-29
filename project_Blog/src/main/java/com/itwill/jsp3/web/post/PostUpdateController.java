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

// 게시글 수정 작업을 실제로 처리하는 역할
@WebServlet(name = "postUpdateController", urlPatterns = { "/post/update" })
@MultipartConfig( // 이미지 파일 크기 설정
        fileSizeThreshold = 1024 * 1024 * 1,  // 1 MB
        maxFileSize = 1024 * 1024 * 20,       // 20 MB
        maxRequestSize = 1024 * 1024 * 200    // 200 MB
    )
public class PostUpdateController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(PostUpdateController.class);
    private final PostService postService = PostService.INSTANCE;
       
    public PostUpdateController() {}

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id")); // modify.jsp에서 요청파라미터의 이름을 확인해서 넣기
        String title = request.getParameter("title"); // modify.jsp에서 요청파라미터의 이름을 확인해서 넣기
        String content = request.getParameter("content"); // modify.jsp에서 요청파라미터의 이름을 확인해서 넣기
        String imageUrl = request.getParameter("image_url");
        boolean removeImage = "on".equals(request.getParameter("removeImage"));

        // 이미지 파일 처리
        Part filePart = request.getPart("file");
        if (filePart != null && filePart.getSize() > 0) {
            String fileName = System.currentTimeMillis() + "_" + Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
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
        } else if (removeImage) {
        	postService.removeImage(id);
            imageUrl = null; // 이미지 삭제
            log.debug("이미지 삭제 처리 완료");
        }

        Post post = Post.builder().id(id).title(title).content(content).imageUrl(imageUrl).build();
        log.debug("doPost(post={})", post);
        
        // 서비스 계층의 메서드를 호출해서 DB 테이블을 업데이트.
        postService.update(post);
        
        // 목록 페이지로 이동(redirect)
        String url = request.getContextPath() + "/post/details?id=" + id;
        
        log.debug("redirect to {}", url);
        response.sendRedirect(url);
    }

}

