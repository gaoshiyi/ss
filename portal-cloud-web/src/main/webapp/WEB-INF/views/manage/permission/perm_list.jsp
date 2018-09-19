<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../common/header-2.0.0.jsp"></jsp:include>
<meta charset="UTF-8">
<title>权限管理</title>
<link rel="stylesheet" href="<c:url value="/resources/css/management.css"/>">   
</head>

<body>
<div class="layout layout-fixed clearfix">
	<div class="content organization">
		<div class="side side-left-tree">
			<div class="side-content">
				<div class="tree-list" id="treeList"></div>
			</div>
		</div>
		<div class="side side-main">
			<div class="side-content">
				<!-- filter-box start -->
				<div class="filter-box">
					<div class="panel-body">
						<form class="form-horizontal" method="post" action="" autocomplete="off">
							<div class="clearfix">
								<div class="control-item">
									<div class="control-group">
										<label class="control-label control-label-auto">上级：</label>
										<div class="control-container box-w120 pull-left">
											<div class="input-box has-btn-dialog">
												<input id="hideQueryPid" type="hidden" name="" value="${queryPerm }">
												<input class="control-text input-full" id="showQueryPid" type="text" name="" value="" placeholder="选择上级" readonly>
												<button class="btn-dialog btn-treelist" id="btnQueryPid" type="button"></button>
											</div>
										</div>
									</div>
								</div>
								<div class="control-item">
									<div class="control-group ml20">
										<label class="control-label control-label-auto">名称：</label>
										<div class="control-container pull-left">
											<input class="control-text input-w160 SearchInput" type="text" name="" value="" placeholder="名称" id="nameQuery">
										</div>
									</div>
								</div>
								<div class="control-item">
									<div class="control-group ml20">
										<button class="button button-primary button-query" type="button" data-keyName="F" id="queryPerms">查询 (F)</button>
									</div>
								</div>
							</div>
						</form>
					</div>
				</div>
				<div class="grid-enwrap-diff">
					<!-- filter-box end -->
					<div class="grid-top-bar clearfix">
						<div class="action-pop pull-left">
							<button class="button button-primary" id="btnAdd" type="button" data-keyName="N">新建 (N)</button>
							<button class="button button-primary" id="btnEdit" type="button" data-keyName="E">修改 (E)</button>
							<button class="button button-primary ml10" id="btnDel" type="button" data-keyName="D">删除 (D)</button>
						</div>
					</div>
					<div class="grid-content" id="grid"></div>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="hide" id="contentPerm"></div>


