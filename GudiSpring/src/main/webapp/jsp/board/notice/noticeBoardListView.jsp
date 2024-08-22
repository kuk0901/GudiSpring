<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="EUC-KR">
    <title>�������� �Խ���</title>
    <link rel="stylesheet" type="text/css" 
    href="${pageContext.request.contextPath}/css/board/noticeboard/noticeBoardList.css">
       <link rel="stylesheet" type="text/css" 
    href="${pageContext.request.contextPath}/css/common/common.css"> 
    <script defer
  src="${pageContext.request.contextPath}/js/movePage/movePageFncs.js"></script>

    
  
</head>
<body>

 <jsp:include page="/jsp/common/header.jsp" />
 
 <div id="main-container">
    <h1>�������� ���</h1>
    <table>
        <thead>
            <tr>
                <th>��ȣ</th>
                <th>����</th>
                <th>����</th>
                <th>�ۼ���</th>
                <th>�ۼ���</th>
                <th>������</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="notice" items="${noticeList}">
                <tr>
                    <td>${notice.contentNo}</td>
                    <td><a href="${pageContext.request.contextPath}/board/notice/detail?contentNo=${notice.contentNo}">${notice.contentSubject}</a></td>
                    <td>${notice.contentText}</td>
                     <td>${notice.nickname}</td>
                    <td>${notice.contentCreDate}</td>
                    <td>${notice.contentUpdateDate}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>   
    </div>
     <jsp:include page="/jsp/common/footer.jsp" />
</body>
</html>
