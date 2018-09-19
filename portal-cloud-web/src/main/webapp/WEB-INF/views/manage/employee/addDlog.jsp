 <%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div id="tabEmp"></div>
<div id="panelEmp">
	<div class="hide">
		<!-- 基本资料 start -->
		<div class="tab-content">
			<form class="form-horizontal bui-form-horizontal bui-form bui-form-field-container" method="post" action='<c:url value="/employee/baseInfoSave"/>' autocomplete="off" data-plus-as-tab="true" onchange="changeEmployee();" >
				<div class="row control-indent control-employee">
					<div class="span18">
						<div class="row">
							<div class="control-group span6">
								<input type="hidden" value="${id}" name="id" id="staffId">
								<input type="hidden" name="isDelFlag" id="isDelFlag">
								<input type="hidden" id="defaulHotelId">
								<label class="control-label control-extent"><s>*</s>帐号：</label>
								<div class="control-container control-container-extent">
									<div class="input-box">
										<c:choose>
											<c:when test="${empty staff.account || staff.account == ''}">  
												<input class="control-text input-full" type="text" name="account" data-rules="{required:true}" id="account">
												<input type="hidden" name="accountAft" id="accountAft">
											</c:when>
											<c:otherwise> 
												<input class="control-text input-full" type="text" name="account" value="${staff.account}" data-rules="{required:true}" disabled>
												<input class="control-text input-full" type="hidden" name="account" value="${staff.account}">
											</c:otherwise>
										</c:choose>
									</div>
								</div>
							</div>
							<div class="control-group span6">
								<label class="control-label control-extent"><s>*</s>姓名：</label>
								<div class="control-container control-container-extent">
									<div class="input-box">
										<input class="control-text input-full" type="text" name="name" value="${staff.name}" data-rules="{required:true}">
									</div>
								</div>
							</div>
							<div class="control-group span6">
								<label class="control-label control-extent">外文姓名：</label>
								<div class="control-container control-container-extent">
									<div class="input-box">
										<input class="control-text input-full" type="text" name="nameEn" value="${staff.nameEn} ${staff.fmyEn}">
									</div>
								</div>
							</div>
							<div class="control-group span6">
								<label class="control-label control-extent">收银帐号：</label>
								<div class="control-container control-container-extent">
									<div class="input-box">
										<input class="control-text input-full" type="text" name="cashAccount" value="${staff.cashAccount}" autocomplete="off">
									</div>
								</div>
							</div>
							<div class="control-group span6">
								<label class="control-label control-extent">收银密码：</label>
								<div class="control-container control-container-extent">
									<div class="input-box">
										<c:choose>
											 <c:when test="${empty id || id == ''}">  
											 	<input class="control-text input-full" type="password" name="cashPwd" value="${staff.cashPwd}" autocomplete="new-password">
											 </c:when>
											 <c:otherwise> 
											 	<c:if test="${empty staff.cashPwd || staff.cashPwd == ''}">
											 		<input class="control-text input-full" type="password" value="" autocomplete="new-password" id="cashPwdShow">
											 	</c:if>
											 	<c:if test="${not empty staff.cashPwd && staff.cashPwd != ''}">
													<input class="control-text input-full" type="password" value="******" autocomplete="new-password" id="cashPwdShow">											 		
											 	</c:if>
											 	<input class="control-text input-full" type="hidden" name="cashPwd" value="${staff.cashPwd}" autocomplete="new-password" id="cashPwdHidden">
											 </c:otherwise>
										</c:choose>	
									</div>
								</div>
							</div>
							<div class="control-group span6">
								<label class="control-label control-extent"><s>*</s>性别：</label>
								<div class="control-container control-container-extent" data-rules="{checkRange:[1,1]}" data-messages="{checkRange:'请选择性别'}">
									<c:if test="${empty staff.sex}">
										<label class="radio"><input type="radio" name="sex" value="0" checked>男</label>
										<label class="radio ml10"><input type="radio" name="sex" value="1">女</label>
									</c:if>
									<c:if test="${staff.sex == 0}">
										<label class="radio"><input type="radio" name="sex" value="0" checked>男</label>
										<label class="radio ml10"><input type="radio" name="sex" value="1">女</label>
									</c:if>
									<c:if test="${staff.sex == 1}">
										<label class="radio"><input type="radio" name="sex" value="0">男</label>
										<label class="radio ml10"><input type="radio" name="sex" value="1" checked>女</label>
									</c:if>
								</div>
							</div>
							<div class="control-group span6">
								<label class="control-label control-extent"><s>*</s>所属酒店：</label>
								<div class="control-container control-container-extent">
									<c:choose>
										<c:when test="${sessionScope.SESSION_USER.grpId ne sessionScope.SESSION_USER.hotelId }">
											<input type="hidden" name="defaultHotelId" value="${not empty staff.id? staff.defaultHotelId:sessionScope.SESSION_USER.hotelId }"/>
											<select class="choose" id="theHotel" data-placeholder="选择所属酒店"  data-rules="{required:true}" disabled >
												<c:forEach items="${allHotels}" var="val">
													<option <c:if test="${staff.hotelId eq val.key}">selected</c:if> value="${val.key}">${val.value}</option>
												</c:forEach>
											</select>
										</c:when>
										<c:otherwise>
											<select class="choose" id="theHotel" data-placeholder="选择所属酒店" name="defaultHotelId" data-rules="{required:true}" >
												<c:choose>
													<c:when test="${not empty staff.id}">
														<c:forEach items="${allHotels}" var="val">
															<option <c:if test="${staff.hotelId eq val.key}">selected</c:if> value="${val.key}">${val.value}</option>
														</c:forEach>
													</c:when>
													<c:otherwise>
														<c:forEach items="${allHotels}" var="val">
															<option <c:if test="${sessionScope.SESSION_USER.hotelId eq val.key}">selected</c:if> value="${val.key}">${val.value}</option>
														</c:forEach>
													</c:otherwise>
												</c:choose>
											</select>
										</c:otherwise>
									</c:choose>
								</div>
							</div>
							<div class="control-group span6">
								<label class="control-label control-extent"><s>*</s>所属部门：</label>
								<div class="control-container control-container-extent">
									<div class="input-box has-btn-dialog">
										<input id="hideEmyDept" type="hidden" value="${staff.defaultDptId}" name="defaultDptId">
										<input class="control-text input-full" id="showEmyDept" type="text" value="${dpt.name }" placeholder="选择所属部门" readonly data-rules="{required:true}">
										<button class="btn-dialog btn-treelist" id="btnEmyDept" type="button"></button>
									</div>
								</div>
							</div>
							<div class="control-group span6">
								<label class="control-label control-extent"><s>*</s>所属职位：</label>
								<div class="control-container control-container-extent">
									<select class="choose" id="theJob" data-placeholder="选择所属职位" name="defaultPostId" data-rules="{required:true}" id="defaultPostId">
										<option value="">选择所属职位</option>
										<c:forEach items="${postList}" var="val">
											<option <c:if test="${staff.defaultPostId eq val.id}">selected</c:if> value="${val.id}">${val.englishName==null?'null':val.englishName}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="control-group span6">
								<label class="control-label control-extent"><s>*</s>类型：</label>
								<div class="control-container control-container-extent">
									<select class="choose" data-placeholder="选择类型" name="type" data-rules="{required:true}">
										<option value="">选择类型</option>
										<option <c:if test="${staff.type == 0}">selected</c:if> value="0">正式</option>
										<option <c:if test="${staff.type == 1}">selected</c:if> value="1">长期外聘</option>
										<option <c:if test="${staff.type == 2}">selected</c:if> value="2">短期外聘</option>
										<option <c:if test="${staff.type == 3}">selected</c:if> value="3">兼职</option>
									</select>
								</div>
							</div>
							<div class="control-group span6">
								<label class="control-label control-extent">工号：</label>
								<div class="control-container control-container-extent">
									<div class="input-box">
										<input class="control-text input-full" type="text" name="staffNo" value="${staff.staffNo}">
									</div>
								</div>
							</div>
							<div class="control-group span6">
								<label class="control-label control-extent">锁定账号：</label>
								<div class="control-container control-container-extent">
									<c:if test="${empty staff.status || staff.status != 2}">
										<label class="checkbox checkbox-beauty">
											<div class="bui-grid pull-left bui-grid-row-selected">
												<input class="x-grid-checkbox" type="checkbox" name="status" value="2">
											</div>
										</label>
									</c:if>
									<c:if test="${staff.status == 2}">
										<label class="checkbox checkbox-beauty bui-grid-row-selected">
											<div class="bui-grid pull-left bui-grid-row-selected">
												<input class="x-grid-checkbox" type="checkbox" name="status" value="2" checked>
											</div>
										</label>
									</c:if>
								</div>
							</div>
							<div class="control-group span6">
								<label class="control-label control-extent"><s>*</s>证件类型：</label>
								<div class="control-container control-container-extent">
									<select class="choose" id="idCardType" data-placeholder="选择证件类型" name="cardTypeId" data-rules="{required:true}">
										<option value="">选择证件类型</option>
										<c:forEach items="${certtypeSel}" var="val">
											<option <c:if test="${staff.cardTypeId eq val.key}">selected</c:if> value="${val.key}">${val.value}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="control-group span12">
								<label class="control-label control-extent"><s>*</s>证件号码：</label>
								<div class="control-container control-container-extent">
									<div class="input-box">
										<input class="control-text input-full" id="cardNo" type="text" name="cardNo" value="${staff.cardNo}" data-rules="{required:true,idCard:true,certNumber:true}">
									</div>
								</div>
							</div>
							<div class="control-group span6">
								<label class="control-label control-extent">邮箱地址：</label>
								<div class="control-container control-container-extent">
									<div class="input-box">
										<input class="control-text input-full" type="text" name="email" value="${staff.email}" data-rules="{email:true}">
									</div>
								</div>
							</div>
							<div class="control-group span6">
								<label class="control-label control-extent">联系电话：</label>
								<div class="control-container control-container-extent">
									<div class="input-box">
										<input class="control-text input-full" type="text" name="contacts" value="${staff.contacts}" data-rules="{telephone:true}">
									</div>
								</div>
							</div>
							<div class="control-group span6">
								<label class="control-label control-extent"><s>*</s>手机号码：</label>
								<div class="control-container control-container-extent">
									<div class="input-box">
										<input class="control-text input-full" type="text" name="mobilePhone" value="${staff.mobilePhone}" data-rules="{mobile:true,required:true}">
									</div>
								</div>
							</div>
							<!--TODO <div class="span12 bui-form-group x-relative" data-rules="{dateRange : true}"> -->
								<div class="control-group span6">
									<label class="control-label control-extent">过期时间：</label>
									<div class="control-container control-container-extent">
										<div class="input-box has-btn-dialog">
											<input class="control-text input-full calendar" type="text" name="expiredDate" value="<fmt:formatDate value="${staff.expiredDate}" type="both" pattern="yyyy/MM/dd"/> " readonly>
											<button class="btn-dialog btn-calendar" type="button"></button>
										</div>
									</div>
								</div>
								<div class="control-group span6">
									<label class="control-label control-extent">出生日期：</label>
									<div class="control-container control-container-extent">
										<div class="input-box has-btn-dialog">
											<input class="control-text input-full calendar" type="text" name="birthDate" id="birthDate" value="<fmt:formatDate value="${staff.birthDate}" type="both" pattern="yyyy/MM/dd"/> " data-rules="{maxDate:'${date}'}" readonly>
											<button class="btn-dialog btn-calendar" type="button"></button>
										</div>
									</div>
								</div>
							<!-- </div> -->
							<div class="control-group span6">
								<label class="control-label control-extent">学历：</label>
								<div class="control-container control-container-extent">
									<select class="choose" data-maxheight="190px" data-placeholder="选择学历" name="degreesId">
										<option value="">选择学历</option>
										<c:forEach items="${degreeSel}" var="val">
											<option <c:if test="${staff.degreesId eq val.key}">selected</c:if> value="${val.key}">${val.value}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="control-group span6">
								<label class="control-label control-extent">国家：</label>
								<div class="control-container control-container-extent">
									<select class="choose-4" data-maxheight="150px" data-placeholder="选择国家" name="countryId" id="personCertCountry">
									
									</select>
								</div>
							</div>
							<div class="control-group span6">
								<label class="control-label control-extent">省/州：</label>
								<div class="control-container control-container-extent">
									<select class="choose-4" data-maxheight="150px" data-placeholder="选择省/州" name="provinceId" id="personCertProvince">
										
									</select>
								</div>
							</div>
							<div class="control-group span6">
								<label class="control-label control-extent">市：</label>
								<div class="control-container control-container-extent">
									<select class="choose-4" data-maxheight="150px" data-placeholder="选择市" name="cityId" id="personCertCity">
										
									</select>
								</div>
							</div>
							<div class="control-group span18">
								<label class="control-label control-extent">详细地址：</label>
								<div class="control-container control-container-extent">
									<div class="input-box">
										<input class="control-text input-full" type="text" name="address" value="${staff.address}">
									</div>
								</div>
							</div>
							<div class="control-group span6">
								<label class="control-label control-extent">联系人1：</label>
								<div class="control-container control-container-extent">
									<div class="input-box">
										<input class="control-text input-full" type="text" name="emergency1" value="${staff.emergency1}">
									</div>
								</div>
							</div>
							<div class="control-group span6">
								<label class="control-label control-extent">联系电话：</label>
								<div class="control-container control-container-extent">
									<div class="input-box">
										<input class="control-text input-full" type="text" name="emergencyNo1" value="${staff.emergencyNo1}" data-rules="{telephone:true}">
									</div>
								</div>
							</div>
							<div class="control-group span6">
								<label class="control-label control-extent">&nbsp;</label>
								<div class="control-container control-container-extent">&nbsp;</div>
							</div>
							<div class="control-group span6">
								<label class="control-label control-extent">联系人2：</label>
								<div class="control-container control-container-extent">
									<div class="input-box">
										<input class="control-text input-full" type="text" name="emergency2" value="${staff.emergency2}">
									</div>
								</div>
							</div>
							<div class="control-group span6">
								<label class="control-label control-extent">联系电话：</label>
								<div class="control-container control-container-extent">
									<div class="input-box">
										<input class="control-text input-full" type="text" name="emergencyNo2" value="${staff.emergencyNo2}" data-rules="{telephone:true}">
									</div>
								</div>
							</div>
							<div class="control-group span18">
								<label class="control-label control-extent">描述：</label>
								<div class="control-container control-container-extent control-container-area">
									<div class="input-box">
										<textarea class="control-text input-full area-describe" name="description" value="${staff.description}"></textarea>
									</div>
								</div>
							</div>

						</div>
					</div>
					<div class="span5 ml20">					
						<!--<c:choose>
							 <c:when test="${empty staff.avatar || staff.avatar == ''}">  
							 	<p><img class="header" src="http://172.16.1.60:8088/face.png" alt="face"></p>
							 </c:when>
							 <c:otherwise> 
							 	<p><img class="header" src="${staff.avatar}"  alt="header"></p>
							 </c:otherwise>
						</c:choose>		-->
						<div class="file-images-wrapper">
							<input id="image_input" name="avatar" type="hidden" value="${staff.avatar}">
				   			<div class="demo-content file-content" id="file-content">
					    		<div id="J_Uploader"></div>
				   			</div>
				   			<div class="demo-content file-content hide" id="img_div">
				    			<div class="bui-uploader-button-wrap">
				    				<div class="text-center demo-box">
				    					<img id="uploadImg">
				    					<span class="x-icon x-icon-error img-del" >×</span>
				    				</div>
				    			</div>
				   			</div>
			   			</div>	
						<p><span class="auxiliary-text h6">仅支持JPG、JPEG、GIF、PNG图片文件，且文件小于200k</span></p>
					</div>
				</div>
			</form>
		</div>
		<!-- 基本资料 end -->
	</div>
