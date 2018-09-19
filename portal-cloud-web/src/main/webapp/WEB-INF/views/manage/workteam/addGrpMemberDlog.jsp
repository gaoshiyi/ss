<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<form class="form-horizontal bui-form-horizontal bui-form bui-form-field-container" method="post" action="" autocomplete="off" data-plus-as-tab="true">
	<div class="sup-content employee-top">
		<div class="clearfix">
			<div class="control-group pull-left">
				<div class="control-container box-w160 pull-left">
					<!-- 工作圈的Id -->
					<input type="hidden" value="${teamId}" id="teamId">
					<!-- 角色的Id -->
					<input type="hidden" value="${roleId}" id="roleId">
					<div class="input-box has-multi-select">
						<c:choose>
							<c:when test="${empty roleId}">
								<input id="hideOpt" type="hidden" name="" value="${orgId}">
								<input class="control-text input-full multi-select" id="showOpt" type="text" name="" value="" placeholder="选择组织" readonly>
							</c:when>
							<c:otherwise>
								<select class="choose" id="roleOrg" disabled="disabled">
									<c:forEach items="${hotelInfoMap}" var="val">
										<option value="${val.key}">${val.value}</option>
									</c:forEach>
								</select>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</div>
			<div class="control-group pull-left ml10">
				<div class="control-container pull-left">
					<input class="control-text input-w160" type="text" name="" value="" placeholder="员工名称" id="empName">
				</div>
			</div>
			<div class="control-group pull-left ml10">
				<button class="button button-primary button-query" type="button" id="btnOrg" data-keyname="N">查询 (F)</button>
			</div>
		</div>
	</div>

	<div class="sub-content">
		<div class="row control-role control-role-employee">
			<div class="side side-left ml10">
				<p>备选择员工</p>
				<div class="clearfix">
					<div class="grid-content grid-content-border" id="gridSelect"></div>
				</div>
			</div>
			<div class="side side-right">
				<p>已选择员工</p>
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
</form>
<script>
	$(function () {
	    chooseConfig();
	});
</script>
<script>
BUI.use(['bui/grid', 'bui/data', 'bui/overlay', 'bui/form', 'bui/tree', 'bui/tab','bui/extensions/treepicker'], function(Grid, Data, Overlay, Form, Tree, Tab, TreePicker) {
	/*组织树*/
	var store = new Data.TreeStore({
        url : '<c:url value="/workTeam/treeOrg"/>',
        autoLoad : true
      }),
    tree = new Tree.TreeList({
	      store : store,
	      elCls: 'tree-noicon',
	      checkType: 'none'
    });
  	var picker = new TreePicker({
	      trigger : '#showOpt',  
	      valueField : '#hideOpt', //如果需要列表返回的value，放在隐藏域，那么指定隐藏域
	      width: 164,  //指定宽度
	      children : [tree] //配置picker内的列表
    });
  	picker.render();
  	var treeItem;
  	tree.on('itemclick',function(ev){
  		treeItem = ev.item;
	});
  	store.on('load',function(){
		var value = '${orgId}';
		picker.setSelectedValue(value);
    });
	/* GridSelect */
	var GridSelect = Grid,
    StoreSelect = Data.Store,
    columnsSelect = [
			{ title: '员工id',dataIndex: 'staffId', visible : false},
			{ title: '帐号',dataIndex: 'createBy', elCls: 'text-left', width: '25%', showTip: true },
			{ title: '姓名',dataIndex: 'chName', elCls: 'text-left', width: '20%', showTip: true },
			{ title: '组织',dataIndex: 'orgInfo', elCls: 'text-left', width: '50%', showTip: true }
	];
	var teamIdVal = $('#teamId').val();
	var roleIdVal = $('#roleId').val();
	var roleOrgVal = $('#roleOrg').val();
    var nodeTypeIni = '';
    var orgIdIni = '';
    if(roleOrgVal != null && roleOrgVal != ''){
		nodeTypeIni = 0;
		orgIdIni = roleOrgVal;
	}
    var storeSelect = new StoreSelect({
        url : '<c:url value="/workTeam/selGrpMemberAll"/>',
        params : {
        	teamId : teamIdVal,
        	roleId : roleIdVal,
        	orgId: orgIdIni,
           	nodeType:nodeTypeIni
        },
        autoLoad: true,
    }),
	gridSelect = new Grid.Grid({
		render: '#gridSelect',
		columns: columnsSelect,
		multipleSelect : true,
		width: '100%',
		//forceFit : true,
		store: storeSelect,
		emptyDataTpl: '<div class="empty-data"><span>暂无数据</div>',
		plugins: [Grid.Plugins.CheckSelection, Grid.Plugins.ColumnResize]
	});
    gridSelect.render();
    gridLeft = gridSelect;
    
	/* GridSelected */
	var GridSelected = Grid,
    StoreSelected = Data.Store,
    columnsSelected = [
		{ title: '员工id',dataIndex: 'staffId', visible : false},
		{ title: '帐号',dataIndex: 'createBy', elCls: 'text-left', width: '25%', showTip: true },
		{ title: '姓名',dataIndex: 'chName', elCls: 'text-left', width: '20%', showTip: true },
		{ title: '组织',dataIndex: 'orgInfo', elCls: 'text-left', width: '50%', showTip: true }
	];
    var storeSelected = new StoreSelect({
    	url : '<c:url value="/workTeam/selGrpMember"/>',
    	params : {
    		teamId : teamIdVal,
        	roleId : roleIdVal,	
        	hotelId: '${hotelId}'
    	},
    	autoLoad: true,
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
	
    $('body').on('click', '#toRight', function() {
		var selections1 = gridSelect.getSelection();
		gridSelect.removeItems(selections1);
		gridSelected.addItems(selections1);
		gridSelect.get('el').find('.bui-grid-hd.checked').removeClass('checked');
		gridSelected.get('el').find('.bui-grid-hd.checked').removeClass('checked');
	});
	$('body').on('click', '#toLeft', function() {
		var selections2 = gridSelected.getSelection();
		gridSelected.removeItems(selections2);
		gridSelect.addItems(selections2);
		gridSelect.get('el').find('.bui-grid-hd.checked').removeClass('checked');
		gridSelected.get('el').find('.bui-grid-hd.checked').removeClass('checked');
	});
//查询
	$('#btnOrg').on('click',function(){
		var records = gridSelected.getItems();
		var selStaffIds = "";
		if(records != null && records.length > 0){
			for(var i = 0 ; i < records.length ; i++){
				selStaffIds += records[i].staffId + '+';		
			}			
		}
		var orgId;
		var nodeType;
		if(treeItem == null){
			var roleOrgVal = $('#roleOrg').val();
			if(roleOrgVal != null && roleOrgVal != ''){
				nodeType = 0;
				orgId = roleOrgVal;
			}else{
				nodeType = "";
				orgId = "";				
			}
		}else{
			orgId = treeItem.id;
			nodeType = treeItem.nodeType;
		}
		var empName = $('#empName').val();
		var params = {
			'orgId': orgId,
           	'nodeType':nodeType,
           	'empName':empName,
           	'teamId' : teamIdVal,
           	'roleId' : roleIdVal,
           	'selStaffIds' : selStaffIds
		}
		storeSelect.load(params);
	})
})
</script>