<%--
  Created by IntelliJ IDEA.
  User: csont
  Date: 2020.04.18.
  Time: 19:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>QR Code</title>
</head>
<body>

<form class="form-horizontal" name="queryForm" method="post" action="qrcode">
    <fieldset>

        <!-- Form Name -->
        <legend>Form Name</legend>

        <!-- Search input-->
        <div class="form-group">
            <label class="col-md-4 control-label" for="label">Search Input</label>
            <div class="col-md-4">
                <input id="label" name="label" type="search" placeholder="LABEL" class="form-control input-md" required="">

            </div>
        </div>

        <!-- Multiple Radios (inline) -->
        <div class="form-group">
            <label class="col-md-4 control-label" for="qrcode">Típus</label>
            <div class="col-md-4">
                <label class="radio-inline" for="qrcode-0">
                    <input type="radio" name="qrcode" id="qrcode-0" value="registration" checked="checked">
                    Registration
                </label>
                <label class="radio-inline" for="qrcode-1">
                    <input type="radio" name="qrcode" id="qrcode-1" value="login">
                    Login
                </label>

            </div>
        </div>

        <!-- Button (Double) -->
        <div class="form-group">
            <label class="col-md-4 control-label" for="button1id">Double Button</label>
            <div class="col-md-8">
                <button id="button1id" name="button1id" class="btn btn-success" type="submit">Query</button>
                <button id="button2id" name="button2id" class="btn btn-danger" type="reset">Clear</button>
            </div>
        </div>

    </fieldset>
</form>

</body>
</html>
