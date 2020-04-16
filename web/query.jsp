<%--
  Created by IntelliJ IDEA.
  User: csont
  Date: 2020.04.10.
  Time: 15:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Query</title>
    <form class="form-horizontal" name="queryForm" method="post" action="query">
        <fieldset>

            <!-- Form Name -->
            <legend>Query</legend>

            <!-- Search input-->
            <div class="form-group">
                <label class="col-md-4 control-label" for="label">Label to query</label>
                <div class="col-md-4">
                    <input id="label" name="label" type="search" placeholder="1081544185" class="form-control input-md">
                </div>
            </div>

            <!-- Multiple Radios (inline) -->
            <div class="form-group">
                <label class="col-md-4 control-label" for="radios">Query type</label>
                <div class="col-md-4">
                    <label class="radio-inline" for="radios-0">
                        <input type="radio" value="justPictures" name="param" id="radios-0" value="1">
                        Pictures
                    </label>
                    <label class="radio-inline" for="radios-1">
                        <input type="radio" value="justUserParameters" name="param" id="radios-1" value="2">
                        User Parameters
                    </label>
                    <label class="radio-inline" for="radios-2">
                        <input type="radio" value="justPrograms" name="param" id="radios-2" value="3">
                        Programs
                    </label>
                    <label class="radio-inline" for="radios-3">
                        <input type="radio" value="" name="param" id="radios-3" value="4">
                        All user all information
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

</head>
<body>

</body>
</html>
