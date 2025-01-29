<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8"/>
        
        <!-- Bootstrap을 사용하기 위한 meta name="viewport" 설정. -->
        <meta name="viewport" content="width=device-width, initial-scale=1"/> 
        
		<title>Spring 2</title>
        
        <!-- Bootstrap CSS 링크. -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" 
            rel="stylesheet" 
            integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" 
            crossorigin="anonymous" />
	</head>
	<body>
		<div class="container-fluid">
            <c:set var="pageTitle" value="회원가입"/>
            <%@ include file="../fragments/header.jspf" %>
            
            <main>
                <div class="mt-2 card card-body"> <%--카드이면서 카드 바디(헤더와 풋터를 갖지않는 카드) --%>
                    <form method="post">
                        <div class="mt-2">
                            <input type="text" class="form-control" id="username"
                                name="username" placeholder="사용자 아이디" required autofocus/> 
                        </div>
                        <%--username 중복체크 결과를 출력할 영역 --%>
                        <div id="checkUsernameResult"></div>
                        
                        <div class="mt-2">
                            <input type="password" class="form-control" id="password"
                                name="password" placeholder="비밀번호" required/>
                        </div>
                        <div id="checkPasswordResult"></div>
                        <div class="mt-2">
                            <input type="email" class="form-control" id="email"
                                name="email" placeholder="이메일" required/>
                        </div>
                        <%-- 이메일 중복체크 결과를 출력할 영역 --%>
                        <div id="checkEmailResult"></div>
                        
                        <div class="mt-2">
                            <button class="form-control btn btn-outline-success disabled"
                                id="btnSignUp">작성 완료</button>
                        </div>
                    </form>
                </div> 
            </main>
            
        </div>
        
        <!--  Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" 
            integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" 
            crossorigin="anonymous"></script>
        
        <!-- Axois JS -->
        <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
        
        <!-- 내가 만든 member.js -->
        <c:url var="membersJS" value="/js/members.js"/>
        <script src="${ membersJS }"></script>
	</body>
</html>

<%-- username이 중복되지 않을 때, 비번을 넣었을 때, 이메일이 중복되지 않을 때, 작성완료 버튼이 뜨게끔 --%>