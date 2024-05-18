<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>게시판 목록</title>
</head>
<body>
<h1>게시판 목록</h1>
<a href="/board/add">게시물 생성</a> <!-- 게시물 생성 버튼 -->
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
