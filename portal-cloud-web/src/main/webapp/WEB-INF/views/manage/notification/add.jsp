<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
<title>消息通知</title>
<form id = "addForm" class="form-horizontal notice-form" method="post" action="" autocomplete="off">
<input id="add_id" name="id" type="hidden" value="${notice.id}">
	<div class="row">
		<div class="control-group span7">
			<label class="control-label control-extent control-extent2"><s>*</s>类型：</label>
			<div class="control-container control-container-extent control-container-extent2">
				<div class="">
					<select id ="add_type" class="choose input-full" data-placeholder="选择类型" data-rules="{required:true}">
						<option value="">选择类型</option>
						<c:forEach items="${msgNoticeLst}" var="tower" varStatus="status">
								<option value="${tower.code}" <c:if test="${tower.code == notice.type}">selected="selected"</c:if>>${tower.name}</option>
					   	</c:forEach>
					</select>
				</div>
			</div>
		</div>
		<div class="control-group span12">
			<label class="control-label control-extent2"><s>*</s>有效时间：</label>
			<div class="control-container control-container-extent2 clearfix">
				<div class="input-box-half">
					<div class="input-box-right">
						<div class="input-box has-btn-dialog">
							<input class="control-text input-full calendar calendar-time" type="text" id="add_validStarttime" type="text" name="validStarttime"  value='<fmt:formatDate value="${notice.validStarttime}" pattern="yyyy/MM/dd HH:mm:ss"/>' data-rules="{required:true, dateMemberValid:0}">
							<button class="btn-dialog btn-calendar" type="button"></button>
						</div>
					</div>
				</div>
				<span class="unit">~</span>
				<div class="input-box-half">
					<div class="input-box-left">
						<div class="input-box has-btn-dialog">
							<input class="control-text input-full calendar calendar-time" type="text" id="add_validEndtime" type="text" name="validEndtime"  value='<fmt:formatDate value="${notice.validEndtime}" pattern="yyyy/MM/dd HH:mm:ss"/>' data-rules="{required:true, dateMemberValid:1}">
							<button class="btn-dialog btn-calendar" type="button"></button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%-- <div class="row">
		<div class="control-group span7">
			<label class="control-label control-extent control-extent2"><s>*</s>发布时间：</label>
			<div class="control-container control-container-extent control-container-extent2">
				<div class="">
					<div class="input-box has-btn-dialog">
						<input class="control-text input-full calendar calendar-time" type="text" id="add_publishTime" type="text" name=""  value="<fmt:formatDate value='${notice.publishTime}' pattern='yyyy/MM/dd HH:mm:ss'/>">
						<button class="btn-dialog btn-calendar" type="button"></button>
					</div>
				</div>
			</div>
		</div>
	</div> --%>
	<div class="row">
		<div class="control-group span19">
			<label class="control-label control-extent control-extent2"><s>*</s>标题：</label>
			<div class="control-container control-container-extent control-container-extent2">
				<div class="input-box">
					<input id = "add_title"  class="control-text input-full" type="text" name="" value="${notice.title }" data-rules="{required:true}">
				</div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="control-group span19">
			<label class="control-label control-extent control-extent2"><s>*</s>概要：</label>
			<div class="control-container control-container-extent control-container-extent2 control-container-area">
				<div class="input-box">
					<textarea id = "add_summary"  class="area input-full" name="" data-rules="{required:true}" value = "${notice.summary }"></textarea>
				</div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="control-group span19">
			<label class="control-label control-extent control-extent2">详情：</label>
			<div class="control-container control-container-extent control-container-extent2 control-container-area">
				<div class="input-box">
					<textarea id = "add_detail" class="area input-full" name="" value = "${notice.detail }"></textarea>
				</div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="control-group span19">
			<label class="control-label control-extent control-extent2">是否发布：</label>
			<div class="control-container control-container-extent control-container-extent2">
				<label class="radio pull-left"><input type="radio" name="add_ispress" value="1" <c:if test="${notice.publishStatus==1 or notice.publishStatus == null}">checked</c:if> >是</label>
				<label class="radio pull-left ml10"><input type="radio" name="add_ispress" value="0" <c:if test="${notice.publishStatus==0}">checked</c:if>>否</label>
			</div>
		</div>
	</div>
</form>

<script>
chooseConfig();
function validateDate() {
	var validStarttime = $("#add_validStarttime").val(); // 开始时间
	var validEndtime = $("#add_validEndtime").val(); // 结束时间
	if (null != validStarttime || '' != validStarttime)
	{
		validStarttime = new Date(validStarttime);
	}
	if (null != validEndtime || '' != validEndtime)
	{
		validEndtime = new Date(validEndtime);
	}
	if (null != validStarttime && undefined != validStarttime 
			&& null != validEndtime && undefined != validEndtime)
	{
		if(validStarttime > validEndtime){
			$("#add_validEndtime").addClass('bui-form-field-error').next().remove();
			$("#add_validEndtime").after('<span class="valid-text"><span class="estate error"><span class="x-icon x-icon-mini x-icon-error">!</span><em>结束日期不能小于开始日期！</em></span></span>');
			return false;
		}
	}
	return true;
}
</script>