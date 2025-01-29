<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        
        <!-- Bootstrap을 사용하기 위한 meta name="viewport" 설정. -->
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        
        <title>JSP3</title>
        
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" 
            rel="stylesheet" 
            integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" 
            crossorigin="anonymous" />
    </head>
    <body>
        <div class="container-fluid">
            <c:set value="회원 가입" var="pageTitle" />
            <%@ include file="../fragments/header.jspf" %>
            
            <main class="mt-2">
                <div class="card">
                    <div class="card-body">

                        <!-- 에러 메시지를 표시하는 부분 -->
                        <c:if test="${not empty errorMessage}">
                            <div class="alert alert-danger">
                                ${errorMessage}
                            </div>
                        </c:if>
                        
                        <form method="post">
                            <div class="mt-2">
                                <label for="username" class="form-label">아이디</label>
                                <input id="username" class="form-control"
                                    type="text" name="username" value="${param.username}" required autofocus />
                            </div>
                            <div class="mt-2">
                                <label for="password" class="form-label">비밀번호</label>
                                <input id="password" class="form-control"
                                    type="password" name="password" required />
                            </div>
                            <div class="mt-2">
                                <label for="email" class="form-label">이메일</label>
                                <input id="email" class="form-control"
                                    type="email" name="email" value="${param.email}" required />
                            </div>
                            <div class="mt-2">
                                <input type="submit" class="form-control btn btn-outline-secondary" 
                                    value="작성완료" />
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
