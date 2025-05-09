
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>excelUpload</title>
</head>
<body>
    <h2>엑셀 파일 업로드</h2>
    <form action="excel/upload.do" method="post" enctype="multipart/form-data">
        <input type="file" name="excelFile" />
        <input type="submit" value="업로드" />
    </form>
</body>
</html>
