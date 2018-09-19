<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<form class="form-horizontal bui-form-horizontal bui-form bui-form-field-container" method="post" action='<c:url value="/employee/strateryInfoSave"/>' autocomplete="off" data-plus-as-tab="true">
	<div class="row control-indent control-employee control-stratery">
		<div class="row-fluid">
			<div class="control-group span24">
				<!-- 工作员工的Id -->
				<input type="hidden" value="${staffIds}" id="staffIds" name="staffId">
				<label class="control-label control-extent">登录酒店：</label>
				<div class="control-container control-container-extent">
					<span class="control-form-text nowrap" title="${groupHotel.name }">${groupHotel.name }</span>
				</div>
			</div>
		</div>
		<input type="hidden" name="workHotelId" id="workHotelId" value="${groupHotel.id }">
		<div class="row-fluid bui-form-group x-relative" data-rules="{dateRange : true}">
			<div class="control-group span12">
				<div class="mr5">
					<label class="control-label control-extent">开始日期：</label>
					<div class="control-container control-container-extent">
						<div class="input-box has-btn-dialog">
							<input class="control-text input-full calendar" type="text" name="beginDate" value="<fmt:formatDate value="${staffHotel.beginDate}" type="both" pattern="yyyy/MM/dd"/> " id="beginDate" readonly>
							<button class="btn-dialog btn-calendar" type="button"></button>
						</div>
					</div>
				</div>
			</div>
			<div class="control-group span12">
				<div class="ml5">
					<label class="control-label control-extent">结束日期：</label>
					<div class="control-container control-container-extent">
						<div class="input-box has-btn-dialog">
							<input class="control-text input-full calendar" type="text" name="endDate" value="<fmt:formatDate value="${staffHotel.endDate}" type="both" pattern="yyyy/MM/dd"/> " id="endDate" readonly>
							<button class="btn-dialog btn-calendar" type="button"></button>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="row-fluid bui-form-group x-relative" data-rules="{dateRange : [true,'结束时间不能小于开始时间！']}">
			<div class="control-group span12">
				<div class="mr5">
					<label class="control-label control-extent">开始时间1：</label>
					<div class="control-container control-container-extent">
						<div class="input-box">
							<input class="control-text input-full time-picker" type="text" name="timeInterval[0]" value="${staffHotel.timeInterval[0]}" id="timeInterval0" data-rules="{time:'hh:mm'}">
						</div>
					</div>
				</div>
			</div>
			<div class="control-group span12">
				<div class="ml5">
					<label class="control-label control-extent">结束时间1：</label>
					<div class="control-container control-container-extent">
						<div class="input-box align-right">
							<input class="control-text input-full time-picker Hunter-align-right" type="text" name="timeInterval[1]" value="${staffHotel.timeInterval[1]}" id="timeInterval1" data-rules="{time:'hh:mm'}">
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="row-fluid bui-form-group x-relative" data-rules="{dateRange : [true,'结束时间不能小于开始时间！']}">
			<div class="control-group span12">
				<div class="mr5">
					<label class="control-label control-extent">开始时间2：</label>
					<div class="control-container control-container-extent">
						<div class="input-box">
							<input class="control-text input-full time-picker" type="text" name="timeInterval[2]" value="${staffHotel.timeInterval[2]}" id="timeInterval2" data-rules="{time:'hh:mm'}">
						</div>
					</div>
				</div>
			</div>
			<div class="control-group span12">
				<div class="ml5">
					<label class="control-label control-extent">结束时间2：</label>
					<div class="control-container control-container-extent">
						<div class="input-box align-right">
							<input class="control-text input-full time-picker Hunter-align-right" type="text" name="timeInterval[3]" value="${staffHotel.timeInterval[3]}" id="timeInterval3" data-rules="{time:'hh:mm'}">
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="row-fluid bui-form-group x-relative" data-rules="{dateRange : [true,'结束时间不能小于开始时间！']}">
			<div class="control-group span12">
				<div class="mr5">
					<label class="control-label control-extent">开始时间3：</label>
					<div class="control-container control-container-extent">
						<div class="input-box">
							<input class="control-text input-full time-picker" type="text" name="timeInterval[4]" value="${staffHotel.timeInterval[4]}" id="timeInterval4" data-rules="{time:'hh:mm'}">
						</div>
					</div>
				</div>
			</div>
			<div class="control-group span12">
				<div class="ml5">
					<label class="control-label control-extent">结束时间3：</label>
					<div class="control-container control-container-extent">
						<div class="input-box align-right">
							<input class="control-text input-full time-picker Hunter-align-right" type="text" name="timeInterval[5]" value="${staffHotel.timeInterval[5]}" id="timeInterval5" data-rules="{time:'hh:mm'}">
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="row-fluid">
			<div class="control-group span24">
				<div class="control-container control-container-checkbox">
					<label class="checkbox checkbox-beauty <c:if test="${staffHotel.week[0] == 1}">bui-grid-row-selected</c:if>"><div class="bui-grid pull-left bui-grid-row-selected"><input class="x-grid-checkbox" type="checkbox" name="week[0]" value="1" id="week0" <c:if test="${staffHotel.week[0] == 1}">checked</c:if> ></div>周一</label>
					<label class="checkbox checkbox-beauty <c:if test="${staffHotel.week[1] == 2}">bui-grid-row-selected</c:if>"><div class="bui-grid pull-left bui-grid-row-selected"><input class="x-grid-checkbox" type="checkbox" name="week[1]" value="2" id="week1" <c:if test="${staffHotel.week[1] == 2}">checked</c:if> ></div>周二</label>
					<label class="checkbox checkbox-beauty <c:if test="${staffHotel.week[2] == 3}">bui-grid-row-selected</c:if>"><div class="bui-grid pull-left bui-grid-row-selected"><input class="x-grid-checkbox" type="checkbox" name="week[2]" value="3" id="week2" <c:if test="${staffHotel.week[2] == 3}">checked</c:if> ></div>周三</label>
					<label class="checkbox checkbox-beauty <c:if test="${staffHotel.week[3] == 4}">bui-grid-row-selected</c:if>"><div class="bui-grid pull-left bui-grid-row-selected"><input class="x-grid-checkbox" type="checkbox" name="week[3]" value="4" id="week3" <c:if test="${staffHotel.week[3] == 4}">checked</c:if> ></div>周四</label>
					<label class="checkbox checkbox-beauty <c:if test="${staffHotel.week[4] == 5}">bui-grid-row-selected</c:if>"><div class="bui-grid pull-left bui-grid-row-selected"><input class="x-grid-checkbox" type="checkbox" name="week[4]" value="5" id="week5" <c:if test="${staffHotel.week[4] == 5}">checked</c:if> ></div>周五</label>
					<label class="checkbox checkbox-beauty <c:if test="${staffHotel.week[5] == 6}">bui-grid-row-selected</c:if>"><div class="bui-grid pull-left bui-grid-row-selected"><input class="x-grid-checkbox" type="checkbox" name="week[5]" value="6" id="week6" <c:if test="${staffHotel.week[5] == 6}">checked</c:if> ></div>周六</label>
					<label class="checkbox checkbox-beauty <c:if test="${staffHotel.week[6] == 7}">bui-grid-row-selected</c:if>"><div class="bui-grid pull-left bui-grid-row-selected"><input class="x-grid-checkbox" type="checkbox" name="week[6]" value="7" id="week7" <c:if test="${staffHotel.week[6] == 7}">checked</c:if> ></div>周日</label>
					<label class="checkbox checkbox-beauty <c:if test="${staffHotel.limitType[0] == 0}">bui-grid-row-selected</c:if>"><div class="bui-grid pull-left bui-grid-row-selected"><input class="x-grid-checkbox" type="checkbox" name="limitType[0]" value="0" id="limitType0" <c:if test="${staffHotel.limitType[0] == 0}">checked</c:if> ></div>PC</label>
					<label class="checkbox checkbox-beauty <c:if test="${staffHotel.limitType[1] == 1}">bui-grid-row-selected</c:if>"><div class="bui-grid pull-left bui-grid-row-selected"><input class="x-grid-checkbox" type="checkbox" name="limitType[1]" value="1" id="limitType1" <c:if test="${staffHotel.limitType[1] == 1}">checked</c:if> ></div>手机</label>
				</div>
			</div>
		</div>
		<div class="row-fluid">
			<div class="control-group">
				<label class="control-label control-extent"><s>*</s>允许登录：</label>
				<div class="control-container control-container-extent">
					<label class="radio"><input type="radio" name="loginType" value="0" id="loginType0" <c:if test="${staffHotel.loginType == null || staffHotel.loginType == 0}">checked</c:if> >可以</label>
					<label class="radio ml10"><input type="radio" name="loginType" value="1" id="loginType1" <c:if test="${staffHotel.loginType == 1}">checked</c:if> >不可以</label>
				</div>
			</div>
		</div>
		<div class="row-fluid">
			<div class="control-group">
				<label class="control-label control-extent"><s>*</s>停用：</label>
				<div class="control-container control-container-extent">
					<label class="checkbox checkbox-beauty <c:if test="${staffHotel.isLimit == 0}">bui-grid-row-selected</c:if>"><div class="bui-grid pull-left"><input class="x-grid-checkbox" type="checkbox" name="isLimit" value="0" <c:if test="${staffHotel.isLimit == 0}">checked</c:if> id="isLimit" ></div></label>
				</div>
			</div>
		</div>
	</div>
