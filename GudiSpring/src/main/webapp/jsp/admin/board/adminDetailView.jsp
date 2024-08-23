<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Detail View</title>
<link rel="stylesheet"
   href="https://cdn.jsdelivr.net/npm/reset-css@5.0.2/reset.min.css">
<link rel="stylesheet" type="text/css" 
    href="${pageContext.request.contextPath}/css/common/common.css">
<link rel="stylesheet" type="text/css" 
    href="${pageContext.request.contextPath}/css/admin/board/adminDetailView.css">
<link rel="stylesheet"
  href="${pageContext.request.contextPath}/css/admin/nav.css" />
<script defer type="text/javascript"
  src="${pageContext.request.contextPath}/js/movePage/movePageFncs.js"></script>
<script defer type="text/javascript"
  src="${pageContext.request.contextPath}/js/admin/navFocus.js"></script>
</head>
<body>

<jsp:include page="/jsp/admin/nav.jsp"></jsp:include>

<div id="main-container">
    <h1>�Խñ� �󼼺���</h1>
    <table id="detailTable">
        <tr>
            <th class="detailHeader">�Խ���</th>
            <td class="detailCell">${boardDetail.boardInfoName}</td>
        </tr>
        <tr>
            <th class="detailHeader">����</th>
            <td class="detailCell">${boardDetail.contentSubject}</td>
        </tr>
        <tr>
            <th class="detailHeader">�ۼ���</th>
            <td class="detailCell">${boardDetail.nickname}</td>
        </tr>
        <tr>
            <th class="detailHeader">�ۼ���</th>
            <td class="detailCell">${boardDetail.contentCreDate}</td>
        </tr>
        <tr>
            <th class="detailHeader">������</th>
            <td class="detailCell">${boardDetail.contentUpdateDate}</td>
        </tr>	
        <tr>
            <th class="detailHeader">����</th>
            <td class="detailCell">${boardDetail.contentText}</td>
        </tr>
    </table>

    
     <!-- ��� ����Ʈ �߰� -->

    <ul id="comment-list" class="comments">
        <c:forEach var="comment" items="${commentList}">
            <li class="comment-item">
                <p><strong>�ۼ���:</strong> ${comment.nickname}</p>
                <p><strong>����:</strong> ${comment.contentComment}</p>
                <p><strong>�ۼ���:</strong> ${comment.commentCreDate}</p>
                <!-- ���� ��ư �߰� -->
                <form action="${pageContext.request.contextPath}/comment/delete" method="post">
                    <input type="hidden" name="commentNo" value="${comment.commentNo}">
                    <input type="hidden" name="contentNo" value="${boardDetail.contentNo}">
                    <button type="submit" class="comment-delete-btn" onclick="return confirm('������ �� ����� �����Ͻðڽ��ϱ�?')">��� ����</button>
                </form>
            </li>
        </c:forEach>
    </ul>

    <div id="admin-actions" class="actions">
        <a href="${pageContext.request.contextPath}/admin/board/list" class="btn view-btn">������� ���ư���</a>
        <form action="${pageContext.request.contextPath}/admin/board/delete" method="post" style="display:inline;">
            <input type="hidden" name="contentNo" value="${boardDetail.contentNo}">
            <button name="sxubmit" type="submit" class="btn delete-btn" onclick="return confirm('������ �����Ͻðڽ��ϱ�?')">����</button>
        </form>
    </div>
    
    
    
    
</div>


</body>
</html>
