<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<form class="form-horizontal bui-form-horizontal bui-form bui-form-field-container" method="post" action="<c:url value='/role/saveRoleStaffList'/>" autocomplete="off" data-plus-as-tab="true">
	<input type="hidden" id="roleId" value="${role.id }" name="roleId"/>
	<input type="hidden" id="hotelId" value="${role.hotelId }" name="hotelId"/>
	<input type="hidden" id="staffIdx" value="" name="staffIdx"/>
	<div class="sup-content employee-top">
		<div class="row">
			<div class="control-group pull-left ml10">
				<div class="control-container box-w140 pull-left" id="groupSelect">
					<select class="choose hide" data-placeholder="请选择集团和酒店" disabled="disabled">
						<option value="">请选择集团和酒店</option>
						<c:forEach items="${groupHotelList }" var="groupHotel">
							<option value="${groupHotel.id }" <c:if test="${groupHotel.id eq role.belongId}">selected</c:if>>${groupHotel.name }</option>
						</c:forEach>
					</select>
					<span class="control-form-text nowrap input-full" id="groupCode"></span>
				</div>
			</div>
			<div class="control-group pull-left ml20">
				<div class="control-container pull-left box-w160">
					<div class="input-box">
						<input class="control-text input-full" type="text" name="" value="" placeholder="员工名称" id="empName">
					</div>
				</div>
			</div>
			<div class="control-group pull-left ml10">
				<button class="button button-primary button-query" type="button" id="selectEmpBtn" data-keyname="N">查询 (F)</button>
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
<!-- 添加员工 Dialog end  -->
<script>
	$(function () {
	    chooseConfig();
	    $('#groupSelect .chosen-container').hide();
	    $('#groupCode').text($('#groupSelect input').val());
	});
</script>
<script>
BUI.use(['bui/grid', 'bui/data', 'bui/overlay', 'bui/form', 'bui/tree', 'bui/extensions/treepicker', 'bui/tab'], function(Grid, Data, Overlay, Form, Tree, TreePicker, Tab) {
	var Grid = Grid,Store = Data.Store;
	/* GridSelect */
	var GridSelect = Grid,
        StoreSelect = Data.Store,
        columnsSelect = [
            { title: 'id',dataIndex: 'id', visible : false},
			{ title: '帐号',dataIndex: 'account', elCls: 'text-left', width: '25%', showTip: true },
			{ title: '姓名',dataIndex: 'name', elCls: 'text-left', width: '25%', showTip: true },
			{ title: '组织',dataIndex: 'createBy', elCls: 'text-left', width: '50%', showTip: true }
		];
    var storeSelect = new Store({
        /* data: dataSelect,
        autoLoad: true, */
        url : '<c:url value="/role/findStaffListByRoleIdLeft"/>',
        autoLoad: true,
        params:{
			roleId: '${role.id}',
			hotelId:'${role.hotelId}'
		}
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

	/* GridSelected */
	var GridSelected = Grid,
        StoreSelected = Data.Store,
        columnsSelected = [
			{ title: 'id',dataIndex: 'id', visible : false},
			{ title: '帐号',dataIndex: 'account', elCls: 'text-left', width: '25%', showTip: true },
			{ title: '姓名',dataIndex: 'name', elCls: 'text-left', width: '25%', showTip: true },
			{ title: '组织',dataIndex: 'createBy', elCls: 'text-left', width: '50%', showTip: true }
		];
    var storeSelected = new Store({
       // data: dataSelected,
       url : '<c:url value="/role/getRoleAddStaffRightList"/>',
        autoLoad: true,
        params:{
			roleId: '${role.id}',
			hotelId:'${role.hotelId}'
		}
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
    gridSelected2 = gridSelected
    gridSelect2 = gridSelect;
    $("#selectEmpBtn").click(function(){
    	var selections = gridSelected.getItems();
		var idvalue = "";
		for (var i = 0; i < selections.length; i++) {
			idvalue += selections[i].id + ",";
		} 
		var roleId = $("#roleId").val();
		var hotelId = $("#hotelId").val();
		var condition = $("#empName").val();
  		var params = {
  				roleId:roleId,
  				hotelId:hotelId,
  	  			staffIds:idvalue,
  	  			condition:condition
  		};
  		storeSelect.load(params);
    })
	
	
	$('body').on('click', '#toRight', function() {
		var selections1 = gridSelect.getSelection();
		gridSelect.removeItems(selections1);
		gridSelected.addItems(selections1);
		gridSelected2 = gridSelected;
		gridSelect.get('el').find('.bui-grid-hd.checked').removeClass('checked');
		gridSelected.get('el').find('.bui-grid-hd.checked').removeClass('checked');
	});
	$('body').on('click', '#toLeft', function() {
		var selections2 = gridSelected.getSelection();
		gridSelected.removeItems(selections2);
		gridSelect.addItems(selections2);
		gridSelected2 = gridSelected;
		gridSelect.get('el').find('.bui-grid-hd.checked').removeClass('checked');
		gridSelected.get('el').find('.bui-grid-hd.checked').removeClass('checked');
	});
		
});
</script>