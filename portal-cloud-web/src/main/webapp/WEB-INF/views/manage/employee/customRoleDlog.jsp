<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<form class="form-horizontal bui-form-horizontal bui-form bui-form-field-container" method="post" action="" autocomplete="off" data-plus-as-tab="true">
	<!-- 用户角色 start -->
	<div class="tab-content">
		<div class="row control-employee control-role-employee form-horizontal">
			<div class="side side-left ml10">
				<p>备选角色</p>
				<div class="clearfix">
					<div class="grid-content grid-content-border" id="gridSelect"></div>
				</div>
			</div>
			<div class="side side-right">
				<p>已选角色</p>
				<div class="clearfix">
					<div class="grid-content grid-content-border" id="gridSelected"></div>
				</div>
			</div>
			<div class="action">
				<button class="button button-primary" id="toRight" type="button" onclick="empInner()">&nbsp;移入 >&nbsp;</button>
				<button class="button button-primary" id="toLeft" type="button" onclick="empOuter()">&nbsp;< 移出&nbsp;</button>
			</div>
		</div>
	</div>
	<!-- 用户角色 end -->		
</form>

<script>
BUI.use(['bui/grid', 'bui/data', 'bui/overlay', 'bui/form', 'bui/tree','bui/extensions/treepicker', 'bui/tab'], function(Grid, Data, Overlay, Form, Tree, TreePicker, Tab) {
	/* GridSelect */
	oldDecide = decide;
	decide = false;
	
	var GridSelect = Grid,
        StoreSelect = Data.Store,
    columnsSelect = [
        { title: 'id',dataIndex: 'id', visible : false},
		{ title: '名称',dataIndex: 'name', elCls: 'text-left', width: '40%', showTip: true },
		{ title: '所属集团或酒店',dataIndex: 'belongName', elCls: 'text-left', width: '60%', showTip: true }
	];
	var staffIdVal = $('#staffId').val();
    var storeSelect = new StoreSelect({
		params : {
			staffId : staffIdVal
		},    	
    	url : '<c:url value="/employee/roleLeft"/>',
        autoLoad: true
    }),
	gridSelect = new Grid.Grid({
		render: '#gridSelect',
		columns: columnsSelect,
		multipleSelect : true,
		width: '100%',
		//forceFit : true,
		store: storeSelect,
		emptyDataTpl: '<div class="empty-data"><span>暂无数据</span></div>',
		plugins: [Grid.Plugins.CheckSelection, Grid.Plugins.ColumnResize]
	});
    gridSelect.render();
	
	/* 用户角色 ================================================================================= */
	/* GridSelected */
	var staffIdVal2 = $('#staffId').val();
	var GridSelected = Grid,
        StoreSelected = Data.Store,
        columnsSelected = [
             { title: 'id',dataIndex: 'id', visible : false},
			{ title: '名称',dataIndex: 'name', elCls: 'text-left', width: '40%', showTip: true },
			{ title: '所属集团或酒店',dataIndex: 'belongName', elCls: 'text-left', width: '60%', showTip: true }
		];
    var storeSelected = new StoreSelected({
    	params : {
			staffId : staffIdVal2
		}, 
    	url : '<c:url value="/employee/roleRight"/>',
        autoLoad: true
    }),
	gridSelected = new Grid.Grid({
		render: '#gridSelected',
		columns: columnsSelected,
		multipleSelect : true,
		width: '100%',
		//forceFit : true,
		store: storeSelected,
		emptyDataTpl: '<div class="empty-data"><span>暂无数据</span></div>',
		plugins: [Grid.Plugins.CheckSelection, Grid.Plugins.ColumnResize]
	});
    gridSelected.render();
    gridRight = gridSelected;
    gridLeft = gridSelect;
    
	$('body').on('click', '#toRight', function() {
		var selections1 = gridSelect.getSelection();
		gridSelect.removeItems(selections1);
		gridSelected.addItems(selections1);
	});
	$('body').on('click', '#toLeft', function() {
		var selections2 = gridSelected.getSelection();
		gridSelected.removeItems(selections2);
		gridSelect.addItems(selections2);
	});
})
</script>