</div>
<script>
$(function () {
	chooseConfig();
});

var areaSelectEmp = new areaSelect('personCertCountry', 'personCertProvince', 'personCertCity', '<c:url value="/common/getAreaData"/>');
areaSelectEmp.init('${staff.countryCode}', '${staff.provinceCode}', '${staff.cityCode}');
</script>
<script>
BUI.use(['bui/uploader','bui/grid', 'bui/data', 'bui/overlay', 'bui/form', 'bui/tree','bui/extensions/treepicker', 'bui/tab'], function(Uploader,Grid, Data, Overlay, Form, Tree, TreePicker, Tab) {

	if($('#image_input').val()){
		$('#uploadImg').attr('src',$('#image_input').val());
		$('#img_div').show();
		$('#file-content').hide();
	}
	
	var uploader = new Uploader.Uploader({
        render: '#J_Uploader',
        url: '<c:url value="/common/upload"/>',
        isSuccess: function(data){
        	if(data.status === 200){
        		var result = data.result;
        		$('#file-content').hide();
        		$('#uploadImg').attr("src", result.path);
        		$('#img_div').show();
        		$('#image_input').val(result.path);
        	} else {
        		BUI.Message.Alert(data.message,'error');
        	}
        },
     }).render();

	 // 删除原有的图片队列显示元素
	$('.bui-queue').remove();
		
	$('.img-del').click(function(){
		$('#file-content').show();
		$('#uploadImg').attr("src", "");
		$('#img_div').hide();
		$('#image_input').val('');
	});

	var hotelId = '${staff.defaultHotelId}';
	$(function(){
		$('#cardNo').focus(function(){
			if ($('#idCardType').val() == "") {
				BUI.Message.Alert('请选择证件类型！','error'); 
			}
		});
		$('#cardNo').blur(function(){
			if ($('#idCardType').val().split('+')[1] == 'ID') {
				var date = getBirthday('cardNo');
				$("#birthDate").val(date);
			}
		});
		$('#idCardType').change(function(){
			if ($('#idCardType').val().split('+')[1] == 'ID') {
				var date = getBirthday('cardNo');
				$("#birthDate").val(date);
			}
			$('#cardNo').trigger('keyup');
		});
	});
	
	/* 验证身份证 */
	Form.Rules.add({
	    name: 'idCard', //规则名称
	    msg: '不是正确的证件号！', //默认显示的错误信息
	    validator: function(value, baseValue, formatMsg) { //验证函数，验证值、基准值、格式化后的错误信息
	    	if ($('#idCardType').val() == '' && value != '') {
	    		//$('#idCardType').addClass('bui-form-field-error').parent().find('.valid-text').remove();
	    		//$('#idCardType').parent().append('<span class="valid-text"><span class="estate error"><span class="x-icon x-icon-mini x-icon-error">!</span><em>没有选择证件类型</em></span></span>');
				return false;
	    	}
	    	if ($('#idCardType').val() != '') {
	    		$('#idCardType').removeClass('bui-form-field-error').parent().find('.valid-text').remove();
	    	}
	    	if ($('#idCardType').val() != '' && value == '') {
	    		return '没有填写证件号码';
	    	}
	    	if ($('#idCardType').val().split('+')[1] == 'ID') {
		    	var iIdNo = $.trim(value);
		    	var red = /^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|[xX])$/;
		    	if (iIdNo.length > 0) {
		    		if (!red.test(iIdNo)) {
		    			return formatMsg;
		    		} else {
		    			var fristAnd = (iIdNo[0]*7) + (iIdNo[1]*9) + (iIdNo[2]*10) + (iIdNo[3]*5) + (iIdNo[4]*8) + (iIdNo[5]*4) + (iIdNo[6]*2) + (iIdNo[7]*1) + (iIdNo[8]*6) + (iIdNo[9]*3) + (iIdNo[10]*7) + (iIdNo[11]*9) + (iIdNo[12]*10) + (iIdNo[13]*5) + (iIdNo[14]*8) + (iIdNo[15]*4) + (iIdNo[16]*2);
			    		var secondBusiness = fristAnd % 11;
			    		var arr = ['1','0','xX','9','8','7','6','5','4','3','2'];
    	    			if(arr[secondBusiness].indexOf(iIdNo[17]) < 0){
    	    				return formatMsg;
    	    			}
		    		}
		    	}
	    	}
	    }
	});
	/* 验证联系电话 */
	Form.Rules.add({
	    name: 'contactTel', //规则名称
	    msg: '号码不正确！', //默认显示的错误信息
	    validator: function(value, baseValue, formatMsg) { //验证函数，验证值、基准值、格式化后的错误信息
	    	var red = /(^((0[1,2]{1}\d{1}-?\d{8})|(0[3-9]{1}\d{2}-?\d{7,8}))$)|(^1\d{10}$)/;
	    	if(value.length > 0){
	    		if(!red.test(value)){
	    			return formatMsg;
	    		}	
	    	}
	    }
	});
	var storeEmyDept = new Data.TreeStore({
        url : '<c:url value="/post/treeOrg"/>',
        params:{orgLevel:1,hotelId:hotelId},
        autoLoad : true
      }),
    treeEmyDept = new Tree.TreeList({
	      store : storeEmyDept,
	      elCls: 'tree-noicon',
	      checkType: 'none'
    });
    
  	var pickerEmyDept = new TreePicker({
  		autoAlign: false,
		align: {
			node: '#showEmyDept',
 			points: ['bl', 'tl'],
 			offset: [0, 1]
		},
		trigger : '#btnEmyDept',
	    textField : '#showEmyDept',  
	    valueField : '#hideEmyDept', //如果需要列表返回的value，放在隐藏域，那么指定隐藏域
	    width: 150,  //指定宽度
	    children : [treeEmyDept] //配置picker内的列表
    });
  	pickerEmyDept.render();
  	
  	storeEmyDept.on('load',function(){
		var value = '${staff.defaultDptId}';
		//pickerEmyDept.setSelectedValue(value);
    });
  	storeOrgTree = storeEmyDept;
  	var treeEmyDeptItem;
  	/* if(hotelId != ''){
		storeEmyDept.load({orgLevel:1,hotelId:hotelId});
	} */
  	//选择部门
  	treeEmyDept.on('itemclick',function(ev){
  		treeEmyDeptItem = ev.item;
  		updateJobList(treeEmyDeptItem.id,false);
	});
  	
  	
  	
  	//选择所属酒店
  	var defaulHotel = '${staff.defaultHotelId}';
  	$('#theHotel').on('change', function(){
  		$('#theJob').val('');
  		$('#theJob').text('');
		var staffIdVal = $('#staffId').val();
		var hotelValue = $(this).val();
		if(staffIdVal == null || staffIdVal == ''){
			storeEmyDept.load({orgLevel:1,hotelId:hotelValue});
			$('#hideEmyDept').val('');
			$('#showEmyDept').val('').change();
			$('#theJob').val('').change();
			$('#theJob').trigger('chosen:updated');
		}
		if(staffIdVal != null && staffIdVal != ''){
			BUI.Message.Show({
				title:'确认',
				msg: '是否删除该员工在本酒店的登录权限?',
				icon: 'question',
				buttons: [{
					text: '是',
					elCls: 'button button-primary button-small',
					handler: function() {
						$('#isDelFlag').val(0);
						this.close();
					}
				}, {
					text: '否',
					elCls: 'button button-small',
					handler: function() {
						$('#isDelFlag').val(1);
						this.close();
					}
				}]
			});
			storeEmyDept.load({orgLevel:1,hotelId:hotelValue});
			$('#hideEmyDept').val('');
			$('#showEmyDept').val('').change();
			$('#theJob').val('').change();
			$('#theJob').trigger('chosen:updated');
			defaulHotel = hotelValue;
			
		}
	});
})


</script>