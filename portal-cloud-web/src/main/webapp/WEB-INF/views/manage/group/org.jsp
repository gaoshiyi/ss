<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> 
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="../../common/header-2.0.0.jsp"></jsp:include>
<link href="<c:url value="/resources/css/management.css"></c:url>" rel="stylesheet">

<title>组织架构</title>
</head>

<body>
	
	<shiro:hasPermission name="/group/group">
		<input type="hidden" id="hotelInfoVisible" value="true">
	</shiro:hasPermission>
	<shiro:hasPermission name="/group/hotel">
		<input type="hidden" id="hotelVisible" value="true">
	</shiro:hasPermission>
	<shiro:hasPermission name="/department/index">
		<input type="hidden" id="dptVisible" value="true">
	</shiro:hasPermission>
	<shiro:hasPermission name="/post/index">
		<input type="hidden" id="jobVisible" value="true">
	</shiro:hasPermission>
	<shiro:hasPermission name="/employee/orgTab">
		<input type="hidden" id="employeeVisible" value="true">
	</shiro:hasPermission>
	<div class="layout layout-fixed clearfix">
	
		<div class="content organization">
			<div class="side side-tree">
				<div class="side-content">
					<div class="control-group bar-search">
						<div class="control-container clearfix">
							<input class="control-text search-text SearchInput"  id="queryHotelName" type="text" placeholder="请输入酒店名称" id="queryHotelName" aria-disabled="false" aria-pressed="false">
							<button class="button button-primary button-query ml20" type="button" id="queryHotelBtn" onclick="queryHotel();" data-keyname="F">查询 (F)</button>
						</div>
					</div>
					<div class="tree-list tree-noicon" id="treeList"></div>
				</div>
			</div>
			<div class="side side-main">
				<div class="side-content">
					<div id="tab" class="tab-organization">
						<ul class="breadcrumb hide" id="breadcrumb"></ul>
					</div>
					<div id="panel" class="panel-organization"></div>
				</div>
			</div>
		</div>
	</div>
	
	<script type="text/javascript">
		$(function () {
			$('#treeList').perfectScrollbar();
			$(window).on('resize', function() {
				$('#treeList').perfectScrollbar('update');
			})
		});
	
		var OrgTree,orgId,nodeType,tab,OrgtreeStore;
		var currentHotelId = hiddenHtlId;
		nodeType = hiddenGrpId == hiddenHtlId ? 0 : 1;
		var hotelInfoVisible = $('#hotelInfoVisible').val() === 'true'? true:false;
		var hotelVisible = $('#hotelVisible').val() === 'true' && hiddenGrpId == hiddenHtlId ? true:false;
		var dptVisible = $('#dptVisible').val() === 'true'? true:false;
		var jobVisible = $('#jobVisible').val() === 'true'? true:false;
		var employeeVisible = $('#employeeVisible').val() === 'true'? true:false;
		var potence = { hotelInfoVisible: hotelInfoVisible, hotelVisible: hotelVisible, 
				dptVisible: dptVisible, jobVisible: jobVisible, employeeVisible: employeeVisible };
		
		orgId = hiddenHtlId;
		var selectTab;
		var title = "酒店信息";
		if(hiddenHtlId == hiddenGrpId && nodeType == 0){
			title = "集团信息";
		}
		
		BUI.use([ 'bui/grid', 'bui/data', 'bui/overlay', 'bui/form','bui/tree', 'bui/extensions/treepicker', 'bui/tab', 'bui/mask'], function(Grid, Data, Overlay, Form, Tree, TreePicker, Tab, Mask) {
			var loadMask = new Mask.LoadMask({
			    el: '#mainContent',
			    msg: 'Loading...'
			});
			var treeStore = new Data.TreeStore({
				url : '<c:url value='/group/findGroupList'/>',
				autoLoad : true
			});
			OrgtreeStore = treeStore;
			var tree = new Tree.TreeList({
				render : '#treeList',
				store : treeStore,
				checkType : 'none', //checkType:勾选模式，提供了4中，all,onlyLeaf,none,custom
				showLine : false,
				listeners : {
					itemrendered: function() {
						if (typeof(freq) == 'undefined') {
							tree.setSelected(tree.getFirstItem());
							freq = 1;
						}
						$('#treeList').perfectScrollbar('update');
					}
				}
			});
			tree.render();
			OrgTree = tree;
			tree.on('collapsed expanded',function(ev){
				$('#treeList').perfectScrollbar('update');
			});
			tree.on('itemclick', function(ev) {
				item = ev.item;
				orgId = item.id;
				nodeType = item.nodeType;
				var org = loopParent(item);
				currentHotelId = loopHotelId(item,nodeType);
				loopBreadcrumb(item);
				var tabChildren = tab.get('children');
				/* 重新设置tab的url参数 */
				$.each(tabChildren,function(i,n){
					var loader = n.get('loader');
					var lastParams = loader.get('lastParams');
					if (typeof(lastParams.orgId) != 'undefined') {
						lastParams.orgId = orgId;
					}
					if (typeof(lastParams.nodeType) != 'undefined') {
						lastParams.nodeType = nodeType;
					}
				});
				/** 
				 * 集团（orgType=0）：展示所有
				 * 酒店（orgType=1）：集团详情titile变成酒店详情，不展示酒店列表，其他都展示
				 * 部门（orgType=2）：展示部门，职位，员工
				 * 职位（orgType=3）：员工
				 * 以上为权限都有的情况
				 **/
				var activedItem = tab.getSelected();
				switch (nodeType) {
					case 0 : //集团
						if (hotelInfoVisible) {
							tab.getItemAt(0).set('title','集团详情');
							tab.getItemAt(0).show();
						}
						if (hotelVisible) {
							tab.getItemAt(1).show();
						}
						if (dptVisible) {
							tab.getItemAt(2).show();
						}
						if (jobVisible) {
							tab.getItemAt(3).show();
						}
						if (employeeVisible) {
							tab.getItemAt(4).show();
						}
						var itemIndex = $('#tab ul li:visible').first().index();
						if (activedItem != tab.getItemAt(0)) {
							if (hotelInfoVisible) {
								tab.setSelected(tab.getItemAt(0));
							} else {
								tab.setSelected(tab.getItemAt(itemIndex));
							}
						}
						break;
					case 1 : //酒店
						if (hotelInfoVisible) {
							tab.getItemAt(0).set('title','酒店详情');
							tab.getItemAt(0).show();
						}
						
						tab.getItemAt(1).hide();
						tab.getItemAt(1).get('panel').hide();
						
						if (dptVisible) {
							tab.getItemAt(2).show();
						}
						if (jobVisible) {
							tab.getItemAt(3).show();
						}
						if (employeeVisible) {
							tab.getItemAt(4).show();
						}
						var itemIndex = $('#tab ul li:visible').first().index();
						if (activedItem != tab.getItemAt(0)) {
							if (hotelInfoVisible) {
								tab.setSelected(tab.getItemAt(0));
							} else {
								tab.setSelected(tab.getItemAt(itemIndex));
							}
						}
						break;
					case 2 : //部门
						tab.getItemAt(0).set('title','酒店详情');
						tab.getItemAt(0).hide();
						tab.getItemAt(0).get('panel').hide();
						
						tab.getItemAt(1).hide();
						tab.getItemAt(1).get('panel').hide();
						
						if (dptVisible) {
							tab.getItemAt(2).show();
						}
						if (jobVisible) {
							tab.getItemAt(3).show();
						}
						if (employeeVisible) {
							tab.getItemAt(4).show();
						}
						var itemIndex = $('#tab ul li:visible').first().index();
						if (activedItem != tab.getItemAt(3)) {
							if (hotelInfoVisible) {
								tab.setSelected(tab.getItemAt(3));
							} else {
								tab.setSelected(tab.getItemAt(itemIndex));
							}
						}
						break;
					case 3 : //职位
						tab.getItemAt(0).set('title','酒店详情');
						tab.getItemAt(0).hide();
						tab.getItemAt(0).get('panel').hide();
						
						tab.getItemAt(1).hide();
						tab.getItemAt(1).get('panel').hide();
						
						tab.getItemAt(2).hide();
						tab.getItemAt(2).get('panel').hide();
						
						tab.getItemAt(3).hide();
						tab.getItemAt(3).get('panel').hide();
						
						if (employeeVisible) {
							tab.getItemAt(4).show();
						}
						var itemIndex = $('#tab ul li:visible').first().index();
						if (activedItem != tab.getItemAt(4)) {
							if (hotelInfoVisible) {
								tab.setSelected(tab.getItemAt(4));
							} else {
								tab.setSelected(tab.getItemAt(itemIndex));
							}
						}
						break;
				}
				
				/* 当前选中的tab */
				var currItem = tab.getSelected();
				/* 刷新当前选中的tab */
				currItem.get('loader').load();
			});
			tab = new Tab.TabPanel({
				render : '#tab',
				elCls : 'nav-tabs',
				panelContainer : '#panel', //如果内部有容器，那么会跟标签项一一对应，如果没有会自动生成
				autoRender : true,
				children : [
				{
					title : title,
					value : '1',
					visible: potence.hotelInfoVisible,
					loader : {
						url : '<c:url value="/group/group"/>',
						params : {
							orgId : orgId
						}
					}
				}, {
					title : '酒店列表',
					value : '2',
					visible: potence.hotelVisible,
					loader : {
						url : '<c:url value="/group/hotel"/>',
						params : {
							orgId : orgId
						}
					}
				}, {
					title : '部门列表',
					value : '3',
					visible: potence.dptVisible,
					loader : {
						url : '<c:url value="/department/index"/>',
						params : {
							orgId : orgId,
							nodeType: nodeType						
						}
					}
				} ,{
					title : '职位列表',
					value : '4',
					visible: potence.jobVisible,
					loader : {
						url : '<c:url value="/post/index"/>',
						params : {
							orgId : orgId,
							nodeType: nodeType
						}
					}
				},{
					title : '员工列表',
					value : '5',
					visible: potence.employeeVisible,
					loader : {
						url : '<c:url value="/employee/orgTab"/>',
						params : {
							orgId : orgId,
							nodeType: nodeType
						}
					}
				}
				]
			});
			var itemIndex = $('#tab ul li:visible').first().index();
			tab.setSelected(tab.getItemAt(itemIndex));
			tab.on('selectedchange', function(ev) {
				var item = ev.item;
				var value = item.get('value');
	    		if (value != 1 && value != 2) {
	    			if(tab.isItemSelected(item)){
	    				if(item.get('loader').get('isloaded')){
	    					item.get('loader').load();
	    				}
	    				item.get('loader').set('isloaded',true);
	    			}
	    		}
			});
			selectTab = tab;
		});
		
		function loopParent(item){
			var orgPath = item.text;
			var parent = item.parent;
			if(parent != null && parent.text != ''){
				orgPath = parent.text + " / "+ orgPath;
				if(parent.parent != null && parent.parent.text != ''){
					orgPath = loopParent(parent.parent) + " / " + orgPath;
				}
			}
			return orgPath;
		}
		
		
		function loopHotelId(item,nodeType){
			if(nodeType > 1){
				var parent = item.parent;
				nodeType = parent.nodeType;
				return loopHotelId(parent,nodeType);
			}else{
				return item.id;
			}
		}
		
		function loopBreadcrumb(item){
			var breadcrumb = '<li><span>' + item.text + '</span></li>';
			var parent = item.parent;
			if(parent != null && parent.text != ''){
				breadcrumb = '<li><span>' + parent.text + '</span><span class="divider">></span></li>' + breadcrumb;
				if(parent.parent != null && parent.parent.text != ''){
					breadcrumb = '<li><span>' + loopBreadcrumb(parent.parent) + '</span><span class="divider">></span></li>' + breadcrumb;
				}
			}
			$('#breadcrumb').show().html(breadcrumb);
			return breadcrumb;
		}
		
		
		function queryHotel(){
			var hotelName = $("#queryHotelName").val().replace(/(^\s*)|(\s*$)/g, "");
			if(hotelName != ''){
				var node = OrgTree.get("store").findNodeBy(function(node){
					var name = node.text;
					var nodeType = node.nodeType;
					if(name.indexOf(hotelName) != -1 && nodeType == 1){
						return true;
					}
					return false;
				});
				if(node){
					OrgTree.collapseAll();
					OrgTree.expandNode(node);
					OrgTree.setSelection(node);
				}
			}
		}
		
		function findHotelId(orgId){
			var node = OrgTree.findNode(orgId);
			if(node != null){
				var nodeType = node.nodeType;
				if(nodeType == 0 || nodeType == 1){
					return orgId;
				}
				var pid = node.parentId;
				var parentNode =  OrgTree.findNode(pid);
				if(parentNode != null){
					var pNodeType = parentNode.nodeType;
					if(pNodeType != 0 && pNodeType != 1){
						return findHotelId(pid);
					}else{
						return pid;
					}
				}
			}
		}
	</script>
	

</body>
</html>