<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<form class="form-horizontal approve-form" id="approveForm" method="post" action="" autocomplete="off" data-plus-as-tab="true">
	<div class="control-item">
		<div class="control-group">
			<label class="control-label control-extent"><s>*</s>选择模板：</label>
			<div class="control-container control-container-extent">
				<select class="choose input-full" id="wfPrfName" name="wfPrfName" data-placeholder="选择模板" data-maxheight="200" data-rules="{required:true}">
					<option value="">选择模板</option>
					<option value="register">注册审批流程模板</option>
					<option value="wardProcess">退房查房流程模板</option>
				</select>
			</div>
		</div>
	</div>
	<div class="control-item">
		<div class="control-group">
			<label class="control-label control-extent"><s>*</s>任务节点：</label>
			<div class="control-container control-container-extent">
				<select class="choose input-full" id="wfTaskKey" name="wfTaskKey" data-placeholder="选择任务节点" data-maxheight="170" data-rules="{required:true}">
					<option value="">选择任务节点</option>
				</select>
			</div>
		</div>
	</div>
</div>

	<div class="control-item">
		<div class="control-group">
			<label class="control-label control-extent"><s>*</s>审批策略：</label>
			<div class="control-container control-container-extent">
				<select class="choose input-full" id="type" name="type" data-placeholder="选择审批策略" data-maxheight="135" data-rules="{required:true}">
					<option value="">选择审批策略</option>
					<option value="01">按人员</option>
					<option value="02">按职位</option>
					<option value="04">按工作圈</option>
				</select>
			</div>
		</div>
	</div>
	<div class="control-item">
		<div class="control-group">
			<label class="control-label control-extent"><s>*</s>审批对象：</label>
			<div class="control-container control-container-extent">
				<div class="input-box has-btn-dialog">
					<input id="auditId" type="hidden" name="auditId" value="" data-rules="{required:true}">
					<input id="auditName" class="control-text input-full" readonly="readonly" type="text" name="auditName" value="" placeholder="审批对象" data-rules="{required:true}">
					<button class="btn-dialog" id="btnChoose" type="button"></button>								    
				</div>
			</div>
		</div>
	</div>
</form>

<script>
chooseConfig();
var selectedItems = [];
var selectedItemNames = [];
$("#wfPrfName").on("change",function(){
	var wfPrfNameSel = $('#wfPrfName').val();
	var html = ["<option value=\"\">选择任务节点/名称</option>"];
	if(wfPrfNameSel === 'register'){
		html.push("<option value=\""+'usertask1'+"\">"+'注册审批抢单'+"</option>");
		$("#wfTaskKey").html(html.join("\r\n"));
	}else if(wfPrfNameSel === 'wardProcess'){
		html.push("<option value=\""+'usertask1'+"\">"+'查房服务员抢单'+"</option>");
		$("#wfTaskKey").html(html.join("\r\n"));
	}else{
		$("#wfTaskKey").html("<option value=\"\">选择任务节点</option>");
	}
	$("#wfTaskKey").trigger('chosen:updated');
});

$("#type").on("change",function(){
	$("#auditId").val('').trigger('change');
	$("#auditName").val('').trigger('change');
	selectedItems = []
	selectedItemNames = [];
	gridClone = {};
});
</script>