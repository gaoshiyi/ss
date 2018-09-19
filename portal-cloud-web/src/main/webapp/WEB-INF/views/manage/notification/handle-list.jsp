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
				<form class="form-horizontal" method="post" action="" autocomplete="off">
					<div class="row">
						<div class="control-item">
							<div class="control-group">
								<label class="control-label control-label-auto">开始时间：</label>
								<div class="control-container pull-left">
									<div class="box-w120">
										<div class="input-box has-btn-dialog">
											<input class="control-text input-full calendar" type="text" id ="startDate" name="startDate" value="">
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
											<input class="control-text input-full calendar" type="text" id ="endDate" name="endDate" value="">
											<button class="btn-dialog btn-calendar" type="button"></button>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="control-item">
							<div class="control-group pull-left">
								<div class="control-container pull-left">
									<input class="control-text input-w200 SearchInput" type="text" id="detail" name="detail" value="" placeholder="部门/负责人/联系电话">
								</div>
							</div>
							<div class="control-group pull-left ml20">
								<div class="control-container">
									<button class="button button-primary button-query" id = "search" type="button" data-keyname="F">查询 (F)</button>
								</div>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
		<div class="grid-enwrap grid-enwrap-diff">
			<!-- grid start -->
			<div class="grid-content mt10" id="grid"></div>
			<!-- grid end -->
		</div>
	</div>
</div>

<script>
BUI.use('bui/calendar', function(Calendar) {
    var datepicker = new Calendar.DatePicker({
        trigger: '.calendar',
		dateMask: 'yyyy/mm/dd',
        autoRender: true
    });
});

BUI.use(['bui/grid', 'bui/data', 'bui/overlay', 'bui/form'], function(Grid, Data, Overlay, Form) {
	/* Grid */
	var Grid = Grid,
        Store = Data.Store,
        columns = [
			{ title: '部门',dataIndex: 'depart', elCls: 'text-center', width: 60, showTip: true},
			{ title: '升级当班负责人',dataIndex: 'principal', elCls: 'text-center', width: 120, showTip: true},
			{ title: '联系电话',dataIndex: 'phone', elCls: 'text-center', width: 70, showTip: true},
			{ title: '确认时间',dataIndex: 'createTime', elCls: 'text-center', width: 110, showTip: true},
		],
        data = [
			{id:'1', department: '前厅部', employee: '小明', phone: '13754826532', time: '2018/04/11 18:00:00'},
			{id:'2', department: '前厅部', employee: '小明', phone: '13754826532', time: '2018/04/11 18:00:00'},
			{id:'3', department: '前厅部', employee: '小明', phone: '13754826532', time: '2018/04/11 18:00:00'},
			{id:'4', department: '前厅部', employee: '小明', phone: '13754826532', time: '2018/04/11 18:00:00'},
			{id:'5', department: '前厅部', employee: '小明', phone: '13754826532', time: '2018/04/11 18:00:00'},
			{id:'6', department: '前厅部', employee: '小明', phone: '13754826532', time: '2018/04/11 18:00:00'},
			{id:'7', department: '前厅部', employee: '小明', phone: '13754826532', time: '2018/04/11 18:00:00'},
			{id:'8', department: '前厅部', employee: '小明', phone: '13754826532', time: '2018/04/11 18:00:00'},
			{id:'9', department: '前厅部', employee: '小明', phone: '13754826532', time: '2018/04/11 18:00:00'},
			{id:'10', department: '前厅部', employee: '小明', phone: '13754826532', time: '2018/04/11 18:00:00'},
			{id:'11', department: '前厅部', employee: '小明', phone: '13754826532', time: '2018/04/11 18:00:00'},
			{id:'12', department: '前厅部', employee: '小明', phone: '13754826532', time: '2018/04/11 18:00:00'},
			{id:'13', department: '前厅部', employee: '小明', phone: '13754826532', time: '2018/04/11 18:00:00'},
			{id:'14', department: '前厅部', employee: '小明', phone: '13754826532', time: '2018/04/11 18:00:00'},
			{id:'15', department: '前厅部', employee: '小明', phone: '13754826532', time: '2018/04/11 18:00:00'}
		];
	
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	var detail = $('#detail').val();
    var store = new Store({
        // data: data,
        url: '<c:url value="/sysMsgnotify/pageFindReceipt"/>',
		type: "POST",
    	// data: data,
    	datatype:"json",
    	params:{
    		validStarttime : startDate,
    		validEndtime : endDate,
    		detail : detail
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
    
    $('#search').click(function(){
    	debugger;
		var validStarttime = $("#startDate").val();
		var validEndtime = $("#endDate").val();
		var detail = $('#detail').val();
		params = {
    		validStarttime : validStarttime,
    		validEndtime : validEndtime,
    		detail : detail
       };
	   store.load(params);
	});
});
</script>
</body>
</html>