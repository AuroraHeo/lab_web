<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1"/> 
        
        <title>JSP3</title>
        
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" 
            rel="stylesheet" 
            integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" 
            crossorigin="anonymous" />
        <style>
            body, th, td, a, h2 {
                color: black; /* 텍스트 색상을 검정색으로 설정 */
            }
            .container-fluid, .table, .card {
                background-color: white; /* 배경 색상을 흰색으로 설정 */
            }
            .clickable-row {
                cursor: pointer;
                background-color: white; /* 행 배경을 흰색으로 설정 */
            }
            .title-header {
                width: 70%; /* 제목 열의 너비를 70%로 설정 */
            }
            .title-header a { 
                text-decoration: none; /* 제목 링크에서 밑줄 제거 */ 
            } 
            .card-custom {
                max-width: 33.333%; /* 카드의 최대 너비를 3등분하여 설정 */
                margin: 0 auto; /* 카드 양쪽 여백을 자동으로 설정 */
            }
            .placeholder {
                background-color: #ddd; /* 회색 배경 */
                width: 100%; /* 너비 100% */
                height: 270px; /* 높이 270px */
                display: flex;
                align-items: center;
                justify-content: center;
                color: white;
                font-size: 1rem;
                border-radius: 10px; /* 둥근 모서리 설정 */
            }
            .post-time {
                color: gray;
                font-size: 0.875rem;
            }
            .gray-image {
                filter: grayscale(100%);
            }
            .default-gray-bg {
                background-color: gray;
                height: 270px; /* 이미지 높이를 270px로 설정 */
                width: 100%; /* 이미지 너비를 100%로 설정 */
                display: flex;
                align-items: center;
                justify-content: center;
                color: white;
                font-size: 1rem;
                border-radius: 10px; /* 둥근 모서리 설정 */
            }
            .post-image {
                width: 100%; /* 원하는 너비로 설정 */
                height: auto; /* 이미지 비율 유지 */
                max-height: 150px; /* 원하는 최대 높이로 설정 */
                border-radius: 10px;
            }
        </style>
        
        <script>
            document.addEventListener("DOMContentLoaded", function() {
                const rows = document.querySelectorAll(".clickable-row");
                rows.forEach(row => {
                    row.addEventListener("click", function() {
                        const href = this.querySelector('a').getAttribute('href');
                        window.location.href = href;
                    });
                });
    
                const images = document.querySelectorAll('.card-img-top');
                images.forEach(img => {
                    img.addEventListener('error', function() {
                        const placeholder = document.createElement('div');
                        placeholder.classList.add('placeholder');
                        this.replaceWith(placeholder);
                    });
                });
            });
        </script>
    </head>
    <body>
        <div class="container-fluid">
            <c:set value="조곤조곤 수다 블로그" var="pageTitle" />
            <%-- pageContext.setAttribute("pageTitle", "조곤조곤 수다 블로그"); --%>
            <%@ include file="./fragments/header.jspf" %>
    
            <!-- 최신 게시글 테이블 -->
            <div class="mt-4">
                <h2 style="font-weight: bold; margin-left: 10px;">최근 게시글</h2>
                <table class="table table-hover mb-5">
                    <thead class="thead-dark">
                        <tr>
                            <th class="title-header">제목</th>
                            <th>작성자</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="post" items="${recentPosts}">
                            <tr class="clickable-row">
                                <td class="title-header">
                                    <c:url value="/post/details" var="postDetailsPage">
                                        <c:param name="id" value="${post.id}" />
                                    </c:url>
                                    <a href="${postDetailsPage}">${post.title}</a>
                                </td>
                                <td>${post.author}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
            <!-- 조회수 top 3 게시글 카드 -->
            <div class="row mt-4 justify-content-center">
                <h2 style="font-weight: bold; margin-left: 20px;">조회수 TOP3</h2>
                <div class="row justify-content-center">
                    <c:forEach var="post" items="${popularPosts}">
                        <div class="col-md-4 mb-3 card-custom">
                            <div class="card h-100">
                                <c:url value="/post/details" var="postDetailsPage">
                                    <c:param name="id" value="${post.id}" />
                                </c:url>
                                <a href="${postDetailsPage}" style="text-decoration: none; color: inherit;">
                                    <c:choose>
                                        <c:when test="${not empty post.imageUrl}">
                                            <img src="${post.imageUrl}" class="card-img-top ${post.viewCount > 100 ? 'gray-image' : ''}" alt="${post.title}" style="object-fit: cover; width: 100%; height: 270px;">
                                        </c:when>
                                        <c:otherwise>
                                            <div class="placeholder" style="width: 100%; height: 270px; background-color: #eaeaea; display: flex; align-items: center; justify-content: center; cursor: default;">No Image</div>   
                                        </c:otherwise>
                                    </c:choose>
                                    <div class="card-body">
                                        <h5 class="card-title">${post.title}</h5>
                                        <p class="card-text">조회수: ${post.viewCount}</p>
                                    </div>
                                </a>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
              
        </div>
        
        <footer class="text-center text-lg-start bg-darkslategray text-floralwhite mt-5">
            <div class="text-center p-4" style="background-color: DarkSlateGrey; color: FloralWhite;">
                © 2025 조곤조곤 수다 블로그
            </div>
        </footer>
        
        <!--  Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" 
            integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" 
            crossorigin="anonymous"></script>
      </body>
</html>

