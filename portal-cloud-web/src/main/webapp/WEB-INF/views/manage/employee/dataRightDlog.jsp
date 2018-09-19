<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<form class="form-horizontal bui-form-horizontal bui-form bui-form-field-container" method="post" action="" autocomplete="off" data-plus-as-tab="true">
<!-- 数据权限 start -->
<div class="tab-content">
	<div class="row control-indent control-employee control-employee-jurisd form-horizontal control-employee-hotel">
		<div class="side side-left ml10">
			<div class="tree-list tree-noicon" id="treeListJurisd"></div>
		</div>
		<div class="side side-right ml10"></div>
	</div>
</div>
<!-- 数据权限 end -->
</form>

<script>

BUI.use(['bui/grid', 'bui/data', 'bui/overlay', 'bui/form', 'bui/tree','bui/extensions/treepicker', 'bui/tab'], function(Grid, Data, Overlay, Form, Tree, TreePicker, Tab) {
	/* 数据权限 ================================================================================= */
	//树节点数据，
	//text : 文本，
	//id : 节点的id,
	//leaf ：标示是否叶子节点，可以不提供，根据childern,是否为空判断
	//expanded ： 是否默认展开
	//checked : 节点是否默认选中
	var dataJurisd = [
		{text: 'PMS',id: '1',expanded: true,children: [
			{text: '预定', id: '11',expanded: true, children: [
				{text: '散客预定', id: '111',expanded: true, children: [
					{text: '新建散客预定', id: '1111', checked: true},
					{text: '复制散客预定', id: '1112'}
				]},
				{text: '团队预订', id: '112',expanded: true, children: [
					{text: '新建团队预定', id: '1121'},
					{text: '复制团队预定', id: '1122'}
				]}
			]}
		]}, 
		{text: 'CRM', id: '2',children: [
			{text: '21',id: '21',children: [
				{text: '211',id: '211',children: [
					{text: '2111',id: '2111'},
					{text: '2112',id: '2112'}
				]},
				{text: '212',id: '212',children: [
					{text: '2121',id: '2121'},
					{text: '2121',id: '2121'},
				]}
			]}
		]}
	];
	var treeJurisd = new Tree.TreeList({
		render: '#treeListJurisd',
		nodes: dataJurisd,
		showLine: false 
	});
	treeJurisd.render();


	/* 组织下拉 */

	//树节点数据，
	//text : 文本，
	//id : 节点的id,
	//leaf ：标示是否叶子节点，可以不提供，根据childern,是否为空判断
	//expanded ： 是否默认展开
	var data = [
		{text: '1', id: '1', cls: 'icon-htl', expanded: true, children: [
			{text: '11', id: '11', cls: 'icon-dpt'}
		]
	}, {
		text: '2',id: '2', cls: 'icon-htl', children: [
			{ text: '21', id: '21', cls: 'icon-dpt', children: [
				{text: '211', id: '211', cls: 'icon-dpt'},
				{text: '212', id: '212', cls: 'icon-dpt'}
			]},
			{text: '22',id: '22', cls: 'icon-dpt'}
		]
	},
	{text: '3',id: '3', cls: 'icon-htl'},
	{text: '4',id: '4', cls: 'icon-htl'}
	],
	store = new Data.TreeStore({
		data: data,
		autoLoad: true
	}),
	tree = new Tree.TreeList({
		store: store,
		//dirSelectable : false,//阻止树节点选中
		//showLine: true //显示连接线
	});
	var picker = new TreePicker({
		trigger: '#showOrganization',
		valueField: '#hideOrganization', //如果需要列表返回的value，放在隐藏域，那么指定隐藏域
		width: 162, //指定宽度
		children: [tree] //配置picker内的列表
	});
	picker.render();
	
})
</script>