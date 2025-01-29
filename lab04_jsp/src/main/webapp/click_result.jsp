<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%><%-- i) --%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8"/>
		<title>JSTL</title>
	</head>
	<body>
		<%@ include file="header.jspf" %>
        
        <main>
            <h1>클릭 결과 페이지</h1>
            <h2>
                안녕하세요,
                <span style="color:${ param.color };">${ param.username }</span>!
                <%--
                    요청 URL의 질의 문자열(query string)에 포함된 요청 파라미터 값을 찾는 방법:
                    (1) request.getParameter("요청파라미터이름")
                    (2) EL: ${ param.요청파라미터이름 }
                --%>
            </h2>
            
            <%-- 요청 파라미터 username의 값이 admin인 경우, guest인 겨우, 그 이외인 경우. --%>
            <h2>scriptlet 사용</h2>
            <% if(request.getParameter("username").equals("admin")){ %>
                <h3>관리자 페이지</h3>
            <% } else if (request.getParameter("username").equals("guest")){%>
                <h3>손님 페이지</h3>
            <% } else { %>
                <h3>일반 사용자 페이지</h3>
            <% } %>
            
            <h2>JSTL 사용</h2>
            <%--i) JSTL 조건문 choose/when (조건이 만족하면 나머지는 건너뜀. 조건을 만족하지 못하면 다음 조건이 만족하는지 확인..)--%>
            <c:choose>
                <c:when test="${ param.username == 'admin' }"> <%-- el은 둘다 문자열이기때문에 == 가능 --%>
                    <h3>관리자 페이지</h3>
                </c:when>
                <c:when test="${ param.username eq 'guest' }">
                    <h3>손님 페이지</h3>
                </c:when>
                <c:otherwise>
                    <h3>일반 사용자 페이지</h3>
                </c:otherwise>
            </c:choose>
            
            <%-- JSTL 조건문 if(JSTL는 else 태그가 없음)(조건문마다 다 거쳐감) --%>
            <c:if test="${ param.username eq 'admin' }">
                <h3>Admin page</h3>
            </c:if>
            <c:if test="${ param.username ne 'admin' }">
                <h3>User page</h3>
            </c:if>
                
        </main>
	</body>
</html>