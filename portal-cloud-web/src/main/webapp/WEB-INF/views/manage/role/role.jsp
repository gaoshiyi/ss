<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> 
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../common/header-2.0.0.jsp"></jsp:include>
<title><spring:message code="role.roleManager"/></title>
<link rel="stylesheet" href="<c:url value="/resources/css/management.css"/>">   
</head>
<body>
<div class="layout clearfix">
	<div class="content">
		<!-- filter-box start -->
		<div class="filter-box mt-15">
			<div class="panel-body">
				<form class="form-horizontal bui-form-horizontal bui-form bui-form-field-container" method="post" action="" autocomplete="off" data-plus-as-tab="true">
					<div class="clearfix">
						<div class="control-item">
							<div class="control-group">
								<label class="control-label control-label-auto">所属集团或酒店：</label>
								<div class="control-container box-w150 pull-left">
									<select class="choose" data-placeholder="请选择集团或酒店" name="belongId" id="belongId">
										<c:if test="${sessionScope.SESSION_USER.grpId eq sessionScope.SESSION_USER.hotelId}">
											<option value="-1">请选择集团或酒店</option>
										</c:if>
										<c:forEach items="${groupHotelList }" var="groupHotel">
											<option value="${groupHotel.id }" <c:if test="${groupHotel.id eq hotelId}">selected</c:if>>${groupHotel.name }</option>
										</c:forEach>
									</select>
								</div>
							</div>
						</div>
						<div class="control-item">
							<div class="control-group ml20">
								<label class="control-label control-label-auto">状态：</label>
								<div class="control-container box-w120 pull-left">
									<select class="choose" data-placeholder="请选择状态" name="status" id="status">
										<option value="">请选择状态</option>
										<option value="0">正常</option>
										<option value="2">锁定</option>
									</select>
								</div>
							</div>
						</div>
						<div class="control-item">
							<div class="control-group ml10">
								<div class="control-container box-w200">
									<div class="input-box">
										<input class="control-text input-full" type="text" name="" value="" placeholder="名称/外文名称" id="selectName">
									</div>
								</div>
							</div>
						</div>
						<div class="control-item">
							<div class="control-group pull-left ml20">
								<button class="button button-primary pull-left button-query" id="selectButton" type="button" data-keyName="F">查询 (F)</button>
							</div>
						</div>
						<shiro:hasPermission name="role_copy">
							<div class="control-item">
								<div class="control-group ml10">
									<div class="control-container">
										<button class="button button-primary pull-left ml10 button-query" id="btnCopy" type="button" data-keyName="C">复制酒店角色 (C)</button>
									</div>
								</div>
							</div>
						</shiro:hasPermission>
					</div>
				</form>
			</div>
		</div>
		<!-- filter-box end -->
		<div class="grid-enwrap grid-enwrap-diff">
			<!-- grid-top-bar start -->
			<div class="grid-top-bar clearfix">
				<div class="action-pop pull-left">
					<shiro:hasPermission name="role_modify">
						<button class="button button-primary" id="btnAdd" type="button" data-keyname="N"><spring:message code="role.add"/> (N)</button>
						<button class="button button-primary" onclick="btnEdit()" id="btnEdit" type="button" data-keyname="E"><spring:message code="role.update"/> (E)</button>
					</shiro:hasPermission>
					
					<shiro:hasPermission name="role_delete">
						<button class="button button-primary ml10" id="btnDel" type="button" data-keyname="D"><spring:message code="role.delete"/> (D)</button>
					</shiro:hasPermission>
				</div>
				<div class="pull-right">
					<button class="btn-icon btn-icon-small btn-icon-up" type="button" title="上移"></button>
					<button class="btn-icon btn-icon-small btn-icon-down" type="button" title="下移"></button>
					<button class="btn-icon btn-icon-small btn-icon-import" type="button" title="导入"></button>
					<button class="btn-icon btn-icon-small btn-icon-export" type="button" title="导出"></button>
					<!-- <button class="btn-icon btn-icon-small btn-icon-export-column" type="button" title="列导出"></button> -->
					<button class="btn-icon btn-icon-small btn-icon-edit" type="button" title="列权限"></button>
					<button class="btn-icon btn-icon-small btn-icon-authority" type="button" title="列调整"></button>
				</div>
			</div>
			<!-- grid-top-bar end -->
	
			<!-- grid start -->
			<div class="grid-content" id="grid"></div>
			<!-- grid end -->
		</div>
	</div>
