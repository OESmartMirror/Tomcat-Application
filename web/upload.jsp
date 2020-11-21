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
    <title>Picture upload</title>
</head>
<body>
<center>
    <form method="post" action="uploadFile" enctype="multipart/form-data">
        <fieldset>
        <div class="col-md-4">
            <input id="label" name="label" type="text" placeholder="1081544185" class="form-control input-md">
        </div>

        Select file to upload: <input type="file" name="uploadFile" multiple  accept="image/jpeg"/>
        <br/><br/>
        <div class="form-group">
            <label class="col-md-4 control-label" for="button1id">Double Button</label>
            <div class="col-md-8">
                <button id="button1id" name="button1id" class="btn btn-success" type="submit">Upload</button>
                <button id="button2id" name="button2id" class="btn btn-danger" type="reset">Clear</button>
            </div>
        </div>
        </fieldset>
    </form>
</center>
</body>
</html>
