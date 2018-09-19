<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<form class="form-horizontal bui-form-horizontal bui-form bui-form-field-container" method="post" action="" autocomplete="off" data-plus-as-tab="true">
	<!-- 工作酒店 start -->
	<div class="tab-content">
		<div class="row control-indent control-employee control-employee-hotel form-horizontal">
			<div class="side side-left ml10">
				<div class="tree-list tree-noicon" id="treeListWorkHotel"></div>
			</div>
			<div class="side side-right ml10">
				<div class="grid-content grid-content-border" id="gridTree"></div>
			</div>
		</div>
	</div>
	<!-- 工作酒店 end -->		
</form>

<script>
BUI.use(['bui/grid', 'bui/data', 'bui/overlay', 'bui/form', 'bui/tree','bui/extensions/treepicker', 'bui/tab'], function(Grid, Data, Overlay, Form, Tree, TreePicker, Tab) {
	oldDecide = decide;
	decide = false;
	var staffIdVal = $('#staffId').val();
	storeDptWorkHotel = new Data.TreeStore({
		url : '<c:url value="/employee/treeOrg"/>',
		params : {
			staffId : staffIdVal,
			orgLevel:0
		},
		autoLoad: true,
		listeners: {
        	load: function(ev) {
        		var item = ev.node;
        		removeNoPostNode(item);
        	}
        }
	});
	var treeWorkHotel = new Tree.TreeList({
		store: storeDptWorkHotel,
		render: '#treeListWorkHotel',
		checkType: 'custom', //checkType:勾选模式，提供了4中，all,onlyLeaf,none,custom
		cascadeCheckd: false,
		showLine: false 
	});
	treeWorkHotel.render();
	treeWorkHotel.on('checkedchange', function(ev) {
		var node = ev.node;
		var postId = $('#theJob').val();
    	var group = loopParent(node);
		if (node.checked) { //勾选上
			decide = true;
			storeTreeWorkHotel.add({
				id: node.id,
				defaultPostId: node.id,
				orgInfo: group,
			});
		} else { //取消勾选
			decide = true;
			storeTreeWorkHotel.remove(ev.node.id, function(obj1,obj2){
				return obj1 == obj2.defaultPostId;
			});
		}
	});
	
	/* GridTree */
	var GridTreeWorkHotel = Grid,
    StoreTreeWorkHotel = Data.Store,
    columnsTreeWorkHotel = [
		{ title: '操作',dataIndex: 'defaultPostId', width: 40, sortable: false, renderer : function(value,obj){
				return '<a class="grid-icon grid-icon-del btn-del" title="删除" href="javascript:;" onclick="delRowHto(\''+value+'\',\''+obj.defaultPostId+'\');"></a>'
			}
		},
		{ title: '组织',dataIndex: 'orgInfo', elCls: 'text-left', width: 300, showTip: true },
	];
    var storeTreeWorkHotel = new StoreTreeWorkHotel({
    	url : '<c:url value="/employee/orgGrid"/>',
    	params : {'staffId' : staffIdVal},
        autoLoad: true,
    }),
    gridTreeWorkHotel = new Grid.Grid({
		render: '#gridTree',
		columns: columnsTreeWorkHotel,
		width: '100%',
		//forceFit : true,
		store: storeTreeWorkHotel,
		idField: 'defaultPostId',
		emptyDataTpl: '<div class="empty-data"><span>暂无数据</span></div>',
		plugins: [Grid.Plugins.ColumnResize]
	});
    gridTreeWorkHotel.render();
    storeWorkHotel = storeTreeWorkHotel;
    gridWorkHotel = gridTreeWorkHotel;
    
    window.delRowHto = function(id,delPostId) {
    	decide = true;
    	var postId = $('#theJob').val();
    	if(delPostId == postId){
    		BUI.Message.Alert('不能删除默认工作酒店信息!','error');
    		return;
    	}
		var item = gridTreeWorkHotel.getItem(id);
		var node = treeWorkHotel.findNode(delPostId);
		storeTreeWorkHotel.remove(item);
		treeWorkHotel.setNodeChecked(node,false);
	}
})


function removeNoPostNode(node){
	var data = node;
	var isDel = false;
	var nodes = data.children;
	for(var i=0; i<nodes.length; i++){
		var child = nodes[i];
		var nodeType = child.nodeType;
		if(nodeType != 3){
			 var children = child.children;
			 if(children == null || children.length == 0){
				 storeDptWorkHotel.remove(child);
				 return true;
			 }else{
				 isDel = removeNoPostNode(child);
				 if(child.children == null || children.length == 0){
					 storeDptWorkHotel.remove(child);
					 return false;
				 }
			 }
		}
	}
}


function loopParent(item){
	var orgPath = item.text;
	var parent = item.parent;
	var nodeType = item.nodeType;
	if(parent != null && parent.text != '' && nodeType != 0 && nodeType != 1 ){
		orgPath = parent.text + " / "+ orgPath;
		if(parent.parent != null && parent.parent.text != '' ){
			orgPath = loopParent(parent.parent) + " / " + orgPath;
		}
	}
	return orgPath;
}



</script>