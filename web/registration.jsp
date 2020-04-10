<%--
  Created by IntelliJ IDEA.
  User: csont
  Date: 2020.01.13.
  Time: 22:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">

<script type="text/javascript">
    $(function checkForm(registrationFrom) {
        if (registrationFrom.email.value != registrationFrom.emailrpt.value)
        {
            alert("A két megadott e-mail cím nem egyezik meg!");
            registrationFrom.email.focus();
            return false;
        }
        else if(registrationFrom.password.value != registrationFrom.passwordrpt.value)
        {
            alert("A két megadott jelszó nem egyezik meg!");
            registrationFrom.password.focus();
            return false;
        }
        else
        {
            return true;
        }
    });
</script>

<head>
    <title>Regisztráció</title>
    <form class="form-horizontal" name="registrationFrom" method="post" action="registration">
        <fieldset>

            <!-- Form Name -->
            <legend style="text-align: center;">Regisztráció</legend>

            <!-- Text input-->
            <div class="form-group">
                <label class="col-md-4 control-label" for="firstname"></label>
                <div class="col-md-4">
                    <input id="firstName" name="firstName" type="text" placeholder="Kereszt név" class="form-control input-md" required="">

                </div>
            </div>

            <!-- Text input-->
            <div class="form-group">
                <label class="col-md-4 control-label" for="lastname"></label>
                <div class="col-md-4">
                    <input id="lastName" name="lastName" type="text" placeholder="Vezeték név" class="form-control input-md" required="">

                </div>
            </div>

            <!-- Text input-->
            <div class="form-group">
                <label class="col-md-4 control-label" for="email"></label>
                <div class="col-md-4">
                    <input id="email" name="email" type="text" placeholder="E-mail cím" class="form-control input-md" required="">

                </div>
            </div>

            <!-- Text input-->
            <div class="form-group">
                <label class="col-md-4 control-label" for="emailrpt"></label>
                <div class="col-md-4">
                    <input id="emailRpt" name="emailRpt" type="text" placeholder="E-mail cím megismétlése" class="form-control input-md" required="">

                </div>
            </div>

            <!-- Password input-->
            <div class="form-group">
                <label class="col-md-4 control-label" for="password"></label>
                <div class="col-md-4">
                    <input id="password" name="password" type="password" placeholder="Jelszó" class="form-control input-md" required="">

                </div>
            </div>

            <!-- Password input-->
            <div class="form-group">
                <label class="col-md-4 control-label" for="passwordrpt"></label>
                <div class="col-md-4">
                    <input id="passwordRpt" name="passwordRpt" type="password" placeholder="Jelszó megismétlése" class="form-control input-md" required="">

                </div>
            </div>

            <!-- Button (Double) -->
            <div class="form-group" >
                <label class="col-md-4 control-label" for="send"></label>
                <div class="col-md-8" align="center" style="text-align: center;">
                    <button id="send" name="send" class="btn btn-success" type="submit" onClick="return checkForm(this.parentNode);">Regisztráció</button>
                    <button id="erase" name="erase" class="btn btn-danger" type="reset">Űrlap törlése</button>
                </div>

                <p><div style="text-align: center;">By creating an account you agree to our <a href="#">Terms & Privacy</a>.</div></p>

            </div>

        </fieldset>
    </form>

</head>
<body>

</body>
</html>
