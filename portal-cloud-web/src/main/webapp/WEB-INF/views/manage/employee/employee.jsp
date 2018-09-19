<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> 
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="UTF-8">
<title>员工管理</title>
<jsp:include page="../../common/header-2.0.0.jsp"></jsp:include>
<link href="<c:url value="/resources/css/management.css"></c:url>" rel="stylesheet">
</head>

<body>
<div class="layout clearfix">
	<div class="content">
		<div class="filter-box mt-15">
			<div class="panel-body">
				<form class="form-horizontal bui-form-horizontal bui-form bui-form-field-container" method="post" action="" autocomplete="off" data-plus-as-tab="true">
					<div class="clearfix">
						<div class="control-item">
							<div class="control-group">
								<!-- 职位跳转过来 -->
								<input type="hidden" value="${postId}" id="postId">
								<!-- 工作圈跳转过来 -->
								<input type="hidden" value="${teamId}" id="teamId">
								<!-- 下拉框选项的酒店ID -->
								<input type="hidden" value="${loginHotelVal}" id="selectOrgHotelId">
								<!-- 当前页信息展示的酒店ID,随查询按钮更新 -->
								<input type="hidden" value="${loginHotelVal}" id="hotelId">
								<label class="control-label control-label-auto">组织：</label>
								<div class="control-container box-w150 pull-left">
									<div class="input-box has-btn-dialog">
										<input id="hideOpt" type="hidden" name="" value="${loginHotelVal}">
										<input class="control-text input-full" id="showOpt" type="text" name="" value="" placeholder="选择组织" readonly>
										<button class="btn-dialog btn-treelist" id="btnOpt" type="button"></button>
									</div>
								</div>
							</div>
						</div>
						<div class="control-item">
							<div class="control-group ml20">
								<label class="control-label control-label-auto">状态：</label>
								<div class="control-container box-w130 pull-left">
									<select class="choose" data-placeholder="选择状态" id="statusId">
										<option value="">选择状态</option>
										<option value="0">正常</option>
										<option value="1">待审核</option>
										<option value="2">锁定</option>
									</select>
								</div>
							</div>
						</div>
						<div class="control-item">
							<div class="control-group ml20">
								<div class="control-container">
									<input class="control-text input-large" type="text" name="" value="" placeholder="中文名/外文名/手机号码/工号" id="inforsId" autocomplete="off">
								</div>
							</div>
						</div>
						<div class="control-item">
							<div class="control-group ml20">
								<button class="button button-primary button-query" type="button" id="btnStaffFind" data-keyname="N">查询(F)</button>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
		<!-- filter-box end -->
		<div class="grid-enwrap grid-enwrap-diff">
			<!-- grid-top-bar start -->
			<div class="grid-top-bar clearfix">
				<div class="action-pop pull-left">
					<shiro:hasPermission name="employee_modify">
						<button class="button button-primary" id="btnAdd" type="button" data-keyname="N">新建 (N)</button>
						<button class="button button-primary" id="btnEdit" type="button" data-keyname="E" onclick="editRow('')">修改 (E)</button>
					</shiro:hasPermission>
					<shiro:hasPermission name="/employee/strateryDlog">
						<button class="button button-primary" id="btnStratery"type="button">批量设置登录策略</button>
					</shiro:hasPermission>
					<shiro:hasPermission name="/employee/delete">
						<button class="button button-primary ml10" id="btnDel" type="button" data-keyname="D" onclick="delRow('')">删除 (D)</button>
					</shiro:hasPermission>
				</div>
				<div class="pull-right">
					<button class="btn-icon btn-icon-small btn-icon-up" type="button" title="上移" disabled></button>
					<button class="btn-icon btn-icon-small btn-icon-down" type="button" title="下移" disabled></button>
					<button class="btn-icon btn-icon-small btn-icon-import" type="button" title="导入" ></button>
					<button class="btn-icon btn-icon-small btn-icon-export" type="button" title="导出" onclick="download();"></button>
					<!-- <button class="btn-icon btn-icon-small btn-icon-export-column" type="button" title="列导出"></button> -->
					<button class="btn-icon btn-icon-small btn-icon-authority" type="button" title="列权限"></button>
					<button class="btn-icon btn-icon-small btn-icon-edit" type="button" title="列调整"></button>
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

