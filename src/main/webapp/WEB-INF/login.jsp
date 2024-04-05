<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>Title</title>
</head>
<body>

<c:if test="${param.result == 'error'}">
  <h1>로그인 에러</h1>
</c:if>

<form action="/login" method="post">
  <input type="text" name="mid">
  <input type="text" name="mpw">
  <label>
    <input type="checkbox" name="auto">
    자동 로그인
  </label>
  <button type="submit">LOGIN</button>
</form>
</body>
</html>