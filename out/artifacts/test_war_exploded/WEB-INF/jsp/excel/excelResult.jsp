<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Result</title>
</head>
<body>
<h2>업로드된 고객 정보</h2>
<table border="1">
    <tr>
        <th>고객명</th>
        <th>전화번호</th>
        <th>이메일</th>
        <th>가입일</th>
    </tr>
    <c:forEach var="customer" items="${customerList}">
        <tr>
            <td>${customer.name}</td>
            <td>${customer.phone}</td>
            <td>${customer.email}</td>
            <td>${customer.joinDate}</td>
        </tr>
    </c:forEach>
</table>
<p>
    ${error}
</p>
<a href="/test">← 다시 업로드</a>
</body>
</html>
