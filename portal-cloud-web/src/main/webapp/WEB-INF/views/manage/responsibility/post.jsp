<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> 
<!-- 职位列表 start -->
<div class="tab-content panel-body pd0">
	<input type="hidden" id="belongId" value="${orgId }"/>
	<!-- filter-box start -->
	<div class="panel filter-box">
		<div class="panel-body">
			<form class="form-horizontal bui-form-horizontal bui-form bui-form-field-container" method="post" action="" autocomplete="off" data-plus-as-tab="true">
				<div class="clearfix">
					<div class="control-item">
						<div class="control-group">
							<label class="control-label control-label-auto">组织：</label>
							<div class="control-container pull-left">
								<input id="hideOpt" type="hidden" name="" value="${orgId}">
								<!-- <input class="control-text input-full multi-select" id="showOpt" type="text" name="" value="" placeholder="选择组织" readonly> -->
								<span class="control-form-text nowrap input-full" id="showOpt"></span>
							</div>
						</div>
					</div>
					<div class="control-item">
						<div class="control-group ml20">
							<div class="control-container">
								<input class="control-text input-w220" type="text" name="" value="" placeholder="名称/外文名称" id="postName">
							</div>
						</div>
					</div>
					<div class="control-item">
						<div class="control-group ml20">
							<button class="button button-primary button-query" id="selectButton" type="button" data-keyname="N">查询 (F)</button>
						</div>
					</div>
				</div>
			</form>
		</div>
		<hr>
	</div>
	<!-- filter-box end -->

	<!-- grid-top-bar start -->
	<div class="grid-top-bar clearfix">
		<div class="action-pop pull-left">
			<shiro:hasPermission name="post_modify">
				<button class="button button-primary button-alike" id="btnAddJob" type="button" data-keyname="N">新建 (N)</button>
				<button class="button button-primary button-alike" id="btnEditJob" onclick="btnEdit()" type="button" data-keyname="E">修改 (E)</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="post_del">
				<button class="button button-primary button-alike ml10" id="btnDelJob" type="button" data-keyname="D">删除 (D)</button>
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
	<div class="grid-content" id="gridJob"></div>
	<!-- grid end -->
