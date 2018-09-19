<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<!-- <div id="tab" class="tab-organization"></div>
<div id="panel" class="panel-organization">
	<div class="hide"> -->
		<div class="tab-content panel-body pd0">
			<form class="form-horizontal bui-form-horizontal bui-form bui-form-field-container" method="post" action="" autocomplete="off" data-plus-as-tab="true">

				<!-- 基本信息 -->
				<div class="panel layout mb10">
					<h2 class="sub-title mb10">基本信息</h2>
					<div class="control-indent control-battalion">
						<div class="control-item clearfix mb10">
							<div class="control-group pull-left box-auto1">
								<label class="control-label control-label-auto">名称：</label>
								<div class="control-container pull-left">
									<span class="control-form-text nowrap input-full">${hotel.name }</span>
								</div>
							</div>
							<div class="control-group pull-left ml10 box-auto2">
								<label class="control-label control-label-auto">编码：</label>
								<div class="control-container pull-left">
									<span class="control-form-text nowrap input-full">${hotel.code }</span>
								</div>
							</div>
							<div class="control-group pull-left ml10 box-auto2">
								<label class="control-label control-label-auto">星级：</label>
								<div class="control-container pull-left">
									<span class="control-form-text nowrap input-full">${hotel.level }</span>
								</div>
							</div>
							<div class="control-group pull-left ml10">
								<label class="control-label control-label-auto">地址：</label>
								<div class="control-container pull-left">
									<span class="control-form-text nowrap input-full control-address" title="${hotel.address }">${hotel.address }</span>
								</div>
							</div>
						</div>
						<div class="control-item clearfix mb10">
							<div class="control-group pull-left box-auto1">
								<label class="control-label control-label-auto">法人：</label>
								<div class="control-container pull-left">
									<span class="control-form-text nowrap input-full">${hotel.legalPerson }</span>
								</div>
							</div>
							<div class="control-group pull-left ml10 box-auto2">
								<label class="control-label control-label-auto">酒店数：</label>
								<div class="control-container pull-left">
									<span class="control-form-text nowrap input-full">${hotel.maxHotel }</span>
								</div>
							</div>
							<div class="control-group pull-left ml10 box-auto2">
								<label class="control-label control-label-auto">注册资金：</label>
								<div class="control-container pull-left">
									<span class="control-form-text nowrap input-full">${hotel.registeredCapital }</span>
								</div>
							</div>
							<div class="control-group pull-left ml10">
								<label class="control-label control-label-auto">资产规模：</label>
								<div class="control-container pull-left">
									<span class="control-form-text nowrap input-full">${hotel.assetSize }</span>
								</div>
							</div>
						</div>
						<div class="control-item clearfix">
							<div class="control-group">
								<label class="control-label control-label-auto">简介：</label>
								<div class="control-container pull-left">
									<span class="control-form-text nowrap input-full">${hotel.intro }</span>
								</div>
							</div>
						</div>
					</div>
				</div>

				<!-- 联系人信息 -->
				<div class="panel layout mb10">
					<h2 class="sub-title mb10">联系人信息</h2>
					<div class="control-indent control-group control-battalion">
						<div class="control-item clearfix mb10">
							<div class="control-group pull-left box-auto1">
								<label class="control-label control-label-auto">联系人：</label>
								<div class="control-container pull-left">
									<span class="control-form-text nowrap input-full">${hotel.contacter }</span>
								</div>
							</div>
							<div class="control-group pull-left ml10">
								<label class="control-label control-label-auto">联系电话：</label>
								<div class="control-container pull-left">
									<span class="control-form-text nowrap input-full">${hotel.contactNo }</span>
								</div>
							</div>
						</div>
						<div class="control-item clearfix">
							<div class="control-group pull-left box-auto1">
								<label class="control-label control-label-auto">传真：</label>
								<div class="control-container pull-left">
									<span class="control-form-text nowrap input-full">${hotel.fax }</span>
								</div>
							</div>
							<div class="control-group pull-left ml10">
								<label class="control-label control-label-auto">邮箱地址：</label>
								<div class="control-container pull-left">
									<span class="control-form-text nowrap input-full">${hotel.email }</span>
								</div>
							</div>
						</div>
					</div>
				</div>
			</form>
		</div>
		
<!-- areaSelect -->
<script src="<c:url value="/resources/js/areaSelect.js"></c:url>"></script>
<script>
    var areaSelectGroup = new areaSelect('country', 'province','city','<c:url value="/common/getAreaData"/>','选择国家');
	//默认查询：001国家编码；0001省州编码；HF市编码
	areaSelectGroup.init('${hotel.countryId}','${hotel.provinceId}','${hotel.cityId}');
</script> 
