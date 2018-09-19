<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> 

<!DOCTYPE html>
<html>
<head>
<title>消息通知</title>
<jsp:include page="../../common/header-2.0.0.jsp"></jsp:include>
<!-- page css -->
<link href="<c:url value="/resources/css/notification.css"></c:url>" rel="stylesheet">
</head>

<body>
<div class="layout clearfix">
	<div class="content">
		<div class="filter-box mt-15">
			<div class="panel-body">
				<form class="form-horizontal" id = "msgform" method="post" action="" autocomplete="off">
					<input id="operType" name="operType" type="hidden" value="0">
					<div class="row">
						<div class="control-item">
							<div class="control-group">
								<label class="control-label control-label-auto">开始时间：</label>
								<div class="control-container pull-left">
									<div class="box-w120">
										<div class="input-box has-btn-dialog">
											<input class="control-text input-full calendar" id="validStarttime" type="text" name="validStarttime" value="" data-rules="{dateMemberValid:0}" >
											<button class="btn-dialog btn-calendar" type="button"></button>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="control-item">
							<div class="control-group">
								<label class="control-label control-label-auto">结束时间：</label>
								<div class="control-container pull-left">
									<div class="box-w120">
										<div class="input-box has-btn-dialog">
											<input class="control-text input-full calendar" type="text" id="validEndtime" type="text" name="validEndtime"  value="" data-rules="{dateMemberValid:1}">
											<button class="btn-dialog btn-calendar" type="button"></button>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="control-item">
							<div class="control-group">
								<label class="control-label control-label-auto">是否发布：</label>
								<div class="control-container pull-left">
									<label class="radio pull-left"><input type="radio" name="ispress" value="1" checked>是</label>
									<label class="radio pull-left ml10"><input type="radio" name="ispress" value="0">否</label>
								</div>
							</div>
						</div>
						<div class="control-item">
							<div class="control-group pull-left">
								<div class="control-container pull-left">
									<input class="control-text input-w200 SearchInput" type="text" id = "summary" name="" value="" placeholder="标题概要">
								</div>
							</div>
							<div class="control-group pull-left ml20">
								<div class="control-container">
									<button id = "search" class="button button-primary button-query" type="button" data-keyname="F">查询 (F)</button>
								</div>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
		<div class="grid-enwrap grid-enwrap-diff">
			<div class="grid-top-bar clearfix">
				<div class="action-pop pull-left">
					<button class="button button-primary" id="btnAdd" type="button" data-keyname="N">新建 (N)</button>
					<button class="button button-primary" id="btnEdit" type="button" data-keyname="E">修改 (E)</button>
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
BUI.use('bui/calendar', function(Calendar) {
    var datepicker = new Calendar.DatePicker({
        trigger: '.calendar',
		dateMask: 'yyyy/MM/dd',
        autoRender: true
    });
});

