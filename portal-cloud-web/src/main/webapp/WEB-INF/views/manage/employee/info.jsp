<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!-- 个人信息 -->
<div class="personal">
	<form class="form-horizontal" method="post" action="<c:url value="/employee/baseInfoSave"/>" autocomplete="off" data-plus-as-tab="true">
		<input type="hidden" value="${staff.id}" name="id" id="id">
		<input type="hidden" value="${staff.account}" name="account" id="account">
		<div class="clearfix">
			<div class="box-48per">
				<label class="control-label control-extent"><s>*</s>账号：</label>
				<div class="control-container control-container-extent">
					<span class="control-form-text input-full nowrap">${staff.account}</span>
				</div>
			</div>
			<div class="box-52per personal-diff">
				<label class="control-label control-extent">&nbsp;</label>
				<div class="control-container control-container-extent">
					<div class="level-box pull-left">
						<label class="radio"><input type="radio" class="bui-form-field-radio bui-form-check-field bui-form-field" name="sex" value="0" <c:if test="${staff.sex == 0 }">checked</c:if> aria-disabled="false">男</label>
						<label class="radio ml5"><input type="radio" class="bui-form-field-radio bui-form-check-field bui-form-field" name="sex" value="1" <c:if test="${staff.sex == 1 }">checked</c:if> aria-disabled="false">女</label>
						<label class="radio ml5"><input type="radio" class="bui-form-field-radio bui-form-check-field bui-form-field" name="sex" value="2" <c:if test="${staff.sex == 2 }">checked</c:if>aria-disabled="false">未知</label>
					</div>
				</div>
			</div>
		</div>
		<div class="clearfix">
			<div class="box-48per">
				<label class="control-label control-extent"><s>*</s>姓名：</label>
				<div class="control-container control-container-extent">
					<div class="input-box">
						<input class="control-text input-full" type="text" value="${staff.familyName} ${staff.name}" name="name"  data-rules="{required:true}">
					</div>
				</div>
			</div>
			<div class="box-52per personal-diff">
				<label class="control-label control-extent">外文姓名：</label>
				<div class="control-container control-container-extent">
					<div class="input-box">
						<input class="control-text input-full" type="text" value="${staff.nameEn} ${staff.fmyEn}" name="nameEn">
					</div>
				</div>
			</div>
		</div>
		
		<div class="clearfix">
			<div class="box-48per">
				<label class="control-label control-extent">联系电话：</label>
				<div class="control-container control-container-extent">
					<div class="input-box">
						<input class="control-text input-full" type="text" value="${staff.contacts }" name="contacts">
					</div>
				</div>
			</div>
			<div class="box-52per personal-diff">
				<label class="control-label control-extent"><s>*</s>手机号码：</label>
				<div class="control-container control-container-extent">
					<div class="pull-left">
						<div class="input-box">
							<input class="control-text input-full" id="mobilePhone" type="text" value="${staff.mobilePhone }" name="mobilePhone"  data-rules="{required:true}">
						</div>
					</div>
					<!-- <div class="pull-left ml20">
						<label class="checkbox checkbox-beauty" id="lock">
							<div class="bui-grid pull-left bui-grid-clearfix-selected"><input class="x-grid-checkbox" type="checkbox"></div>锁
						</label>
					</div> -->
				</div>
			</div>
		</div>
		<div class="clearfix">
			<div class="box-48per">
				<label class="control-label control-extent">出生日期：</label>
				<div class="control-container control-container-extent">
					<div class="input-box has-btn-dialog">
						<input class="control-text input-full calendar" type="text" name="birthDate" value="<fmt:formatDate value="${staff.birthDate}" type="both" pattern="yyyy/MM/dd"/> " data-rules="{maxDate:'${date}'}" readonly>
						<button class="btn-dialog btn-calendar" type="button"></button>
					</div>
				</div>
			</div>
			<div class="box-52per personal-diff">
				<label class="control-label control-extent">学历：</label>
				<div class="control-container control-container-extent">
					<select class="choose chosen-align-right" name="degreesId" data-maxheight="162px">
						<c:forEach items="${degreeMap}" var="val">
							<option <c:if test="${fn:split(val.key, '+')[0] eq staff.degreesId}">selected</c:if> value="${val.key}">${val.value}</option>
						</c:forEach>
					</select>
				</div>
			</div>
		</div>
		<div class="clearfix">
			<label class="control-label control-extent">邮箱地址：</label>
			<div class="control-container control-container-extent">
				<div class="input-box">
					<input class="control-text input-full" type="text" value="${staff.email }" name="email">
				</div>
			</div>
		</div>
		<div class="clearfix">
			<div class="box-35per">
				<label class="control-label control-extent">国家：</label>
				<div class="control-container control-container-extent">
					<select class="choose-4 chosen-align-top" name="countryId" id="personCertCountry" data-maxheight="162px" data-placeholder="选择国家">
					</select>
				</div>
			</div>
			<div class="box-33per personal-diff3">
				<label class="control-label control-extent">省/州：</label>
				<div class="control-container control-container-extent">
					<select class="choose-4 chosen-align-top" name="provinceId" id="personCertProvince" data-maxheight="162px" data-placeholder="选择省/州">
					</select>
				</div>
			</div>
			<div class="box-32per personal-diff4">
				<label class="control-label control-extent">市：</label>
				<div class="control-container control-container-extent">
					<select class="choose-4 chosen-align-top chosen-align-right" name="cityId" id="personCertCity" data-maxheight="162px" data-placeholder="选择市">
					</select>
				</div>
			</div>
		</div>
		<div class="clearfix">
			<label class="control-label control-extent">详细地址：</label>
			<div class="control-container control-container-extent">
				<div class="input-box">
					<input class="control-text input-full" type="text" value="${staff.address }" name="address">
				</div>
			</div>
		</div>
		<div class="clearfix">
			<div class="box-35per">
				<label class="control-label control-extent">联系人1：</label>
				<div class="control-container control-container-extent">
					<div class="input-box">
						<input class="control-text input-full" type="text" value="${staff.emergency1 }" name="emergency1">
					</div>
				</div>
			</div>
			<div class="box-65per personal-diff2">
				<label class="control-label control-extent">联系人1电话：</label>
				<div class="control-container control-container-extent">
					<div class="input-box">
						<input class="control-text input-full" type="text" value="${staff.emergencyNo1 }" name="emergencyNo1">
					</div>
				</div>
			</div>
		</div>
		<div class="clearfix">
			<div class="box-35per">
				<label class="control-label control-extent">联系人2：</label>
				<div class="control-container control-container-extent">
					<div class="input-box">
						<input class="control-text input-full" type="text" value="${staff.emergency2 }" name="emergency2">
					</div>
				</div>
			</div>
			<div class="box-65per personal-diff2">
				<label class="control-label control-extent">联系人2电话：</label>
				<div class="control-container control-container-extent">
					<div class="input-box">
						<input class="control-text input-full" type="text" value="${staff.emergencyNo2 }" name="emergencyNo2">
					</div>
				</div>
			</div>
		</div>
	</form>
</div>
<script>

	$(function () {
	    chooseConfig();
	    $('#lock input').on('change',function(){
	    	if($(this).prop('checked')){
	    		$('#phoneNum').prop('disabled',true);
	    	}else{
	    		$('#phoneNum').prop('disabled',false);
	    	}
	    })
	});
	var areaSelectEmp = new areaSelect('personCertCountry', 'personCertProvince', 'personCertCity', '<c:url value="/common/getAreaData"/>');
	areaSelectEmp.init('${staff.countryCode}', '${staff.provinceCode}', '${staff.cityCode}');
	
</script>
