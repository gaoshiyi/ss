<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> 
<!DOCTYPE html>
<html>
<head>
<title>工作圈管理</title>
<jsp:include page="../../common/header-2.0.0.jsp"></jsp:include>
<link href="<c:url value="/resources/css/management.css"></c:url>" rel="stylesheet">
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
								<label class="control-label control-label-auto">状态：</label>
								<div class="control-container box-w150 pull-left">
									<select class="choose" data-placeholder="选择状态" id="statusSel">
										<option value="">选择状态</option>
										<option value="0">正常</option>
										<option value="2">锁定</option>
									</select>
								</div>
							</div>
						</div>
						<div class="control-item">
							<div class="control-group ml20">
								<label class="control-label control-label-auto">是否可申请加入：</label>
								<div class="control-container box-w140 pull-left">
									<select class="choose" data-placeholder="是否可申请加入" id="isJoinSel">
										<option value="">是否可申请加入</option>
										<option value="0">可以</option>
										<option value="1">不可以</option>
									</select>
								</div>
							</div>
						</div>
						<div class="control-item">
							<div class="control-group ml20">
								<label class="control-label control-label-auto">分类：</label>
								<div class="control-container box-w150 pull-left">
									<select class="choose" data-placeholder="选择分类" id="typeSel">
										<option value="">选择分类</option>
										<c:forEach items="${typeSel}" var ="val">
							          		<option value="${val.key}">${val.value}</option>
							        	</c:forEach>
									</select>
								</div>
							</div>
						</div>
						<div class="control-item">
							<div class="control-group ml20">
								<button class="button button-primary button-query" type="button" id="btnFind" data-keyname="N">查询 (F)</button>
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
					<shiro:hasPermission name="workTeam_modify">
						<button class="button button-primary" id="btnAdd" type="button" data-keyname="N">新建 (N)</button>
						<button class="button button-primary" id="btnEdit" type="button" data-keyname="E" onclick="editRow('')">修改 (E)</button>
					</shiro:hasPermission>
					<shiro:hasPermission name="workTeam_delete">
						<button class="button button-primary ml10" id="btnDel" type="button" data-keyname="D" onclick="delRow('')">解散 (D)</button>
					</shiro:hasPermission>
				</div>
				<div class="pull-right">
					<button class="btn-icon btn-icon-small btn-icon-up" type="button" title="上移" disabled></button>
					<button class="btn-icon btn-icon-small btn-icon-down" type="button" title="下移" disabled></button>
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
var gridRight;
var gridLeft;
BUI.use(['bui/grid', 'bui/data', 'bui/overlay', 'bui/form', 'bui/tree', 'bui/tab'], function(Grid, Data, Overlay, Form, Tree, Tab) {
    /* Grid */
	var Grid = Grid,
    Store = Data.Store,
    typeSel = {
    		<c:forEach items="${typeSel}" var ="val">
	          "${val.key}":"${val.value}",
	        </c:forEach>	
	},
	grpHotelSel = {
			<c:forEach items="${groupHotelList}" var ="val">
	          "${val.id}":"${val.name}",
	        </c:forEach>
    },
    columns = [
 			{ title: '操作',dataIndex: 'id', sortable: false, width: 160, renderer : function(value,obj){
	 				var lock = obj.status == 2 ? 'grid-icon-lock' : 'grid-icon-unlock';
					return  '<shiro:hasPermission name="workTeam_modify"><a class="grid-icon grid-icon-edit btn-edit" title="编辑" href="javascript:;" onclick="editRow(\''+value+'\');"></a></shiro:hasPermission>'+
					'<shiro:hasPermission name="workTeam_delete"><a class="grid-icon grid-icon-del btn-del" title="解散" href="javascript:;" onclick="delRow(\''+value+'\');"></a></shiro:hasPermission>'+
					'<shiro:hasPermission name="workTeam_lock"><a class="grid-icon '+lock+' btn-lock" title="锁定" href="javascript:;" onclick="lock(\''+value+'\',this);"></a></shiro:hasPermission>'+
					'<shiro:hasPermission name="workTeam_members"><a class="grid-icon grid-icon-user-add btn-user-add" title="添加员工" href="javascript:;" onclick="userAdd(\''+value+'\');"></a></shiro:hasPermission>'
 				}
 			},
 			{ title: '名称',dataIndex: 'name', elCls: 'text-left', width: 100, showTip: true },
 			{ title: '外文名称',dataIndex: 'englishName', elCls: 'text-left', width: 100, showTip: true },
 			{ 
 				title: '分类',
 				dataIndex: 'typeId', 
 				elCls: 'text-left', 
 				width: 100,
 				renderer : Grid.Format.enumRenderer(typeSel)
 			},
 			{ title: '创建人',dataIndex: 'master', elCls: 'text-left', width: 70, showTip: true },
 			{ title: '状态',dataIndex: 'status', width: 60, renderer : Grid.Format.enumRenderer({
 					'0':'正常',
 					'2':'锁定',
 				})
 			},
 			{ title: '可申请加入',dataIndex: 'applyJoin', width: 80, renderer : Grid.Format.enumRenderer({
 					'1':'不可以',
 					'0':'可以'
 				})
 			},
 			{ title: '管理员',dataIndex: 'administrator', elCls: 'text-left', width: 200, showTip: true, renderer : function(value){
 					return '<span title="'+ value +'">'+ value +'</span>'
 				}
 			},
 			{ title: '成员人数',dataIndex: 'memberCount', elCls: 'text-right', width: 70, showTip: true, renderer : function(value,obj,index) {
	 				//return '<a class="text-primary" href="javascript:;" onclick="toStaffList(\''+obj.id+'\');">'+ value +'</a>'	
	 				return '<a class="text-primary text-underline page-action" href="'+ ctx + '/employee/page?teamId=' + obj.id + '" data-id="29665f2219dd11e7b364000c29bcba56" data-title="员工管理" data-type="open" data-reload="true">'+ value +'</a>'	
 				}
 			},
 			{ 
 				title: '所属集团或酒店',
 				dataIndex: 'belongId',
 				elCls: 'text-left', 
 				width: 200 ,
 				showTip: true,
 				renderer : Grid.Format.enumRenderer(grpHotelSel)
			},
 		];
    var store = new Store({
    	url : '<c:url value="/workTeam/index"/>',
        autoLoad: true,
        pageSize:10,
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
 // 跳转到员工列表页面
	window.toStaffList = function(id){
	 	if(window.parent) {
	 		window.parent.tab.addTab({
				title: '员工管理',
				href: '${pageContext.request.contextPath}/employee/page?teamId=' + id,
				id: 'customer_query'
			});
	 	} else {
	 		window.location.href='${pageContext.request.contextPath}/employee/page?teamId=' + id;
	 	}
		
	}
//查询圈成员信息；
    $('#btnFind').on('click',function(){
    	var statusVal = $("#statusSel").val();
    	var isJoinVal = $("#isJoinSel").val();
    	var typeVal = $("#typeSel").val();
    	var nameVal = $("#name").val();
    	var params = {
   			status:statusVal,
   			applyJoin:isJoinVal,
   			typeId:typeVal,
   			name:nameVal
    	};
    	store.load(params);
    });
    
//行解散
	window.delRow = function(idvalue) {
		if(idvalue == "" || idvalue == null){
			var selections = grid.getSelection();
			if(selections.length == 0){
				Message.Alert({msg:'请选择需要解散的数据！'});
				return;
			}
			for (var i = 0; i < selections.length; i++) {
 				idvalue += selections[i].id + ",";
 			}
		}
		Message.Confirm(this,{msg:'确定解散吗？',fn:function(obj){
	    	$.ajax({
	    		url : '<c:url value="/workTeam/delete"/>',
				type : 'post',
				data : {
					idvalue : idvalue,
					status : 1
				},
				dateType : 'html',
				success : function(data, textStatus) {
					if (data == "success") {
						store.load();
						obj.close();
					}
				}
			});
	    }})
	};

//行锁定
	window.lock = function(idvalue,obj) {
		var msg = $(obj).hasClass('grid-icon-unlock') ? '确定锁定吗？' : '确定解锁吗？';
		Message.Confirm(this,{msg:msg,fn:function(dialog){
	    	$.ajax({
	    		url : '<c:url value="/workTeam/lock"/>',
				type : 'post',
				data : {
					idvalue : idvalue
				},
				dateType : 'html',
				success : function(data, textStatus) {
					store.load();
					dialog.close();
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
	};
//判断是否填写数据默认为没有false
	var decide = false;
/* 新建 Dialog */
	var dialogWorkGroup;
	function dialogWorkGroupInit(id) {
		dialogWorkGroup = new Overlay.Dialog({
			title: '新建工作圈',
			width: 880,
			height: 340,
			contentId: 'contentWorkGroup',
			closeAction:'destroy',
			loader:{
				url : '<c:url value="/workTeam/newDlog"/>',
				autoLoad : false, //不自动加载
				lazyLoad : false,
				failure: function(response,params){ // 错误调用
		        	// 关闭弹出窗口
		        	dialogWorkGroup.close();
		        	Message.Alert({msg:'请求失败，请与系统管理员联系！', icon:'error'}); 
		        },
				callback : function() {
					decide = false;
					node = dialogWorkGroup.get('el').find('form').eq(0); //查找内部的表单元素
		            form = new Form.HForm({
		                srcNode: node,
		                autoRender: true
		            });
		            node.find('input, select, textarea').on('change', function(){
		            	decide = true;
		            });
				}
			},
			buttons: [{
				text: '保存 (S)',
				elCls: 'button button-primary key-S',
				handler: function() {					
					if (form.isValid()) {
		                //可以直接action 提交
						form&&form.ajaxSubmit({
							success : function(data, status) {
								if(data == "repeat"){
									BUI.Message.Alert('已存在!','warning');
									return;
								}
								dialogWorkGroup.close();
								store.load();
							},
							dataType:"text"
						});
					}
				}
			}, {
				text: '关闭 (Esc)',
				elCls: 'button key-Esc',
				handler: function() {
					if(decide == true){
		        		Message.Confirm(this, {msg: '是否放弃已编辑的信息？' });
		        	}else{
		        		dialogWorkGroup.close();
		        	}
				}
			}],
			listeners: {
				closeclick: function(e) {
					if(decide == true){
		        		Message.Confirm(this, {msg: '是否放弃已编辑的信息？' });
		        	}else{
		        		dialogWorkGroup.close();
		        	}
					return false;
				},
				afterDestroy: function(e) {
					decide = false;
				}
			}
		});
		dialogWorkGroup.show()
	}
	$('#btnAdd').on('click', function() {
		dialogWorkGroupInit();
		dialogWorkGroup.get('loader').load();
	});
	
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
		dialogWorkGroupInit(id);
		dialogWorkGroup.set('title','修改工作圈');
		dialogWorkGroup.get('loader').load({'id':id});
	}
	var decide2 = false;
	/* 添加员工 Dialog */
	var dialogEmployee;
	var first = 0;
	function dialogEmployeeInit(id){
		dialogEmployee = new Overlay.Dialog({
			title: '添加员工',
			width: 1055,
			height: 520,
			closeAction:'destroy',
			loader:{
				url : '<c:url value="/workTeam/addGrpMemberDlog"/>',
				autoLoad : false, //不自动加载
				lazyLoad : false,
				failure: function(response,params){ // 错误调用
		        	// 关闭弹出窗口
		        	dialogEmployee.close();
		        	Message.Alert({msg:'请求失败，请与系统管理员联系！', icon:'error'}); 
		        },
				callback : function() {
					decide2 = false;
				 	var node = dialogEmployee.get('el').find('form'); //查找内部的表单元素
		            form = new Form.HForm({
		                srcNode: node,
		                autoRender: true
		            }),
		            node.find('input, select, textarea').on('change', function() { //判断是否有修改内容 
		            	if(first < 2){
							first++;		           		
			           	}else{
			           		first++;	
			            	decide2 = true;
			           	}
					});
				}
			},
			buttons: [{
				text: '保存 (S)',
				elCls: 'button button-primary key-S',
				handler: function() {
					var parms = gridRight.getItems();
					$.ajax({
						url : '<c:url value="/workTeam/saveGrpMembers"/>',
    	                type : "POST",  
    	                data: {'parms': JSON.stringify(parms)},
    	                success : function(data, stats) {  
    	                	if (data == "success") {
    							store.load();
    						}
    	                }
    	            });
					this.close();
				}
			}, {
				text: '关闭 (Esc)',
				elCls: 'button key-Esc',
				handler: function() {
					//Message.Confirm(this, {msg: '确认关闭吗？'});
					//this.close();
					if(decide2 == true){
						 Message.Confirm(this, { msg: '是否放弃已编辑的信息？' });
					}else{
						dialogEmployee.close();
					}
				}
			}],
			listeners: {
				closeclick: function(e) {
					if(decide2 == true){
						Message.Confirm(this, {msg: '是否放弃已编辑的信息？' });
					}else{
						dialogEmployee.close();
					}
					return false;
				},
				afterDestroy: function(e) {
					decide2 = false;
				}
			}
		});
		dialogEmployee.show();
	}
//添加员工
	window.userAdd = function(id) {
		first = 0;
		dialogEmployeeInit(id);
		dialogEmployee.get('loader').load({'teamId':id});
	}
	
	// 添加员工移入提示信息
	window.empInner = function(){
		var items = gridLeft.getItems();
		if(items.length > 0){
			var selections = gridLeft.getSelection();
			if(selections.length > 0){
				decide2 = true;
			}else {
				Message.Alert({msg:'请选择需要移入的数据！'});	
				decide2 = true;
			}
		}
	}
	
	// 添加员工移出提示信息
	window.empOuter = function(){
		var items = gridRight.getItems();
		if(items.length > 0){
			var selections = gridRight.getSelection();
			if(selections.length > 0){
				decide2 = true;
			}else {
				Message.Alert({msg:'请选择需要移出的数据！'});	
				decide2 = true;
			}
		}
	}

});
</script>
</body>
</html>