</div>
<!-- 职位列表 end -->				

					
<script>
$(function () {
	chooseConfig();
});
</script>
<script>
var gridSelected2;
var gridSelect2;
var treeItemDpt;
var treeItemDpt2;
BUI.use(['bui/grid', 'bui/data', 'bui/overlay', 'bui/form', 'bui/tree', 'bui/extensions/treepicker', 'bui/tab'], function(Grid, Data, Overlay, Form, Tree, TreePicker, Tab) {
	/* 职位列表 ================================================================================= */
	/* Grid */
	var GridJob = Grid,
        StoreJob = Data.Store,
        columnsJob = [
			{ title: '操作',dataIndex: 'id', sortable: false, width: 80, renderer : function(value){
					return  '<shiro:hasPermission name="post_modify"> <a class="grid-icon grid-icon-edit btn-edit" title="编辑" href="javascript:;" onclick="editRowJob(\''+value+'\');"></a> </shiro:hasPermission>'+
					'<shiro:hasPermission name="post_del"> <a class="grid-icon grid-icon-del btn-del" title="删除" href="javascript:;" onclick="delRowJob(\''+value+'\');"></a> </shiro:hasPermission>'
				}
			},
			{ title: '职级',dataIndex: 'name', elCls: 'text-left', width: 100, showTip: true },
			{ title: '名称',dataIndex: 'englishName', elCls: 'text-left', width: 100, showTip: true },
			{ title: '默认工作圈',dataIndex: 'defaultTeamName', elCls: 'text-left', width: 100, showTip: true },
			{ title: '默认角色',dataIndex: 'defaultRoleName', elCls: 'text-left', width: 150, showTip: true },
			{ title: '员工数量',dataIndex: 'staffCount', elCls: 'text-right', width: 80, showTip: true, renderer: 
				function(value,obj,index) {
					if(employeeVisible){
						return '<a class="text-primary text-underline" href="javascript:;" onclick="toStaffList(\''+obj.id+'\');">'+ value +'</a>'
					}else{
						return value;
					}
				}
			},
			{ title: '组织',dataIndex: 'organization', elCls: 'text-left', width: 200, showTip: true }
		];
	
    var storeJob = new StoreJob({
    	url : '<c:url value="/post/list"/>',
        autoLoad: true,
		pageSize: 10,
		params:{
			/* orgId:'${orgId}', */
			orgId : '${orgId}',
			nodeType:'${nodeType}'
		},
		listeners : {
	       	exception : function(ev){
	       		Message.Alert({msg:'<spring:message code="EO035"/>', icon:'error'});
	       	}
        }
    }),
	gridJob = new GridJob.Grid({
		render: '#gridJob',
		columns: columnsJob,
		width: '100%',
		//forceFit : true,
		store: storeJob,
		emptyDataTpl: '<div class="empty-data"><span>暂无数据</span></div>',
		plugins: [Grid.Plugins.CheckSelection, Grid.Plugins.RowNumber, Grid.Plugins.ColumnResize],
		bbar:{
			pagingBar: {
				xclass: 'pagingbar-number'
			}
		}
	});
    gridJob.render();

	//行编辑
	window.editRowJob = function(id) {
		var belongId = $("#belongId").val();
		dialogJobInit(id);
		dialogJob.set('title','修改部门职位信息');
		//dialogJobInit.get('loader').load({postId:id});
		dialogJob.get('loader').load({postId:id,belongId:belongId,hotelId:currentHotelId});
	}

	//行删除
	window.delRowJob = function(id) {
		BUI.Message.Confirm('确认删除吗？',function(){
			$.ajax({
				url : '<c:url value="/post/deletePostById"/>',
				type : "post",
				data : {
					postId : id
				},
				dataType : "html",
				success : function(data, textStatus) {
					if (data == "success") {
						storeJob.load();
					} else {
						Message.Alert({msg:'该职位拥有效的员工，无法删除!'});
					}
				}

			})
           },'question');
	}
	
	//删除选中的记录
	$("#btnDelJob").click(function(){
		var selections = gridJob.getSelection();
		var idvalue = "";
		for (var i = 0; i < selections.length; i++) {
			idvalue += selections[i].id + ",";
		}
		if(idvalue == ""){
			Message.Alert({msg:'请选择需要删除的数据！'});
		}else{
			BUI.Message.Confirm('确认删除吗？',function(){
				$.ajax({
					url : '<c:url value="/post/deleteAll"/>',
					type : 'get',
					data : {
						postIds : idvalue
					},
					dateType : 'html',
					success : function(data, textStatus) {
						if (data == "success") {
							storeJob.load();
						}else {
							Message.Alert({msg:'该职位拥有效的员工，无法删除!'});
						}
					}
				});
               },'question');
			
		}
	})
	
	// 查询
    $("#selectButton").click(function(){
		
		var postName = $("#postName").val();
		storeJob.load({
			'orgId':orgId,  // 部门id
			'condition':postName,
           	'nodeType':nodeType
		});
		
	})
	
	// 编辑按钮
	window.btnEdit = function(){
		var selects = gridJob.getSelection();
		if(selects.length <= 0){
			Message.Alert({msg:'请选择需要修改的数据！'});
		}else if(selects.length > 1){
			Message.Alert({msg:'请选择一条数据进行修改！'});
		}else{
			var selectObj = gridJob.getSelected();
			var id = selectObj.id;
			dialogJobInit(id);
			//dialogRoleInit.show();
			dialogJob.set('title','修改部门职位信息');
			var belongId = $("#belongId").val();
			dialogJob.get('loader').load({postId:id,belongId:belongId,hotelId:currentHotelId});
		}
	}
	
	// 跳转到员工列表页面
	window.toStaffList = function(id){
		var item = selectTab.getItemAt(4);
		orgId = id, nodeType = 3;
		item.get('loader').load();
	  	selectTab.setSelected(item);
	}
	//decide是否修改判断
	var decide = false;// 默认未修改
	/* 新建 Dialog */
	var dialogJob;
	function dialogJobInit(id) {
		dialogJob = new Overlay.Dialog({
			title: '设置部门职位信息',
			elCls: 'job-dialog',
			height: 620,
			closeAction:'destroy',
			loader:{
				url : '<c:url value="/post/form"/>',
				autoLoad: false, //不自动加载
			    lazyLoad: false,
			    failure: function(response,params){ // 错误调用
		        	// 关闭弹出窗口
		        	dialogJob.close();
		        	Message.Alert({msg:'<spring:message code="EO035"/>', icon:'error'}); 
		        }, 
			    callback : function() {
		        	decide = false;
		            var node = dialogJob.get('el').find('form'); //查找内部的表单元素
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
					
						if(id == null || id == ""){
							// 获取角色id
							var selections = gridSelected2.getItems();
							var idvalue = "";
							for (var i = 0; i < selections.length; i++) {
								idvalue += selections[i].id + ",";
							}
							$("#roleIds").val(idvalue);
							var text = $("#postNameId option:selected").text();
							$("#name").val(text);
							
							// 所属部门
							if(treeItemDpt != undefined){
								var noteType = treeItemDpt.nodeType;
								var deptId;
								if(noteType == 2){
									deptId = treeItemDpt.id;
								}
								$("#deptId").val(deptId);
							}
						} else {
							// 获取角色id
							var selections = gridSelected2.getItems();
							var idvalue = "";
							for (var i = 0; i < selections.length; i++) {
								idvalue += selections[i].id + ",";
							}
							var text = $("#postNameId option:selected").text();
							$("#name").val(text);
							$("#roleIds").val(idvalue);
								// 所属部门
								/* if(treeItemDpt2 != undefined){
									
									var noteType = treeItemDpt2.nodeType;
									var deptId;
									if(noteType == 2){
										deptId = treeItemDpt2.id;
									}
									$("#deptId").val(deptId);
									
								} */
								$("#postId").val(id);
						}
					
					
					
					
					/* if(id != null) {
						$("#postId").val(id);
					} */
					//验证是否通过
					if (form.isValid()) {
						form&&form.ajaxSubmit({
							success : function(data, status) {
								if(data == "success"){									
									dialogJob.close();
									storeJob.load();
									OrgtreeStore.load();
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
			}, {
				text: '关闭 (Esc)',
				elCls: 'button key-Esc',
				handler: function() {
					if(decide == true){
						 Message.Confirm(this, { msg: '是否放弃已编辑的信息？' });
					}else{
						dialogJob.close();
					}
				}
			}],
			listeners: {
				afterRenderUI: function(e) {
					node = dialogJob.get('el').find('form').eq(0); //查找内部的表单元素
		            form = new Form.HForm({
		                srcNode: node,
		                autoRender: true
		            });
				},
				closeclick: function(e) {
					if(decide == true){
						Message.Confirm(this, {msg: '是否放弃已编辑的信息？' });
					}else{
						dialogJob.close();
					}
					return false;
				},
				afterDestroy: function(e) {
					decide = false;
				}
			}
		});
		dialogJob.show();
	}
	$('#btnAddJob').on('click', function() {
		var belongId = $("#belongId").val();
		dialogJobInit();
		dialogJob.get('loader').load({'belongId':belongId,'hotelId':currentHotelId});
	});
	
	/*组织树*/
/* 	var storeOpt = new Data.TreeStore({
        url : '<c:url value="/post/treeOrg"/>',
        autoLoad : true,
        params:{hotelId:orgId,orgLevel:3}
      }),
    treeOpt = new Tree.TreeList({
	      store : storeOpt,
	      elCls: 'tree-noicon',
	      checkType: 'none'
    });
  	var pickerOpt = new TreePicker({
	      trigger : '#showOpt',  
	      valueField : '#hideOpt', //如果需要列表返回的value，放在隐藏域，那么指定隐藏域
	      width: $('#showOpt').outerWidth(),  //指定宽度
	      children : [treeOpt] //配置picker内的列表
    });
  	pickerOpt.render();
  	treeOpt.on('itemclick',function(ev){
  		treeItem = ev.item;
	});
  	
    if(typeof(OrgTree) == 'undefined'){
  		OrgTree = treeOpt;
  	} */
  	var treeItem;
  	var org = OrgTree.findNode(orgId);
  	var orgName = org.text;
  	$("#showOpt").text(orgName);
  	
	/*  ================================================================================= */
	// 添加默认角色移入提示信息
	window.roleInner = function(){
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
	
	// 添加默认角色移出提示信息
	window.roleOuter = function(){
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
	
	
	
});
</script>					
