<%@ page import="hello.service.UserSessionManager" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>게시판 목록2</title>
</head>
<body>
<h1>게시판 목록</h1>
<a href="/board/view">커뮤니티</a> <!-- 게시물 생성 버튼 -->
<a href="/member">로그인</a>
<a href="/member?mode=signup">회원가입</a>

<%
    String username = UserSessionManager.getInstance().getUsername(request.getSession().getId());
    System.out.println(username + "," + request.getSession().getId());
    // 사용자 정보 출력
    if (username != null) {
%>
<p>현재 로그인된 사용자: <%= username %></p>
<%
    }
%>

<table border="1">
    <thead>
    <tr>
        <th>번호</th>
        <th>제목</th>
        <th>작성자</th>
        <th>작성일</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="board" items="${boardList}">
        <tr>
            <td>${board.id}</td>
            <td>${board.title}</td>
            <td>${board.author}</td>
            <td>${board.createdDate}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
