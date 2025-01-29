<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8"/>
		<title>Cookie</title>
	</head>
	<body>
		<%@ include file="../../header.jspf" %>
        
        <main>
            <h1>Cookie</h1>
            <h2>방문횟수: ${ visitCount }</h2>
            <%-- <%= request.getAttribute("visitCount")%> --%>
        </main>
	</body>
</html>