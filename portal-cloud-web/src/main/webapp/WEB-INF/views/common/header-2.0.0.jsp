<%@page import="com.sstc.hmis.portal.common.base.PropertyUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% 
   String commonResUrl = PropertyUtil.COMMON_RESOURCES_URL;
   String ver = PropertyUtil.COMMON_RESOURCES_VER;
   String ctx = request.getContextPath();
%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<meta charset="UTF-8">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="format-detection" content="telephone=no, email=no">

<!-- bui css  -->
<link rel="stylesheet" href="<%=commonResUrl%>/bui/build/css/bs3/dpl-2.0.0-min.css?v=<%=ver %>">
<link rel="stylesheet" href="<%=commonResUrl%>/bui/build/css/bs3/bui-2.0.0-min.css?v=<%=ver %>">
<!-- plugin css  -->
<link rel="stylesheet" href="<%=commonResUrl%>/public/plugin/choose/css/choose.min.css?v=<%=ver %>">
<link rel="stylesheet" href="<%=commonResUrl%>/public/plugin/perfect-scrollbar/css/perfect-scrollbar.min.css?v=<%=ver %>">
<link rel="stylesheet" href="<%=commonResUrl%>/public/plugin/timepicker/css/jquery-timepicker.min.css?v=<%=ver %>">
<!-- public css  -->
<link rel="stylesheet" href="<%=commonResUrl%>/public/css/base-2.0.0-min.css?v=<%=ver %>">
 
<script src="<%=commonResUrl%>/jquery/jquery-1.8.1.min.js"></script>
<script src="<%=commonResUrl%>/vue/vue.min.js"></script>
<script src="<%=commonResUrl%>/bui/build/loader-min.js?v=<%=ver %>"></script>
<!-- plugin js  -->
<script src="<%=commonResUrl%>/public/plugin/choose/js/choose.jquery.min.js?v=<%=ver %>"></script>
<script src="<%=commonResUrl%>/public/plugin/perfect-scrollbar/js/perfect-scrollbar.jquery.min.js"></script>
<script src="<%=commonResUrl%>/public/plugin/emulatetab/plusastab.joelpurra.min.js?v=<%=ver %>"></script>
<script src="<%=commonResUrl%>/public/plugin/emulatetab/emulatetab.joelpurra.min.js?v=<%=ver %>"></script>
<script src="<%=commonResUrl%>/public/plugin/timepicker/js/jquery-timepicker.min.js?v=<%=ver %>"></script>
<!-- just for main  -->
<script src="<%=commonResUrl%>/public/plugin/slimscroll/jquery.slimscroll.min.js"></script>

<!-- public js  -->
<script src="<%=commonResUrl%>/public/js/common-min.js?v=<%=ver %>"></script>

<script type="text/javascript">
	JoelPurra.PlusAsTab.setOptions({ key: 13 });
	
	var hiddenGrpId = "${sessionScope.SESSION_USER.grpId }";
	var hiddenHtlId = "${sessionScope.SESSION_USER.hotelId }";
	var contextPath = "<%=request.getContextPath()%>";
	
	var ctx = window.location.origin + contextPath
	//document.domain = window.location.hostname.indexOf('sstcsoft') >= 0 ? 'sstcsoft.com' : window.location.hostname;

	$.ajaxSetup({
		xhrFields: {
	        withCredentials: true
		},
		headers: { // 默认添加请求头
	        "X-Requested-With": "XMLHttpRequest" 
	    } ,
		crossDomain: true,
		error: function(jqXHR, textStatus, errorMsg){ // 出错时默认的处理函数
	        // jqXHR 是经过jQuery封装的XMLHttpRequest对象
	        // textStatus 可能为： null、"timeout"、"error"、"abort"或"parsererror"
	        // errorMsg 可能为： "Not Found"、"Internal Server Error"等
	        var res = jqXHR.responseText;
	        if(typeof(res) != 'undefined'){
		        console.log( '发送AJAX请求到"' + this.url + '"时出错[' + jqXHR.status + ']：' + errorMsg );   
		        console.log( '发送AJAX请求到"' + this.url + '"时出错,服务器返回[' + res + ']：' );   
		        if(res){
		        	res = JSON.parse(res);
					var status = res.status;
					if(status == 599){
						window.top.location.href = document.location.origin;
						return;
					}
					if(status == 598){
						 BUI.Message.Alert(jqXHR.status + ':夜审中，请稍后再试...', function(){},'error');
						 return;
					}
		        }
			    BUI.Message.Alert(jqXHR.status + ':<spring:message code="EO035"/>', function(){},'error'); 
	        }
	    }
	});	
	
	String.prototype.format = function(args) {  
	    var result = this;  
	    if (arguments.length > 0) {      
	        if (arguments.length == 1 && typeof (args) == "object") {  
	            for (var key in args) {  
	                if(args[key]!=undefined){  
	                    var reg = new RegExp("({" + key + "})", "g");  
	                    result = result.replace(reg, args[key]);  
	                }  
	            }  
	        }else {
	            for (var i = 0; i < arguments.length; i++) {  
	                if (arguments[i] != undefined) {  
	                    //var reg = new RegExp("({[" + i + "]})", "g");//这个在索引大于9时会有问题  
	                    var reg = new RegExp("({)" + i + "(})", "g");  
	                    result = result.replace(reg, arguments[i]);  
	             }  
	          }  
	       }  
	   }  
	   return result;  
	}  
</script>
