<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<form class="form-horizontal bui-form-horizontal bui-form bui-form-field-container" method="post" action="<c:url value="/role/copyRole"/>" autocomplete="off" data-plus-as-tab="true">
	<input type="hidden" name="roleIds" value="" id="copyRoleIds"/>
	<div class="sup-content employee-top">
		<div class="clearfix">
			<div class="control-group pull-left">
				<label class="control-label control-label-auto">所属集团或酒店：</label>
				<div class="control-container box-w160 pull-left">
					<select class="choose" data-placeholder="选择集团或酒店" id="copyBelongId">
						<option value="">请选择集团或酒店</option>
						<c:forEach items="${groupHotelList }" var="groupHotel">
							<option value="${groupHotel.id }">${groupHotel.name }</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="control-group pull-left ml10">
				<div class="control-container pull-left box-w160">
					<div class="input-box">
						<input class="control-text input-full" type="text" name="" value="" placeholder="角色名" id="copyRoleName">
					</div>
				</div>
			</div>
			<div class="control-group pull-left ml10">
				<button class="button button-primary button-query" type="button" id="copySelect" data-keyname="N">查询 (F)</button>
			</div>
		</div>
	</div>
	<div class="sub-content">
		<div class="row control-role control-role-copy control-role-employee">
			<div class="side side-left ml10">
				<p>备选择角色</p>
				<div class="clearfix">
					<div class="grid-content grid-content-border" id="gridSelectCopy"></div>
				</div>
			</div>
			<div class="side side-right">
				<p>已选择角色</p>
				<div class="clearfix">
					<div class="grid-content grid-content-border" id="gridSelectedCopy"></div>
				</div>
			</div>
			<div class="action">
				<button class="button button-primary" id="toRightCopy" type="button" onclick="copyInner()">&nbsp;移入 >&nbsp;</button>
				<button class="button button-primary" id="toLeftCopy" type="button" onclick="copyOuter()">&nbsp;< 移出&nbsp;</button>
			</div>
		</div>
	</div>
</form>
<script>
	$(function () {
	    chooseConfig();
	});
</script>
<script>
BUI.use(['bui/grid', 'bui/data', 'bui/overlay', 'bui/form', 'bui/tree'], function(Grid, Data, Overlay, Form, Tree) {
	/* GridSelect */
	var GridSelectCopy = Grid,
        StoreSelectCopy = Data.Store,
        columnsSelectCopy = [
            { title: 'id',dataIndex: 'id', visible : false},
			{ title: '角色名',dataIndex: 'name', elCls: 'text-left', width: '80%', showTip: true },
		];
    var storeSelectCopy = new StoreSelectCopy({
    	url: '<c:url value="/role/findRoleByBelongId"/>',
		autoLoad: true,
		listeners: {
        	load: function(ev) {
        		var selections = gridSelectedCopy.getItems();
        		for (var i = 0; i < selections.length; i++) {
        			var name = selections[i].name;
	        		var nodes = storeSelectCopy.findAll("name",name);
	        		if(nodes != null){
	        			storeSelectCopy.remove(nodes);
	        		}
        		} 
        	}
        }
    }),
	gridSelectCopy = new GridSelectCopy.Grid({
		render: '#gridSelectCopy',
		columns: columnsSelectCopy,
		multipleSelect : true,
		width: '100%',
		//forceFit : true,
		store: storeSelectCopy,
		emptyDataTpl: '<div class="empty-data"><span>暂无数据</span></div>',
		plugins: [Grid.Plugins.CheckSelection, Grid.Plugins.ColumnResize]
	});
    gridSelectCopy.render();

	/* GridSelected */
	var GridSelectedCopy = Grid,
        StoreSelectedCopy = Data.Store,
        columnsSelectedCopy = [
            { title: 'id',dataIndex: 'id', visible : false},
			{ title: '角色名',dataIndex: 'name',sortable:false,elCls: 'text-left', width: '80%', showTip: true },
		];
    var storeSelectedCopy = new StoreSelectedCopy({
      //  data: dataSelectedCopy,
        autoLoad: true,
    }),
	gridSelectedCopy = new GridSelectedCopy.Grid({
		render: '#gridSelectedCopy',
		columns: columnsSelectedCopy,
		multipleSelect : true,
		width: '100%',
		//forceFit : true,
		store: storeSelectedCopy,
		emptyDataTpl: '<div class="empty-data"><span>暂无数据</span></div>',
		plugins: [Grid.Plugins.CheckSelection, Grid.Plugins.ColumnResize]
	});
    gridSelectedCopy.render();
	
	$('body').on('click', '#toRightCopy', function() {
		
		var selections1 = gridSelectCopy.getSelection();
		gridSelectCopy.removeItems(selections1);
		gridSelectedCopy.addItems(selections1);
		gridSelectCopy.get('el').find('.bui-grid-hd.checked').removeClass('checked');
		gridSelectedCopy.get('el').find('.bui-grid-hd.checked').removeClass('checked');
		gridSelectedCopy2 = gridSelectedCopy;
	});
	$('body').on('click', '#toLeftCopy', function() {
		var selections2 = gridSelectedCopy.getSelection();
		gridSelectedCopy.removeItems(selections2);
		gridSelectCopy.addItems(selections2);
		gridSelectCopy.get('el').find('.bui-grid-hd.checked').removeClass('checked');
		gridSelectedCopy.get('el').find('.bui-grid-hd.checked').removeClass('checked');
		gridSelectedCopy2 = gridSelectedCopy;
	});
	gridSelectedCopy2 = gridSelectedCopy;
	gridSelectCopy2 = gridSelectCopy;
	$("#copySelect").click(function(){
		
		var selections = gridSelectedCopy.getItems();
		var idvalue = "";
		for (var i = 0; i < selections.length; i++) {
			idvalue += selections[i].id + ",";
		} 
		var copyRoleName = $("#copyRoleName").val();
		var copyBelongId = $("#copyBelongId").val();
		var params = {
				belongId:copyBelongId,
				roleName:copyRoleName,
				roleIds:idvalue
  		};
		storeSelectCopy.load(params);
	})
	
	
});
</script>