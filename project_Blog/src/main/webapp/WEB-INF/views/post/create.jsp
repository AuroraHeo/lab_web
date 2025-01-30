<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        
        <!-- Bootstrap을 사용하기 위한 meta name="viewport" 설정. -->
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        
        <title>새 글 작성</title>
        
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" 
            rel="stylesheet" 
            integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" 
            crossorigin="anonymous" />
            
        <script>
            function previewImage(event) {
                var reader = new FileReader();
                reader.onload = function() {
                    var output = document.getElementById('preview');
                    output.src = reader.result;
                    output.style.display = 'block';
                };
                reader.readAsDataURL(event.target.files[0]);
            }
        </script>
    </head>
    <body>
        <div class="container-fluid">
            <c:set value="조곤조곤 수다 블로그" var="pageTitle" />
            <%@ include file="../fragments/header.jspf" %> <!-- header.jspf -->

            <main class="mt-2">
                <div class="card">
                    <div class="card-header">
                        <h2>새 글 작성</h2>
                    </div>
                    <div class="card-body">
                        <%-- form에서 action 속성을 생략하면 현재 URL로 다시 요청을 보냄. --%>
                        <c:url value="/post/create" var="postCreatePage"/>
                        <form action="${postCreatePage}" method="post" enctype="multipart/form-data">
                            <div class="mt-2">
                                <input type="text" class="form-control"
                                    name="title" placeholder="제목" required autofocus/>
                            </div>
                            <div class="mt-2">
                                <textarea rows="5" class="form-control"
                                    name="content" placeholder="내용" required></textarea>
                            </div>
                            <div class="mt-2">
                                <label for="file">이미지 첨부</label>
                                <input type="file" class="form-control" id="file" name="file" onchange="previewImage(event)">
                                <img id="preview" src="#" alt="미리보기" style="display: none; max-width: 100%; height: auto; margin-top: 10px;">
                            </div>
                            <div class="d-none">
                                <input type="text" class="form-control"
                                    name="author" value="${ signedInUser }" />
                            </div>
                            <div class="mt-2 d-flex justify-content-end">
                                <input class="me-2 btn btn-outline-danger" 
                                    type="reset" value="취소" />
                                <input class="btn btn-outline-success" 
                                    type="submit" value="저장"/>
                            </div>
                        </form>
                    </div>
                </div>
            </main>
        </div>
        
        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" 
            integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" 
            crossorigin="anonymous"></script>
    </body>
</html>
