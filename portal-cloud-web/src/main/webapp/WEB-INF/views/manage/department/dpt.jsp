<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> 
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>部门列表</title>
</head>
<body>
<!-- 部门列表 start -->
<div class="tab-content panel-body pd0">
	<!-- filter-box start -->
<div class="panel filter-box">
	<div class="panel-body">
		<form class="form-horizontal bui-form-horizontal bui-form bui-form-field-container" method="post" action="" autocomplete="off" data-plus-as-tab="true">
			<div class="clearfix">
				<div class="control-item">
					<div class="control-group">
						<label class="control-label control-label-auto">组织：</label>
						<div class="control-container pull-left">
							<input id="hideDpt" type="hidden" name="" value="${orgId}">
							<!-- <input class="control-text input-full multi-select" id="showDpt" type="text" name="" value="" placeholder="选择组织" readonly> -->
							<span class="control-form-text nowrap input-full" id="showDpt"></span>
						</div>
					</div>
				</div>
				<div class="control-item">
					<div class="control-group ml20">
						<label class="control-label control-label-auto">部门类型：</label>
						<div class="control-container box-w130 pull-left">
							<select class="choose" data-placeholder="选择部门类型" id="deptType">								
								<option value="0">直属</option>
								<option value="1">全部</option>
							</select>
						</div>
					</div>
				</div>
				<div class="control-item">
					<div class="control-group ml10">
						<div class="control-container">
							<input class="control-text input-w220" type="text" name="" value="" placeholder="名称/外文名称" id="deptName">
						</div>
					</div>
				</div>
				<div class="control-item">
					<div class="control-group ml20">
						<button class="button button-primary button-query" type="button" id="search" data-keyname="N">查询 (F)</button>
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
		<shiro:hasPermission name="dpt_modify">
			<button class="button button-primary" id="btnAddDpt" type="button" data-keyname="N">新建 (N)</button>
			<button class="button button-primary" id="btnEditDpt" onclick="btnEditDpt()"  type="button" data-keyname="E">修改 (E)</button>
		</shiro:hasPermission>
		<shiro:hasPermission name="dpt_del">
			<button class="button button-primary ml10" id="btnDelDpt" type="button" data-keyname="D">删除 (D)</button>
		</shiro:hasPermission>
	</div>
	<div class="pull-right">
		<button class="btn-icon btn-icon-small btn-icon-up" type="button" title="上移"></button>
		<button class="btn-icon btn-icon-small btn-icon-down" type="button" title="下移"></button>
		<button class="btn-icon btn-icon-small btn-icon-import" type="button" title="导入"></button>
		<button class="btn-icon btn-icon-small btn-icon-export" type="button" title="导出"></button>
		<!-- <button class="btn-icon btn-icon-small btn-icon-export-column" type="button" title="列导出"></button> -->
		<button class="btn-icon btn-icon-small btn-icon-authority" type="button" title="列权限"></button>
		<button class="btn-icon btn-icon-small btn-icon-edit" type="button" title="列调整"></button>
	</div>
</div>
<!-- grid-top-bar end -->

<!-- grid start -->
<div class="grid-content" id="gridDpt"></div>
<!-- grid end -->
</div>
<!-- 部门列表 end -->
					
<script>

$(function () {
	chooseConfig();
});



