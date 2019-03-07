<%--
  Created by IntelliJ IDEA.
  User: xiaoxiong
  Date: 2019/3/3
  Time: 17:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>websocket javascript echo client</title>
    <script type="text/javascript">
        var echo_websocket;
        var output;
        function init() {
            output=document.getElementById("output");

        }

        function send_echo() {
            var wsUri = "ws://127.0.0.1:8080/programmaticEcho";
            writeToScreen("Connecting to "+wsUri);
            echo_websocket= new WebSocket(wsUri);
            echo_websocket.onopen=function (evt) {
                writeToScreen("connected!");
                var textID = document.getElementById("textId");
                doSend(textID.value);
            }

            echo_websocket.onmessage=function (evt) {
                writeToScreen("Recevied message :"+evt.data);
                //echo_websocket.close();
            }

            echo_websocket.onerror=function (evt) {
                writeToScreen('<span style="color:red;"> ERROR </span> '+evt.toString());
                echo_websocket.close();
            }

        }

        function doSend(message) {
                echo_websocket.send(message);
                writeToScreen("send message :"+message);
        }

        function writeToScreen(message) {

            var pre = document.createElement("p");
            pre.style.wordWrap="break-word";
            pre.innerHTML=message;
            output.appendChild(pre);
        }


        window.addEventListener("load",init,false);

    </script>

</head>
<body>

<h1>Echo Server</h1>
 <div style="text-align:left;">
     <form action="">
         <input onclick="send_echo()" type="button" value="press to send">
         <input id="textId" name="textId" type="text" value="Hello WebSocket">
     <br>
     </form>
 </div>

<div id="output">

</div>

</body>
</html>
