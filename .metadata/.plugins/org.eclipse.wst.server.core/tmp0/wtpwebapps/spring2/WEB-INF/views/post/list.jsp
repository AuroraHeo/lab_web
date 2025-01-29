<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        
        <!-- Bootstrap을 사용하기 위한 meta name="viewport" 설정. -->
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        
        <title>Spring 2</title>
        
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" 
            rel="stylesheet" 
            integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" 
            crossorigin="anonymous" />
    </head>
    <body>
        <div class="container-fluid">
            <c:set var="pageTitle" value="포스트 목록" />
            <%@ include file="../fragments/header.jspf" %>
            
            <main>
                <div class="mt-2 card">
                    <div class="card-header">
                        <c:url var="postSearchPage" value="/post/search"/>
                        <form action="${ postSearchPage }" method="get">
                            <div class="row"><!-- element 차지 비율 3 : 7 : 2 -->
                                <div class="col-3">
                                    <select class="form-control" name="category">
                                        <option value="t">제목</option>
                                        <option value="c">내용</option>
                                        <option value="tc">제목+내용</option>
                                        <option value="a">작성자</option>
                                    </select>
                                </div>
                                <div class="col-7">
                                    <input type="text" class="form-control" 
                                        name="keyword" placeholder="검색어 입력" required/>
                                </div>
                                <div class="col-2">
                                    <input type="submit" value="검색" 
                                        class="form-control btn btn-outline-secondary"/>
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
                                <c:forEach items="${posts}" var="p">
                                    <tr>
                                        <td>${p.id}</td>
                                        <td>
                                            <c:url var="postDetailsPage" value="/post/details">
                                                <c:param name="id" value="${ p.id }"/>
                                            </c:url>
                                            <a href="${ postDetailsPage }">${p.title}</a>
                                        </td>
                                        <td>${p.author}</td>
                                        <td>${p.modifiedTime}</td>
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