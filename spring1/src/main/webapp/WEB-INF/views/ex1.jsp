<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8"/>
        
        <!-- Bootstrap을 사용하기 위한 meta name="viewport" 설정. -->
        <meta name="viewport" content="width=device-width, initial-scale=1"/> 
        
		<title>Spring 1</title>
        
        <!-- Bootstrap CSS 링크. -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" 
            rel="stylesheet" 
            integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" 
            crossorigin="anonymous" />
	</head>
	<body>
		<header>
            <h1>EX 1</h1>
            <nav>
                <ul>
                    <li>
                        <c:url value="/" var="homePage"/>
                        <a href="${ homePage }">홈 페이지</a>
                    </li>
                </ul>
            </nav>
        </header>
        
        <main>
            <h2>요청 처리 결과</h2>
            <div>${ user }</div> <!-- user.toString()이 호출됨. lombok이 자동으로 만든 toString()을 확인해보기용 -->
            <div>username: ${ user.username }</div> <!-- user.getUsername() -->
            <div>age: ${ user.age }</div> <!-- user.getAge() -->
        </main>
        
        <!--  Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" 
            integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" 
            crossorigin="anonymous"></script>
	</body>
</html>