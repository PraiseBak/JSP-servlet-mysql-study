<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>회원가입</title>
</head>
<body>
<h2>회원가입/h2>

<form action="/member" method="post">
    <label for="username">사용자 이름:</label><br>
    <input type="text" id="username" name="username"><br>
    <label for="password">비밀번호:</label><br>
    <input type="password" id="password" name="password"><br><br>
    <input type="submit" value="로그인">
</form>
</body>
</html>