function download(){
	window.location.href = '<c:url value="/employee/download" />';
}

</script>

<script>
var gridLeft;
var gridRight;
var gridWorkHotel;
var orgId = hiddenHtlId;
var nodeType = 1;
var storeWorkHotel;
var grid;
var storeOrgTree;
var xForm;
//判断是否填写数据默认为没有false
var decide = false;
//基本资料 tab切换时候 是否改变数据
var oldDecide = false; 
BUI.use(['bui/grid', 'bui/data', 'bui/overlay', 'bui/form', 'bui/tree','bui/extensions/treepicker', 'bui/tab'], function(Grid, Data, Overlay, Form, Tree, TreePicker, Tab) {
/*组织树*/
	staffStoreDpt = new Data.TreeStore({
		url : '<c:url value="/employee/treeOrgTab"/>',
		params : {
			'nodeTypeVal' : -1
		},
		autoLoad: true
	}),
	
	staffTreeDpt = new Tree.TreeList({
		store: staffStoreDpt,
		elCls: 'tree-noicon',
        checkType: 'none'
	});
	var pickerDpt = new TreePicker({
		autoAlign: false,
		align: {
			node: '#showOpt',
 			points: ['bl', 'tl'],
 			offset: [0, 1]
		},
		trigger : '#btnOpt',
		textField : '#showOpt',  
        valueField : '#hideOpt',  
		width: 164, //指定宽度
		children: [staffTreeDpt] //配置picker内的列表
	});
	pickerDpt.render();
	
	function loopParent(item,nodeType){
		if(nodeType > 1){
			var parent = item.parent;
			nodeType = parent.nodeType;
			return loopParent(parent,nodeType);
		}else{
			return item.id;
		}
	}
	
	var treeItem;
	staffTreeDpt.on('itemclick',function(ev){
  		treeItem = ev.item;
  		orgId = treeItem.id;
  		nodeType = treeItem.nodeType;
  		var hotelId = loopParent(treeItem,nodeType);
  		$("#selectOrgHotelId").val(hotelId);
	});
	staffStoreDpt.on('load',function(){
		var value = '${loginHotelVal}';
		pickerDpt.setSelectedValue(value);
    });
/* Grid */
	var postId2 = $('#postId').val();
	var teamId = $('#teamId').val();
	var Grid = Grid,
    Store = Data.Store,
    columns = [
		{ title: '操作',dataIndex: 'id', sortable: false, width: 160, renderer : function(value,obj){ 
				var lock = obj.status == 2 ? 'grid-icon-lock' : 'grid-icon-unlock';
				var defaultHotelId = obj.defaultHotelId;
				return  '<shiro:hasPermission name="employee_modify"><a class="grid-icon grid-icon-edit btn-edit" title="编辑" href="javascript:;" onclick="editRow(\''+value+'\');"></a></shiro:hasPermission>'+
				'<shiro:hasPermission name="/employee/delete"><a class="grid-icon grid-icon-del btn-del" title="删除" href="javascript:;" onclick="delRow(\''+value+'\');"></a></shiro:hasPermission>'+
				'<shiro:hasPermission name="/employee/lock"><a class="grid-icon '+lock+' btn-lock" title="锁定" href="javascript:;" onclick="lock(\''+value+'\',this);"></a></shiro:hasPermission>'+
				'<a class="grid-icon grid-icon-reset btn-reset" title="重置密码" href="javascript:;" onclick="resetPwd(\''+value+'\');"></a>'+
				'<shiro:hasPermission name="/employee/strateryDlog"><a class="grid-icon grid-icon-strategy btn-strategy" title="登录策略" href="javascript:;" onclick="loginStratery(\''+value + '\', \''+ defaultHotelId +'\');"></a></shiro:hasPermission>'+
				'<shiro:hasPermission name="employee_role"><a class="grid-icon grid-icon-link btn-link" title="关联角色" href="javascript:;" onclick="roleLink(\''+value+'\');"></a></shiro:hasPermission>'
			}
		},
		{ title: '姓名',dataIndex: 'name', elCls: 'text-left', width: 80, showTip: true },
		{ title: '外文姓名',dataIndex: 'nameEn', elCls: 'text-left', width: 100, showTip: true },
		{ title: '账号',dataIndex: 'account', elCls: 'text-left', width: 100, showTip: true },
		{ title: '工号',dataIndex: 'staffNo', elCls: 'text-center', width: 100, showTip: true },
		{ title: '手机号码',dataIndex: 'mobilePhone', elCls: 'text-center', width: 100, showTip: true },
		{ title: '状态',dataIndex: 'status', width: 60, renderer : Grid.Format.enumRenderer({
				'2':'锁定',
				'0':'正常'
			})
		},
		{ title: '归属组织',dataIndex: 'orgInfo', elCls: 'text-left', width: 150, showTip: true }
	];
    var store = new Store({
    	url : '<c:url value="/employee/list"/>',
    	params : {
    		'orgId':orgId,
    		'nodeType':nodeType,
    		'teamId':teamId
   		}, 
        autoLoad: true,
        listeners : {
	       	exception : function(ev){
	       		Message.Alert({msg:'请求失败，请与系统管理员联系！', icon:'error'});
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

//行编辑
	window.editRow = function(id) {
		if(id == null || id == ''){
			var selections = grid.getSelection();
			if(selections.length == 0){
				Message.Alert({msg:'请选择需要修改的数据！'});
				return;
			}else if(selections.length > 1){
				Message.Alert({msg:'请选择一条数据进行修改！'});
				return;
			}else{
				var record = selections[0];
				id = record.id;
			}
		}
		first = 0;
		dialogEmployeeInit(id);
		dialogEmployee.set('title','修改员工信息');
		dialogEmployee.get('loader').load({'id':id,'hotelId':$("#hotelId").val()});
	}

//行删除
	window.delRow = function(idvalue) {
//批量删除
		if(idvalue == "" || idvalue == null){
			var selections = grid.getSelection();
			if(selections.length == 0){
				Message.Alert({msg:'请选择需要删除的数据！'});
				return;
			}
			for (var i = 0; i < selections.length; i++) {
					idvalue += selections[i].id + ",";
				}
		}
		Message.Confirm(this,{msg:'确认删除吗？',fn:function(obj){
	    	$.ajax({
	    		url : '<c:url value="/employee/delete"/>',
				type : 'post',
				data : {
					idvalue : idvalue,
					orgId:orgId,
					orgType:nodeType
				},
				dateType : 'json',
				success : function(data, textStatus) {
					if (data.status == 200) {
						store.load();
					}else{
						BUI.Message.Alert('删除失败','error');
					}
					obj.close();
				}
			});
	    }})
	}

//行锁定
	window.lock = function(idvalue,obj) {
		var msg = $(obj).hasClass('grid-icon-unlock') ? '确定锁定吗？' : '确定解锁吗？';
		Message.Confirm(this,{msg:msg,fn:function(dialog){
	    	$.ajax({
	    		url : '<c:url value="/employee/lock"/>',
				type : 'post',
				data : {
					idvalue : idvalue
				},
				dateType : 'html',
				success : function(data, textStatus) {
					if (data == "normal") { //解锁
						$(obj).addClass('grid-icon-unlock').removeClass('grid-icon-lock');
					}else if(data == "lock"){ //锁定
						$(obj).addClass('grid-icon-lock').removeClass('grid-icon-unlock');
					}else{
						BUI.Message.Alert('锁定失败','error');
					}
					store.load();
					dialog.close();
				}
			});
	    }})
	}

	//行重置密码
	window.resetPwd = function(id) {
		$.ajax({
			type : "POST",
			dataType : "json",
			url : '<c:url value="/employee/resetPwd" />',
			data : {id:id},
			success : function(result) {
				var code = result.status;
				if (code == '102') {
					BUI.Message.Alert('请勿在1分钟内重复操作！','error'); 
				}else if(code == '200'){
					BUI.Message.Alert('随机密码已发送到员工手机！','success');
				}else if(code == '500'){
					BUI.Message.Alert(result.message,'error'); 
				}else{
					BUI.Message.Alert('请求失败，请与系统管理员联系！','error'); 
				}
			}
		});
	}

//行用户登录策略
	window.loginStratery = function(ids,defaultHotelId) {
		dialogStrateryInit(ids);
		dialogStratery.get('loader').load({
			"ids":ids,
			"hotelId":$("#hotelId").val()
		});
	}

//行关联角色
	window.roleLink = function(id) {
		first = 0;
		dialogEmployeeInit(id,3)
		dialogEmployee.set('title','修改员工信息');
		dialogEmployee.get('loader').load({'id':id,'hotelId':$("#hotelId").val()});
	}


	var first = 0;
/* 新建 Dialog */
	var tab;
	var form;
	var dialogEmployee;
	function dialogEmployeeInit(id,index) {
		dialogEmployee = new Overlay.Dialog({
			title: '新建员工信息',
			width: 960,
			height: 620,
			closeAction:'destroy',
			loader:{
				url : '<c:url value="/employee/newDlog"/>',
				autoLoad : false, //不自动加载
				lazyLoad : false,
				callback : function() {
					decide = false;
					oldDecide = false;
					if (id && id != '') {
						tab = new Tab.TabPanel({
							render: '#tabEmp',
							elCls: 'nav-tabs',
							panelContainer: '#panelEmp', //如果内部有容器，那么会跟标签项一一对应，如果没有会自动生成
							autoRender: true,
							children: [{
								title: '基本资料',
								value: '1',
								selected: (index && index == 1) || !index ? true : false,
							},{
								title: '工作酒店',
								value: '2',
								loader: {
									url: '<c:url value="/employee/workHotelDlog"/>',
									params: {}
								}
							},{
								title: '用户角色',
								value: '3',
								loader: {
									url: '<c:url value="/employee/customRoleDlog"/>',
									params: {}
								}
							}]
						});
					}else {
						tab = new Tab.TabPanel({
							render: '#tabEmp',
							elCls: 'nav-tabs',
							panelContainer: '#panelEmp', //如果内部有容器，那么会跟标签项一一对应，如果没有会自动生成
							autoRender: true,
							children: [{
								title: '基本资料',
								value: '1',
								selected: (index && index == 1) || !index ? true : false,
							}]
						});
					}
					
					if(index && index != 1) {
						tab.setSelected(tab.getItemAt(index-1));
					}
					
					tab.on('selectedchange', function(ev) {
	            		var item = ev.item;
	            		var value = item.get('value');
	            		if (value != 1) {
	            			if(tab.isItemSelected(item)){
	            				if(item.get('loader').get('isloaded')){
	            					item.get('loader').load();
	            				}
	            				item.get('loader').set('isloaded',true);
	            			}
	            		}
	            	});
					var node = dialogEmployee.get('el').find('form').eq(0); //查找内部的表单元素
					node.find('input, select, textarea').on('change', function(){
						if(first < 2){
							first++;
							decide = false;
						}else{
							first++;
			            	decide = true;
						}
		            }),
					form = new Form.HForm({
		                srcNode: node,
		                autoRender: true
		            });
					xForm = form;
				}
			},
			buttons: [{
				text: '保存 (S)',
				elCls: 'button button-primary key-S',
				handler: function() {
					var tabIndex = $('#tabEmp').find('.bui-tab-item-selected').index();
					if(tabIndex == 0){
						var cashPwdShowVal = $('#cashPwdShow').val();
						if(cashPwdShowVal != null && cashPwdShowVal != '' && cashPwdShowVal != '******'){
							$('#cashPwdHidden').val(cashPwdShowVal);
						};
						if (form.isValid()) {
							form&&form.ajaxSubmit({
								success : function(data, status) {
									decide = false;
									oldDecide = false;
									var statusA = data.status;
									if(statusA == 101){
										BUI.Message.Alert(data.message,'error');
										updateJobList(data.result,true);
										$('#defaultPostId').val('');
										return;
									}
									$('#isDelFlag').val('');
									if(statusA == 102){
										BUI.Message.Alert(data.message,'error');
										return;
									}
									if(statusA == 200){
										var accountAft = $('#accountAft').val();
										var staffIdVal = data.result;
										$('#staffId').val(staffIdVal); 
										$('#defaulHotelId').val($('#theHotel').val());
										$('#account').attr("disabled",true); 
										$('#accountAft').val($('#account').val());
										BUI.Message.Alert('保存成功!','success');
										decide = false;
										first = 0;
		    	                		
										if (accountAft == '') {
											tab.addChild({
												title: '工作酒店',
												value: '2',
												loader: {
													url: '<c:url value="/employee/workHotelDlog"/>',
												}
											});
											tab.addChild({
												title: '用户角色',
												value: '3',
												loader: {
													url: '<c:url value="/employee/customRoleDlog"/>',
												}
											});
										}
									}
									store.load();
								},
								dataType:"json"
							})
						};
					}else if(tabIndex == 1){
						var staffVal = $('#staffId').val();
						if(staffVal == null || staffVal == ""){
							 BUI.Message.Alert('基础信息未保存!','error');
							 return;
						}
						var parms = gridWorkHotel.getItems();
						if(parms == null || parms == ''){
							Bui.Message.Alert('您没有选择工作酒店!');
							return;
						}
						var postIdInfo = '';
						for(var i = 0;i < parms.length;i++){
							postIdInfo += parms[i].defaultPostId + '+';
						}
						$.ajax({
							url : '<c:url value="/employee/saveWorkHotel"/>',
	    	                type : "POST",  
	    	                data: {
	    	                	'postIdInfo': postIdInfo,
	    	                	'staffVal':staffVal
	    	                },
	    	                success : function(data, stats) {  	
	    	                	decide = false;
	    	                	if (data == "success") {
	    	                		decide = false;
	    							BUI.Message.Alert('保存成功!','success');
	    						}else{
	    							Message.Alert({msg:'请选择保存的成员信息！',icon:'error'});
	    							return;
	    						};
	    	                	store.load();
	    	                } 
	    	            });
					}else if(tabIndex == 2){
						var staffVal = $('#staffId').val();
						if(staffVal == null || staffVal == ""){
							 BUI.Message.Alert('基础信息未保存!','error');
							 return;
						}
						var parms = gridRight.getItems();
						$.ajax({
							url : '<c:url value="/employee/saveRight"/>',
	    	                type : "POST",  
	    	                data: {
	    	                	'parms': JSON.stringify(parms),
	    	                	'staffVal':staffVal
	    	                },
	    	                success : function(data, stats) {  
	    	                	decide = false;
	    	                	if (data == "success") {
	    	                		decide = false;
	    	                		BUI.Message.Alert('保存成功!','success');
	    						}else if(data == "before"){
	    							BUI.Message.Alert('结束日期大于开始日期!','error');
	    							return;
	    						}else{
	    							Message.Alert({msg:'请选择保存的成员信息！',icon:'error'});
	    							return;
	    						};
	    						store.load();
	    	                }
	    	            });
					}
				}
			}, {
				text: '关闭 (Esc)',
				elCls: 'button key-Esc',
				handler: function() {		
					var tabIndex = $('#tabEmp').find('.bui-tab-item-selected').index();
					if(tabIndex==0){
						if(oldDecide == true){
							Message.Confirm(this, {msg: '是否放弃已编辑的信息？' });
			        	}else{
			        		dialogEmployee.close();
			        	}
					}else{
						if(decide == true){
							Message.Confirm(this, {msg: '是否放弃已编辑的信息？' });
			        	}else{
			        		dialogEmployee.close();
			        	}
					}
					
				}
			}],
			listeners: {
				closeclick: function(e) {
					var tabIndex = $('#tabEmp').find('.bui-tab-item-selected').index();
					if(tabIndex==0){
						if(oldDecide == true){
							Message.Confirm(this, {msg: '是否放弃已编辑的信息？' });
			        	}else{
			        		dialogEmployee.close();
			        	}
					}else{
						if(decide == true){
							Message.Confirm(this, {msg: '是否放弃已编辑的信息？' });
			        	}else{
			        		dialogEmployee.close();
			        	}
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
	$('#btnAdd').on('click', function() {
		first = 0;
		dialogEmployeeInit();
		dialogEmployee.get('loader').load({"hotelId":$("#hotelId").val()});
	});

	window.changeEmployee = function(){
		decide = true;
		oldDecide = true;
	}
	
	var decide2 = false;// 默认未修改
	/* 用户登录策略 Dialog */
	var dialogStratery;
	function dialogStrateryInit(ids) {
		dialogStratery = new Overlay.Dialog({
			title: '用户登录策略设置',
			width: 560,
			height: 435,
			closeAction:'destroy',
			loader:{
				url : '<c:url value="/employee/strateryDlog"/>',
				params : {
					ids: ids,
					hotelId: $("#hotelId").val()
				},
				autoLoad : false, //不自动加载
				lazyLoad : false,
				failure: function(response,params){ // 错误调用
		        	// 关闭弹出窗口
		        	dialogStratery.close();
		        	Message.Alert({msg:'请求失败，请与系统管理员联系！', icon:'error'}); 
		        },
				callback : function() {
					decide2 = false;
					node = dialogStratery.get('el').find('form').eq(0); //查找内部的表单元素
		            form = new Form.HForm({
		                srcNode: node,
		                autoRender: true
		            }),
		            node.find('input, select, textarea').on('change', function() { //判断是否有修改内容
						decide2 = true;
					});
				}
			},
			buttons: [{
				text: '保存 (S)',
				elCls: 'button button-primary key-S',
				handler: function() {
					if (form.isValid()) {
						form&&form.ajaxSubmit({
							success : function(data) {
								var code = data.status;
								if(code == '200'){
									dialogStratery.close();
								}
							}
						});
					}
				}
			}, {
				text: '关闭 (Esc)',
				elCls: 'button key-Esc',
				handler: function() {
					if(decide2 == true){
						Message.Confirm(this, {msg: '是否放弃已编辑的信息？' });
		        	}else{
		        		dialogStratery.close();
		        	}
				}
			}],
			listeners: {
				afterRenderUI: function(e) {
					node = dialogStratery.get('el').find('form').eq(0); //查找内部的表单元素
		            form = new Form.HForm({
		                srcNode: node,
		                autoRender: true
		            });
				},
				closeclick: function(e) {
					if(decide2 == true){
						Message.Confirm(this, {msg: '是否放弃已编辑的信息？' });
		        	}else{
		        		dialogStratery.close();
		        	}
					return false;
				},
				afterDestroy: function(e) {
					decide2 = false;
				}
			}
		});
		dialogStratery.show();
	}
	$('#btnStratery').on('click', function() {
		var selects = grid.getSelection();
		var ids = '';
		if(selects.length <= 0){
 			Message.Alert({msg:'请选择需要设置的数据！'});
			return;
 		}
		var baseHotelId = '';
 		for(var i=0; i<selects.length; i++){
 			if(selects[i].defaultHotelId != null && selects[i].defaultHotelId != ''){
 				baseHotelId = selects[i].defaultHotelId;
 				break;
 			}
 		}
 		if(baseHotelId == null || baseHotelId == ''){
 			BUI.Message.Alert('请选择有组织的信息!','error');
 			return;
 		}
 		for(var i=0; i<selects.length; i++){
 			if(selects[i].defaultHotelId != baseHotelId ){
 				BUI.Message.Alert('请选择相同酒店!','error');
 				return;
 			}
 			ids += selects[i].id+"+";
 		}
 		var currentHotelId = $("#hotelId").val();
 		$("#btnStratery").prop('disabled',true);
 		$.ajax({
			type : "POST",
			dataType : "json",
			url : '<c:url value="/employee/isStrateryBlank" />',
			data : {ids:ids,hotelId: currentHotelId},
			success : function(result) {
				if (result.result) {
					dialogStrateryInit(ids);
					dialogStratery.get('loader').load({'ids':ids,'hotelId':currentHotelId});
				} else{
					BUI.Message.Confirm('选择的帐号已设置登录策略，确定覆盖设置？',function(){
						dialogStrateryInit(ids);
						dialogStratery.get('loader').load({'ids':ids,'hotelId':currentHotelId});
					}); 
				}
				$("#btnStratery").prop('disabled',false);
			}
		});
	});


	
//查询
	$('#btnStaffFind').on('click',function(){
		if(treeItem != null){
			orgId = treeItem.id;
			nodeType = treeItem.nodeType;
		}
		var statusVal = $('#statusId').val();
		var infoVal = $('#inforsId').val();
		var postId2 = $('#postId').val();
		
		
		var params = {
				'orgId':orgId,
				'nodeType':nodeType,
				'status':statusVal,
				'info':infoVal,
				'pageIndex':0,
				'start':0,
				'teamId':""
		}
		store.load(params);
		$("#hotelId").val($("#selectOrgHotelId").val())
	})
	
	// 添加默认角色移入提示信息
	window.empInner = function(){
		var items = gridLeft.getItems();
		if(items.length > 0){
			var selections = gridLeft.getSelection();
			if(selections.length > 0){
				decide = true;
			}else {
				Message.Alert({msg:'请选择需要移入的数据！'});	
				decide = true;
			}
		}
	}
	
	// 添加默认角色移出提示信息
	window.empOuter = function(){
		var items = gridRight.getItems();
		if(items.length > 0){
			var selections = gridRight.getSelection();
			if(selections.length > 0){
				decide = true;
			}else {
				Message.Alert({msg:'请选择需要移出的数据！'});	
				decide = true;
			}
		}
	}

});


function updateJobList(dptId,flag){
	if (dptId != $('#hideEmyDept').val() || flag) {
		$('#theJob').val('').change();
		$('#theJob').trigger('chosen:updated');
		$.ajax({
			type : "POST",
			dataType : "json",
			url : '<c:url value="/post/findPosts"/>',
			data : {dptId:dptId},
			async : false,
			success : function(result) {
				if (result.code == "200") {
					var obj = result.obj;
					var html = '<option value="">选择所属职位</option>';
					for(var i = 0;i< obj.length;i++){
						html += "<option value='" + obj[i].id+"'>"+obj[i].name+"</option>";
					}
					$('#theJob').html(html);
		  			$('#theJob').trigger('chosen:updated');
				} 
			}
		});
	}
}
function updateCardTypeList(hotelId){
	if (hotelId != '') {
		$.ajax({
			type : "POST",
			dataType : "json",
			url : '<c:url value="/employee/findCardType"/>',
			data : {hotelId:hotelId},
			async : false,
			success : function(result) {
				if (result.code == "200") {
					var obj = result.obj;
					var html = '<option value="">选择证件类型</option>';
					for(var i = 0;i< obj.length;i++){
						html += "<option value='" + obj[i].id+"'>"+obj[i].name+"</option>";
					}
					$('#idCardType').html(html);
		  			$('#idCardType').trigger('chosen:updated');
				} 
			}
		});
	}
}



</script>
</body>
</html>