<%@ page import="hello.entity.Board" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>게시판 목록2</title>
</head>
<body>
<h1>게시판 목록</h1>
<a href="/board/add">게시물 생성</a> <!-- 게시물 생성 버튼 -->
<a href="/member">로그인</a>
<a href="/member?mode=signup">회원가입</a>

<table border="1">
    <thead>
    <tr>
        <th>번호</th>
        <th>제목</th>
        <th>내용</th>
        <th>작성자</th>
    </tr>
    </thead>
    <tbody>
    <% List<Board> boardList = (List<Board>)request.getAttribute("boardList");
        if (boardList != null) {
            for (Board board : boardList) { %>
    <tr>
        <td><%= board.getId() %></td>
        <td><%= board.getTitle() %></td>
        <td><%= board.getContent() %></td>
        <td><%= board.getMember().getUsername()%></td>
    </tr>
    <% }
    } %>
    </tbody>
</table>
</body>
</html>
