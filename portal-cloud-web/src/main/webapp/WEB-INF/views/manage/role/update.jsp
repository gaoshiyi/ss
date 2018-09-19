<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!-- 新建 Dialog start  -->
<form class="form-horizontal bui-form-horizontal bui-form bui-form-field-container" method="post" action="<c:url value="/role/updateRole"/>" autocomplete="off" data-plus-as-tab="true">
	
	<div class="sup-content mb20">
		<p>基本资料</p>
		<div class="clearfix">
			<div class="control-group pull-left">
				<label class="control-label control-label-auto"><s>*</s>名称：</label>
				<div class="control-container box-w140 pull-left">
					<div class="input-box">
						<input class="control-text input-full" type="text" name="chinaName" value="${role.name }" id="chinaName" data-rules="{required:true}">
						<input type="hidden" value="${role.id }" id="roleId2" name="roleId">
						<input type="hidden" id="permissionIds" value="" name="permissionIds"/>
						<input type="hidden" value="${role.belongId }" name="belongId2"/>
					</div>
				</div>
			</div>
			<div class="control-group pull-left ml20">
				<label class="control-label control-label-auto">外文名称：</label>
				<div class="control-container box-w150 pull-left">
					<div class="input-box">
						<input class="control-text input-full" type="text" name="englishName" value="${role.englishName }" id="englishName">
					</div>
				</div>
			</div>
			<div class="control-group pull-left ml20">
				<label class="control-label control-label-auto">折扣率：</label>
				<div class="control-container box-w60 pull-left">
					<div class="input-box">
						<input class="control-text input-full" type="number" name="disaccountRate"
						 min="0" max="100" maxlength="3" value="${role.disaccountRate == null? 100:role.disaccountRate }" id="disaccountRate">
					</div>
				</div>
				<label class="control-label control-label-auto">&nbsp;%</label>
			</div>
			<div class="control-group pull-left ml20">
				<label class="control-label control-label-auto"><s>*</s>所属集团或酒店：</label>
				<div class="control-container box-w150 pull-left" id="belongSelect">
					<select class="choose hide" data-placeholder="请选择集团或酒店" id="belongId2"  disabled="disabled">
						<option value="">请选择集团或酒店</option>
						<c:forEach items="${groupHotelList }" var="groupHotel">
							<option value="${groupHotel.id }" <c:if test="${groupHotel.id eq role.belongId}">selected</c:if>>${groupHotel.name }</option>
						</c:forEach>
					</select>
					<span class="control-form-text nowrap input-full" id="belongId2Code"></span>
				</div>
			</div>
		</div>
	</div>
	
	<div class="sub-content">
		<p>角色授权</p>
		<div class="clearfix">
			<div class="row control-indent control-role">
				<div class="side side-left ml10">
					<div class="tree-list tree-noicon" id="treeList" onclick="treeClick()"></div>
				</div>
				<div class="side side-right ml20">
					<div class="grid-content grid-content-border" id="gridTree" onclick="treeClick()"></div>
				</div>
			</div>
		</div>
	</div>
</form>
<!-- 新建 Dialog end  -->
<script>
	$(function () {
	    chooseConfig();
	    $('#belongSelect .chosen-container').hide();
	    $('#belongId2Code').text($('#belongSelect input').val());
	});
</script>
<script>
BUI.use(['bui/grid', 'bui/data', 'bui/overlay', 'bui/form', 'bui/tree'], function(Grid, Data, Overlay, Form, Tree) {
	var treeStore = new Data.TreeStore({
		url: '<c:url value="/role/perms"/>',
		params: {flag: 'update',roleId: "${role.id}"},
		autoLoad: true
	});
    var tree = new Tree.TreeList({
        render: '#treeList',
        store : treeStore,
        checkType: 'all', //checkType:勾选模式，提供了4中，all,onlyLeaf,none,custom
        showLine: true 
    });
    tree.render();
    
	tree.on('checkedchange', function(ev) {
		var node = ev.node;
		if (!node.leaf){
			return;
		}
	
		var name = loopParent(node);
		
		if (node.checked) { //勾选上
			storeTree.add({
				id: node.id,
				name: name
			});
		} else { //取消勾选
			storeTree.remove(ev.node.id, function(obj1,obj2){
				return obj1 == obj2.id;
			});
		}
	});
	
	/* GridTree */
	var GridTree = Grid,
        StoreTree = Data.Store,
        columnsTree = [
			{ title: '操作',dataIndex: 'id', width: 15, sortable: false, renderer : function(value){ 
					return '<a class="grid-icon grid-icon-del btn-del" title="删除" href="javascript:;" onclick="delRow(\''+value+'\',this);"></a>'
				}
			},
			{ title: '权限路径',dataIndex: 'name', elCls: 'text-left', width: 125, showTip: true }
		];
    var storeTree = new StoreTree({
        url: '<c:url value="/role/loadcheckedPermissionList"/>',
		params: {flag: 'update',roleId: "${role.id}"},
        autoLoad: true,
    }),
	gridTree = new GridTree.Grid({
		render: '#gridTree',
		columns: columnsTree,
		width: '100%',
		//forceFit : true,
		store: storeTree,
		emptyDataTpl: '<div class="empty-data"><span>暂无数据</span></div>',
		plugins: [Grid.Plugins.ColumnResize]
	});
    gridTree.render();
    gridTree2 = gridTree;

	//删除权限
	window.delRow = function(id,obj) {
		var index = $(obj).parents('tr').index() - 1;
		var item = gridTree.getItemAt(index);
		var node = tree.findNode(id);
		storeTree.remove(item);
		tree.setNodeChecked(node,false);
	}	
	
});




</script>