</div>

<script>
var gridTree2;
var gridRight;
var gridLeft;
var gridSelectedCopy2;
var gridSelected2;
var gridSelectCopy2;
var gridSelect2;

function loopParent(item){
	var orgPath = item.text;
	var parent = item.parent;
	if(parent != null && parent.text != ''){
		orgPath = parent.text + " > "+ orgPath;
		if(parent.parent != null && parent.parent.text != ''){
			orgPath = loopParent(parent.parent) + " > " + orgPath;
		}
	}
	return orgPath;
}

BUI.use(['bui/grid', 'bui/data', 'bui/overlay', 'bui/form', 'bui/tree'], function(Grid, Data, Overlay, Form, Tree) {
    /* Grid */
	var Grid = Grid,
        Store = Data.Store,
        columns = [
			{ title: '操作',dataIndex: 'id', width: 100, sortable: false, renderer : function(value,obj){ 
					var lock = obj.status == 2 ? 'grid-icon-lock' : 'grid-icon-unlock';
					return  '<shiro:hasPermission name="role_modify"> <a class="grid-icon grid-icon-edit btn-edit" title="编辑" href="javascript:;" onclick="editRoleRow(\''+value+'\');"></a></shiro:hasPermission>'+
					'<shiro:hasPermission name="role_delete"> <a class="grid-icon grid-icon-del btn-del" title="删除" href="javascript:;" onclick="delRoleRow(\''+value+'\');"></a></shiro:hasPermission>'+
					'<shiro:hasPermission name="role_lock"> <a class="grid-icon '+lock+' btn-lock" title="锁定" href="javascript:;" onclick="lock(\''+value+'\',this);"></a></shiro:hasPermission>'+
					'<shiro:hasPermission name="/role/toEditEmployee"> <a class="grid-icon grid-icon-user-add btn-user-add" title="添加员工" href="javascript:;" onclick="userAdd(\''+value+'\');"></a></shiro:hasPermission>'
				}
			},
			{ title: '名称',dataIndex: 'name', elCls: 'text-left', width: 100, showTip: true },
			{ title: '外文名称',dataIndex: 'englishName', elCls: 'text-left', width: 100, showTip: true },
			{ title: '折扣率（%）',dataIndex: 'disaccountRate', elCls: 'text-right', width: 60, showTip: true },
			{ title: '状态',dataIndex: 'statusString', elCls: 'text-center', width: 100 ,renderer:function(value,obj){
				return obj.status == 0? "正常": obj.status == 2? "锁定":"删除"; 
			}},
			{ title: '所属集团或酒店',dataIndex: 'belongName', elCls: 'text-left', width: 150, showTip: true }
		];
    var store = new Store({
    	url : '<c:url value="/role/list"/>',
        autoLoad: true,
		pageSize: 10,
		listeners : {
	       	exception : function(ev){
	       		Message.Alert({msg:'<spring:message code="EO035"/>', icon:'error'});
	       	}
        }
    }),
	grid = new Grid.Grid({
		render: '#grid',
		columns: columns,
		width: '100%',
		//forceFit : true,
		store: store,
		emptyDataTpl: '<div class="empty-data"><span>暂无数据</span></div>',
		plugins: [Grid.Plugins.CheckSelection, Grid.Plugins.RowNumber, Grid.Plugins.ColumnResize],
		bbar:{
			pagingBar: {
				xclass: 'pagingbar-number'
			}
		}
	});
    grid.render();
    
    // 查询
    $("#selectButton").click(function(){
		var roleName = $("#selectName").val();
		var status = $("#status").val();
		var belongId = $("#belongId").val();
		store.load({
			condition:roleName,
			status:status,
			belongId:belongId
		});
	})
	
	// 单个删除
	window.delRoleRow = function(id){
    	BUI.Message.Confirm('<spring:message code="WO003"/>',function(){
			$.ajax({
				url : '<c:url value="/role/deleteRoleById"/>',
				type : "post",
				data : {
					roleId : id
				},
				dataType : "html",
				success : function(data, textStatus) {
					if (data == "success") {
						store.load();
					}else if(data == "staffError"){
						Message.Alert({msg:'该角色拥有有效的员工，无法删除!'});
					} else {
						Message.Alert({msg:'该角色拥有有效的职位，无法删除!'});
					}
				}
			})
           },'question');
    }
    
  //删除选中的记录
	$("#btnDel").click(function(){
		var selections = grid.getSelection();
		var idvalue = "";
		for (var i = 0; i < selections.length; i++) {
			idvalue += selections[i].id + ",";
		}
		if(idvalue == ""){
			Message.Alert({msg:'请选择需要删除的数据！'});
		}else{
			BUI.Message.Confirm('确认删除吗？',function(){
				$
				.ajax({
					url : '<c:url value="/role/deleteAll"/>',
					type : 'get',
					data : {
						roleIds : idvalue
					},
					dateType : 'html',
					success : function(data, textStatus) {
						if (data == "success") {
							store.load();
						}else if(data == "staffError"){
							Message.Alert({msg:'该角色拥有有效的员工，无法删除!'});
						} else {
							Message.Alert({msg:'该角色拥有有效的职位，无法删除!'});
						}
					}
				});
               },'question');
			
		}
	})
	
	//锁定
	window.lock = function(idvalue,obj){
		var msg = $(obj).hasClass('grid-icon-unlock') ? '确定锁定吗？' : '确定解锁吗？'
			Message.Confirm(this,{msg:msg,fn:function(dialog){
		    	$.ajax({
		    		url : '<c:url value="/role/unLock"/>',
					type : 'post',
					data : {
						roleId : idvalue
					},
					dateType : 'html',
					success : function(data, textStatus) {
						dialog.close();
						store.load();
						if (data == "normal") { //解锁
							$(obj).addClass('grid-icon-unlock').removeClass('grid-icon-lock');
						}else if(data == "lock"){ //锁定
							$(obj).addClass('grid-icon-lock').removeClass('grid-icon-unlock');
						}else{
							BUI.Message.Alert('锁定失败','error');
						}
					}
				});
		    }})
	  
	  
  	}

	//行编辑
	window.editRoleRow = function(id) {
		dialogRoleInit(id);
		dialogRole.set('title','修改角色信息');
		dialogRole.get('loader').load({roleId:id});
	}

	//添加员工
	window.userAdd = function(id) {
		dialogEmployeeInit(id);
		dialogEmployee.show();
		dialogEmployee.get('loader').load({roleId:id});
	}
	// 添加角色
	$('#btnAdd').on('click', function() {
		dialogRoleInit();
		dialogRole.show();
		dialogRole.get('loader').load({});
	});
	window.btnEdit = function(){
		var selects = grid.getSelection();
		if(selects.length <= 0){
			Message.Alert({msg:'请选择需要修改的数据！'});
		}else if(selects.length > 1){
			Message.Alert({msg:'请选择一条数据进行修改！'});
		}else{
			var selectObj = grid.getSelected();
			var id = selectObj.id;
			dialogRoleInit(id);
			//dialogRoleInit.show();
			dialogRole.set('title','修改角色信息');
			dialogRole.get('loader').load({roleId:id});
		}
	}
	//decide是否修改判断
	var decide = false;// 默认未修改
	/* 新建 Dialog */
	var dialogRole;
	function dialogRoleInit(id) {
		dialogRole = new Overlay.Dialog({
			title: '新建角色信息',
			elCls: 'role-dialog',
			height: 540,
			closeAction:'destroy',
			loader: {
		        url: '<c:url value="/role/form"/>',
		        autoLoad: false, //不自动加载
		        lazyLoad: false,
		        failure: function(response,params){ // 错误调用
		        	// 关闭弹出窗口
		        	dialogRole.close();
		        	Message.Alert({msg:'<spring:message code="EO035"/>', icon:'error'}); 
		        }, 
		        callback: function() {
		        	decide = false;
		            var node = dialogRole.get('el').find('form'); //查找内部的表单元素
		            form = new Form.HForm({
		                srcNode: node,
		                autoRender: true
		            }),
		            node.find('input, select, textarea').on('change', function() { //判断是否有修改内容
						decide = true;
					});
		        }
		    },
			buttons: [{
				text: '保存 (S)',
				elCls: 'button button-primary key-S',
				handler: function() {
					// 得到选中的权限id
					var permissionIds = "";
					var items = gridTree2.getItems();
					for(var i=0; i<items.length; i++){
						var id = items[i].id;
						permissionIds = permissionIds + id + ","
					} 
					$("#permissionIds").val(permissionIds);
					
					//验证是否通过
					if (form.isValid()) {
						form&&form.ajaxSubmit({
							success : function(data, status) {
								data = eval('(' + data + ')');
								var code = data.status;
								if(code == "200"){
									dialogRole.close();
									store.load();
								} else if(code == "102"){
									Message.Alert({msg:'角色已经存在'});
									//Message.Alert({msg:'<spring:message code="EC008"/>'});
								} else if(code == "101"){
									Message.Alert({msg:'参数错误，请检查后重新提交'});
								} else {
									Message.Alert({msg:data});
								}
							},
							dataType:"text"
						});
					}
					
				}
			}, {
				text: '关闭 (Esc)',
				elCls: 'button key-Esc',
				handler: function() {
					//Message.Confirm(this, {msg: '确认关闭吗？'});
					if(decide == true){
						 Message.Confirm(this, { msg: '是否放弃已编辑的信息？' });
					}else{
						dialogRole.close();
					}
				}
			}],
			listeners: {
				closeclick: function(e) {
					if(decide == true){
						Message.Confirm(this, {msg: '是否放弃已编辑的信息？' });
					}else{
						dialogRole.close();
					}
					return false;
				},
				afterDestroy: function(e) {
					decide = false;
				}
			}
		});
		dialogRole.show();
	}
	
	
	
	/* 添加员工 Dialog */
	var dialogEmployee;
	function dialogEmployeeInit(id){
		dialogEmployee = new Overlay.Dialog({
			title: '添加员工',
			width: 1055,
			height: 520,
			closeAction:'destroy',
			loader: {
				url : '<c:url value="/role/toEditEmployee"/>',
		        autoLoad: false, //不自动加载
		        lazyLoad: false,
		        failure: function(response,params){ // 错误调用
		        	// 关闭弹出窗口
 		        	dialogEmployee.close();
 		        	Message.Alert({msg:'<spring:message code="EO035"/>', icon:'error'});
		        },
		        callback: function() {
		        	decide = false;
		            var node = dialogEmployee.get('el').find('form'); //查找内部的表单元素
		            form = new Form.HForm({
		                srcNode: node,
		                autoRender: true
		            }),
		            node.find('input, select, textarea').on('change', function() { //判断是否有修改内容
						decide = true;
					});
		        }
		    },			
			buttons: [{
				text: '保存 (S)',
				elCls: 'button button-primary key-S',
				handler: function() {
					var selections = gridSelected2.getItems();
					var idvalue = "";
					for (var i = 0; i < selections.length; i++) {
						idvalue += selections[i].id + ",";
					}
					$("#staffIdx").val(idvalue);
					
					if (form.isValid()) {
						form&&form.ajaxSubmit({
							success : function(data, status) {
								if(data == "success"){
									dialogEmployee.close();
									store.load();
								} else if(data == "error"){
									//Message.Alert({msg:'<spring:message code="EC008"/>'});
								} else {
									Message.Alert({msg:data});
								}
							},
							dataType:"text"
						});
					}
				},
			}, {
				text: '关闭 (Esc)',
				elCls: 'button key-Esc',
				handler: function() {
					//Message.Confirm(this, {msg: '确认关闭吗？'});
					if(decide == true){
						 Message.Confirm(this, { msg: '是否放弃已编辑的信息？' });
					}else{
						dialogEmployee.close();
					}
				}
			}],
			listeners: {
			    closeclick: function(e) {
					if(decide == true){
						Message.Confirm(this, {msg: '是否放弃已编辑的信息？' });
					}else{
						dialogEmployee.close();
					}
					return false;
				},
				afterDestroy: function(e) {
					decide = false;
				}
			}
		});
		dialogEmployee.show();
	}

	
	/* 复制角色 ================================================================================= */
	/* 复制角色Dialog */
	var dialogRoleCopy;
	function dialogRoleCopyInit(id){
		dialogRoleCopy = new Overlay.Dialog({
			title: '复制角色信息',
			width: 870,
			height: 520,
			closeAction:'destroy',
			loader: {
		        url: '<c:url value="/role/copy"/>',
		        autoLoad: false, //不自动加载
		        lazyLoad: false,
		        failure: function(response,params){ // 错误调用
		        	// 关闭弹出窗口
		        	 dialogRoleCopy.close();
		        	Message.Alert({msg:'<spring:message code="EO035"/>', icon:'error'}); 
		        }, 
		        callback: function() {
		        	//decide2 = false;
		            var node = dialogRoleCopy.get('el').find('form'); //查找内部的表单元素
		            form = new Form.HForm({
		                srcNode: node,
		                autoRender: true
		            }),
		            node.find('input, select, textarea').on('change', function() { //判断是否有修改内容
		            	//decide2 = true;
					});
		        }
			},
			buttons: [{
				text: '保存 (S)',
				elCls: 'button button-primary key-S',
				handler: function() {
					// 获取角色id
					var selections = gridSelectedCopy2.getItems() ;
					if(selections.length > 0){
						var idvalue = "";
						for (var i = 0; i < selections.length; i++) {
							idvalue += selections[i].id + ",";
						}
						$("#copyRoleIds").val(idvalue);
						//验证是否通过
						if (form.isValid()) {
							form&&form.ajaxSubmit({
								success : function(data, status) {
									if(data == "success"){
										dialogRoleCopy.close();
										store.load();
									} else if(data == "error"){
										//Message.Alert({msg:'<spring:message code="EC008"/>'});
									} else {
										Message.Alert({msg:data});
									}
								},
								dataType:"text"
							});
						}
					}
					
				}
			}, {
				text: '关闭 (Esc)',
				elCls: 'button key-Esc',
				handler: function() {
					//Message.Confirm(this, {msg: '确认关闭吗？'});
					if(decide == true){
							Message.Confirm(this, { msg: '是否放弃已编辑的信息？' });
					}else{
						dialogRoleCopy.close();
					}
					decide = false;
				}
			}],
			listeners: {
				closeclick: function(e) {
					if(decide == true){
						Message.Confirm(this, {msg: '是否放弃已编辑的信息？' });
					}else{
						dialogRoleCopy.close();
					}
					return false;
				},
				afterDestroy: function(e) {
					decide = false;
				}
			}
		});
		
		dialogRoleCopy.show();
	}
	$('#btnCopy').on('click', function() {
		dialogRoleCopyInit();
		dialogRoleCopy.get('loader').load();
	});
	// 复制角色移入提示
	window.copyInner = function(){
		var items = gridSelectCopy2.getItems();
		if(items.length > 0){
			var selections = gridSelectCopy2.getSelection();
			if(selections.length > 0){
				decide = true;
			}else {
				Message.Alert({msg:'请选择需要移入的数据！'});	
				decide = true;
			}
		}
	}
	// 复制角色移出提示
	window.copyOuter = function(){
		var items = gridSelectedCopy2.getItems();
		if(items.length > 0){
			var selections = gridSelectedCopy2.getSelection();
			if(selections.length > 0){
				decide = true;
			}else {
				Message.Alert({msg:'请选择需要移出的数据！'});	
				decide = true;
			}
		}
	}
	
	// 添加员工移入提示信息
	window.empInner = function(){
		var items = gridSelect2.getItems();
		if(items.length > 0){
			var selections = gridSelect2.getSelection();
			if(selections.length > 0){
				decide = true;
			}else {
				Message.Alert({msg:'请选择需要移入的数据！'});	
				decide = true;
			}
		}
	}
	
	// 添加员工移出提示信息
	window.empOuter = function(){
		var items = gridSelected2.getItems();
		if(items.length > 0){
			var selections = gridSelected2.getSelection();
			if(selections.length > 0){
				decide = true;
			}else {
				Message.Alert({msg:'请选择需要移出的数据！'});	
				decide = true;
			}
		}
	}
	
	window.treeClick = function(){
		decide = true;
	}
});
</script>
</body>
</html>