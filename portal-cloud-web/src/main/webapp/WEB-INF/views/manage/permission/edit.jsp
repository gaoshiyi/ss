 <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- 新建 部门列表 Dialog start  -->

	<form class="form-horizontal sector-list" method="post" action='<c:url value="/perm/saveOrUpdate"/>' autocomplete="off" data-plus-as-tab="true">
		<div class="row-fluid">
			<div class="control-group span24">
				<label class="control-label control-extent"><s>*</s>名称：</label>
				<div class="control-container control-container-extent">
					<div class="input-box">
						<input class="control-text input-full" type="text" name="name" value="${perm.name }" data-rules="{required:true}" autocomplete="off" >
					</div>
				</div>
			</div>
		</div>
		<div class="row-fluid">
			<div class="control-group span24">
				<label class="control-label control-extent"><s>*</s>类型：</label>
				<div class="control-container control-container-extent">
					<select class="choose" id="type" name="type" data-placeholder="选择类型" data-rules="{required:true}">
						<option value="">选择类型</option>
						<option value="0" <c:if test="${perm.type eq 0 }">selected</c:if> >产品</option>
						<option value="1" <c:if test="${perm.type eq 1 }">selected</c:if> >系统</option>
						<option value="2" <c:if test="${perm.type eq 2 }">selected</c:if> >模块</option>
						<option value="3" <c:if test="${perm.type eq 3 }">selected</c:if> >功能</option>
						<option value="4" <c:if test="${perm.type eq 4 }">selected</c:if> >操作</option>
					</select>
				</div>
			</div>
		</div>
		<div class="row-fluid">
			<div class="control-group span24">
				<label class="control-label control-extent">上级信息：</label>
				<div class="control-container control-container-extent">
					<div class="input-box has-btn-dialog">
						<input id="pid" type="hidden" name="pid" value="">
						<input class="control-text input-full" id="showPerm" type="text" name="" value="" placeholder="选择上级" readonly>
						<button class="btn-dialog btn-treelist" id="btnPerm" type="button"></button>
					</div>
				</div>
			</div>
		</div>
		<div class="row-fluid">
			<div class="control-group span24">
				<label class="control-label control-extent">请求URL：</label>
				<div class="control-container control-container-extent">
					<div class="input-box">
						<input class="control-text input-full" type="text" name="url" value="${perm.url }">
					</div>
				</div>
			</div>
		</div>
		<div class="row-fluid">
			<div class="control-group span11">
				<label class="control-label control-extent">价格：</label>
				<div class="control-container control-container-extent">
					<div class="input-box">
						<input class="control-text input-full" type="text" name="price" value="${perm.price }">
					</div>
				</div>
			</div>
			<div class="control-group span13">
				<div class="ml10">
					<label class="control-label control-extent">图标样式：</label>
					<div class="control-container control-container-extent">
						<div class="input-box">
							<input class="control-text input-full" type="text" name="iconClass" value="${perm.iconClass }">
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="row-fluid">
			<div class="control-group span13">
				<label class="control-label control-extent">展示顺序：</label>
				<div class="control-container control-container-extent">
					<div class="input-box">
						<input class="control-text input-full" type="text" name="order" value="${perm.order }">
					</div>
				</div>
			</div>
			<label class="checkbox checkbox-beauty pull-left ml10 <c:if test="${perm.isMenu eq 1 }" >bui-grid-row-selected</c:if>"><div class="bui-grid pull-left"><input class="x-grid-checkbox" type="checkbox" name="isMenuCk" id="isMenuCk" <c:if test="${perm.isMenu eq 1 }" >checked</c:if>></div>菜单展示</label>
			<input type="hidden" name="isMenu" id="isMenu" value="${perm.isMenu }">
			<label class="checkbox checkbox-beauty pull-right <c:if test="${perm.isReport eq 1 }" >bui-grid-row-selected</c:if>"><div class="bui-grid pull-left"><input class="x-grid-checkbox" type="checkbox" name="isReportCk" id="isReportCk" value="${perm.isReport }"  <c:if test="${perm.isReport eq 1 }" >checked</c:if>></div>报表</label>
			<input type="hidden" name="isReport" id="isReport" value="${perm.isReport }">
		</div>
		<div class="row-fluid">
			<div class="control-group pull-left">
				<label class="control-label control-extent">打开方式：</label>
				<div class="control-container control-container-extent">
					<label class="checkbox checkbox-beauty pull-left ml10 <c:if test="${perm.openType eq 1 }" >bui-grid-row-selected</c:if>"><div class="bui-grid pull-left"><input class="x-grid-checkbox" type="checkbox" name="openTypeCk" id="openTypeCk" <c:if test="${perm.openType eq 1 }" >checked</c:if>></div>弹框</label>
					<input type="hidden" name="openType" id="openType" value="${perm.openType }" >
				</div>
			</div>
			<div class="control-container pull-left ml20 box-auto">
				<div class="input-box">
					<input class="control-text input-full" placeholder="高"  id="height" <c:if test="${perm.openType eq 0 }">disabled</c:if> type="number" min="0" max="9999" maxlength="4" name="height" value="${perm.height }">
				</div>
			</div>
			<span class="multiply">x</span>
			<div class="control-container pull-left box-auto">
				<div class="input-box">
					<input class="control-text input-full" placeholder="宽" id="width" <c:if test="${perm.openType eq 0 }">disabled</c:if> type="number" min="0" max="9999" maxlength="4" name="width" value="${perm.width }">
				</div>
			</div>
		</div>
		<div class="row-fluid">
			<div class="control-group span24">
				<label class="control-label control-extent">关联URL：</label>
				<div class="control-container control-container-extent">
					<div class="input-box">
						<textarea class="control-text input-full area-url" name="subUrlText" id="subUrlText">${subUrlText }</textarea>
					</div>
				</div>
			</div>
		</div>
		<input type="hidden" name="id" id="id" value="${perm.id }">
		<input type="hidden" name="subUrl" id="subUrl" value="">
	</form>
