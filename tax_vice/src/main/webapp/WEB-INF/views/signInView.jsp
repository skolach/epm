<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>SignUp</title>
</head>

<body>
    <script>
        var check = function () {
            if (document.getElementById('new_password').value ==
                document.getElementById('confirm_password').value) {
                document.getElementById('message').style.color = 'green';
                document.getElementById('message').innerHTML = 'matching';
                document.getElementById('submit').disabled = false;
            } else {
                document.getElementById('message').style.color = 'red';
                document.getElementById('message').innerHTML = 'not matching';
                document.getElementById('submit').disabled = true;
            }
        }
    </script>

<jsp:include page="_menu.jsp"></jsp:include>
<jsp:include page="_error.jsp"></jsp:include>
<div id="signUp">
    <div class="container">
        <div id="login-row" class="row justify-content-center align-items-center">
            <div id="signUp-column" class="col-md-6">
                <div id="signUp-box" class="col-md-12">
                    <form id="signUp-form" class="form" action="${pageContext.request.contextPath}/signIn" method="post">
                        <input type="hidden" name="redirectId" value="${param.redirectId}" />
                        <h3 class="text-center text-info">Sign up</h3>
                        <div class="form-group">
                            <label for="new_name" class="text-info">Username:</label><br>
                            <input type="text" name="new_name" id="new_name" class="form-control">
                        </div>
                        <div class="form-group">
                            <label for="new_login" class="text-info">Login:</label><br>
                            <input type="text" name="new_login" id="new_login" class="form-control">
                        </div>
                        <div class="form-group">
                            <label for="new_password" class="text-info">Password:</label><br>
                            <input type="password" name="new_password" id="new_password" class="form-control" onkeyup="check();">
                        </div>
                        <div class="form-group">
                            <label for="confirm_password" class="text-info">Confirm password:</label><br>
                            <input type="password" name="confirm_password" id="confirm_password" class="form-control" onkeyup="check();">
                            <span id="message"></span>
                        </div>
                        <div class="form-group">
                            <button disabled type="submit" name="submit" class="btn btn-primary" value="submit" id="submit">Submit</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<p style="color: red;">${errorString}</p>

</body>

</html>