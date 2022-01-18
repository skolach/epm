<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <style>
        p {
            text-align: center;
            font-size: 60px;
            margin-top: 0px;
        }
    </style>
    <script>

        function startCountDown() {
            
            var countDown = 10

            var x = setInterval(function () {
                
                countDown = countDown - 1;
                document.getElementById("countDownProgress").setAttribute("style","width:" + countDown +"0%");
                document.getElementById("countDownProgress").innerText = countDown + "s";
                if (countDown < 0) {
                    clearInterval(x);
                    document.getElementById("discard").click();
                }
            }, 1000);
        }

    </script>
    <title>Proposals</title>
</head>

<body onload="startCountDown();">
    <jsp:include page="_menu.jsp"></jsp:include>
    <jsp:include page="_error.jsp"></jsp:include>

    <div>
        <div class="progress">
            <div id="countDownProgress" class="progress-bar progress-bar-danger" role="progressbar"
                aria-valuenow="4" aria-valuemin="0" aria-valuemax="10" style="width:100%">
            </div>
        </div>
    </div>

    <div class="container" id="proposals">
        <h3>Proposals</h3>

        <form method="POST" action="proposalTask">

            <div class="col-sm-6">
                <table class="table table-striped">
                    <tr>
                        <th> </th>
                        <th>car number</th>
                        <th>description</th>
                        <th>timeToWait</th>
                        <th>price</th>
                    </tr>

                    <c:set var="theFirst" value="true" />
                    <c:forEach items="${proposals}" var="proposal">
                        <tr>
                            <c:choose>
                                <c:when test="${theFirst=='true'}">
                                    <td><input type="radio" name="proposal" value="${proposal.proposalId}"
                                            checked="checked"></td>
                                    <c:set var="theFirst" value="false" />
                                </c:when>
                                <c:otherwise>
                                    <td><input type="radio" name="proposal" value="${proposal.proposalId}"></td>
                                </c:otherwise>
                            </c:choose>
                            <td>${proposal.carNumber}</td>
                            <td>${proposal.description}</td>
                            <td>${proposal.timeToWait}</td>
                            <td>${proposal.price}</td>
                        </tr>
                    </c:forEach>
                </table>
                <br>
                <div class="btn-group">
                    <input class="btn btn-success" type="submit" name="Submit" value="Submit" id="submit" />
                    <input class="btn btn-danger" type="submit" name="Discard" value="Discard" id="discard" />
                </div>
            </div>
        </form>
    </div>
</body>

</html>