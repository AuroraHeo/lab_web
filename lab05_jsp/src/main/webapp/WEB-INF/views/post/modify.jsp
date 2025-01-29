<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8"/>
        
        <!-- Bootstrap을 사용하기 위한 meta name="viewport" 설정. -->
        <meta name="viewport" content="width=device-width, initial-scale=1"/> 
        
		<title>JSP2</title>
        
        <!-- Bootstrap CSS 링크. -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" 
            rel="stylesheet" 
            integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" 
            crossorigin="anonymous" />
	</head>
	<body>
        <div class="container-fluid">
            <c:set value="포스트 수정하기" var="pageTitle"/>
            <%@ include file="../fragments/header.jspf" %> <!-- header.jspf -->
            
            <main class="mt-2">
                <div class="card">
                    <div class="card-header">
                        <h1>글 수정하기</h1>
                    </div>
                    <div class="card-body">
                        <form id="modifyForm">
                            <div class="mt-2 d-none"> <!-- d-none: 화면에서 번호가 안보이게(개발자 도구에서는 볼 수 있음) -->
                                <label class="form-label" for="id">번호</label> <!-- 번호라는 레이블도 누르면 focus가 됨 -->
                                <input class="form-control" id="id" name="id" type="text" value="${ post.id }" readonly/> <!-- readonly : 읽기 전용(편집 불가), name 속성을 넣어서 submit되게끔 -->
                            </div>
                            <div class="mt-2">
                                <label class="form-label" for="title">제목</label>
                                <input class="form-control" id="title" name="title" type="text" value="${ post.title }" autofocus/>
                            </div>
                            <div class="mt-2">
                                <label class="form-label" for="content">내용</label>
                                <textarea class="form-control" id="content" name="content" rows="5">${ post.content }</textarea> <!-- textarea는 시작태그와 종료태그 사이에 값(value)을 넣기 -->
                            </div>
                            <div class="mt-2 d-none">
                                <label class="form-label" for="author">작성자</label>
                                <input class="form-control" id="author" type="text" value="${ post.author }" readonly /> <!-- textarea는 시작태그와 종료태그 사이에 값을 넣기 -->
                            </div>
                        </form>
                    </div>
                    
                    <c:if test="${ post.author eq signedInUser }">
                        <div class="card-footer">
                            <div class="d-flex justify-content-center">
                                <button class="btn btn-outline-danger me-2" id="btnDelete">삭제</button>
                                <button class="btn btn-outline-success" id="btnUpdate">업데이트</button>
                            </div>
                        </div>
                    </c:if>
                    
                </div>
            </main>
        </div>
        
        <!--  Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" 
            integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" 
            crossorigin="anonymous"></script>
        
        <%-- 정적(static) resource인 CSS, JS, 이미지, 동영상은 client는 직접 접근 불가라서 WEB-INF폴더에 저장하면 안됨.--%>
        <%-- src/main/webapp/static/js/post_modify.js 파일을 포함. --%>
        <c:url value="/static/js/post_modify.js" var="postModifyJS"/> <!-- c:url를 이용하는 이유: 앞에 contextpath까지 붙여서 안전한 주소를 만들어주니까 -->
        <script src="${ postModifyJS }"></script>
	</body>
</html>

<!-- mt-*, mb-*, ms-*, me-* -->
<!-- form태그 안에 버튼은 무조건 submit돼버리니까 form 태그 바깥에서 버튼을 만듦 -->