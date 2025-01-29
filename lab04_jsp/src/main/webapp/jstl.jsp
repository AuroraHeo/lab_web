<%@page import="com.itwill.jsp1.model.Contact"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8"/>
		<title>JSTL</title>
        <style>
            
        </style>
	</head>
	<body>
	   <%@ include file="header.jspf" %>
       
        <main>
            <h1>JSTL(JSP/Jakarta Standard Tag Library)</h1>
            <%-- JSTL 사용하기:
                1. pom.xml 파일에 의존성(dependency)을 추가.
                    o. jakarta.servlet.jsp.jstl:jakarta.servlet.jsp.jstl-api:version
                    o. org.glassfish.web:jakarta.servlet.jsp.jstl:version
                2. JSTL을 사용하는 jsp 파일에서 지시문 <%@ taglib prefix="" url="" %>을 작성.
            --%>
            
            <%
            String[] sns = {"인*", "얼굴책", "X", "싸이월드"};
            pageContext.setAttribute("sites", sns);
            %>
            
            <h2>scriptlet, expression 사용한 리스트</h2>
            <ul>
            <% for(String s : sns){ %>
                <li><%= s %></li>
            <% } %> 
            </ul>
            
            <h2>JSTL, EL 사용한 리스트</h2>
            <ul>
                <c:forEach var="s" items="${ sites }">
                    <li>${ s }</li>
                </c:forEach>
            </ul>
            
            <%
            //테이블을 작성하기 위한 더미 데이터.
            ArrayList<Contact> contacts = new ArrayList<>();
            for(int i = 0; i < 5; i++){
            	Contact c = new Contact(i, "이름-" + i,"전화번호-" + i,"이메일-" + i);
                contacts.add(c);
            }
            
            //EL에서 리스트를 사용할 수 있도록 contacts를 pageContext에 저장.
            pageContext.setAttribute("contactList", contacts);
            %>
            
            <h2>scriptlet, expression 사용한 테이블 작성</h2>
            <table>
                <thead>
                    <tr>
                        <th>번호</th>
                        <th>이름</th>
                        <th>전화번호</th>
                        <th>이메일</th>
                    </tr>
                </thead>
                <tbody>
                    <%for (Contact c : contacts) {%>
                    <tr>
                        <td><%=c.getId()%></td>
                        <td><%=c.getName()%></td>
                        <td><%=c.getPhone()%></td>
                        <td><%=c.getEmail()%></td>
                    </tr>
                    <% } %>
                </tbody>
            </table>

            <h2>JSTL, EL 사용한 테이블 작성</h2>
            <table>
                <thead>
                <tr>
                    <th>번호</th>
                    <th>이름</th>
                    <th>전화번호</th>
                    <th>이메일</th>
                </tr>
                </thead>
                <tbody>
                    <%-- ${contactList}는 <%= pageContext.getAttribute("contactList") %>와 동일--%>
                    <c:forEach var="contact" items="${contactList}">
                        <tr>
                            <%-- EL은 프로퍼티 이름으로 getter 메서드를 찾음
                                ${c.id}는 <%= c.getId() %>와 동일.
                             --%>
                            <td>${c.id}</td>
                            <td>${c.name}</td>
                            <td>${c.phone}</td>
                            <td>${c.email}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        
        <h2>JSTL URL 태그</h2>
        <div>
            <a href="click_result.jsp?username=guest&color=crimson">클릭 1</a>
        </div>
        <%-- URL에서 &는 요청 파라미터를 구분하기 위한 구분자로 사용.
            '&'를 포함하는 문자열을 요청 파라미터 값으로 전달하기 위해서는
            '&' 문자에 해당하는 UTF-8 코드값으로 URL에 인코딩해야함. (c:url과 c:param 이용)
        --%>
        <div>
            <c:url value="click_result.jsp" var="resultPage">
                <%-- 요청 파라미터의 이름과 값을 설정. c:param을 이용하면 특수문자로 넣을 수 있음. --%>
                <c:param name="username" value="admin"/> <%-- value에 admin이나 guest나 gu&est로 바꿔보기(click_result.jsp에서 설정했음.) --%>
                <c:param name="color" value="dodgerblue"/>
            </c:url>
            <a href="${ resultPage }">클릭 2</a>
        </div>
    </main>
    </body>
</html>