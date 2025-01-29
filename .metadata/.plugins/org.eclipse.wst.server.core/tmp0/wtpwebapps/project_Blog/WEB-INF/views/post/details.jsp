<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8"/>
        
        <!-- Bootstrap을 사용하기 위한 meta name="viewport" 설정. -->
        <meta name="viewport" content="width=device-width, initial-scale=1"/> 
        
        <title>글 상세보기</title>
        
        <!-- Bootstrap CSS 링크. -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" 
            rel="stylesheet" 
            integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" 
            crossorigin="anonymous" />
    </head>
    <body>
        <div class="container-fluid">
            <c:set value="조곤조곤 수다 블로그" var="pageTitle"/>
            <%@ include file="../fragments/header.jspf" %> <!-- header.jspf -->
            
            <main class="mt-2">
                <div class="card">
                    <div class="card-header">
                        <h2>글 상세보기</h2>
                    </div>
                    <div class="card-body">
                        <form>
                            <div class="mt-2">
                                <label class="form-label" for="id">번호</label>
                                <input class="form-control" id="id" type="text" value="${ post.id }" readonly/>
                            </div>
                            <div class="mt-2">
                                <label class="form-label" for="title">제목</label>
                                <input class="form-control" id="title"
                                    type="text" value="${ post.title }"
                                    readonly />
                            </div>
                            <div class="mt-2">
                                <label class="form-label" for="views">조회수</label>
                                <input class="form-control" id="views"
                                    type="text" value="${ post.viewCount }"
                                    readonly />
                            </div>
                            <c:if test="${ not empty post.imageUrl }">
                                <div class="mt-2">
                                    <label class="form-label" for="image"></label>
                                    <img class="img-fluid" id="image"
                                        src="${post.imageUrl}"
                                        alt="첨부 이미지" />
                                </div>
                            </c:if>
                            <div class="mt-2">
                                <label class="form-label" for="content">내용</label>
                                <textarea class="form-control" id="content"
                                    rows="5" readonly>${ post.content }</textarea>
                            </div>

                            <div class="mt-2">
                                <label class="form-label" for="author">작성자</label>
                                <input class="form-control" id="author"
                                    type="text" value="${ post.author }"
                                    readonly />
                            </div>
                            <div class="mt-2">
                                <label class="form-label" for="createdTime">작성시간</label>
                                <input class="form-control" id="createdTime" type="text" value="${ post.formattedCreatedTime }" readonly/>
                            </div>
                            <div class="mt-2">
                                <label class="form-label" for="modifiedTime">수정시간</label>
                                <input class="form-control" id="modifiedTime" type="text" value="${ post.formattedModifiedTime }" readonly/>
                            </div>
                        </form>
                    </div>
                    <%-- 포스트 작성자 아이디와 로그인 사용자 아이디가 같은 경우에만 수정하기 버튼을 보여줌. --%>
                    <c:if test="${ post.author eq signedInUser }">
                        <div class="card-footer">
                            <div class="d-flex justify-content-center">
                                <c:url value="/post/modify" var="postModifyPage">
                                    <c:param name="id" value="${ post.id }"/>
                                </c:url>
                                <a class="btn btn-outline-success" href="${ postModifyPage }">수정하기</a>
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
    </body>
</html>