</form>

<script>
$(function() {
	chooseConfig();
});
/*
 * 酒店下拉切换响应事件；
 */
$('#workHotelId').change(function(){ 
	var workHotelVal = $(this).children('option:selected').val();
	var staffIdVal = $('#staffIds').val();
	$.ajax({
		url : '<c:url value="/employee/strateryEdit"/>',
		type : 'post',
		data : {
			workHotelVal : workHotelVal,
			staffIdVal:staffIdVal
		},
		dateType : 'html',
		success : function(data, textStatus) {
			if(data == null || data == ''){
				return;
			}
			$('#beginDate').val(data.beginDateStr);
			$('#endDate').val(data.endDateStr);
			var timeIntervalArr = data.timeInterval;
			if(timeIntervalArr != null && timeIntervalArr != '' && timeIntervalArr.length > 0){
				$('#timeInterval0').val(timeIntervalArr[0]);
				$('#timeInterval1').val(timeIntervalArr[1]);
				$('#timeInterval2').val(timeIntervalArr[2]);
				$('#timeInterval3').val(timeIntervalArr[3]);
				$('#timeInterval4').val(timeIntervalArr[4]);
				$('#timeInterval5').val(timeIntervalArr[5]);
			}
			var weekArr = data.week;
			if(weekArr != null && weekArr != ''){
				for(var i = 0;i < 7;i++){
					if(i < weekArr.length){
						var weekVal = weekArr[i];
						if(weekVal != null && weekVal != ''){
							$('#week'+i+'').attr("checked",true);
						}else{
							$('#week'+i+'').attr("checked",false);
						}
					}else{
						$('#week'+i+'').attr("checked",false);
					}
					
				};
			}
			var limitVar = data.isLimit;
			if(limitVar == 0){
				$('#isLimit0').attr("checked",true);
				$('#isLimit1').attr("checked",false);
			}else if(limitVar == 1){
				$('#isLimit0').attr("checked",false);
				$('#isLimit1').attr("checked",true);
			}
			if(data.blockup == 1){
				$('#blockup').attr("checked",true);
			}
		}
	}); 
}) 
</script>