<script>
$(function () {
	$('#treeList').perfectScrollbar();
	$(window).on('resize', function() {
		$('#treeList').perfectScrollbar('update');
	})
});
BUI.use(['bui/grid', 'bui/data', 'bui/overlay', 'bui/form', 'bui/tree', 'bui/extensions/treepicker', 'bui/tab','bui/select'], function(Grid,  Data, Overlay, Form, Tree, TreePicker, Tab,Select) {
	//树节点数据，
	//text : 文本，
	//id : 节点的id,
	//leaf ：标示是否叶子节点，可以不提供，根据childern,是否为空判断
	//expanded ： 是否默认展开
	//checked : 节点是否默认选中
	var treeStore = new Data.TreeStore({
				url : '<c:url value='/perm/tree'/>',
				autoLoad : true
			});
	var tree = new Tree.TreeList({
		render: '#treeList',
		store : treeStore,
		elCls: 'tree-noicon',
		checkType: 'none', //checkType:勾选模式，提供了4中，all,onlyLeaf,none,custom
		showLine: false,
		listeners : {
			itemrendered: function() {
				$('#treeList').perfectScrollbar('update');
			}
		}
	});
	tree.render();
	tree.on('collapsed expanded',function(ev){
		$('#treeList').perfectScrollbar('update');
	});
	tree.on('itemclick',function(ev){
		var item = ev.item;
	});

	var queryTree = new Tree.TreeList({
		store: treeStore,
		elCls: 'tree-noicon',
        checkType: 'none'
	});
	var pickerPerm = new TreePicker({
		autoAlign: false,
		align: {
			node: '#showQueryPid',
 			points: ['bl', 'tl'],
 			offset: [0, 1]
		},
		trigger : '#btnQueryPid',
		textField : '#showQueryPid',  
        valueField : '#hideQueryPid',  
		width: 200, //指定宽度
		children: [queryTree] //配置picker内的列表
	});
	pickerPerm.render();
	
	
	
	
	/* 权限列表 ================================================================================= */
	/* Grid */
	var Grid = Grid,
        Store = Data.Store,
        columns = [
			{ title: '操作',dataIndex: 'id', sortable: false, width: 70, renderer : function(value){ 
					return  '<a class="grid-icon grid-icon-edit btn-edit" title="编辑" href="javascript:;" onclick="editRow(\''+value+'\');"></a>'+
					'<a class="grid-icon grid-icon-del btn-del" title="删除" href="javascript:;" onclick="delRow(\''+value+'\');"></a>'
				}
			},
			{ title: '名称',dataIndex: 'name', elCls: 'text-left', width: 80, showTip: true },
			{ title: '类型',dataIndex: 'type', elCls: 'text-center', width: 80, showTip: true, renderer : Grid.Format.enumRenderer({
				'0':'产品',
				'1':'系统',
				'2':'模块',
				'3':'功能',
				'4':'操作'
			}) },
			{ title: '权限路径',dataIndex: 'permPath', elCls: 'text-left', width: 170, showTip: true },
			{ title: '请求URL',dataIndex: 'url', elCls: 'text-left', width: 170, showTip: true },
			{ title: '展示顺序',dataIndex: 'order', elCls: 'text-right', width: 80, showTip: true },
			{ title: '参考价格',dataIndex: 'price', elCls: 'text-right', width: 80, showTip: true },
		],
		
		store = new Store({
		   url : '<c:url value="/perm/list"/>',
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
			dialogMagInit(id);
			dialogMag.set('title','修改权限');
			dialogMag.get('loader').load({id:id});
		}
		
		$("#btnEdit").click(function(){
			var selects = grid.getSelection();
			if(selects.length <= 0){
				Message.Alert({msg:'请选择需要修改的数据！'});
			}else if(selects.length > 1){
				Message.Alert({msg:'请选择一条数据进行修改！'});
			}else{
				var selectObj = grid.getSelected();
				var id = selectObj.id;
				dialogMagInit(id);
				//dialogRoleInit.show();
				dialogMag.set('title','修改角色信息');
				dialogMag.get('loader').load({id:id});
			}
		})
	
		//行删除
		window.delRow = function(id) {
			Message.Confirm(this,{msg:'确定删除吗？',fn:function(obj){
		    	$.ajax({
		    		url : '<c:url value="/perm/delete"/>',
					type : 'post',
					data : {
						id : id
					},
					success : function(data, textStatus) {
						if (data.status == "200") {
							store.load();
						}else{
							BUI.Message.Alert('删除失败','error');
						}
						obj.close();
					}
				});
		    }})
		}
		// 批量删除
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
					$.ajax({
						url : '<c:url value="/perm/delete"/>',
						type : 'get',
						data : {
							id : idvalue
						},
						dateType : 'html',
						success : function(data, textStatus) {
							if (data.status == "200") {
								store.load();
							}else{
								BUI.Message.Alert('删除失败','error');
							}
						}
					});
	               },'question');
				
			}
		})
		var decide = false;// 默认未修改
		/* 新建 Dialog */
		var dialogMag;
		function dialogMagInit(id) {
			dialogMag = new Overlay.Dialog({
				title: '新建权限',
				width: 390,
				height: 420,
				closeAction:'destroy',
				loader:{
					url : '<c:url value="/perm/edit"/>',
					autoLoad : false, //不自动加载
					lazyLoad : false,
					params:{id:id},
					failure: function(response,params){ // 错误调用
			        	// 关闭弹出窗口
			        	dialogMag.close();
			        	Message.Alert({msg:'请求失败，请与系统管理员联系！', icon:'error'}); 
			        },
					callback : function() {
						decide = false;
						node = dialogMag.get('el').find('form').eq(0); //查找内部的表单元素
			            form = new Form.HForm({
			                srcNode: node,
			                autoRender: true
			            });
					}
				},
				buttons: [{
					id: 'dd',
					text: '保存 (S)',
					elCls: 'button button-primary key-S',
					handler: function() {
						if (form.isValid()) {
							handleSubUrl();
							form&&form.ajaxSubmit({
								success : function(data, status) {
									var status = data.status;
									if(status == 200){
										dialogMag.close();
										treeStore.load();
										store.load();
										
									}else if(status == 101){
										Message.Alert({msg:'参数错误！'});	
										return;
									}
								}
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
							dialogMag.close();
						}
					}
				}],
				listeners: {
					afterRenderUI: function(e) {
						node = dialogMag.get('el').find('form').eq(0); //查找内部的表单元素
			            form = new Form.HForm({
			                srcNode: node,
			                autoRender: true
			            });
					},
					closeclick: function(e) {
						if(decide == true){
							Message.Confirm(this, {msg: '是否放弃已编辑的信息？' });
						}else{
							dialogMag.close();
						}
						return false;
					},
					afterDestroy: function(e) {
						decide = false;
					}
				}
			});
			dialogMag.show()
		}
		$('#btnAdd').on('click', function() {
			dialogMagInit();
			dialogMag.get('loader').load();
		});
	
		$('#type').on('change', function() {
			var value = $(this).val();
			$('#magProduct').trigger('change');
			$('#magSystem').trigger('change');
			$('#magModule').trigger('change');
		});
	
		function validMag() {
			var value = $('#type').val();
			if (value) {
				var magProduct = form.getChild('magProduct');
				var magSystem = form.getChild('magSystem');
				var magModule = form.getChild('magModule');
				switch (value) {
					case '0': //产品
						magProduct.removeRule('required');
						magSystem.removeRule('required');
						magModule.removeRule('required');
						magProduct.clearErrors();
						magSystem.clearErrors();
						magModule.clearErrors();
						break;
					case '1': //系统
						magProduct.addRule('required',true);
						magSystem.removeRule('required');
						magModule.removeRule('required');
						magSystem.clearErrors();
						magModule.clearErrors();
						break;
					case '3': //模块
						magProduct.addRule('required',true);
						magSystem.addRule('required',true);
						magModule.removeRule('required');
						magModule.clearErrors();
						break;
					case '4': //功能
						magProduct.addRule('required',true);
						magSystem.addRule('required',true);
						magModule.addRule('required',true);
						break;
					default :
						magProduct.removeRule('required');
						magSystem.removeRule('required');
						magModule.removeRule('required');
						magProduct.clearErrors();
						magSystem.clearErrors();
						magModule.clearErrors();
						break;
				}
				//form.get('hasValid',false);
			}
		}
		
		
		$('#magProduct, #magSystem, #magModule').on('change', function() {
			//validMag();
		});
		/*  ================================================================================= */
		
		
		$("#queryPerms").on('click',function(){
			var pid = $("#hideQueryPid").val();
			var name = $("#nameQuery").val();
			var params = {
					'pid':pid,
					'name':name,
					'pageIndex':0
			}
			store.load(params);
		});
	}); 
	
	
	function loadPerm(t,type,selectId,option){
		var pid = $(t).val();
		if(pid != ''){
			$.ajax({
				url : '<c:url value="/perm/subPerms"/>',
				type : 'post',
				data : {pid : pid},
				dateType : 'html',
				success : function(data) {
					//var json = jQuery.parseJSON(data);
					if(data.status == '200'){
						var result = data.result;
						var html = "<option value=''>"+option+"</option>";
						for (var i = 0; i < result.length; i++) {
							var j = result[i];
							html += "<option value='"+ j.id +"'>"+ j.name +"</option>";
						}
						$("#"+selectId).html(html);
						$("#"+selectId).trigger('chosen:updated');
					}
				}
			});
		}
	}
	

	function handleSubUrl(){
		var subUrlText = $('#subUrlText').val();
		if(subUrlText != ''){
			var subUrl = subUrlText.split(',');
			$("#subUrl").val(subUrl);
		}
		
	}
</script>
</body>
</html>