BUI.use(['bui/grid', 'bui/data', 'bui/overlay', 'bui/form', 'bui/tree', 'bui/extensions/treepicker', 'bui/tab'], function(Grid, Data, Overlay, Form, Tree, TreePicker, Tab) {
	
	/* 部门列表 ================================================================================= */
	/* Grid */
	var GridDpt = Grid,
        StoreDpt = Data.Store,
        columnsDpt = [
			{ title: '操作',dataIndex: 'id', sortable: false, width: 70, renderer : function(value){ 
					return  '<shiro:hasPermission name="dpt_modify"><a class="grid-icon grid-icon-edit btn-edit" title="编辑" href="javascript:;" onclick="editDeptRow(\''+value+'\');"></a></shiro:hasPermission>'+
					'<shiro:hasPermission name="dpt_del"><a class="grid-icon grid-icon-del btn-del" title="删除" href="javascript:;" onclick="delDeptRow(\''+value+'\');"></a></shiro:hasPermission>'
				}
			},
			{ title: '名称',dataIndex: 'name', elCls: 'text-left', width: 100, showTip: true },
			{ title: '外文名称',dataIndex: 'englishName', elCls: 'text-left', width: 100, showTip: true },
			{ title: '编码',dataIndex: 'code', elCls: 'text-center', width: 100, showTip: true },
			{ title: '职位数量',dataIndex: 'jobCount', elCls: 'text-right', width: 80, showTip: true, renderer: 
				function(value,obj,index) {
					if(jobVisible){
						return '<a class="text-primary  text-underline" href="javascript:;" onclick="toPostTab(\''+obj.id+'\');">'+ value +'</a>'
					}else{
						return value;
					}
				}
			},
			{ title: '员工数量',dataIndex: 'employeeCount', elCls: 'text-right', width: 80, showTip: true, renderer: 
				function(value,obj,index) {
					if(employeeVisible){
						return '<a class="text-primary  text-underline" href="javascript:;" onclick="toStaffTab(\''+obj.id+'\');">'+ value +'</a>'
					}else{
						return value;
					}
				}
			},
			{ title: '组织',dataIndex: 'orgName', elCls: 'text-left', width: 260, showTip: true }
		];
       
    var storeDpt = new StoreDpt({
    	 url : '<c:url value="/department/findDepartmentList"/>',
			params : {
				id : '${orgId}',				
				nodeType:'${nodeType}'
			},
        autoLoad: true,
        listeners : {
	       	exception : function(ev){
	       		Message.Alert({msg:'请求失败，请与系统管理员联系！', icon:'error'});
	       	}
        }
    }),
	gridDpt = new GridDpt.Grid({
		render: '#gridDpt',
		columns: columnsDpt,
		width: '100%',
		store: storeDpt,
		emptyDataTpl: '<div class="empty-data"><span>暂无数据</span></div>',
		plugins: [Grid.Plugins.CheckSelection, Grid.Plugins.RowNumber, Grid.Plugins.ColumnResize],
		bbar:{
			pagingBar: {
				xclass: 'pagingbar-number'
			}
		}
	});
    
    gridDpt.render();
    
   	//搜索
   $("#search").bind("click",function(){
		
		var deptType=$("#deptType").val();
		var deptName=$("#deptName").val().replace(/(^\s*)|(\s*$)/g, "");
		
		var groupId=$("#deptHotel").val();
		var groupType="${groupType}";
		
		if(groupId == ''){
			groupId = "${parentId}";
		}
		storeDpt.load({
			deptType:deptType,
			deptName:deptName,
			groupId:groupId,
			groupType:groupType
		})
	})
	
	window.btnEditDpt = function(){
		var selects = gridDpt.getSelection();
		if(selects.length <= 0){
			Message.Alert({msg:'请选择需要修改的数据！'});
			return;
		}else if(selects.length > 1){
			Message.Alert({msg:'请选择一条数据进行修改！'});
			return;
		}else{
			var selectObj = gridDpt.getSelected();
			var id = selectObj.id;
			dialogDptInit(id);
	   		dialogDpt.set('title','修改部门');
	   		dialogDpt.get('loader').load({id:id});
		}
	}
	
	window.toPostTab = function(id){
		var item = selectTab.getItemAt(3);
		item.get('loader').load({orgId: id, nodeType: 2});
	  	selectTab.setSelected(item);
   	}
	window.toStaffTab = function(id){
		var item = selectTab.getItemAt(4);
		item.get('loader').load({orgId: id, nodeType: 2});
		orgId = id;
		nodeType = 2;
		var node = OrgTree.findNode(orgId);
		OrgTree.expandNode(node);
		OrgTree.setSelection(node);
	  	selectTab.setSelected(item);
   	}

	//行编辑
	window.editDeptRow = function(id) {
		dialogDptInit(id);
   		dialogDpt.set('title','修改部门');
   		dialogDpt.get('loader').load({id:id});
	}

	//行删除
	window.delDeptRow = function(id) {
		BUI.Message.Confirm('确认删除吗？',function(){
			$.ajax({
				url : '<c:url value="/department/deleteDepartmentInfo"/>',
				data : {
					id : id					
				},
				dateType : 'html',
				success : function(data) {
					if (data == '-1') {
						Message.Alert({msg:'部门关联员工或职位，无法删除！'});
						return;
					}else{
						if(groupId == ''){
							groupId = "${parentId}";
						}
						var deptType=$("#deptType").val();
						var deptName=$("#deptName").val();
						var groupId=$("#deptHotel").val();
						var groupType="${groupType}";
						storeDpt.load({
							deptType:deptType,
							deptName:deptName,
							groupId:groupId,
							groupType:groupType
						});
						OrgTree.get('store').load();
					}
				}
			});
           },'question');		
	}

	//decide是否修改判断
	var decide = false;// 默认未修改
	var dialogDpt;
	function dialogDptInit(id) {
		dialogDpt = new Overlay.Dialog({
			title: '新建部门',
			width: 430,
			height: 420,
			contentId: 'contentDpt',
			closeAction:'destroy',
			loader:{
				url : '<c:url value="/department/editDptDlog"/>',
				autoLoad : false,
				lazyLoad : false,
				params:{orgId:orgId},
				failure: function(response,params){ // 错误调用
		        	// 关闭弹出窗口
		        	dialogDpt.close();
		        	Message.Alert({msg:'请求失败，请与系统管理员联系！', icon:'error'}); 
		        },
				callback : function() {
					decide = false;
					var node = dialogDpt.get('el').find('form').eq(0); //查找内部的表单元素
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
					if (form.isValid()) {
		                //可以直接action 提交
		                debugger;
		                var hotelId = $('#sHotel').val();
		                $("#hotelIdx").val(hotelId);
		                
						form&&form.ajaxSubmit({
							success : function(data, status) {
								
								data = eval("("+data+")");
								if(data.code == '204'){
									Message.Alert({msg:'名称已经存在',icon:'error'});	
									return;
								}else if(data.code == '205'){
									Message.Alert({msg:'编码已经存在',icon:'error'});	
									return;
								}
								dialogDpt.close();
								var obj = data.obj;
								var item = {text: obj.name, id: obj.id, checked: false, nodeType: 2, cls: "icon-dpt"};
								var pid = obj.pid;
								if(pid == null){
									pid = obj.hotelId;								
								}
								var node = OrgTree.findNode(pid);
								var addNode = OrgTree.findNode(item.id);
								if(addNode != null){
									OrgTree.get('store').remove(addNode);
								}
								
								//OrgTree.get('store').add(item,node);
								OrgTree.get('store').load();
								var deptType=$("#deptType").val();
								var deptName=$("#deptName").val();
								var groupId=$("#deptHotel").val();
								var groupType="${groupType}";
								if(groupId == ''){
									groupId = "${parentId}";
								}
								storeDpt.load({
									deptType:deptType,
									deptName:deptName,
									groupId:groupId,
									groupType:groupType
								});
								
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
						dialogDpt.close();
					}
				}
			}],
			
			listeners: {
				closeclick: function(e) {
					if(decide == true){
						Message.Confirm(this, {msg: '是否放弃已编辑的信息？' });
					}else{
						dialogDpt.close();
					}
					return false;
				},
				afterDestroy: function(e) {
					decide = false;
				}
			}
		});
		dialogDpt.show();
	}
	$('#btnAddDpt').on('click', function() {
		dialogDptInit();
		dialogDpt.get('loader').load();
	});
	
	
	$("#btnDelDpt").on('click',function(){
		var selects = gridDpt.getSelection();
		if(selects.length <= 0){
			Message.Alert({msg:'请选择需要删除的数据！'});
			return;
		}else {
			var ids = "";
			for (var i = 0; i < selects.length; i++) {
				ids += selects[i].id + ",";
			}
			if(ids == ""){
				BUI.Message.Confirm('请选择需要删除的数据！',function(){
					
	               },'question');
				
			}else{
				BUI.Message.Confirm('确认删除吗？',function(){
					$.ajax({
						url : '<c:url value="/department/deleteDepartmentInfo"/>',
						type : 'get',
						data : {
							id : ids
						},
						dateType : 'html',
						success : function(data, textStatus) {
							if (data == '-1') {
								Message.Alert({msg:'部门关联员工或职位，无法删除！'});
								return;
							}else{
								storeDpt.load();
								for (var i = 0; i < selects.length; i++) {
									var id = selects[i].id ;
									OrgTree.get('store').remove(OrgTree.findNode(id));
								}
							}
						}
					});
	               },'question');
				
			}
		}
		
	});
	
	
	/*组织树*/
/* 	var storeDpt = new Data.TreeStore({
        url : '<c:url value="/post/treeOrg"/>',
        autoLoad : true,
        params:{hotelId:orgId,orgLevel:3}
      }),
    treeDpt = new Tree.TreeList({
	      store : storeDpt,
	      elCls: 'tree-noicon',
	      checkType: 'none'
    });
  	var pickerDpt = new TreePicker({
	      trigger : '#showDpt',  
	      valueField : '#hideDpt', //如果需要列表返回的value，放在隐藏域，那么指定隐藏域
	      width: $('#showDpt').outerWidth(),  //指定宽度
	      children : [treeDpt] //配置picker内的列表
    });
  	pickerDpt.render();
  	
  	var treeItem;
  	treeDpt.on('itemclick',function(ev){
  		treeItem = ev.item;
	});
  	
    if(typeof(OrgTree) == 'undefined'){
  		OrgTree = treeDpt;
  	} */
  	setTimeout(function(){
	  	var org = OrgTree.findNode(orgId);
	  	var orgName = org.text;
	  	$("#showDpt").text(orgName);
  	},10)
	
	/*  ================================================================================= */	
	
});
</script>
</body>
</html>