BUI.use(['bui/grid', 'bui/data', 'bui/overlay', 'bui/form'], function(Grid, Data, Overlay, Form) {
	/* Grid */
	var Grid = Grid,
        Store = Data.Store,
		ispressObj = {'0': '否', '1': '是'},
		typeObj = {'0': '系统升级', '1': 'bug修复'},
        columns = [
        	{ title: '操作',dataIndex: 'id', elCls: 'text-center', width: 80, sortable: false, renderer: function(value,record,index){
					var url = '<c:url value="'+ ctx + '/sysMsgnotify/handleList?id='+value+'" />';
        			return '<a class="grid-icon grid-icon-edit btn-edit" href="javascript:;" title="编辑"></a>'+
					'<a class="grid-icon grid-icon-strategy page-action" href="'+url+'" data-id="handel-list" data-reload="true" data-type="open" data-title="回执列表" title="查看回执"></a>'+
					'<a class="grid-icon grid-icon-view btn-view" href="javascript:view(\''+value+'\');" title="预览"></a>'+
					'<a class="grid-icon grid-icon-del btn-del" href="javascript:delRow(\''+value+'\');" title="删除"></a>'
				}
			},
			{ title: '是否发布',dataIndex: 'publishStatus', elCls: 'text-center', width: 60, showTip: function(value,record,index){return ispressObj[value]}, renderer: Grid.Format.enumRenderer(ispressObj)},
			{ title: '发布时间',dataIndex: 'publishTime', elCls: 'text-center', width: 70, showTip: true},
			// { title: '类型',dataIndex: 'type', elCls: 'text-center', width: 70, showTip: function(value,record,index){return typeObj[value]}, renderer: Grid.Format.enumRenderer(typeObj)},
			{ title: '类型',dataIndex: 'type', elCls: 'text-center', width: 70, showTip: true},
			{ title: '有效时间',dataIndex: 'validateDate', elCls: 'text-center', width: 180, showTip: true},
			{ title: '标题',dataIndex: 'title', elCls: 'text-left', width: 100, showTip: true},
			{ title: '概要',dataIndex: 'summary', elCls: 'text-left', width: 200, showTip: true}
		],
        data = [
			{id:'1', ispress: '1', pressTime: '2018/03/06', type: '0', duration: '2018/04/11 18:00:00 ~ 2018/04/12 18:00:00', title: '4月11日升级通知', essentials: '本系统将于2018年04月11日晚上.'},
			{id:'2', ispress: '1', pressTime: '2018/03/06', type: '0', duration: '2018/04/11 18:00:00 ~ 2018/04/12 18:00:00', title: '4月11日升级通知', essentials: '本系统将于2018年04月11日晚上.'},
			{id:'3', ispress: '1', pressTime: '2018/03/06', type: '0', duration: '2018/04/11 18:00:00 ~ 2018/04/12 18:00:00', title: '4月11日升级通知', essentials: '本系统将于2018年04月11日晚上.'},
			{id:'4', ispress: '1', pressTime: '2018/03/06', type: '0', duration: '2018/04/11 18:00:00 ~ 2018/04/12 18:00:00', title: '4月11日升级通知', essentials: '本系统将于2018年04月11日晚上.'},
			{id:'5', ispress: '1', pressTime: '2018/03/06', type: '0', duration: '2018/04/11 18:00:00 ~ 2018/04/12 18:00:00', title: '4月11日升级通知', essentials: '本系统将于2018年04月11日晚上.'},
			{id:'6', ispress: '1', pressTime: '2018/03/06', type: '0', duration: '2018/04/11 18:00:00 ~ 2018/04/12 18:00:00', title: '4月11日升级通知', essentials: '本系统将于2018年04月11日晚上.'},
			{id:'7', ispress: '1', pressTime: '2018/03/06', type: '0', duration: '2018/04/11 18:00:00 ~ 2018/04/12 18:00:00', title: '4月11日升级通知', essentials: '本系统将于2018年04月11日晚上.'},
			{id:'8', ispress: '1', pressTime: '2018/03/06', type: '0', duration: '2018/04/11 18:00:00 ~ 2018/04/12 18:00:00', title: '4月11日升级通知', essentials: '本系统将于2018年04月11日晚上.'}
		];
	var validStarttime = $("#validStarttime").val();
	var validEndtime = $("#validEndtime").val();
	var publishStatus = $('input[name="ispress"]:checked').val();
	var summary = $("#summary").val();
    var store = new Store({
    	url: '<c:url value="/sysMsgnotify/pagefind"/>',
		type: "POST",
    	// data: data,
    	datatype:"json",
    	params:{
    		validStarttime:validStarttime,
    		validEndtime:validEndtime,
    		publishStatus:publishStatus,
    		summary:summary
       },
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
    
    var msgFormNode = $('#msgform')[0];
    msgFormForm = new Form.HForm({
        srcNode: msgFormNode,
        autoRender: true
    });	

	/* 新建/编辑 ================================================================================= */
	var dialogNotice;
	dialogNoticeInit = function() {
		dialogNotice = new Overlay.Dialog({
			title: '新建',
			width: 790,
			height: 500,
			closeAction:'destroy',
			loader: {
				url: '/sysMsgnotify/add',
	            autoLoad: false, //不自动加载
	            lazyLoad: false,
	            callback: function() {
	                $('#btnAdd').prop('disabled',false);
					var node = dialogNotice.get('el').find('form');
					form = new Form.HForm({
						srcNode : node,
						autoRender : true
					});
	            }
			},
			buttons: [{
				text: '保存 (S)',
				elCls: 'button button-primary key-S',
				handler: function() {
					operType = $("#operType").val(); // 操作类型-新增, 修改
					var id = $("#add_id").val(); // notice id
				 	var type = $("#add_type").val();
					var validStarttime = $("#add_validStarttime").val(); // 开始时间
					var validEndtime = $("#add_validEndtime").val();	// 结束时间
					var publishTime = $("#add_publishTime").val();	// 发布时间
					var title = $("#add_title").val();	// 标题
					var summary = $("#add_summary").val(); // 摘要
					var detail = $("#add_detail").val(); // 详情
					var publishStatus = $('input[name="add_ispress"]:checked').val(); // 发布状态
					var param = {
								 id : id,
							     type : type,
							  	 validStarttime : validStarttime, 
								 validEndtime : validEndtime,
								 publishTime  :	publishTime,
								 title : title, 
								 summary : summary, 
								 detail : detail, 
								 publishStatus : publishStatus,
								 operType : operType};
					if (form.isValid() && validateDate())
					{ // 校验通过
						$.ajax({
	       	                url:'<c:url value="/sysMsgnotify/save"/>',
	       	                type : "POST",  
	       	                datatype:"json",  
	       	                data : param,  
	       	                async:false,
	       	                success : function(data, stats) {  
	       	                  	if(data.status == 200) {
	       	                  		store.load();
	       	                  		if (0 == operType)
	       	                  		{
	       	                  			BUI.Message.Alert("新增通知信息成功！", 'success');	
	       	                  		} else {
	       	                  			BUI.Message.Alert("修改通知信息成功！", 'success');	
	       	                  		}
	       	                  		dialogNotice.close();
	       	                  	} else {
		    						setTimeout(function(){
	       	                  			BUI.Message.Alert(data.message, 'error');
	       	                  		},10);
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
		dialogNotice.show();
	}
	
	/* 新建 */
	$('#btnAdd').on('click',function() {
		$(this).prop('disabled',true);
		$("#operType").val("0"); // 新增
		dialogNoticeInit();
		dialogNotice.get('loader').load();
		$(this).prop('disabled',false);
	});
	
	initEditData = function(noticeId)
	{ // 初始化编辑数据
		$.ajax({
               url:'<c:url value="/sysMsgnotify/initEditData"/>',
               type : "POST",  
               datatype:"json",  
               data : {id : noticeId},  
               async:false,
               success : function(data, stats) {  
                 	if(data.status == 200) {
                 		dialogNotice.get('loader').load();
                 		/* $("#add_type").val(data.type);
                 		$("#add_validStarttime").val(data.validStarttime);
                 		$("#add_validEndtime").val(data.validEndtime);
                 		$("#add_publishTime").val(data.publishTime);
                 		$("#add_title").val(data.title);
                 		$("#add_summary").val(data.summary);
                 		$("#add_detail").val(data.detail); */
                 	} else {
					setTimeout(function(){
                 			BUI.Message.Alert(data.message, 'error');
                 		},10);
               	}
               }
           });
	}
	
	/* 编辑 */
	$('body').on('click', '#btnEdit, #grid .btn-edit', function() {
		var selection = grid.getSelection();
		if (selection.length == 0 || selection.length > 1) {
			BUI.Message.Alert('请选择一条数据进行编辑！');
			return;
		}
		var id = selection[0].id;
		$("#operType").val("1"); // 编辑
		$(this).prop('disabled',true);
		dialogNoticeInit();
		// initEditData(id);
		dialogNotice.set('title','编辑');
		var params = {
					id : id
			};
		dialogNotice.get('loader').load(params);
		$(this).prop('disabled',false);
	});

	/*  ================================================================================= */

	/* 详情 ================================================================================= */
	var dialogNoticeView;
	dialogNoticeViewInit = function(id) {
		dialogNoticeView = new Overlay.Dialog({
			title: '详情',
			width: 630,
			height: 460,
			closeAction:'destroy',
			loader: {
				url: '<c:url value="/sysMsgnotify/view"/>',
	            autoLoad: false, //不自动加载
	            lazyLoad: false,
	            params: {id : id},
	            callback: function() {
					
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
		dialogNoticeView.show();
	}

	window.view=function(idValue){
		$(this).prop('disabled',true);
		dialogNoticeViewInit(idValue);
		dialogNoticeView.get('loader').load();
		$(this).prop('disabled',false);
	}
	/* $('body').on('click', '#grid .btn-view', function() {
		$(this).prop('disabled',true);
		dialogNoticeViewInit();
		dialogNoticeView.get('loader').load();
		$(this).prop('disabled',false);
	}); */
	/*  ================================================================================= */
	/* ===========================查看回执===========================*/
	/* window.watch = function(idValue){
		var viewMasterUrl = ctx + '/reserve/common/toMasterIndex?rsvId=${customerReservation.primaryId}';
		$('#viewMaster').attr('href', viewMasterUrl);
	} */
	/* ===========================查看回执===========================*/
	/* 删除 ================================================================================= */
	//行删除
	window.delRow=function(idValue){
		BUI.Message.Confirm('确认删除？',function(){
			var result = remove(idValue);
			if (result)
			{
				store.load();
			} else {
				BUI.Message.Alert('删除失败','error');
			}
		},'question');
	};
	
	remove = function(idString) {
		var result = false;
		$.ajax({
               url:'<c:url value="/sysMsgnotify/delete"/>',
               type : "POST",  
               datatype:"json",  
               data : {idStr : idString},  
               async:false,
               success : function(data, stats) {  
                 	if(data.status == 200) {
                 		result = true;
                 	}
               }
           });
		return result;
	}
	
	$('#btnDel').on('click', function() {
		var selection = grid.getSelection();
		var selectionLen = selection.length;
		if (selectionLen == 0) {
			BUI.Message.Alert('请选择需要删除的数据！');
			return;
		}
		var idString = "";
		for(var i=0;i<selectionLen;i++){
			if(i+1 == selectionLen){
				idString += selection[i].id;
			}else{
				idString += selection[i].id + ",";
			}
		}
		
		$(this).prop('disabled',true);
		var result = remove(idString);
		if (result)
		{
			store.load();
		} else {
			BUI.Message.Alert('删除失败','error');
		}
		$(this).prop('disabled',false);
	});
	/*  ================================================================================= */
	//来、离期、天数  日期校验
	Form.Rules.add({
		name : 'dateMemberValid',
		msg : '日期校验',
		validator : function(value,baseValue,formatMsg){
			var validStarttime = $("#validStarttime").val();	//开始时间
			var validEndtime = $("#validEndtime").val();	//结束时间
			if (null != validStarttime || '' != validStarttime)
			{
				validStarttime = new Date(validStarttime);
			}
			if (null != validEndtime || '' != validEndtime)
			{
				validEndtime = new Date(validEndtime);
			}
  	 		if (null != validStarttime && undefined != validStarttime 
  	 				&& null != validEndtime && undefined != validEndtime)
  	 		{
  	 			if(validStarttime > validEndtime){
  	 				if (baseValue == 0)
  	 				{
  	 					return "开始日期不能大于结束日期";
  	 				} else {
  	 					return "结束日期不能小于开始日期";
  	 				}
  	 			}
  	 		}
		}
	});
	
	$('#search').click(function(){
		var validStarttime = $("#validStarttime").val();
		var validEndtime = $("#validEndtime").val();
		var publishStatus = $('input[name="ispress"]:checked').val();
		var summary = $("#summary").val();
		params = {
    		validStarttime:validStarttime,
    		validEndtime:validEndtime,
    		publishStatus:publishStatus,
    		summary:summary
       };
	   store.load(params);
	});
});
</script>
</body>
</html>