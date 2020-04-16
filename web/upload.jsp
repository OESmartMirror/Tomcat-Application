<%--
  Created by IntelliJ IDEA.
  User: csont
  Date: 2020.04.13.
  Time: 11:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Title</title>
</head>
<body>
<center>
    <form method="post" action="UploadServlet" enctype="multipart/form-data">
        Select file to upload: <input type="file" name="uploadFile" multiple  accept="image/jpeg"/>
        <br/><br/>
        <input type="submit" value="Upload" />
    </form>
</center>
</body>
</html>
