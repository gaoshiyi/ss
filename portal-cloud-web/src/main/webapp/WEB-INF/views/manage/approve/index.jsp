<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="UTF-8">
<title>审批人管理</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<jsp:include page="../../common/header-2.0.0.jsp"></jsp:include>
<!-- page css -->
<link rel="stylesheet" href="<c:url value="/resources/css/approve.css"/>">
</head>
<script>
JoelPurra.PlusAsTab.setOptions({ key: 13 });
</script>
</head>

<body>
<div class="layout clearfix">
	<div class="content">
		<div class="grid-enwrap grid-enwrap-diff">
			<div class="grid-top-bar clearfix">
				<div class="action-pop pull-left">
					<button class="button button-primary" id="btnAdd" type="button" data-keyname="N">新建 (N)</button>
					<button class="button button-primary ml10" id="btnDel" type="button" data-keyname="D">删除 (D)</button>
				</div>
			</div>
			<!-- grid start -->
			<div class="grid-content" id="grid"></div>
			<!-- grid end -->
		</div>
	</div>
</div>

<script>
var gridClone = {};
var approveForm;
BUI.use(['bui/grid', 'bui/data', 'bui/overlay', 'bui/form'], function(Grid, Data, Overlay, Form) {
	/* Grid */
	var Grid = Grid,
        Store = Data.Store,
		wfPrcfIdObj = {'register': '注册审批流程模板', 'wardProcess': '退房查房流程模板'},
		typeObj = {'01': '按人员', '02': '按职位','04':'按工作圈'},
        columns = [
        	{ title: '操作',dataIndex: 'id', elCls: 'text-center', width: 40, sortable: false, renderer: function(value,record,index){
					return '<a class="grid-icon grid-icon-del btn-del" href="javascript:;" data-index="'+index+'" title="删除"></a>'
				}
			},
			{ title: '生成时间',dataIndex: 'date', elCls: 'text-center', width: 100, showTip: true},
			{ title: '类型编码',dataIndex: 'documentNo', elCls: 'text-center', width: 70, showTip: true},
			{ title: '模板名称',dataIndex: 'wfPrfName', elCls: 'text-center', width: 100, showTip: true, renderer: Grid.Format.enumRenderer(wfPrcfIdObj)},
			{ title: '审批策略',dataIndex: 'type', elCls: 'text-center', width: 90, showTip: true, renderer: Grid.Format.enumRenderer(typeObj)},
			{ title: '审批对象',dataIndex: 'count', elCls: 'text-center', width: 90, showTip: true, renderer: function(value,record,index){
					if(value > 0){
						return '<a class="text-primary text-underline btn-view" href="javascript:;" data-index="'+index+'">' + value + '</a>'
					}else{
						return '<span>' + value + '</span>'
					}
					
				}
			},
		];
    var store = new Store({
    	url: '<c:url value="/workflowapprover/list"/>',
        autoLoad: true,
        pageSize: 10
    }),
	grid = new Grid.Grid({
		render: '#grid',
		columns: columns,
		idField : 'id',
		width: '100%',
		//forceFit : true,
		//innerBorder: false, //单元格左右之间是否出现边框
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

	/* 新建 ================================================================================= */
	var dialogAdd;
	dialogAddInit = function() {
		dialogAdd = new Overlay.Dialog({
			title: '新建审批权限',
			width: 360,
			height: 320,
			closeAction:'destroy',
			loader: {
				url: '<c:url value="/workflowapprover/add"/>',
	            autoLoad: false, //不自动加载
	            lazyLoad: false,
	            callback: function() {
	                $('#btnAdd').prop('disabled',false);
					var node = dialogAdd.get('el').find('form');
					form = new Form.HForm({
						srcNode : node,
						autoRender : true
					});
	            }
			},
			buttons: [{
				text: '确定 (K)',
				elCls: 'button button-primary key-K',
				handler: function() {
                    //拿取form数据
                    var record = form.toObject();
                    var wfPrcfId = record.wfPrfName;
                    var wfTaskKey = record.wfTaskKey;
                    var type = record.type;
                    var dptId = '';
                    var auditIds = record.auditId;
                    if(form.isValid()){
                		$.ajax({
                            url: '<c:url value="/workflowapprover/save"/>',
                            type: "POST",
                            dataType: "json",
                            data: {wfPrcfId:wfPrcfId,wfTaskKey:wfTaskKey,type:type,auditIds:auditIds,dptId:dptId},
                            success: function(data) {
                                if (data.status == 200) {
                                	BUI.Message.Alert("保存成功！","success");
                                	store.load();
                                	dialogAdd.close();
                                }

                            }
                        });

                    }
				}
			}, {
				text: '关闭 (Esc)',
				elCls: 'button key-Esc',
				handler: function() {
					this.close();
				}
			}],
			listeners: {
				closeclick: function(e) {
					//return false;
				}
			}
		});
		dialogAdd.show();
	}
	
	/* 新建 */
	$('#btnAdd').on('click',function() {
		$(this).prop('disabled',true);
		dialogAddInit();
		dialogAdd.get('loader').load();
	});

	/* 选择审批人 */
	$('body').on('click', '#btnChoose', function() {
		var type = $('#type').val();
		if (type == '') {
			BUI.Message.Alert('请先选择审批策略！','warning');
			return;
		}
		$(this).prop('disabled',true);
		
		if (type == '01') { //按人员
			var items = $.isEmptyObject(gridClone) ? '' : gridClone.getItems();
			dialogPerInit();
			dialogPer.get('loader').load();
		} else if (type == '04') { //按工作圈
			dialogTeamInit();
			dialogTeam.get('loader').load();
		} else { //按部门
			var items = $.isEmptyObject(gridClone) ? '' : gridClone.getItems();
			dialogOrgInit();
			dialogOrg.get('loader').load();
		}
	});
	/*  ================================================================================= */

	/* 添加审批对象（按人员） ================================================================================= */
	var dialogPer;
	dialogPerInit = function() {
		dialogPer = new Overlay.Dialog({
			title: '添加员工',
			width: 1050,
			height: 520,
			closeAction: 'destroy',
			loader: {
	            url: '<c:url value="/workflowapprover/addPer"/>',
	            autoLoad: false, //不自动加载
	            lazyLoad: false,
	            callback: function() {
					$('#btnChoose').prop('disabled',false);
	            }
	        },
			buttons: [{
				text: '保存 (S)',
				elCls: 'button button-primary key-S',
				handler: function() {
					var items = gridClone.getItems();
					var ids = [];
					var names = [];
					if (items.length == 0) {
						BUI.Message.Alert('请选择审员工！','warning');
						return;
					}
					
					form.getChild('auditName').clearErrors();
					form.getChild('auditId').clearErrors();
					$.each(items,function(index,item){
						ids.push(item.id);
						names.push(item.name)
					});
					$("#auditId").val(ids.join(","));
					$("#auditName").val(names.join(","));
					$("#auditName").attr("title",names.join(","));
					this.close();
				}
			}, {
				text: '关闭 (Esc)',
				elCls: 'button key-Esc',
				handler: function() {
					this.close();
				}
			}],
			listeners: {
				closeclick: function(e) {
					//return false;
				}
			}
		});
		dialogPer.show();
	}

	/*  ================================================================================= */

	/* 添加审批对象（按部门） ================================================================================= */
	var dialogOrg;
	dialogOrgInit = function() {
		dialogOrg = new Overlay.Dialog({
			title: '选择职位',
			width: 1050,
			height: 520,
			closeAction: 'destroy',
			loader: {
	            url: '<c:url value="/workflowapprover/addOrg"/>',
	            autoLoad: false, //不自动加载
	            lazyLoad: false,
	            callback: function() {
					$('#btnChoose').prop('disabled',false);
	            }
	        },
			buttons: [{
				text: '保存 (S)',
				elCls: 'button button-primary key-S',
				handler: function() {
					var items = gridClone.getItems();
					var ids = [];
					var names = [];
					if (items.length == 0) {
						BUI.Message.Alert('请选择审批职位！','warning');
						return;
					}
					form.getChild('auditName').clearErrors();
					form.getChild('auditId').clearErrors();
					$.each(items,function(index,item){
						ids.push(item.id);
						names.push(item.name)
					});
					$("#auditId").val(ids.join(","));
					$("#auditName").val(names.join(","));
					$("#auditName").attr("title",names.join(","));
					this.close();
				}
			}, {
				text: '关闭 (Esc)',
				elCls: 'button key-Esc',
				handler: function() {
					this.close();
				}
			}],
			listeners: {
				closeclick: function(e) {
					//return false;
				}
			}
		});
		dialogOrg.show();
	}

	/*  ================================================================================= */


	/* 添加审批对象（按工作圈） ================================================================================= */
	var dialogTeam;
	dialogTeamInit = function() {
		dialogTeam = new Overlay.Dialog({
			title: '选择工作圈',
			width: 790,
			height: 500,
			closeAction: 'destroy',
			loader: {
	            url: '<c:url value="/workflowapprover/addTeam"/>',
	            autoLoad: false, //不自动加载
	            lazyLoad: false,
	            callback: function() {
					$('#btnChoose').prop('disabled',false);
	            }
	        },
			buttons: [{
				text: '保存 (S)',
				elCls: 'button button-primary key-S',
				handler: function() {
					var items = gridClone.getItems();
					if (items.length == 0) {
						BUI.Message.Alert('请选择审批工作圈！','warning');
						return;
					}

					$("#auditId").val(selectedItems.join(",")).trigger('change');
					$("#auditName").val(selectedItemNames.join(",")).trigger('change');
					$("#auditName").attr("title",selectedItemNames.join(","));
					form.getChild('auditName').clearErrors();
					form.getChild('auditId').clearErrors();
					this.close();
				}
			}, {
				text: '关闭 (Esc)',
				elCls: 'button key-Esc',
				handler: function() {
					this.close();
				}
			}],
			listeners: {
				closeclick: function(e) {
					//return false;
				}
			}
		});
		dialogTeam.show();
	}

	/*  ================================================================================= */

	/* 审批对象 ================================================================================= */
	var dialogView;
	dialogViewInit = function() {
		dialogView = new Overlay.Dialog({
			title: '审批对象',
			width: 730,
			height: 420,
			closeAction: 'destroy',
			loader: {
	            url: '<c:url value="/workflowapprover/view"/>',
	            autoLoad: false, //不自动加载
	            lazyLoad: false,
	            callback: function() {
					$('#grid .btn-view').prop('disabled',false);
	            }
	        },
			buttons: [{
				text: '关闭 (Esc)',
				elCls: 'button key-Esc',
				handler: function() {
					this.close();
				}
			}],
			listeners: {
				closeclick: function(e) {
					//return false;
				}
			}
		});
		dialogView.show();
	}

	$('#grid').on('click', '.btn-view', function() {
		if ($(this).prop('disabled')) {
			return;
		}
		var index = $(this).attr('data-index');
		var item = grid.getItemAt(index);
		var params = {
			'wfPrfId': item.wfPrfName,
			'type': item.type,
			'documentNo': item.documentNo
		}

		$(this).prop('disabled',true);
		dialogViewInit();
		dialogView.get('loader').load(params);
	});
	/*  ================================================================================= */

	/* 删除 ================================================================================= */
	$('#btnDel').on('click', function(){
		var _this = $(this);
		if ($(this).prop('disabled')) {
			return;
		}

		var items = grid.getSelection();
		if (items.length == 0 || items.length > 1) {
			BUI.Message.Alert('请选择一条数据操作！', 'warning');
			return;
		}
		var item = items[0];
		var param = {
			'wfPrfId': item.wfPrfName,
			'type': item.type
		}

		BUI.Message.Confirm('确定要删除吗？', function(){
			delApprove(param,_this);
		});
	});
	$('#grid').on('click', '.btn-del', function() {
		var _this = $(this);
		if ($(this).prop('disabled')) {
			return;
		}
		var index = $(this).attr('data-index');
		var item = grid.getItemAt(index);
		var param = {
			'wfPrfId': item.wfPrfName,
			'type': item.type
		}

		BUI.Message.Confirm('确定要删除吗？', function(){
			delApprove(param,_this);
		});
	});
	/*  ================================================================================= */
	function delApprove(param,obj) {
		$(obj).prop('disabled',true);
		$.ajax({
			type : "POST",
			dataType : "json",
			url : '<c:url value="/workflowapprover/delete"/>',
			data : param,
			success : function(result) {
				if (result.status == 200) {
				   
				} else {
					BUI.Message.Alert('删除失败！','warning')
				}
				store.load();
				$(obj).prop('disabled',false);
			}
		});
	}
});


</script>
</body>
</html>