<!-- 新建 部门列表 Dialog end  -->


<script>
	$(function () {
	    chooseConfig();
	});
</script>
<script>

BUI.use(['bui/grid', 'bui/data', 'bui/overlay', 'bui/form', 'bui/tree', 'bui/extensions/treepicker', 'bui/tab'], function(Grid, Data, Overlay, Form, Tree, TreePicker, Tab) {
	var storePermTree = new Data.TreeStore({
	    url : '<c:url value="/perm/tree"/>',
	    autoLoad : true,
	  }),
	treePerm = new Tree.TreeList({
	      store : storePermTree,
	      elCls: 'tree-noicon',
	      checkType: 'none'
	});
	var pickerPerm = new TreePicker({
		autoAlign: false,
		align: {
			node: '#showPerm',
 			points: ['bl', 'tl'],
 			offset: [0, 1]
		},
		trigger : '#btnPerm',
      	textField : '#showPerm',  
      	valueField : '#pid', //如果需要列表返回的value，放在隐藏域，那么指定隐藏域
      	width: $('#showPerm').outerWidth(),  //指定宽度
      	children : [treePerm] //配置picker内的列表
	});
	pickerPerm.render();
	
	var node1 = storePermTree.findNode('${perm.pid}');
	var node2 = treePerm.findNode('${perm.pid}');
	
	storePermTree.on('load',function(ev){
		pickerPerm.setSelectedValue('${perm.pid}');
	});
	//var treeItemDpt;
	treePerm.on('itemclick',function(ev){
		var perm = ev.item;
	});
	
	$('#openTypeCk').on('change', function() {
		var isDisabled = true;
		var openType = 0;
		if ($(this).is(':checked')) {
			isDisabled = false;
			openType = 1;
		}else{
			$("#height").val(0);
			$("#width").val(0);
		}
		$("#openType").val(openType);
		$('#height').prop('disabled', isDisabled);
		$('#width').prop('disabled', isDisabled);
	});

	$("#isReportCk").on("change",function(){
		var isReport = 0;
		if($(this).is(':checked')){
			isReport = 1;
		}
		$("#isReport").val(isReport);
	});
	$("#isMenuCk").on("change",function(){
		var isMenu = 0;
		if($(this).is(':checked')){
			isMenu = 1;
		}
		$("#isMenu").val(isMenu);
	});

});

</script>