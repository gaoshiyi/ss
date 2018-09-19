<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%-- <%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> 
<!DOCTYPE html>
<html>
<head>
<title>消息通知</title>
<jsp:include page="../../common/header-2.0.0.jsp"></jsp:include>
<!-- page css -->
<link href="<c:url value="/resources/css/notification.css"></c:url>" rel="stylesheet">
</head> --%>
<input type ="hidden" id = "noticeId" value="${notice.id}">
<div class="form-horizontal notice-form">
	<div class="row">
		<div class="control-group span15">
			<label class="control-label control-extent">标题：</label>
			<div class="control-container control-container-extent">
				<span class="control-form-text input-full nowrap">${notice.title}</span>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="control-group span7">
			<label class="control-label control-extent">发布时间：</label>
			<div class="control-container control-container-extent">
				<span class="control-form-text input-full nowrap"><fmt:formatDate value="${notice.publishTime}" pattern="yyyy/MM/dd HH:mm:ss"/></span>
			</div>
		</div>
		<div class="control-group span7">
			<label class="control-label control-extent">发布人：</label>
			<div class="control-container control-container-extent">
				<span class="control-form-text input-full nowrap">${notice.publisher}</span>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="control-group span15 mb10">
			<label class="control-label control-extent">概要：</label>
			<div class="control-container control-container-extent control-container-auto clearfix">
				<div class="control-form-text input-full detail"><span>
				<!-- 您好！非常感谢使用YUDO智慧酒店管理系统，针对近期用户需求以及BUG，我司将于XXXX年XX月XX日晚上XX点XX分做系统升级，系统升级约需XX分钟。 -->
				${notice.summary}
				</span></div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="control-group span15">
			<label class="control-label control-extent">详情：</label>
			<div class="control-container control-container-extent control-container-auto clearfix">
				<div class="control-form-text input-full detail">
					<!-- <pre>升级注意事项如下：
1.升级时不影响正常操作使用，但可能会出现操作卡顿的现像。
2.升级完成后，请关闭浏览器重新登录系统,初次登入系统打开页面可能会稍慢，继续操作使用系统即可恢复为正常速度。
3.升级后如有紧急问题可拨打XXXXXX与我们紧急联系，值班工程师会马上处理升级后出现的问题；
4.具体升级详细信息请参考《XXXX年XX月XX日程序发版细功能更新清单》
5.请指负责此次升级事宜的酒店的负责人信息和联系电话</pre> -->
					<pre>${notice.detail}</pre>
				</div>
			</div>
		</div>
	</div>
</div>

<script>
var dialogNoticeHandle;
BUI.use(['bui/overlay', 'bui/form'], function(Overlay, Form) {
	/* 确认回执 ================================================================================= */
	window.dialogNoticeHandleInit = function() {
		dialogNoticeHandle  = new Overlay.Dialog({
			title: '确认回执',
			width: 360,
			height: 360,
			zIndex: 1078,
			closeAction:'destroy',
			loader: {
				url: '<c:url value="/sysMsgnotify/handle"/>',
				autoLoad: false, //不自动加载
				lazyLoad: false,
				callback: function() {
					var handleFormNode = $('#handleForm')[0];
					handleForm = new Form.HForm({
				        srcNode: handleFormNode,
				        autoRender: true
				    });	
				}
			},
			buttons: [{
				text: '保存 (S)',
				elCls: 'button button-primary key-S',
				handler: function() {
					//隐藏右下角tip
					notification.close();
					var noticeId = $("#noticeId").val();
					var accountId = $("#ruleId").val();
					var principal=$("#ruleId").find("option:selected").text();  //获取Select选择的Text 
					var phone = $("#phone").val();
					var depart = $("#hideDpt2").val();
					if (handleForm.isValid())
					{
						$.ajax({
				               url:'<c:url value="/sysMsgnotify/saveReceipt"/>',
				               type : "POST",  
				               datatype:"json",  
				               data : {operType : 0, 
				            	   	   noticeId : noticeId, 
				            	 	   accountId : accountId, 
				            	 	   principal : principal, 
				            	 	   depart : depart, 
				            	 	   phone : phone},  
				               async:false,
				               success : function(data, stats) {  
				                 	if(data.status == 200) {
				                 		BUI.Message.Alert("确认回执成功!", 'success');
				                 	}
				               }
				           });
						this.close();
					}
				}
			}, {
				text: '关闭 (Esc)',
				elCls: 'button key-Esc',
				handler: function() {
					this.close();
				}
			}]
		});
		dialogNoticeHandle.show();
	}
	/*  ================================================================================= */
});
</script>
