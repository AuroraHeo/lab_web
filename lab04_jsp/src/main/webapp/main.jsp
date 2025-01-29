<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8"/>
		<title>JSP</title>
	</head>
	<body>
    <%-- 
        directive(지시문)의 종류:
            o. page 지시문 <%@ page attr="value" ... %>
            o. include 지시문 <%@ include file="value" %>
            o. taglib 지시문 <%@ taglib attr="value" ... %>d
        include 지시문(directive):
            여러 개의 페이지들이 공통된 컨텐트(예: 헤더, 푸터)를 포함하는 경우,
            공통된 컨텐트들을 별도의 jsp/jspf 파일로 작성하고,
            공통된 커텐트를 필요로 하는 페이지(예: main.jsp)에서 jsp/jspf 파일을 포함해서 사용.
            include 지시문이 사용된 위치에 jsp/jspf 파일의 내용이 삽입되고,
            하나의 java 소스 코드로 변환 후 자바 클래스로 컴파일됨.
            확장자 jspf: jsp fragment(조각)
    --%>
    <%@ include file="header.jspf" %>
		
        <main>
            <h1>메인 페이지</h1>
        </main>
    <%@ include file="footer.jsp" %>    
	</body>
</html>

<%-- .jsp > .java > .class > WAS가 객체 생성 > WAS가 메서드 호출(HTML) --%>