<%@page import="com.itwill.jsp1.model.Contact"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8"/>
		<title>Action Tag</title>
        <style>
            div.card{
                border: 1px solid gray;
                border-radius: 8px;
                margin: 8px;
                padding: 8PX;
            }
        </style>
	</head>
	<body>
		<%@ include file="header.jspf" %>
        
        <main>
            <h1>JSP Action Tag</h1>
            <%--
            JSP Action Tag: scriptlet에서 사용되는 일부 자바 코드들을 HTML 또는 XML과
            비슷하게 태그, 태그 속성(attribute), 태그 컨텐트로 작성해서 대체하기 위한 문법.
            액션 태그를 대/소문자를 구분! (HTML태그는 대/소문자를 구분하지 않음.)
                o. <jsp:forward>: request.getRequestDispatcher("").forward(request, response)
                o. <jsp:include>: <%@ include file="" %> 비슷.
                o. <jsp:useBean>: (기본)생성자 호출.
                o. <jsp:getProperty>: getter메서드 호출.
                o. <jsp:setProperty>: setter메서드 호출.
             --%>
             
             <h2>액션 태그를 사용하지 않은 자바 객체 생성</h2>
             <%
             Contact contact1 = new Contact(); //기본생성자 호출
             //setter 메서드 호출
             contact1.setId(1);
             contact1.setName("홍길동");
             contact1.setPhone("010-1234-5678");
             contact1.setEmail("hgd@test.com");
             %>
             <div class="card">
                ID: <%= contact1.getId() %> <br/>
                Name: <%= contact1.getName() %> <br/>
                Phone: <%= contact1.getPhone() %> <br/>
                Email: <%= contact1.getEmail() %>
             </div>
             
             <h2>액션 태그 자바 빈을 사용한 객체 생성</h2>
             <jsp:useBean id="contact2" class="com.itwill.jsp1.model.Contact"></jsp:useBean>
             <%-- Contact contact2 = new Contact(); 과 같은 기능
                  <jsp:useBean>은 기본생성자를 가지고 있는 클래스만 사용할 수 있음. Bean(객체)를 use(이용). id는 setProperty나 getProperty에서 찾으려고 하는 name임
              --%>
              
             <jsp:setProperty name="contact2" property="id" value="2" />
             <%-- contact2.setId(2); 와 같은 기능 (객체의 property(필드)를 set(셋팅)
                name 속성: (useBean에서 설정한) 자바 빈의 아이디.
                property 속성: 프로퍼티 이름. 클래스의 필드(변수) 이름.
                value 속성 : 프로퍼티에 설정할 값.
              --%>
             <jsp:setProperty name="contact2" property="name" value="오쌤" />
             <jsp:setProperty property="phone" name="contact2" value="010-0000-0000"/>
             <jsp:setProperty property="email" name="contact2" value="jake@itwill.com"/>
             
             <div>
                <%-- contact2.getId();과 같은 기능 --%>
                ID: <jsp:getProperty property="id" name="contact2"/> <br/>
                이름: <jsp:getProperty property="name" name="contact2"/> <br/>
                전화번호: <jsp:getProperty property="phone" name="contact2"/> <br/>
                이메일: <jsp:getProperty property="email" name="contact2"/> <br/>
             </div>
              
        </main>
        
<%--        <%@ include file="footer.jsp" %> --%>
        <jsp:include page="footer.jsp"></jsp:include>
        <%--
           o. <%@ include file="" %>는 하나의 JSP 파일로 합쳐진 후에, 하나의 java 파일로 변환되고 컴파일됨.
           o. <jsp: include page="" >는 각각의 JSP 파일들이 java 파일로 변환되고 컴파일된 후에, 하나의 HTML 문서가 작성됨.
         --%>
	</body>
</html>