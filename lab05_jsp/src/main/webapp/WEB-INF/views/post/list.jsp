<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8"/>
        
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
            <c:set value="포스트 목록" var="pageTitle" />
            <%@ include file="../fragments/header.jspf" %> <!-- header.jspf -->
            
            <main>
                <div class="mt-2 card">
                    <div class="card-header">
                        <c:url value="/post/search" var="postSearchPage"/>
                        <form action="${ postSearchPage }" method="get">
                            <div class="row">
                                <div class="col-3">
                                    <select class="form-control" name="category">
                                        <option value="t">제목</option>
                                        <option value="c">내용</option>
                                        <option value="tc">제목+내용</option>
                                        <option value="a">작성자</option>
                                    </select>
                                </div>
                                <div class="col-7">
                                    <input class="form-control" type="text" 
                                        name="keyword" placeholder="검색어" required/>
                                </div>
                                <div class="col-2">
                                    <input class="form-control btn btn-outline-secondary" 
                                        type="submit" value="검색"/> <!-- 'form 안에서' type을 submit으로 해주면 버튼처럼 보임 -->
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="card-body">
                        <table class="table table-striped table-hover">
                            <thead>
                                <tr>
                                    <th>번호</th>
                                    <th>제목</th>
                                    <th>작성자</th>
                                    <th>수정시간</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${ posts }" var="p">
                                    <tr>
                                        <td>${ p.id }</td>
                                        <c:url value="/post/details" var="postDetailsPage">
                                            <c:param name="id" value="${ p.id }"></c:param>
                                        </c:url>
                                        <td>
                                            <a href="${ postDetailsPage }">${ p.title }</a>
                                        </td>
                                        <td>${ p.author }</td>
                                        <td>${ p.modifiedTime }</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
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

<!-- 부트스트랩은 가로로 12개 칸(column)을 사용 ex) 위에 jsp는 원하는 비율인 3 : 7 : 2로 분할함-->

<!-- 버튼 만들기 -->
<!-- i) 'form 밖에서' button을 만들고 js로 처리하면 됨 -->
<!-- ii) 브라우저가 form 데이터를 제출하도록 하려면 'form 안에서' type을 submit으로 -->