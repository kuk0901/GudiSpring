<%@ page import="gudiSpring.board.dto.customerservice.CustomerServiceDto"%>
<%@ page import="gudiSpring.comment.dto.CommentDto"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 상세 조회</title>
<link rel="stylesheet"
  href="https://cdn.jsdelivr.net/npm/reset-css@5.0.2/reset.min.css">
<link rel="stylesheet" type="text/css"
  href="${pageContext.request.contextPath}/css/common/common.css">
<link rel="stylesheet" type="text/css"
  href="${pageContext.request.contextPath}/css/board/customerservice/customerServiceBoardDetail.css">
<script defer
  src="${pageContext.request.contextPath}/js/movePage/movePageFncs.js"></script>
  <script defer
  src="${pageContext.request.contextPath}/js/board/customerservice/customerServiceBoardDetail.js"></script>
 <script>
        var contextPath = '<%= request.getContextPath() %>';
    </script>
</head>
<body>
  <div id="wrapper">
    <jsp:include page="/jsp/common/header.jsp" />

    <!-- 헤더와 본문 사이에 구분을 주기 위한 div -->
    <div id="header-spacer"></div>

    <div id="main-container">
      <h2>게시글 상세 조회</h2>
      <!-- 게시글 정보 출력 -->

      <%
      CustomerServiceDto boardDto = (CustomerServiceDto) request.getAttribute("boardDto");
      if (boardDto != null) {
      %>
      <!-- 게시글 정보 -->
      <table class="board-info-table">
        <tr>
          <th>번호</th>
          <td><%=boardDto.getContentNo()%></td>
          <th>작성일</th>
          <td><%=boardDto.getContentCreDate()%></td>
        </tr>
        <tr>
          <th>제목</th>
          <td colspan="3"><%=boardDto.getContentSubject()%></td>
        </tr>
        <tr>
          <th>수정일</th>
          <td><%=boardDto.getContentUpdateDate()%></td>
          <th>작성자</th>
          <td>${boardDto.nickname}</td>
        </tr>
      </table>

      <!-- 게시글 내용 -->
      <div class="board-content">

        <p class="content-text"><%=boardDto.getContentText()%></p>
      </div>






      <!-- 게시글 수정 버튼 추가 -->
<%--       <c:if test="${sessionScope.userDto.userNo == comment.userNo}"> --%>
      <button
        onclick="location.href='<%=request.getContextPath()%>/board/customerserviceboard/edit?contentNo=<%=boardDto.getContentNo()%>'">수정</button>
      <!-- 게시글 삭제 버튼 추가 -->
      <button
        onclick="confirmDeletePost(<%=boardDto.getContentNo()%>)">게시글
        삭제</button>
<%-- 		</c:if> --%>
      <h3>댓글 달기</h3>
      <form action="<%=request.getContextPath()%>/addComment"
        method="post" onsubmit="return validateForm(this);">
        <input type="hidden" name="contentNo"
          value="<%=boardDto.getContentNo()%>"> <input
          type="hidden" name="boardType" value="customerserviceboard">
        <!-- 게시판 유형 value필수설정-->
        <textarea name="commentContent" rows="4" cols="50"
          placeholder="댓글을 입력하세요"></textarea>
        <br> 
        <input type="submit" value="댓글 추가">
      </form>
      <h3>댓글</h3>
      <ul class="comments">
        <c:forEach var="comment" items="${commentList}">
          <li class="comment">
           <p>
                <strong>닉네임:</strong> <c:out value="${comment.nickname}" />
                &nbsp;&nbsp;&nbsp;&nbsp; <!-- 간격을 위해 공백 추가 -->
                <strong>작성일:</strong> <c:out value="${comment.commentCreDate}" />
                &nbsp;&nbsp;&nbsp;&nbsp;
                <strong>번호:</strong> <c:out value="${comment.commentNo}" />
            </p>
            <p id="commentContent-${comment.commentNo}">
               <br>
                <span class="comment-content"><c:out value="${comment.contentComment}" /></span>
            </p>
            <!-- 게시판 유형 필수설정-->
            <c:set var="contextPath" value="${pageContext.request.contextPath}" />
			<c:if test="${sessionScope.userDto.userNo == comment.userNo}">
				<!--버튼2개-->
				<div class="comment-buttons">
					<button class="deleteButton" onclick="confirmDelete(${comment.commentNo}, ${boardDto.getContentNo()} , 'customerserviceboard')">삭제</button>
					<button class="editButton" onclick="openEditForm(${comment.commentNo})">수정</button>
           		 </div>
           </c:if>
            <!-- 수정 폼 -->
            <div id="editForm-${comment.commentNo}" class="edit-form">
              <form action="<%=request.getContextPath()%>/editComment"
                method="post" onsubmit="return validateForm(this);">
                <input type="hidden" name="commentNo"
                  value="${comment.commentNo}"> <input
                  type="hidden" name="boardType" value="customerserviceboard">
                <!-- 게시판 유형 value필수설정-->
                <input type="hidden" name="contentNo" value="<%=boardDto.getContentNo()%>">
                <textarea name="commentContent" rows="3">${comment.contentComment}</textarea>
                <input type="submit" value="수정 완료">
              </form>
            </div>      
    
          </li>
        </c:forEach>
      </ul>
     
      <%
      }
      %>
      <a href="<%=request.getContextPath()%>/board/customerserviceboard/list"
        class="back-link">목록으로 돌아가기</a>
    </div>
    <jsp:include page="/jsp/common/footer.jsp" />
  </div>
  <!-- 		헤더고정용 -->
</body>
</html>
