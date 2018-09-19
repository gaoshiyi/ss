<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%-- <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> 
<!DOCTYPE html>
<html>
<head>
<title>消息通知</title>
<jsp:include page="../../common/header-2.0.0.jsp"></jsp:include>
<!-- page css -->
<link href="<c:url value="/resources/css/notification.css"></c:url>" rel="stylesheet">
</head> --%>
<form class="form-horizontal notice-form" id="handleForm" method="post" action="" autocomplete="off" data-plus-as-tab="true">
	<input type = "hidden" id ="hotelId" value="${hotelId}"/>
	<input type = "hidden" id ="noticeId" value="${noticeId}"/>
	<div class="control-group">
		<label class="control-label control-extent3"><s>*</s>部门：</label>
		<div class="control-container control-container-extent control-container-extent3">
			<div class="input-box has-btn-dialog">
				<input id="hideDpt2" type="hidden" name="deptId" value="${department.id }">
				<input class="control-text input-full" id="showDpt2" type="text" name="" value="${department.name }" placeholder="选择所属部门" readonly data-rules="{required:true}">
				<button class="btn-dialog btn-treelist" id="btnDept2" type="button"></button>
			</div>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label control-extent3"><s>*</s>升级当班负责人：</label>
		<div class="control-container control-container-extent control-container-extent3">
			<select class="choose input-full"  id ="ruleId"  data-maxheight="200" data-placeholder="选择升级当班负责人" data-rules="{required:true}">
				<option value="">选择升级当班负责人</option>
			</select>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label control-extent3"><s>*</s>联系电话：</label>
		<div class="control-container control-container-extent control-container-extent3">
			<div class="input-box">
				<input class="control-text input-full" type="text" id="phone" name="phone" value="" data-rules="{required:true, number:true}">
			</div>
		</div>
	</div>
</form>

<script>
chooseConfig();

BUI.use(['bui/tree','bui/data', 'bui/select', 'bui/extensions/treepicker'], function(Tree, Data, Select, TreePicker) {
	
	
	var hotelId = $("#hotelId").val();
	/*部门树*/
	var storeDpt2 = new Data.TreeStore({
	    url : '<c:url value="/post/treeOrg"/>',
	    autoLoad : true,
	    params:{
	    	'hotelId': hotelId,
			'nodeType': '1',
			'disableLevel': 1
	    }
	  }),
	treeDpt2 = new Tree.TreeList({
	      store : storeDpt2,
	      elCls: 'tree-noicon',
	      checkType: 'none'
	});
		
	var pickerDpt2 = new TreePicker({
		autoAlign: false,
		align: {
			node: '#showDpt2',
			points: ['bl', 'tl'],
			offset: [0, 1]
		},
		trigger : '#btnDept2',
		textField : '#showDpt2',  
		valueField : '#hideDpt2', //如果需要列表返回的value，放在隐藏域，那么指定隐藏域
		width: $('#showDpt2').outerWidth(),  //指定宽度
		children : [treeDpt2] //配置picker内的列表
	});
	pickerDpt2.render();
		
	//var treeItemDpt;
	treeDpt2.on('itemclick',function(ev){
		treeItemDpt2 = ev.item;
	});
		
	treeDpt2.on('selectedchange',function(ev){
		treeItemDpt = ev.item;
		updateJobList(ev.item.id);
		/* var node = loopHotel(treeItemDpt);
		var belongId = node.id;
		storeSelect.load({belongId:belongId}); */
	});
	
	function loopHotel(item){
		var orgPath = item.text;
		var parent = item.parent;
		if(parent != null && parent.text != '' && (parent.nodeType == 0 || parent.nodeType == 1)){
			return parent;
		}else{
			return loopHotel(parent);
		}
	}
})

function updateJobList(dptId){
	$.ajax({
		type : "POST",
		dataType : "json",
		url : '<c:url value="/sysMsgnotify/listStaff"/>',
		data : {deptId:dptId},
		async : false,
		success : function(result) {
			if (result.status == "200") {
				var obj = result.result;
				var html = '<option value="">选择升级当班负责人</option>';
				for(var i = 0;i< obj.length;i++){
					html += "<option value='" + obj[i].id+"'>"+ obj[i].familyName + "" +  obj[i].name+"</option>";
				}
				$('#ruleId').html(html);
	  			$('#ruleId').trigger('chosen:updated');
			} 
		}
	});
}
</script>