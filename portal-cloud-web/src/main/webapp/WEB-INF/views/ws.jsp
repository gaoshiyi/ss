<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="com.sstc.hmis.portal.common.base.PropertyUtil"%>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<% String commonResUrl = PropertyUtil.COMMON_RESOURCES_URL;
   String ctx = request.getContextPath();
%>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>广播式WebSocket</title>
    <script src="<%=commonResUrl%>/jquery/jquery-1.8.1.min.js"></script>
    <script src="<%=ctx%>/resources/js/ws/sockjs.min.js"></script>
    <script src="<%=ctx%>/resources/js/ws/stomp.js"></script>
    <script src="<%=ctx%>/resources/js/ws/stomp.subscribe.js"></script>
</head>
<body>
<noscript><h2 style="color: #e80b0a;">Sorry，浏览器不支持WebSocket</h2></noscript>
<div>
    <div>
    	<label>主题</label><input type="text" id="topic" value="/topic/test"/>
        <button id="connect" onclick="connect();">连接</button>
        <button id="disconnect" disabled="disabled" onclick="disconnect();">断开连接</button>
    </div>

    <div id="conversationDiv">
<!--         <label>输入你的名字</label><input type="text" id="name"/> -->
<!--         <button id="sendName" onclick="sendName();">发送</button> -->
        <p id="response"></p>
    </div>
</div>
<script type="text/javascript">

	var client;
	
	function connect(){
		var topic = $("#topic").val();
		
		client = new StomClient('<%=ctx%>/ws/endpoint', topic, function onConnect(){
			 $("#response").append("连接成功！"  + "<br>");
			 setConnected(true);
		},function onError(){
            $("#response").append("连接失败!" + "<br>");
		},function onMessage(data){
            $("#response").append(data.message  + "<br>");
		});
		client.connect();
	}
	
	function disconnect(){
		client.disconnect(function(){
            $("#response").append("断开链接!" + "<br>");
			setConnected(false);
		});
	}
	
	function setConnected(connected) {
		document.getElementById("connect").disabled = connected;
		document.getElementById("disconnect").disabled = !connected;
	}

</script>
</body>
</html>