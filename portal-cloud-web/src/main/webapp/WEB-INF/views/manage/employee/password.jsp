<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- 修改密码 -->
<div class="password">
	<form class="form-horizontal" method="post" action='<c:url value="employee/pwd/update"/>' autocomplete="off" data-plus-as-tab="true">
		<div class="clearfix">
			<div class="control-group">
				<label class="control-label control-extent"><s>*</s>账号：</label>
				<div class="control-container control-container-extent">
					<span class="control-form-text input-full nowrap">${sessionScope.SESSION_USER.account }</span>
				</div>
			</div>
		</div>
		<div class="clearfix">
			<div class="control-group">
				<label class="control-label control-extent"><s>*</s>原密码：</label>
				<div class="control-container control-container-extent">
					<div class="input-box">
						<input class="control-text input-full" type="password" value="" name="password" data-rules="{required:true}">
					</div>
				</div>
			</div>
		</div>
		<div class="clearfix">
			<div class="control-group">
				<label class="control-label control-extent"><s>*</s>新密码：</label>
				<div class="control-container control-container-extent">
					<div class="input-box">
						<input class="control-text input-full" type="password" value="" name="newPwd" id="newPwd" data-rules="{required:true,password:true}">
					</div>
				</div>
			</div>
		</div>
		<div class="clearfix">
			<div class="control-group">
				<label class="control-label control-extent"><s>*</s>再确认：</label>
				<div class="control-container control-container-extent">
					<div class="input-box">
						<input class="control-text input-full" type="password" value="" name="pwdConfirm" id="pwdConfirm" data-rules="{required:true,password:true}">
					</div>
				</div>
			</div>
		</div>
	</form>
</div>
<script>

BUI.use(['bui/form'],function(Form){
	Form.Rules.add({
		name:'password',
		msg:'密码由数字、字母构成，至少6位',
		validator: function(value,baseValue,formatMsg){
			var pPattern = /^.*(?=.{6,})(?=.*\d)(?=.*[a-zA-Z]).*$/;
			//var pPattern = /^.*(?=.{6,})(?=.*\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&*? ]).*$/;
			if(!pPattern.test(value)){
				return formatMsg;
			}
		}
	});
});

</script>
