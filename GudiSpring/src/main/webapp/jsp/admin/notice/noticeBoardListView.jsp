<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>�������� �����ڿ�</title>
  <!-- Reset CSS -->
	    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/reset-css@5.0.2/reset.min.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/common/common.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/admin/notice/noticeBoardList.css">
 <link rel="stylesheet"
  href="${pageContext.request.contextPath}/css/common/admin.css" />
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
		<h1>�������� ���</h1>
		<!-- �۾��� ��ư -->
		<div class="buttons">
			<a href="${pageContext.request.contextPath}/admin/notice/add"
				class="btn-write">�۾���</a>
		</div>
		<table>
			<thead>
				<tr>
					<th>��ȣ</th>
					<th>����</th>
					<th>����</th>
					<th>�ۼ���</th>
					<th>�ۼ���</th>
					<th>������</th>
					<th>����</th>

				</tr>
			</thead>
			<tbody>
				<c:forEach var="notice" items="${noticeList}">
					<tr>
						<td>${notice.contentNo}</td>
						<td><a
							href="${pageContext.request.contextPath}/admin/notice/detail?contentNo=${notice.contentNo}"
							class="abtn">${notice.contentSubject}</a></td>
						<td>${notice.contentText}</td>
						<td>${notice.nickname}</td>
						<td>${notice.contentCreDate}</td>
						<td>${notice.contentUpdateDate}</td>
						<td>
							<!-- ���� ��ư --> <a
							href="${pageContext.request.contextPath}/admin/notice/edit?contentNo=${notice.contentNo}"
							class="abtn">����</a> <!-- ���� ��ư --> <a
							href="${pageContext.request.contextPath}/admin/notice/delete?contentNo=${notice.contentNo}"
							class="abtn">����</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	
</body>
</html>
