<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript" src="https://ajax.aspnetcdn.com/ajax/jquery/jquery-1.10.2.min.js"></script>
<script type="text/javascript"
        src="http://cdn.bootcss.com/bootstrap-select/2.0.0-beta1/js/bootstrap-select.js"></script>
<link rel="stylesheet" type="text/css"
      href="http://cdn.bootcss.com/bootstrap-select/2.0.0-beta1/css/bootstrap-select.css">

<html>
<head>
    <title>Chat System</title>
    <link rel="stylesheet" type="text/css" href="css/style1.css" id="style1"/>
    <link rel="stylesheet" type="text/css" href="css/style2.css" id="style2"/>
</head>
<body>


<script type="text/javascript">
    function refresh() {
        $.ajax({
            url: "BasicServlet",
            type: "GET",
            success: function (e) {
                console.log(e)
                document.getElementById("screen").value = e
            }
        });
    }

    function send() {
        $.ajax({
            url: "BasicServlet",
            type: "POST",
            data: $('#sendForm').serialize(),
            success: function (e) {
                console.log(e)
                refresh()
            }
        })
    }

    function clearChat() {
        $.ajax({
            url: "BasicServlet",
            type: "DELETE",
            data: $('#clearOrSave').serialize(),
            success: function () {
                alert("clear success")
            }
        })
        refresh()
    }

    window.onload = function () {
        setInterval(refresh, 5000);
        changeCss(1)
    }

    function changeCss(id) {
        if(id === 1) {
            document.getElementById('style1').disabled = false
            document.getElementById('style2').disabled = true
        } else {
            document.getElementById('style1').disabled = true
            document.getElementById('style2').disabled = false
        }
    }


</script>
<div>
    <label>
        Screen:
        <br/>
        <textarea id="screen" rows="20" cols="100" readonly>

</textarea>
    </label>
</div>
<form id="sendForm" action="BasicServlet" method="POST">
    <label>
        username:
        <input type="text" name="user">
    </label>
    <br/>
    <label>
        message:
        <input type="text" name="message">
    </label>
    <br/>
    <input type="button" value="send" onclick="send()">
</form>

<br/>
<div>
    <form id="clearOrSave" action="BasicServlet" method="GET">
        <label>
            from:
            <input type="datetime-local" name="from">
        </label>
        <br/>
        <label>
            to:
            <input type="datetime-local" name="to">
        </label>
        <br/>
        <label>
            format:
            <select name="format" form="clearOrSave">
                <option value="plain-text">plain-text</option>
                <option value="xml">xml</option>
            </select>
        </label>
        <br/>
        <label>
            <input type="submit" value="save as a file">
        </label>
        <br/>
        <label>
            <input type="button" value="clear" onclick="clearChat()">
        </label>
    </form>
</div>

<div id="changeStyle">
<button id="light" onclick="changeCss(1)">Light</button>
<button id="night" onclick="changeCss(2)">Night</button>
</div>
</body>
</html>
