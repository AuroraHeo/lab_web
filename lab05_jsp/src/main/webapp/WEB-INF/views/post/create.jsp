<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        
        <!-- Bootstrap을 사용하기 위한 meta name="viewport" 설정. -->
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        
        <title>JSP2</title>
        
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" 
            rel="stylesheet" 
            integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" 
            crossorigin="anonymous" />
    </head>
    <body>
        <div class="container-fluid">
            <c:set value="새 글 작성" var="pageTitle" />
            <%@ include file="../fragments/header.jspf" %> <!-- header.jspf -->
            
            <main class="mt-2">
                <div class="card">
                    <div class="card-header">
                        <h1>새 블로그 작성</h1>
                    </div>
                    <div class="card-body">
                        <%-- form에서 action 속성을 생략하면 현재 URL로 다시 요청을 보냄. 생략할 수 있지만 공부차 넣음 --%>
                        <c:url value="/post/create" var="postCreatePage"/>
                        <form action="${postCreatePage}" method="post"> <!-- action 속성을 생략하면 현재 주소 그대로, method 속성을 생략하면 get -->
                            <div class="mt-2">
                                <input type="text" class="form-control"
                                    name="title" placeholder="제목" required autofocus/>
                            </div>
                            <div class="mt-2">
                                <textarea rows="5" class="form-control"
                                    name="content" placeholder="내용" required></textarea><!-- 5줄부터 스크롤 생김 -->
                            </div>
                            <div class="d-none">
                                <input type="text" class="form-control"
                                    name="author" value="${ signedInUser }" /> <!-- name을 지정해주지 않으면 넘어가지 않음(네트워크-페이로드에서 확인해보기)  --> 
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