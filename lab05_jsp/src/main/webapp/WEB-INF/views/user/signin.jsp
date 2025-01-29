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
            <c:set value="로그인" var="pageTitle" />
            <%@ include file="../fragments/header.jspf" %>
            
            <main class="mt-2">
                <div class="card">
                    <div class="card-body">
                        <form method="post">
                            <div class="mt-2">
                                <input type="text" class="form-control"
                                    name="username" placeholder="아이디" required autofocus />
                            </div>
                            <div class="mt-2">
                                <input type="password" class="form-control"
                                    name="password" placeholder="비밀번호" required />
                            </div>
                            <div class="d-none">
                                <input name="target" value="${ param.target }" readonly/>
                            </div>
                            
                            <%-- 로그인 실패 후에 다시 로그인 페이지로 이동한 경우 --%>
                            <c:if test="${ not empty param.result && param.result eq 'f' }">
                                <div class="text-danger mt-2">아이디와 패스워드를 확인하세요.</div>
                            </c:if>
                            
                            <div class="mt-2">
                                <input class="form-control btn btn-outline-primary"
                                    type="submit" value="로그인" />
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

<!-- 아이디와 비밀번호가 일치되어서 로그인 되면 session에 로그인 정보를 저장해야됨(만료시간 전까지는 정보가 유지가 되니까.. request에 저장하면 안됨(응답 받으면 요청 객체가 사라지니까)) -->