<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<form class="form-horizontal bui-form-horizontal bui-form bui-form-field-container" method="post" action='<c:url value="/workTeam/save"/>' autocomplete="off" data-plus-as-tab="true">
	<div class="row control-indent control-workgroup">
		<div class="control-group span8">
			<label class="control-label control-extent"><s>*</s>名称：</label>
			<div class="control-container control-container-extent">
				<div class="input-box">
					<!-- 判断新建或者修改 -->
					<input type="hidden" name="Id" value="${workTeam.id}">
					<input class="control-text input-full" type="text" name="name" value="${workTeam.name}" data-rules="{required:true}">
				</div>
			</div>
		</div>
		<div class="control-group span8">
			<label class="control-label control-extent">外文名称：</label>
			<div class="control-container control-container-extent">
				<div class="input-box">
					<input class="control-text input-full" type="text" name="englishName" value="${workTeam.englishName}">
				</div>
			</div>
		</div>
		<div class="control-group span5">
			<label class="control-label control-extent control-extent-diff"><s>*</s>分类：</label>
			<div class="control-container control-container-extent control-container-diff">
				<select class="choose chosen-align-right" data-placeholder="选择分类" data-maxheight="200px" name="typeId" data-rules="{required:true}">
					<option value="">选择分类</option>
					<c:forEach items="${typeSel}" var ="val">
		          		<option <c:if test="${workTeam.typeId eq val.key}">selected</c:if> value="${val.key}">${val.value}</option>
		        	</c:forEach>
				</select>
			</div>
		</div>
		<div class="control-group span8">
			<label class="control-label control-extent"><s>*</s>所属集团或酒店：</label>
			<div class="control-container control-container-extent">
				<select class="choose" data-maxheight="180px" data-placeholder="选择所属集团或酒店" name="belongId"  data-rules="{required:true}">
					<option value="">选择所属集团或酒店</option>
					<c:forEach items="${groupHotelList}" var="groupHotel">
						<option <c:if test="${workTeam.belongId eq groupHotel.id}">selected</c:if> value="${groupHotel.id}">${groupHotel.name}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="control-group span8">
			<label class="control-label control-extent">可申请加入：</label>
			<div class="control-container control-container-extent">
				<label class="checkbox checkbox-beauty <c:if test="${workTeam.applyJoin == 0}">bui-grid-row-selected</c:if>">
					<div class="bui-grid pull-left">
						<input class="x-grid-checkbox" type="checkbox" name="applyJoin" value="0" <c:if test="${workTeam.applyJoin == 0}">checked</c:if>>
					</div>
				</label>
			</div>
		</div>
		<div class="control-group span21">
			<label class="control-label control-extent">简介：</label>
			<div class="control-container control-container-extent control-container-area">
				<div class="input-box">
					<textarea class="control-text input-full synopsis" name="intro" value="${workTeam.intro}"></textarea>
				</div>
			</div>
		</div>
	</div>
</form>
<script>
	$(function () {
	    chooseConfig();
	});
</script>