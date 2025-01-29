<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8"/>
		<title>MVC</title>
	</head>
	<body>
        <%@ include file="../../header.jspf" %> <%-- ../ : 한단계 위의 상위폴더로 이동 --%>
		<%-- 
            현재 contact.jsp 파일이 있는 폴더 위치: webapp/WEB-INF/views/contact.jsp
            jspf 파일이 있는 폴더 위치: webapp/header.jspf
            현재 파일이 있는 위치를 기준으로 상대 경로로 파일 위치를 찾아가는 방법:
            ./ : 현재 폴더
            ../ : 상위 폴더 
        --%>
        
        <main>
            <h1>MVC</h1>
            <h2>연락처 입력 페이지</h2>
            
            <%-- form에서 action속성을 설정하지 않으면 현재 요청주소 그대로 요청보냄. --%>
            <form method="post">
                <div>
                    <input type="number" name="id" placeholder="번호" autofocus/>
                </div>
                <div>
                    <input type="text" name="name" placeholder="이름"/>
                </div>
                <div>
                    <input type="text" name="phone" placeholder="전화번호"/>
                </div>
                <div>
                    <input type="email" name="email" placeholder="이메일"/>
                </div>
                <div>
                    <input type="submit" value="저장"/> <%-- 서버로 요청을 보냄 --%>
                    <input type="reset" value="취소"/> <%-- 작성된 내용을 모두 지움--%>
                </div>
            </form>
        </main>
	</body>
</html>

<!-- contextpath: http://localhost:8080/jsp1/ -->

<!-- 
외부(브라우저나 클라이언트)에게는 공개하지 않는 폴더(WAS(ex.톰캣)만 사용하는 폴더): WEB-INT폴더 

-->