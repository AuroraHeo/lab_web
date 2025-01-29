<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8"/>
        
        <!-- Bootstrap을 사용하기 위한 meta name="viewport" 설정. -->
        <meta name="viewport" content="width=device-width, initial-scale=1"/> 
        
        <title>JSP3</title>
        
        <!-- Bootstrap CSS 링크. -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" 
            rel="stylesheet" 
            integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" 
            crossorigin="anonymous" />
            
        <style> 
            .pagination-arrow { 
                border: none; 
                color: #6c757d; /* 회색 */ 
                font-size: 0.8em; /* 글자 크기 작게 */ 
                padding: 0.375rem 0.75rem; /* 기본 버튼 패딩 */ 
                font-weight: bold; /* 글자 진하게 */ 
                text-decoration: none; /* 밑줄 */
            }
            .btn-outline-primary {
                 color: gray; /* 버튼 텍스트 색상 회색으로 설정 */
                 border-color: gray; /* 버튼 테두리 색상 회색으로 설정 */
            }
            .post-image {
                width: 100%; /* 원하는 너비로 설정 */
                height: auto; /* 이미지 비율 유지 */
                max-height: 150px; /* 원하는 최대 높이로 설정 */
                border-radius: 10px;
            }
            .post-time {
                color: gray;
                font-size: 0.875rem;
            }
            .post-card { 
                display: block; 
                text-align: left; 
                width: 100%; 
                border: none; 
                background: none; 
                cursor: pointer; 
                padding: 0; 
                margin: 0; 
                text-decoration:
            }
            a {
                color: black; /* 링크 기본 색상 검정색 */
                text-decoration: none; /* 밑줄 제거 */
            }
            a:hover {
                font-weight: bold; /* 마우스를 갖다댔을 때 굵게 */
                cursor: pointer; /* 커서 모양을 변경하여 클릭 가능함을 표시 */
            }
            .post-link {
                color: black; /* 링크 기본 색상 검정색 */
                text-decoration: none; /* 밑줄 제거 */
            }
            .post-link:hover .title, .post-link:hover .content {
                font-weight: bold; /* 마우스를 갖다댔을 때 굵게 */
            }
        </style>
    </head>
    <body>
       <div class="container-fluid">
    <c:set value="조곤조곤 수다 블로그" var="pageTitle" />
    <%@ include file="../fragments/header.jspf" %> <!-- header.jspf -->

    <main>
        <div class="mt-2 card">
            <div class="card-header">
                <c:url value="/post/search" var="postSearchPage" />
                <form action="${postSearchPage}" method="get">
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
                            <input class="form-control" type="text" name="keyword" placeholder="검색어" required />
                        </div>
                        <div class="col-2">
                            <input class="form-control btn btn-outline-secondary" type="submit" value="검색" />
                        </div>
                    </div>
                </form>
            </div>
            <div class="card-body">
                  <c:choose>
                    <c:when test="${empty param.category && empty param.keyword}">
                      <!-- 일반 게시글 리스트와 페이징 버튼 -->
                      <c:forEach var="post" items="${posts}">
                        <div class="row mb-4" style="height: 200px;">
                          <!-- 카드의 높이를 고정 설정 -->
                          <div class="col-9 d-flex flex-column justify-content-between">
                            <div>
                              <div class="row mb-2">
                                <div class="col-6">
                                  <p>${post.author}</p>
                                </div>
                                <div class="col-6">
                                  <p>조회수: ${post.viewCount}</p>
                                </div>
                              </div>
                              <div class="row mb-2">
                                <div class="col-12">
                                  <p class="post-time">${post.formattedCreatedTime}</p>
                                </div>
                              </div>
                              <div class="row mb-2">
                                <div class="col-12">
                                  <c:url value="/post/details" var="postDetailsPage">
                                    <c:param name="id" value="${post.id}" />
                                  </c:url>
                                  <a href="${postDetailsPage}" class="post-link">
                                    <span class="title">제목: ${post.title}</span>
                                    <br>
                                    <span class="content">${fn:substring(post.content, 0, 80)}...</span>
                                  </a>
                                </div>
                              </div>
                            </div>
                          </div>
                          <c:if test="${not empty post.imageUrl}">
                             <div class="col-3">
                               <img src="${post.imageUrl}" alt="" class="img-fluid post-image"
                                  style="height: 220px; width: 100%; object-fit: cover; max-width:260px; margin-top: 25px; margin-left:10px;">
                            </div>
                          </c:if>                       
                        </div>
                        <hr>
                      </c:forEach>
                      <div class="card-footer d-flex justify-content-center">
                        <!-- 첫 페이지로 이동 버튼 -->
                        <c:url value="/post/list" var="firstPageUrl">
                          <c:param name="offset" value="0" />
                        </c:url>
                        <a href="${firstPageUrl}" class="pagination-arrow me-2 btn btn-outline-secondary">
                          <<
                        </a>
                        <!-- 첫 페이지 번호 -->
                        <a href="${firstPageUrl}" class="btn btn-outline-secondary mx-1 ${currentPage == 1 ? 'active' : ''}">
                          1
                        </a>
                        <!-- 동적 페이지 번호 생성 -->
                        <c:forEach var="i" begin="${currentPage - 5 > 1 ? currentPage - 5 : 2}" end="${currentPage + 5 < totalPages ? currentPage + 5 : totalPages}">
                          <c:if test="${i > 1 && i < totalPages}">
                            <c:url value="/post/list" var="pageUrl">
                              <c:param name="offset" value="${(i - 1) * limit}" />
                            </c:url>
                            <a href="${pageUrl}" class="btn btn-outline-secondary mx-1 ${i == currentPage ? 'active' : ''}">
                              ${i}
                            </a>
                          </c:if>
                        </c:forEach>
                        <!-- 마지막 페이지 번호 -->
                        <c:url value="/post/list" var="lastPageUrl">
                          <c:param name="offset" value="${(totalPages - 1) * limit}" />
                        </c:url>
                        <a href="${lastPageUrl}" class="btn btn-outline-secondary mx-1 ${currentPage == totalPages ? 'active' : ''}">
                          ${totalPages}
                        </a>
                        <!-- 마지막 페이지로 이동 버튼 -->
                        <a href="${lastPageUrl}" class="pagination-arrow ms-2 btn btn-outline-secondary">
                          >>
                        </a>
                      </div>
                    </c:when>
                    <c:otherwise>
                      <!-- 검색 결과 게시글 리스트 -->
                      <c:forEach var="post" items="${posts}">
                        <div class="row mb-4" style="height: 200px;">
                          <!-- 카드의 높이를 고정 설정 -->
                          <div class="col-9 d-flex flex-column justify-content-between">
                            <div>
                              <div class="row mb-2">
                                <div class="col-6">
                                  <p>${post.author}</p>
                                </div>
                                <div class="col-6">
                                  <p>조회수: ${post.viewCount}</p>
                                </div>
                              </div>
                              <div class="row mb-2">
                                <div class="col-12">
                                  <p class="post-time">${post.formattedCreatedTime}</p>
                                </div>
                              </div>
                              <div class="row mb-2">
                                <div class="col-12">
                                  <c:url value="/post/details" var="postDetailsPage">
                                    <c:param name="id" value="${post.id}" />
                                  </c:url>
                                  <a href="${postDetailsPage}" class="post-link">
                                    <span class="title">제목: ${post.title}</span>
                                    <br>
                                    <span class="content">${fn:substring(post.content, 0, 125)}...</span>
                                  </a>
                                </div>
                              </div>
                            </div>
                          </div>
                          <c:if test="${not empty post.imageUrl}">
                            <div class="col-3">
                                <img src="${post.imageUrl}" alt="" class="img-fluid post-image"
                                     style="height: 220px; width: 100%; object-fit: cover; max-width:260px; margin-top: 25px; margin-left:10px;">
                            </div>
                         </c:if>     
                        </div>
                        <hr>
                      </c:forEach>
                      <!-- 여기에서 버튼 추가 -->
                      <div class="d-flex justify-content-center mt-3">
                        <c:url value="/post/list" var="firstPageUrl" />
                        <a href="${firstPageUrl}" class="btn btn-outline-secondary">이전</a>
                      </div>
                    </c:otherwise>
                  </c:choose>
                </div>
         </div>
        </main>    
    </div>
       
        <!--  Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" 
            integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" 
            crossorigin="anonymous">
        </script>
    </body>
    
</html>

